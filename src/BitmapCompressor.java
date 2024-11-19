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
    // Will find the average eventually and change this but currently a static value
    public static final int BITS_PER_CHUNK = 16;

    public static void compress() {

        // TODO: complete compress()
        // Take in at most two bytes of data each time and compress it into the number of
//        String s = BinaryStdIn.readString();
//        int n = s.length();
//        double averageBits = findAverage(s);
//        BinaryStdOut.write(averageBits);
//        BinaryStdOut.write(BITS_PER_CHUNK + 1);
//        // Write out each character
//        int currentNum;
//        int count;
//        while (!s.isEmpty()) {
//            count = 0;
//            currentNum = s.charAt(0);
//            while (s.charAt(count) == currentNum) {
//                count++;
//            }
//            BinaryStdOut.write(count, log2(BITS_PER_CHUNK));
//            s = s.substring(count);
//        }
//        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {

        // TODO: complete expand()
    }

    public static double findAverage(String s) {
        int totalLength = 0;
        int segments = 0;
        int count;
        int currentNum;
        while (!s.isEmpty()) {
            count = 0;
            currentNum = s.charAt(0);
            while (s.charAt(count) == currentNum) {
                count++;
            }
            totalLength += (count);
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