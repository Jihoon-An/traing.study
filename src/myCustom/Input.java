package myCustom;

import java.util.Scanner;

public class Input {
    public static int _integer_() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(">>> ");
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다, 정수를 입력하세요.");
            }
        }
    }

    public static int _integer_(int min, int max) {
        while (true) {
            int input_num = _integer_();
            if (min <= input_num && input_num <= max) {
                return input_num;
            } else {
                System.out.println("범위 밖의 입력입니다, 다시 입력하세요.");
            }
        }
    }

    public static double _double_() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(">>> ");
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다, 정수를 입력하세요.");
            }
        }
    }

    public static double _double_(double min, double max) {
        while (true) {
            double input_num = _double_();
            if (min <= input_num && input_num <= max) {
                return input_num;
            } else {
                System.out.println("범위 밖의 입력입니다, 다시 입력하세요.");
            }
        }
    }

    public static String _string_() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">>> ");
        return scanner.nextLine();
    }

    public static String _string_(String... filter) {
        while (true) {
            String inputString = _string_();
            boolean success = false;
            for (String f : filter) {
                if (inputString.equals(f)) {
                    success = true;
                    break;
                }
            }
            if (success) {
                return inputString;
            } else {
                System.out.println("지정값 외의 입력입니니다, 다시 입력하세요.");
            }
        }
    }
}
