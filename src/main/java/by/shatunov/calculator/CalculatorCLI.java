package by.shatunov.calculator;

import by.shatunov.calculator.operations.Calculator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorCLI {

    private static final Pattern PATTERN_MULTIPLY_AND_DIVIDE = Pattern.compile("([-]?[\\d.]+)[^-+]*?([*/])[^-+]*?([-]?[\\d.]+)");
    private static final Pattern PATTERN_ADD_AND_SUBTRACT = Pattern.compile("([-]?[\\d.]+)[^-]*?([+\\-])[^-]*?([-]?[\\d.]+)");

    private static Pattern currentPattern = PATTERN_MULTIPLY_AND_DIVIDE;

    private static ClassPathXmlApplicationContext context = null;
    private static Calculator calculator = null;

    private static String recursive(String expression) {
        if (!expression.matches("[\\d.]+")) {

            Matcher matcher = currentPattern.matcher(expression);

            if (matcher.find()) {
                String a = matcher.group(1);
                String operator = matcher.group(2);
                String b = matcher.group(3);

                switch (operator) {
                    case "+":
                        calculator = (Calculator) context.getBean("plus");
                        break;
                    case "-":
                        calculator = (Calculator) context.getBean("minus");
                        break;
                    case "*":
                        calculator = (Calculator) context.getBean("multiply");
                        break;
                    case "/":
                        calculator = (Calculator) context.getBean("divide");
                        break;
                }

                Double result = calculator
                        .setA(Double.valueOf(a))
                        .setB(Double.valueOf(b))
                        .calc();

                expression = expression.replaceFirst(currentPattern.toString(), result.toString());
                expression = recursive(expression);
            } else {
                currentPattern = PATTERN_ADD_AND_SUBTRACT;
                matcher = currentPattern.matcher(expression);
                if (matcher.find()) {
                    expression = recursive(expression);
                } else {
                    System.out.println("Wrong expression");
                }
                currentPattern = PATTERN_MULTIPLY_AND_DIVIDE;
            }
        }
        return expression;
    }

    static void calculate(String expression, ClassPathXmlApplicationContext context) {
        CalculatorCLI.context = context;

        if (expression.equals("q")) {
            System.out.println("Bye!");
            System.exit(0);
        }

        System.out.println("> " + recursive(expression));
    }
}
