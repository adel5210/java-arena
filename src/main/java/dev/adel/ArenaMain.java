package dev.adel;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class ArenaMain {
    public static void main(String[] args) {
        final long longSizeInBytes = ValueLayout.JAVA_LONG.byteSize();
        int numLongs = 100;
        final long totalBytes = longSizeInBytes * numLongs;

        try (final Arena arena = Arena.ofAuto()) {
            System.out.println("Allocate MS of " + totalBytes + " bytes off-heap");
            final MemorySegment longArrayMS = arena.allocate(totalBytes);
            System.out.println("Memory segment allocated: " + longArrayMS);

            System.out.println("Writing long values to MS...");
            for (int i = 0; i < numLongs; i++) {
                final long val = (i + 1) * 100L;
                longArrayMS.setAtIndex(ValueLayout.JAVA_LONG, i, val);
                System.out.println("Value: " + val + " at index " + i);
            }

            System.out.println("Accessing MS directly as long array ...");
            for (int i = 0; i < numLongs; i++) {
                final long val = longArrayMS.getAtIndex(ValueLayout.JAVA_LONG, i);
                System.out.println("MS Value: " + val + " at index " + i);
            }

            System.out.println("Closing arena and deallocate MS ...");

        }

    }
}