import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int secondNumber = new Scanner(System.in).nextInt();
        int sum = firstNumber + secondNumber;
        int difference = firstNumber - secondNumber;
        int product = firstNumber * secondNumber;
        double quotient = (double) firstNumber / secondNumber;
        System.out.println("Сумма введенных чисел равна " + sum);
        System.out.println("Разность введенных чисел равна " + difference);
        System.out.println("Произведение введенных чисел равно " + product);
        System.out.println("Частное введенных чисел равно " + quotient);
    }
}
