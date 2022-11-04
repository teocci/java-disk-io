package com.github.teocci.diskio.interfaces;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface RWHandler
{
    int SIZE_GB = Integer.getInteger("sizeGB", 8);
    int BLOCK_SIZE = 64 * 1024;
    int blocks = (int) (((long) SIZE_GB << 30) / BLOCK_SIZE);
    byte[] acceptBuffer = new byte[555];

    void write(File f) throws IOException;

    void read(File f) throws IOException;
}
