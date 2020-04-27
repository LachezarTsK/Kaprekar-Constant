import java.util.Scanner;

public class Solution {

  private static int[] digits_frequency;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int num = scanner.nextInt();
    int result = find_numberOfSteps_toReach_kaprekarConstant(num);
    System.out.println(result);
  }

  /**
   * Finds the number of steps to transform the input integer into a Kaprekar Constant for four
   * digits (6174) by repeatedly rearranging the digits of the input into descending and ascending
   * order, and then subtracting the ascending from the descending integer.
   *
   * <p>Most of the integers less than 10000 can be transformed to a kaprekar constant for four
   * digits, with some exceptions, where followig the described steps will result in zero.
   *
   * <p>Exception example: 1111 => 1111(descedning) - 1111(ascedning)=0;
   *
   * @return A non-negative integer, representing the number of steps, if the input can be
   *     transformed to a Kaprekar Constant for four digits. Otherwise, it returns -1.
   */
  public static int find_numberOfSteps_toReach_kaprekarConstant(int num) {

    if (num >= 10000) {
      return -1;
    }

    int total_stepsToReach_kaprekarConstant = 0;
    while (num != 6174) {
      record_digitsFrequency(num);
      int descending = arrangeInteger_inDescendingDigits(num);
      int ascending = arrangeInteger_inAscendingDigits(num);

      if (descending == ascending) {
        return -1;
      }

      num = descending - ascending;
      total_stepsToReach_kaprekarConstant++;
    }

    return total_stepsToReach_kaprekarConstant;
  }

  /**
   * Creates an integer where its digits are rearranged in an descending order. If the integer has
   * less than four digits, then one or more zeros are added at the back of the rearranged integer,
   * so that it has four digits.
   *
   * <p>Example: (34 => 4300), (1 => 1000), (794 => 9740)
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
   * <p>Example: (5280 => 258), (9005 => 59), (8000 => 8)
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
