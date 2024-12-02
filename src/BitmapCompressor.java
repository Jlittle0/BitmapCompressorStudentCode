/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Josh Little
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */

    public static void compress() {
        int optimalBits = 8;
        int maxLength  = 255;

        int currentBit;
        int currentNum = 0;
        int count = 0;

        while (!BinaryStdIn.isEmpty()) {
            // Get the current/next bit
            currentBit = BinaryStdIn.readInt(1);
            // If that bit matches the current string and doesn't go over max, increase count
            if (currentBit == currentNum && count < maxLength) {
                count++;
            } else {
                // If the above conditions aren't met, write out current run and reset count
                BinaryStdOut.write(count, optimalBits);
                count = 0;
                // If current run stopped because of a mismatch, change currentNum to 0 or 1;
                if (currentBit != currentNum)
                    currentNum = (currentNum + 1) % 2;
                // If the current run stopped because the max length was reached, don't swap
                // and instead write out 8 blank bits of 0s.
                else
                    BinaryStdOut.write(0, optimalBits);
            }
        }
        // Write out the remaining bits
        BinaryStdOut.write(count, optimalBits);
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        int currentBit = 0;
        // As long as there are bits to read... (everything should be multiple of 8)
        while (!BinaryStdIn.isEmpty()) {
            // Read 8 bits at a time
            int seqLength = BinaryStdIn.readInt(8);
            // Print the string of bits
            for (int i = 0; i < seqLength; i++){
                BinaryStdOut.write(currentBit, 1);
            }
            // Iterate from 0 to 1 or 1 to 0 for the currentBit that should be checked for.
            currentBit = (currentBit + 1) % 2;
        }
        // Close the file.
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}