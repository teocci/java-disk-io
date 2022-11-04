package com.github.teocci.diskio;

import com.github.teocci.diskio.interfaces.RWHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelRW implements RWHandler
{
    final ByteBuffer buffer = ByteBuffer.allocateDirect(BLOCK_SIZE);

    private final boolean doChecksum;

    public ChannelRW()
    {
        this.doChecksum = false;
    }

    public ChannelRW(boolean doChecksum)
    {
        this.doChecksum = doChecksum;
    }


    @Override
    public String toString()
    {
        return "Channel";
    }

    @Override
    public void write(File f) throws IOException
    {
        try (FileOutputStream output = new FileOutputStream(f)) {
            FileChannel fc = output.getChannel();
            long start = System.nanoTime();
            for (int i = 0; i < blocks; i++) {
                buffer.clear();
                while (buffer.remaining() > 0) fc.write(buffer);
                CLIHelper.printProgress(start, i, blocks - 1, "Writing");
            }
        }
    }

    @Override
    public void read(File f) throws IOException
    {
        try (FileInputStream input = new FileInputStream(f)) {
            FileChannel fc = input.getChannel();
            long start = System.nanoTime();
            for (int i = 0; i < blocks; i++) {
                buffer.clear();
                while (buffer.hasRemaining()) fc.read(buffer);
                CLIHelper.printProgress(start, i, blocks - 1, "Reading");
            }
        }
    }
}
