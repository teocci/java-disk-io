package com.github.teocci.diskio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main
{
    public static void main(String[] args)
    {
        try {
            String localPath= new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            System.out.printf("PWD: %s\n", localPath);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            DiskIO.measure(new ChannelRW());
            DiskIO.measure(new StreamRW());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}