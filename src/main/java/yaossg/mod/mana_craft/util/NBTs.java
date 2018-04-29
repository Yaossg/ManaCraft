package yaossg.mod.mana_craft.util;

import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.*;

import javax.annotation.Nonnull;
import java.util.Map;

public class NBTs {
    @Nonnull public static NBTTagByte of(byte arg) {
        return new NBTTagByte(arg);
    }
    @Nonnull public static NBTTagShort of(short arg) {
        return new NBTTagShort(arg);
    }
    @Nonnull public static NBTTagInt of(int arg) {
        return new NBTTagInt(arg);
    }
    @Nonnull public static NBTTagLong of(long arg) {
        return new NBTTagLong(arg);
    }
    @Nonnull public static NBTTagFloat of(float arg) {
        return new NBTTagFloat(arg);
    }
    @Nonnull public static NBTTagDouble of(double arg) {
        return new NBTTagDouble(arg);
    }
    @Nonnull public static NBTTagString of(String arg) {
        return new NBTTagString(arg);
    }
    @Nonnull public static NBTTagByteArray of(byte[] arg) {
        return new NBTTagByteArray(arg);
    }
    @Nonnull public static NBTTagIntArray of(int[] arg) {
        return new NBTTagIntArray(arg);
    }
    @Nonnull public static NBTTagLongArray of(long[] arg) {
        return new NBTTagLongArray(arg);
    }
    @Nonnull public static NBTTagByteArray array(byte... arg) {
        return of(arg);
    }
    @Nonnull public static NBTTagIntArray array(int... arg) {
        return of(arg);
    }
    @Nonnull public static NBTTagLongArray array(long... arg) {
        return of(arg);
    }

    @Nonnull public static NBTTagList of(@Nonnull Iterable<NBTBase> arg) {
        NBTTagList list = new NBTTagList();
        for(NBTBase each : arg)
            list.appendTag(each);
        return list;
    }
    @Nonnull public static NBTTagList ofStrings(@Nonnull Iterable<String> arg) {
        NBTTagList list = new NBTTagList();
        for(String each : arg)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList ofByteArrrays(@Nonnull Iterable<byte[]> arg) {
        NBTTagList list = new NBTTagList();
        for(byte[] each : arg)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList ofIntArrays(@Nonnull Iterable<int[]> arg) {
        NBTTagList list = new NBTTagList();
        for(int[] each : arg)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList ofLongArrays(@Nonnull Iterable<long[]> arg) {
        NBTTagList list = new NBTTagList();
        for(long[] each : arg)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagCompound of(String k1, NBTBase v1) {
        return of(ImmutableMap.of(k1, v1));
    }
    @Nonnull public static NBTTagCompound of(String k1, NBTBase v1, String k2, NBTBase v2) {
        return of(ImmutableMap.of(k1, v1, k2, v2));
    }
    @Nonnull public static NBTTagCompound of(String k1, NBTBase v1, String k2, NBTBase v2, String k3, NBTBase v3) {
        return of(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
    }
    @Nonnull public static NBTTagCompound of(String k1, NBTBase v1, String k2, NBTBase v2, String k3, NBTBase v3, String k4, NBTBase v4) {
        return of(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }
    @Nonnull public static NBTTagCompound of(String k1, NBTBase v1, String k2, NBTBase v2, String k3, NBTBase v3, String k4, NBTBase v4, String k5, NBTBase v5) {
        return of(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
    }
    @Nonnull public static NBTTagCompound of(@Nonnull Map<String, NBTBase> arg) {
        NBTTagCompound map = new NBTTagCompound();
        for (Map.Entry<String, NBTBase> entry :arg.entrySet())
            map.setTag(entry.getKey(), entry.getValue());
        return map;
    }
    @Nonnull public static NBTTagCompound ofStrings(@Nonnull Map<String, String> arg) {
        NBTTagCompound map = new NBTTagCompound();
        for (Map.Entry<String, String> entry :arg.entrySet())
            map.setTag(entry.getKey(), of(entry.getValue()));
        return map;
    }
    @Nonnull public static NBTTagCompound ofByteArrays(@Nonnull Map<String, byte[]> arg) {
        NBTTagCompound map = new NBTTagCompound();
        for (Map.Entry<String, byte[]> entry :arg.entrySet())
            map.setTag(entry.getKey(), of(entry.getValue()));
        return map;
    }
    @Nonnull public static NBTTagCompound ofIntArrays(@Nonnull Map<String, int[]> arg) {
        NBTTagCompound map = new NBTTagCompound();
        for (Map.Entry<String, int[]> entry :arg.entrySet())
            map.setTag(entry.getKey(), of(entry.getValue()));
        return map;
    }
    @Nonnull public static NBTTagCompound ofLongArrays(@Nonnull Map<String, long[]> arg) {
        NBTTagCompound map = new NBTTagCompound();
        for (Map.Entry<String, long[]> entry :arg.entrySet())
            map.setTag(entry.getKey(), of(entry.getValue()));
        return map;
    }
    @Nonnull public static NBTTagList asList(@Nonnull byte... args) {
        NBTTagList list = new NBTTagList();
        for(byte each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull short... args) {
        NBTTagList list = new NBTTagList();
        for(short each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull int... args) {
        NBTTagList list = new NBTTagList();
        for(int each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull long... args) {
        NBTTagList list = new NBTTagList();
        for(long each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull float... args) {
        NBTTagList list = new NBTTagList();
        for(float each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull double... args) {
        NBTTagList list = new NBTTagList();
        for(double each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull String... args) {
        NBTTagList list = new NBTTagList();
        for(String each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull byte[]... args) {
        NBTTagList list = new NBTTagList();
        for(byte[] each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull int[]... args) {
        NBTTagList list = new NBTTagList();
        for(int[] each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull long[]... args) {
        NBTTagList list = new NBTTagList();
        for(long[] each : args)
            list.appendTag(of(each));
        return list;
    }
    @Nonnull public static NBTTagList asList(@Nonnull NBTBase... args) {
        NBTTagList list = new NBTTagList();
        for(NBTBase each : args)
            list.appendTag(each);
        return list;
    }
    public static class Builder {
        public NBTTagCompound cache;
        public Builder() {
            cache = new NBTTagCompound();
        }
        Builder with(NBTTagCompound tag) {
            cache.merge(tag);
            return this;
        }
        Builder within(String k) {
            cache = of(k, cache);
            return this;
        }
        NBTTagCompound build() {
            return cache;
        }
    }

    @Nonnull static Builder builder() {
        return new Builder();
    }

    @Nonnull static NBTTagCompound merge(@Nonnull NBTTagCompound tag0, @Nonnull NBTTagCompound... tags) {
        Builder builder = builder().with(tag0);
        for(NBTTagCompound tag : tags)
            builder.with(tag);
        return builder.build();
    }
}