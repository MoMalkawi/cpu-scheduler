package simulation.utils;

public class NumberUtils {

    private NumberUtils() {}

    public static int countDigits(int number) {
        return String.valueOf(number).length();
    }

}
