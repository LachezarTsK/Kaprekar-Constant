import java.util.Scanner;

public class Solution {

  private static int[] digits_frequency;
  private static final int KAPREKAR_CONSTANT_FOR_FOUR_DIGITS = 6174;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int num = scanner.nextInt();
    int result = find_numberOfSteps_toReach_kaprekarConstant(num);
    System.out.println(result);
  }

  /**
   * Finds the number of steps to transform the input integer into a Kaprekar Constant for four
   * digits (6174) by repeatedly rearranging the digits of the input into integers with descending 
   * and ascending digit order, and then subtracting the ascending from the descending integer.
   *
   * Most of the integers less than 10000 can be transformed to a Kaprekar Constant for four
   * digits, with some exceptions, where followig the described steps will result in zero.
   *
   * Example: 5478 => 8754(descedning) - 4578(ascedning) = 4176;
   *          4176 => the same steps, until reaching Kaprekar Constant for four digits.
   * Exception example: 1111 => 1111(descedning) - 1111(ascedning) = 0;
   *
   * @return A non-negative integer, representing the number of steps, if the input can be
   *         transformed to a Kaprekar Constant for four digits. Otherwise, it returns -1.
   */
  public static int find_numberOfSteps_toReach_kaprekarConstant(int num) {
    if (num >= 10000||arrangeInteger_inDescendingDigits(num)==arrangeInteger_inAscendingDigits(num)) {
      return -1;
    }

    int total_stepsToReach_kaprekarConstant = 0;
    while (num != KAPREKAR_CONSTANT_FOR_FOUR_DIGITS) {
      record_digitsFrequency(num);
      num = arrangeInteger_inDescendingDigits(num)==arrangeInteger_inAscendingDigits(num);
      total_stepsToReach_kaprekarConstant++;
    }

    return total_stepsToReach_kaprekarConstant;
  }

  /**
   * Creates an integer where its digits are rearranged in a descending order. If the integer has
   * less than four digits, then one or more zeros are added at the back of the rearranged integer,
   * so that it has four digits.
   *
   * Example: (34 => 4300), (1 => 1000), (794 => 9740)
   *
   *@return An integer with descending order of its digits.
   */
  private static int arrangeInteger_inDescendingDigits(int num) {

    int result = 0;
    int digit_place = 1000;

    for (int i = digits_frequency.length - 1; i > 0; i--) {
      int frequency = digits_frequency[i];
      while (frequency > 0) {
        result = result + i * digit_place;
        digit_place = digit_place / 10;
        frequency--;
      }
    }
    return result;
  }

  /**
   * Creates an integer where its digits are rearranged in an ascending order. If the integer has
   * one or more zeros, trailing or between the other non-zero digits, then they are disregarded,
   * since placing zeros at the front of the integer does not change the value of the integer.
   *
   * Example: (5280 => 258), (9005 => 59), (8000 => 8)
   *
   *@return An integer with ascending order of its digits.
   */
  private static int arrangeInteger_inAscendingDigits(int num) {

    int result = 0;
    int digit_place = 1;

    for (int i = digits_frequency.length - 1; i > 0; i--) {
      int frequency = digits_frequency[i];
      while (frequency > 0) {
        result = result + i * digit_place;
        digit_place = digit_place * 10;
        frequency--;
      }
    }
    return result;
  }

  private static void record_digitsFrequency(int num) {
    digits_frequency = new int[10];
    while (num > 0) {
      digits_frequency[num % 10]++;
      num = num / 10;
    }
  }
}
