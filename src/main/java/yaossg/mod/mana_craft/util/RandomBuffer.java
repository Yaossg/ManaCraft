package yaossg.mod.mana_craft.util;

import java.util.Random;

public class RandomBuffer {
    Random random;
    long buffer = 0;
    int left = 0;
    public RandomBuffer(Random random) {
        this.random = random;
    }
    public boolean isEmpty() {
        return left == 0;
    }
    public void clear() {
        buffer = left = 0;
    }
    public void reallocate(int bits) {
        clear();
        allocate(bits);
    }
    public void allocate(int bits) {
        if(bits < 32) {
            buffer = random.nextInt();
            left = 32;
        } else {
            buffer = random.nextLong();
            left = 64;
        }
    }
    public long getBitsAsLong(int bits) {
        if(left < bits) reallocate(bits);
        long ret = buffer & ~(-1L << bits);
        left -= bits;
        buffer >>>= bits;
        return ret;
    }
    public int getBitsAsInt(int bits) {
        if(left < bits) reallocate(bits);
        int ret = (int)(buffer & ~(-1L << bits));
        left -= bits;
        buffer >>>= bits;
        return ret;
    }
    public float getFloat(int bits) {
        return (getBitsAsInt(bits) & ~(-1L << bits)) / (float)(1 << bits);
    }
    public double getDouble(int bits) {
        return (getBitsAsLong(bits) & ~(-1L << bits)) / (double)(1 << bits);
    }
    public boolean getBoolean() {
        return getBoolean(1);
    }
    public boolean getBoolean(int bits) {
        return getBitsAsInt(bits) == 0;
    }
    public long getLong() {
        return getBitsAsLong(64);
    }
    public long getULong() {
        return getBitsAsLong(63);
    }
    public int getInt() {
        return getBitsAsInt(32);
    }
    public int getUInt() {
        return getBitsAsInt(31);
    }
    public float getFloat() {
        return getFloat(24);
    }
    public double getDouble() {
        return getDouble(53);
    }
}
