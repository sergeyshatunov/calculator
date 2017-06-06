package by.shatunov.calculator;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter an expression, 'q' for quit");

        while (true) {
            String expression = scanner.nextLine();
            CalculatorCLI.calculate(expression, context);
        }
    }
}
