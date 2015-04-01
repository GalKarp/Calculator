package application;

public class Memento {
	private Double firstNumber;
	private Double secondNumber;
	private String operator;

	public Memento(Double firstNumber, Double secondNumber, String operator) {
		super();
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.operator = operator;
	}

	public Double getFirstNumber() {
		return firstNumber;
	}

	public Double getSecondNumber() {
		return secondNumber;
	}

	public String getOperator() {
		return operator;
	}
}
