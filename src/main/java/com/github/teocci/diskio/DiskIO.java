package com.github.teocci.diskio;

import com.github.teocci.diskio.interfaces.RWHandler;

import java.io.File;
import java.io.IOException;

public class DiskIO
{
    static final int SIZE_GB = Integer.getInteger("sizeGB", 8);

    static void measure(RWHandler rw) throws IOException
    {
        File file = File.createTempFile("delete", "me");
        file.deleteOnExit();
        System.out.printf("\nWrites %d GB with %s\n", SIZE_GB, rw);
        long start = System.nanoTime();
        rw.write(file);
        long mid = System.nanoTime();
        System.out.printf("\nReads %d GB with %s\n", SIZE_GB, rw);
        rw.read(file);
        long end = System.nanoTime();
        long size = file.length();
        System.out.printf("\nWrite speed %.1f GB/s, read Speed %.1f GB/s\n",
                (double) size / (mid - start), (double) size / (end - mid));
    }

}
