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

        String s = BinaryStdIn.readString();
        int n = s.length();
        // Find the average run length for the given string and then ceiling that.
        double average = findAverage(s);
        int roundedAverage = (int)Math.ceil(average);
        int optimalBits = countUsedBits(roundedAverage - 1);
        int maxIntegerForBits = (int)Math.pow(2, optimalBits);

        // Writes out the number of bits that should be read in for each chunk in header as int.
        BinaryStdOut.write(optimalBits);
        BinaryStdOut.write(n);

//         Write out each character
        int currentNum;
        int count;
       while (!s.isEmpty()) {
            count = 0;
            currentNum = s.charAt(0);
            while (count < s.length() && s.charAt(count) == currentNum && count < maxIntegerForBits) {
                count++;
            }
            // Mod by the max value for that number of bits to allow the largest power of 2 be all zeros.
           if (count != 0) {
               BinaryStdOut.write(count % maxIntegerForBits, optimalBits);
               if (currentNum == 0 || currentNum == 1)
                    BinaryStdOut.write(currentNum, 1);
               s = s.substring(count);
           }
           else
               s = s.substring(1);
        }
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        int metaBits = BinaryStdIn.readInt();
        int metaLength = BinaryStdIn.readInt();
        int temp = 0;

        while (!BinaryStdIn.isEmpty() && temp < metaLength) {
            int runLength = BinaryStdIn.readInt(metaBits);
            int runNum = BinaryStdIn.readInt(1);
            for (int i = 0; i < runLength; i++)
                BinaryStdOut.write(runNum, 1);
            temp += runLength;
        }

        BinaryStdOut.close();
    }

    public static double findAverage(String s) {
        int totalLength = s.length();
        int segments = 0;
        int count;
        int currentNum;
        while (!s.isEmpty()) {
            count = 0;
            currentNum = s.charAt(0);
            while (count < s.length() && s.charAt(count) == currentNum) {
                count++;
            }
            segments++;
            s = s.substring(count);
        }
        return (double)totalLength / segments;
    }

    // Method taken from elsewhere for testing purposes
    public static int countUsedBits(int z) {
        if (z == 0) {
            return 0;
        }
        return countUsedBits(z >>> 1) + 1;
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