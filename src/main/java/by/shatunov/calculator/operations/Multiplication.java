package by.shatunov.calculator.operations;

public class Multiplication implements Calculator {

    private Double a;
    private Double b;

    public Multiplication() {
    }

    public Multiplication(Double a, Double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Double calc() {
        return a * b;
    }

    @Override
    public Calculator setA(Double a) {
        this.a = a;
        return this;
    }

    @Override
    public Calculator setB(Double b) {
        this.b = b;
        return this;
    }
}
