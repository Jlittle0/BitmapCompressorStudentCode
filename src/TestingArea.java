public class TestingArea {
    public static int countUsedBits(int z) {
        if (z == 0) {
            return 0;
        }
        return countUsedBits(z >>> 1) + 1;
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
                System.out.println("test");
                count++;
            }
            segments++;
            s = s.substring(count);
        }
        return (double)totalLength / segments;
    }

    public static void main(String[] args) {
        int num = 4;

        System.out.println(1 << 8);

        double average = findAverage("0000000000000000000000000000000011111100000000000000000011110000000000000000000011111111110000001111111100000000000000000000111100000000000000000011110000000000000");
        System.out.println(average);
        int map = (int)Math.ceil(average);
        System.out.println(map + " | " + countUsedBits(map - 1));


    }



}
