package application;

public class Originator {
	private Double firstNumber;
	private Double secondNumber;
	private String operator;

	public Double getFirstNumber() {
		return firstNumber;
	}

	public Double getSecondNumber() {
		return secondNumber;
	}

	public String getOperator() {
		return operator;
	}

	public void setState(Double firstNumber, Double secondNumber, String operator) {
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
		this.operator = operator;
	}

	public Memento saveStateToMemento() {
		return new Memento(firstNumber, secondNumber, operator);
	}

	public void getStateFromMemento(Memento memento) {
		if (memento != null) {
			this.firstNumber = memento.getFirstNumber();
			this.secondNumber = memento.getSecondNumber();
			this.operator = memento.getOperator();
		}
	}
}
