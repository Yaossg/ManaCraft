package yaossg.mod.mana_craft.util;

import java.util.Random;

public class RandomBuffer {
    Random random;
    long buffer = 0;
    int left = 0;
    public RandomBuffer(Random random) {
        this.random = random;
    }
    public void clear() {
        allocate(left = 0);
    }
    public void reallocate(int bits) {
        clear();
        allocate(bits);
    }
    public void allocate(int bits) {
        if(left != 0) {
            left += bits;
        } else if(bits <= 0) {
            buffer = 0;
        } else if(bits < 32) {
            buffer = random.nextInt();
            left = 32;
        } else if(bits <= 64) {
            buffer = random.nextLong();
            left = 64;
        } else {
            buffer = random.nextLong();
            left = bits;
        }
    }
    public long getAsLong(int bits) {
        if(buffer < (1 << bits) - 1 && left != 0)
            allocate(left);
        if(left < bits) allocate(64);
        long ret = buffer & ~(Long.MIN_VALUE << bits);
        left -= bits;
        buffer >>>= bits;
        return ret;
    }
    public int getAsInt(int bits) {
        if(buffer < (1 << bits) - 1 && left != 0)
            allocate(left);
        if(left < bits) allocate(64);
        int ret = (int)(buffer & ~(Integer.MIN_VALUE << bits));
        left -= bits;
        buffer >>>= bits;
        return ret;
    }
    public boolean getBoolean() {
        return getBoolean(1);
    }
    public boolean getBoolean(int bits) {
        return getAsInt(bits) == 0;
    }
    public long getLong() {
        return getAsLong(64);
    }
    public long getULong() {
        return getAsLong(63);
    }
    public int getInt() {
        return getAsInt(32);
    }
    public int getUInt() {
        return getAsInt(31);
    }
    public float getFloat() {
        return getFloat(32);
    }
    public double getDouble() {
        return getDouble(64);
    }
    public float getFloat(int bits) {
        int ret = getAsInt(bits);
        return (ret & ~(Integer.MIN_VALUE << bits)) / (float)(1 << bits);
    }
    public double getDouble(int bits) {
        long ret = getAsLong(bits);
        return (ret & ~(Long.MIN_VALUE << bits)) / (double)(1 << bits);
    }
}
