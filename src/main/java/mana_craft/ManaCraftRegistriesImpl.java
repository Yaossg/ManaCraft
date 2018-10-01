package mana_craft;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mana_craft.api.registry.IMBFuel;
import mana_craft.api.registry.IMPRecipe;
import mana_craft.api.registry.ManaCraftRegistries;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ManaCraftRegistriesImpl extends ManaCraftRegistries {
    public static final ManaCraftRegistriesImpl INSTANCE = new ManaCraftRegistriesImpl();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public List<Path> walk(Path value) {
        try {
            return Files.walk(value)
                    .filter(path -> "json".equals(
                            FilenameUtils.getExtension(path.getFileName().toString())))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public void loadAll() {
        pathRecipe.forEach((modid, path) -> loadEntries(modid, walk(path), IMPRecipe::parse, this::addRecipe));
        ManaCraft.logger.info("loaded {} recipes", recipes.size());
        pathFuel.forEach((modid, path) -> loadEntries(modid, walk(path), IMBFuel::parse, this::addFuel));
        ManaCraft.logger.info("loaded {} fuels", fuels.size());
    }

    @Override
    public <T> void loadEntries(String modid, List<Path> paths, BiFunction<JsonContext, JsonObject, T> parser, BiConsumer<T, Path> consumer) {
        if(paths.isEmpty()) return;
        JsonContext context = new JsonContext(modid);
        for (Path path : paths) {
            if("_constants.json".equals(path.getFileName().toString())) {
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    JsonObject[] json = JsonUtils.fromJson(GSON, reader, JsonObject[].class);
                    ReflectionHelper.findMethod(JsonContext.class, "loadConstants", "loadConstants", JsonObject[].class).invoke(context, (Object) json);
                } catch (Exception e) {
                    ManaCraft.logger.error("Unexpected Exception: ", e);
                }
            }
        }
        paths.removeIf(path -> path.getFileName().toString().startsWith("_"));
        paths.forEach(path -> {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
                consumer.accept(parser.apply(context, json), path);
            } catch (Exception e) {
                ManaCraft.logger.error("Unexpected Exception: ", e);
            }
        });
    }
}
