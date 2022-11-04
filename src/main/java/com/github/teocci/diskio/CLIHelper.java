package com.github.teocci.diskio;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class CLIHelper
{
    static final long HOURS_IN_MINUTES = TimeUnit.HOURS.toMinutes(1);
    static final long MINUTES_IN_SECONDS = TimeUnit.MINUTES.toSeconds(1);

    static long eta(long start, long current, long total)
    {
        long now = System.nanoTime();
        return current == 0 ? 0 : (total - current) * (now - start) / current;
    }

    static String etaToString(long current, long eta)
    {
        return current == 0 ? "N/A" : String.format(
                "%02d:%02d:%02d",
                TimeUnit.NANOSECONDS.toHours(eta),
                TimeUnit.NANOSECONDS.toMinutes(eta) % HOURS_IN_MINUTES,
                TimeUnit.NANOSECONDS.toSeconds(eta) % MINUTES_IN_SECONDS
        );
    }

    static int percentRemaining(long current, long total)
    {
        return (int) (current * 100 / total);
    }

    static int percentPad(int percent)
    {
        return percent == 0 ? 2 : 2 - (int) (Math.log10(percent));
    }

    static int ratio(long current, long total)
    {
        long diff = total - current;
        if (diff == 0) return 0;

        return (int) (Math.log10(diff));
    }

    static void printProgress(long start, long current, long total, String label)
    {
        long eta = eta(start, current, total);
        String etaHMS = etaToString(current, eta);

        int percent = percentRemaining(current, total);

        StringBuilder string = new StringBuilder(140);
        string.append('\r')
                .append(label)
                .append(String.join("", Collections.nCopies(percentPad(percent), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.format(" ETA: %s", etaHMS));

        System.out.print(string);
    }
}
