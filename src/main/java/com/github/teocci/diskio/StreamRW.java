package com.github.teocci.diskio;

import com.github.teocci.diskio.interfaces.RWHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamRW implements RWHandler
{
    final byte[] buffer = new byte[BLOCK_SIZE];

    private final boolean doChecksum;

    public StreamRW()
    {
        this.doChecksum = false;
    }

    public StreamRW(boolean doChecksum)
    {
        this.doChecksum = doChecksum;
    }

    @Override
    public String toString()
    {
        return "Stream";
    }

    @Override
    public void write(File f) throws IOException
    {
        try (FileOutputStream out = new FileOutputStream(f)) {
            long start = System.nanoTime();
            for (int i = 0; i < blocks; i++) {
                out.write(buffer);
                CLIHelper.printProgress(start, i, blocks - 1, "Writing");
            }
        }
    }

    @Override
    public void read(File f) throws IOException
    {
        try (FileInputStream in = new FileInputStream(f)) {
            long start = System.nanoTime();
            for (int i = 0; i < blocks; i++) {
                while (in.read(buffer) != -1) {
                    if (in.available() == 0) break;
                }
                CLIHelper.printProgress(start, i, blocks - 1, "Reading");
            }
        }
    }
}
