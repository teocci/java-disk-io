package com.github.teocci.diskio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MeasureDiskIO
{
    static final int SIZE_GB = Integer.getInteger("sizeGB", 8);
    static final int BLOCK_SIZE = 64 * 1024;

    public static void main(String[] args)
    {
        try {
            ByteBuffer buffer = ByteBuffer.allocateDirect(BLOCK_SIZE);
            File tmp = File.createTempFile("delete", "me");
            tmp.deleteOnExit();
            int blocks = (int) (((long) SIZE_GB << 30) / BLOCK_SIZE);
            long start = System.nanoTime();
            try (FileOutputStream output = new FileOutputStream(tmp)) {
                FileChannel fc = output.getChannel();
                for (int i = 0; i < blocks; i++) {
                    buffer.clear();
                    while (buffer.remaining() > 0) fc.write(buffer);
                    CLIHelper.printProgress(start, i, blocks - 1, "Writes");
                }
            }
            long mid = System.nanoTime();
            try (FileInputStream input = new FileInputStream(tmp)) {
                FileChannel fc = input.getChannel();
                for (int i = 0; i < blocks; i++) {
                    buffer.clear();
                    while (buffer.remaining() > 0) fc.read(buffer);
                    CLIHelper.printProgress(mid, i, blocks - 1, "Reads");
                }
            }
            long end = System.nanoTime();

            long size = tmp.length();
            System.out.printf("\nWrite speed %.1f GB/s, read Speed %.1f GB/s\n",
                    (double) size / (mid - start), (double) size / (end - mid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
