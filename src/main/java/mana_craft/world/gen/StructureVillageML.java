package mana_craft.world.gen;

import mana_craft.block.ManaCraftBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;
import java.util.Random;

import static net.minecraft.world.gen.structure.StructureVillagePieces.*;
import static net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;

public class StructureVillageML extends Village {
    @SuppressWarnings("unused")
    public StructureVillageML() {}
    public StructureVillageML(Start start, int type, StructureBoundingBox box, EnumFacing facing) {
        super(start, type);
        setCoordBaseMode(facing);
        boundingBox = box;
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox box) {
        if (averageGroundLvl < 0) {
            averageGroundLvl = getAverageGroundLevel(worldIn, box);
            if(averageGroundLvl < 0)
                return true;
            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 4 - 1, 0);
        }
        IBlockState fence = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        fillWithAir(worldIn, box, 0, 0, 0, 2, 3, 1);
        setBlockState(worldIn, fence, 1, 0, 0, boundingBox);
        setBlockState(worldIn, fence, 1, 1, 0, boundingBox);
        setBlockState(worldIn, fence, 1, 2, 0, boundingBox);
        setBlockState(worldIn, ManaCraftBlocks.manaLantern.getDefaultState(), 1, 3, 0, boundingBox);
        return true;
    }

    public static class Handler implements IVillageCreationHandler {
        @Override
        public PieceWeight getVillagePieceWeight(Random random, int size) {
            return new PieceWeight(StructureVillageML.class, 2, MathHelper.getInt(random, 1 + size, 3 + size * 2));
        }

        @Override
        public Class<?> getComponentClass() {
            return StructureVillageML.class;
        }

        @Override
        public Village buildComponent(PieceWeight villagePiece, Start startPiece, List<StructureComponent> pieces, Random random, int x, int y, int z, EnumFacing facing, int size) {
            StructureBoundingBox box = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 3, 4, 2, facing);
            //noinspection ConstantConditions <-stupid idea thinks it always false
            return canVillageGoDeeper(box) && StructureComponent.findIntersecting(pieces, box) == null ? new StructureVillageML(startPiece, size,  box, facing) : null;
        }
    }
}
