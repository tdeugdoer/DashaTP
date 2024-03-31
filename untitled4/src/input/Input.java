package input;

import java.util.Scanner;

public class Input {
    public static int inputInt(Scanner sc, String text) {
        while (true) {
            System.out.print(text + ": ");
            try {
                int value = Integer.parseInt(sc.nextLine());
                if (value < 0) {
                    System.out.println("Число не может быть отрицательным. Повторите попытку");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод! Повторите попытку");
            }
        }
    }

    public static double inputDouble(Scanner sc, String text) {
        while (true) {
            System.out.print(text + ": ");
            try {
                double value = Double.parseDouble(sc.nextLine());
                if (value < 0.0) {
                    System.out.println("Число не может быть отрицательным. Повторите попытку");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Неверный ввод! Повторите попытку");
            }
        }
    }
}
