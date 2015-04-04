package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller{
    static Stage prevStage;
    private ScaleTransition scaleTransition;
    private Stage myStage;


	boolean actionPerformed = false;
	private String operator = "+", MEMORY = "";
	private double memory;
	private static double sceneH;
	private static double sceneW;
	private boolean flag = false;

	private double arg1 = 0;
	private double arg2 = 1;
	private double results = 0;
	private int decimals = 2;
	private Originator originator = new Originator();
    private CareTaker careTaker = new CareTaker();

	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Label DEG, RAD, GRA, M, secondText;
	@FXML
	private TextField mainText;
	@FXML
	private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
			btnPunct, ACBtn, CBtn, divide, egal, minus, multiply, plus, numInv,
			MPlus, MRBtn, plusMinus, DRGBtn, PIBtn, InvBtn, sinBtn, cosBtn,
			tanBtn, lnBtn, factorialBtn, squareBtn, sqrtBtn, powBtn,
			radicalBtn, closeBtn;
	@FXML
	private Label calc;


	
	

	
	
	@FXML
	void ACBtnHandler(ActionEvent event) {
		reset();
	}

	@FXML
	void CBtnHandler(ActionEvent event) {
		mainText.setText("");
	}

	@FXML
	void divideHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " / ");
		operator = "/";
		computeOperation();
	}

	@FXML
	void multiplyHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " * ");
		operator = "*";
		computeOperation();
	}

	@FXML
	void plusHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " + ");
		operator = "+";
		computeOperation();
	}

	@FXML
	void minusHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " - ");
		operator = "-";
		computeOperation();
	}

	@FXML
	void btn0Handler(ActionEvent event) {
		if (actionPerformed) {
			mainText.setText("");
			secondText.setText("");
		}
		actionPerformed = false;
		if (!mainText.getText().equals("0")) {
			mainText.setText(mainText.getText() + "0");
		}
	}

	@FXML
	void btn1Handler(ActionEvent event) {
		NumericalBottonHandle(btn1);
	}

	@FXML
	void btn2Handler(ActionEvent event) {
		NumericalBottonHandle(btn2);
	}

	@FXML
	void btn3Handler(ActionEvent event) {
		NumericalBottonHandle(btn3);
	}

	@FXML
	void btn4Handler(ActionEvent event) {
		NumericalBottonHandle(btn4);
	}

	@FXML
	void btn5Handler(ActionEvent event) {
		NumericalBottonHandle(btn5);
	}

	@FXML
	void btn6Handler(ActionEvent event) {
		NumericalBottonHandle(btn6);
	}

	@FXML
	void btn7Handler(ActionEvent event) {
		NumericalBottonHandle(btn7);
	}

	@FXML
	void btn8Handler(ActionEvent event) {
		NumericalBottonHandle(btn8);
	}

	@FXML
	void btn9Handler(ActionEvent event) {
		NumericalBottonHandle(btn9);
	}

	@FXML
	void btnPunctHandler(ActionEvent event) {
		if (actionPerformed) {
			mainText.setText("");
			secondText.setText("");
		}
		actionPerformed = false;
		if (mainText.getText().equals("")) {
			mainText.setText("0.");
		}
		if (!mainText.getText().contains(".")) {
			mainText.setText(mainText.getText() + ".");
		}
	}

	@FXML
	void numInvBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			decimals = Math.max(getNumberOfDigits(argument), 6);
			mainText.setText(String.format("%." + decimals + "f", 1 / argument));
			actionPerformed = true;
		}
	}

	@FXML
	void MPlusBtnHandler(ActionEvent event) {
		if (!((mainText.getText().equals("0")) || (mainText.getText()
				.equals("")))) {
			MEMORY = mainText.getText();
			M.setText("Memorie: " + MEMORY);
			actionPerformed = true;
		} else {
			secondText.setText("");
			M.setText("");
			MEMORY = "";
		}
	}

	@FXML
	void MRBtnHandler(ActionEvent event) {
		if (!MEMORY.equals("")) {
			mainText.setText(MEMORY);
		}
	}

	@FXML
	void plusMinusHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.valueOf(mainText.getText());
			if (argument > 0) {
				mainText.setText("-"
						+ (String.format(
								("%." + getNumberOfDigits(argument) + "f"),
								argument)));
			}
			if (argument < 0) {
				mainText.setText((String.format(("%."
						+ getNumberOfDigits(argument) + "f"),
						Math.abs(argument))));
			}
		} else {
			mainText.setText("-");
		}
	}

	double trigFactor = 0.017453292519943295;// DEG to RAD
	int trigIncrement = 1;
	String DRGStatus = "degrees";
	boolean firstFunction = true;

	@FXML
	void DRGBtnHandler(ActionEvent event) {
		trigIncrement++;
		if (trigIncrement == 1) {
			DRGStatus = "degrees";
			trigFactor = 0.017453292519943295;
			DEG.setText("DEG");
			GRA.setText("");
		}
		if (trigIncrement == 2) {
			DRGStatus = "radians";
			trigFactor = 1;
			DEG.setText("");
			RAD.setText("RAD");
		}
		if (trigIncrement == 3) {
			DRGStatus = "grads";
			trigFactor = 0.015707963267948;
			GRA.setText("GRA");
			RAD.setText("");
		}
		trigIncrement = trigIncrement % 3;
	}

	@FXML
	void PIBtnHandler(ActionEvent event) {
		mainText.setText(String.format("%.8f", Math.PI));
	}

	@FXML
	void InvBtnHandler(ActionEvent event) {
		if (firstFunction == true) {
			firstFunction = false;
			sinBtn.setText("asin");
			cosBtn.setText("acos");
			tanBtn.setText("atan");
		} else {
			firstFunction = true;
			sinBtn.setText("sin");
			cosBtn.setText("cos");
			tanBtn.setText("tan");
		}
	}

	@FXML
	void sinBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			if (firstFunction) {
				results = Math.sin(trigFactor * argument);
				ComputeTrigonometricalOperation("sin", DRGStatus);
			} else {
				if (-1 <= argument && 1 >= argument) {
					results = Math.asin(argument);
					ComputeTrigonometricalOperation("asin", "");
				} else {
					secondText
							.setText("argument must be between -1 and 1");
				}
			}
		}
	}

	@FXML
	void cosBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			if (firstFunction) {
				results = Math.cos(trigFactor * argument);
				ComputeTrigonometricalOperation("cos", DRGStatus);
			} else {
				if (-1 <= argument && 1 >= argument) {
					results = Math.acos(argument);
					ComputeTrigonometricalOperation("acos", "");
				} else {
					secondText
							.setText("argument must be between -1 and 1");
				}
			}
		}
	}

	@FXML
	void tanBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			if (firstFunction) {
				results = Math.tan(trigFactor * argument);
				ComputeTrigonometricalOperation("tan", DRGStatus);
			} else {
				results = Math.atan(argument);
				ComputeTrigonometricalOperation("atan", "");
			}
		}
	}

	@FXML
	void lnBtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.parseDouble(mainText.getText());
			if (argument > 0) {
				decimals = Math.max(getNumberOfDigits(argument), 6);
				secondText.setText("ln(" + argument + ") = ");
				mainText.setText(String.format("%." + decimals + "f",
						Math.log(argument)));
				actionPerformed = true;
			} else {
				secondText.setText("argument must be greater than 0 !");
			}
			actionPerformed = true;
		}
	}

	@FXML
	void factorialBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			actionPerformed = true;
			if (argument > 2 && argument <= 23) {
				decimals = getNumberOfDigits(argument);
				if (decimals == 0) {
					long fact = 1; // this will be the result
					for (long i = 1; i <= argument; i++) {
						fact *= i;
					}
					secondText.setText(argument + "! = ");
					mainText.setText(fact + "");
				}
			} else {
				secondText
						.setText("argument should be an integ between 3 and 23 !");
			}
		}
	}

	@FXML
	void squareBtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.parseDouble(mainText.getText());
			decimals = getNumberOfDigits(argument);
			secondText.setText(argument + " ^2 = ");
			mainText.setText(String.format("%." + decimals + "f", argument
					* argument));
			actionPerformed = true;
		}
	}

	@FXML
	void sqrtBtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.parseDouble(mainText.getText());
			decimals = Math.max(getNumberOfDigits(argument), 6);
			secondText.setText("√" + argument + " = ");
			argument = Math.sqrt(argument);
			if ((((int) argument) - argument) == 0) {
				decimals = 0;
			}
			mainText.setText(String.format("%." + decimals + "f", argument));
			actionPerformed = true;
		}
	}

	@FXML
	void powBtnHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " ^ ");
		operator = "^";
		computeOperation();
	}

	@FXML
	void equalBtnHandler(ActionEvent event) {
		equalPressed();
		System.out.println("equal pressed");
	}
	@FXML
	void redoBtnHandler(ActionEvent event) {
		originator.getStateFromMemento(careTaker.getNext());
		 arg1= originator.getFirstNumber();
		 arg2= originator.getSecondNumber();
		 operator=originator.getOperator()+"";
		 mainText.setEditable(true);
			secondText.setText("");


	     mainText.setText(arg1 + operator + arg2);
	      flag=true;


	}
	@FXML
	void undoBtnHandler(ActionEvent event) throws ScriptException {
		originator.getStateFromMemento(careTaker.getPrev());
		 arg1= originator.getFirstNumber();
		 arg2= originator.getSecondNumber();
		 operator=originator.getOperator()+"";
//		equalPressed();
		 mainText.setEditable(true);
		secondText.setText("");
		mainText.setText(arg1 + operator + arg2);

	      flag=true;

	    


		
	}

	// INITIALIZE
	@FXML
	void initialize() {
		AnchorPane.setFocusTraversable(true);
		if (AnchorPane.isFocused()) {
			System.out.println("anchor pane focused");
		}
	}

	// END OF INITIALIZE

	// Keyboard input
	@FXML
	void keboardListener(KeyEvent ke) {
		// number input
//		if ("123456789".contains(ke.getText())) {
//			NumericalBottonHandle(ke);
//		}
//		if (ke.getCode().equals(KeyCode.DIGIT0)
//				|| ke.getCode().equals(KeyCode.NUMPAD0)) {
//			if (actionPerformed) {
//				mainText.setText("");
//				secondText.setText("");
//			}
//			actionPerformed = false;
//			if (!mainText.getText().equals("0")) {
//				mainText.setText(mainText.getText() + "0");
//			}
//		}
//		if (".".contains(ke.getText())) {
//			if (actionPerformed) {
//				mainText.setText("");
//				secondText.setText("");
//			}
//			actionPerformed = false;
//			if (mainText.getText().equals("")) {
//				mainText.setText("0.");
//			}
//			if (!mainText.getText().contains(".")) {
//				mainText.setText(mainText.getText() + ".");
//			}
//		}
//		// operation input
//		if ("+-*/".contains(ke.getText())) {
//			if (ke.getText().equals("+")) {
//				operationHandler("+");
//			}
//			if (ke.getText().equals("-")) {
//				operationHandler("-");
//			}
//			if (ke.getText().equals("*")) {
//				operationHandler("*");
//			}
//			if (ke.getText().equals("/")) {
//				operationHandler("/");
//			}
//		}
//
//		// Enter key calls equal method
//		if (ke.getCode().equals(KeyCode.ENTER)) {
//			equalPressed();
//			// System.out.println("equal din key listener");
//		}
	}

	@FXML
	void mainTextEnterPressed(ActionEvent event) {
		// egalPressed();
		// System.out.println("equal din maintext");
	}

	// END OF Keyboard input

	// CLOSE BUTTON
	@FXML
	void closeBtnHandler(ActionEvent event) {
		System.exit(0);
	}

	// END CLOSE

	public void computeOperation() {
		if (!mainText.getText().equals("")) {
			memory = Double.parseDouble(mainText.getText());
			arg1 = Double.parseDouble(mainText.getText());
			System.out.println(memory);
				
			System.out.println(arg1);
			mainText.setText("");
		} else {
			arg1 = memory;
			mainText.setText("");			
			System.out.println(memory);
			
		}
	}

	public void operationHandler(String zz) {// for simple operations
		secondText.setText(mainText.getText() + " " + zz + " ");
		operator = zz;
		computeOperation();
	}

	public void reset() {
		mainText.setText("");
		secondText.setText("");
		arg1 = arg2 = 0;
		actionPerformed = false;
		M.setText("");
	}

	public int getNumberOfDigits(double zz) {
		String z = String.valueOf(zz);
		if (!z.endsWith(".0")) {
			return (z.length() - z.indexOf(".") - 1);
		} else
			return 0;
	}

	public void NumericalBottonHandle(Button btn) {// FOR BUTTONS
		if (actionPerformed) {
			mainText.setText("");
			secondText.setText("");
		}
		actionPerformed = false;
		mainText.setText(mainText.getText() + btn.getText());
	}

	public void NumericalBottonHandle(KeyEvent ke) {// FOR KEYS
		if (actionPerformed) {
			mainText.setText("");
			secondText.setText("");
		}
		actionPerformed = false;
		mainText.setText(mainText.getText() + ke.getText());
	}

	public void ComputeTrigonometricalOperation(String status, String drgStatus) {
		decimals = 6;
		secondText.setText(status + "(" + mainText.getText() + ") " + drgStatus
				+ " =");
		if (results > 0.99999 && results < 1.00001) {
			results = 1;
			decimals = 0;
		}
		if (results > -1.00001 && results < -0.99999) {
			results = -1;
			decimals = 0;
		}
		if (results > -0.00001 && results < 0.00001) {
			results = 0;
			decimals = 0;
		}
		mainText.setText(String.format("%." + decimals + "f", results));
		actionPerformed = true;
	}

	public void equalPressed() {
		try {
			if(flag==false){

			if (!mainText.getText().equals("")) {
				arg2 = Double.parseDouble(mainText.getText());
			} else {
				arg2 = 0;
			}

			if (!secondText.getText().contains("=")) {
				secondText.setText(secondText.getText() + " " + arg2 + " = ");
			} else {
				secondText.setText(arg2 + " " + operator + arg1 + " =");
			}
			}
			if(flag==true){
				String str=mainText.getText();
				String str2=mainText.getText();
			    String[] splitString = (str.split("[+*/]"));
			    String[] splitString2 = (str.split("[0-9]+[.][0-9]+"));
			      arg1=Double.parseDouble(splitString[0]);
			      
			      arg2=Double.parseDouble(splitString[1]);
			      operator=splitString2[1];
			      
			      System.out.println(arg1 + "" + operator +"" + arg2);
				secondText.setText(arg1 + " " + operator + arg2 + " =");
				flag=false;
			}
			
			if (operator.equals("+")) {
				results = arg1 + arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}
			if (operator.equals("-")) {
				results = arg1 - arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}
			if (operator.equals("*")) {
				results = arg1 * arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}
			if (operator.equals("^")) {
				results = Math.pow(arg1, arg2);
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}
			if (operator.equals("rad")) {
				results = Math.pow(arg1, (double) (1 / arg2));
				decimals = Math.max(Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2)), 7);
			}
			if (operator.equals("/")) {
				if (arg2 == 0) {
					results = 0;
				} else {
					results = arg1 / arg2;
					decimals = Math.max(5, Math.max(getNumberOfDigits(arg1),
							getNumberOfDigits(arg2)));
				}
				
			}
			  originator.setState(arg1, arg2, operator);
			  careTaker.add(originator.saveStateToMemento());
			mainText.setText(String.format(("%." + decimals + "f"), results));

			actionPerformed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void saveSceneSize(Double height, Double width) {
		
		sceneW=width;

	}

	public static void setPrimaryScene(Stage primaryStage) {
		
		prevStage=primaryStage;
	}
	@FXML
	void onRegular(ActionEvent event) throws IOException {
		            prevStage.setWidth(prevStage.getWidth()-318);
	            	closeBtn.setLayoutX(280);
	            	calc.setLayoutX(150);
	            	mainText.setPrefWidth(300);
	            	secondText.setPrefWidth(300);

    }
	@FXML
	void onScientific(ActionEvent event) throws IOException {
		            prevStage.setWidth(sceneW);
	            	closeBtn.setLayoutX(593);
	            	calc.setLayoutX(285);
	            	mainText.setPrefWidth(609);
	            	secondText.setPrefWidth(598);

    }



			
		}

	
	


