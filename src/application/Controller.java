package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.script.ScriptException;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;




public class Controller{
	public enum trigFunctions {
	    SIN, COS, TAN, ASIN,
	    ACOS, ATAN
	}
	//############################ Variables ############################//
	private static Stage prevStage;
	private static double sceneW;	
	private double arg1 = 0;
	private double arg2 = 1;
	private double results = 0;
	private double memory;
	private boolean nan = false;
	private double trigFactor = 0.017453292519943295;// DEG to RAD
	private int decimals = 2;
	private boolean actionPerformed = false;	
	private boolean flag = false;
	private boolean percentPressed = false;
	private boolean expPressed = false;
	private boolean firstFunction = true;	
	private String operator = "+", MEMORY = "";
	private String DRGStatus = "degrees";
	private Originator originator = new Originator();
	private CareTaker careTaker = new CareTaker();
	//#########################	javaFX variables#########################//
	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private Label M, secondText;
	@FXML
	private RadioButton deg;
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
	//#####################################################################//
	@FXML
	void ACBtnHandler(ActionEvent event) {
		reset();
	}

	@FXML
	void CBtnHandler(ActionEvent event) {
		mainText.setText("");
	}
	//Calculator Keys 0-9
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
	void modBtnHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " Mod ");
		operator = "mod";
		computeOperation();
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
	
	@FXML
	void percentBtnHandler(ActionEvent event) {
		if ((!mainText.getText().isEmpty()) && (arg2 != 0)) {
				percentPressed = true;
//			secondText.setText(mainText.getText() + " % ");
//			operator = "%";
//			computeOperation();	
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
			}
			else {
				//Limit very big numbers
				secondText.setText("argument should be an integ between 3 and 23 !");
			}
		}
	}
	
	@FXML
	void intBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			actionPerformed = true;
			decimals = getNumberOfDigits(argument);
			secondText.setText("Int(" + argument + ")");
			argument = Math.floor(argument);
			mainText.setText("" + argument);
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
	void XsqrtYBtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			operator = "yroot";
			double argument = Double.parseDouble(mainText.getText());
			decimals = Math.max(getNumberOfDigits(argument), 6);
			String str = argument + " yroot";
			secondText.setText(str);
			computeOperation();
		}
	}
	
	@FXML
	void Xsqrt3BtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			operator = "3root";
			double argument = Double.parseDouble(mainText.getText());
			decimals = Math.max(getNumberOfDigits(argument), 6);
			String str = argument + " 3root";
			arg2 = 3;
			secondText.setText(str);
			computeOperation();
		}
	}

	@FXML
	void squareBtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.parseDouble(mainText.getText());
			decimals = getNumberOfDigits(argument);
			secondText.setText(argument + " ^2 = ");
			mainText.setText(String.format("%." + decimals + "f", argument * argument));
			actionPerformed = true;
		}
	}
	
	@FXML
	void square3BtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.parseDouble(mainText.getText());
			decimals = getNumberOfDigits(argument);
			secondText.setText(argument + " ^3 = ");
			mainText.setText(String.format("%." + decimals + "f", argument * argument * argument));
			actionPerformed = true;
		}
	}
	
	@FXML
	void FeBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			String str = mainText.getText();
			if(!expPressed) {
				str = str + ".e+0";
				expPressed = true;
			}
			else {
				int index = str.indexOf("e")-1;
				str = str.substring(0, index);
				expPressed = false;
			}
			mainText.setText(str);
		}
	}

	@FXML
	void powBtnHandler(ActionEvent event) {
		secondText.setText(mainText.getText() + " ^ ");
		operator = "^";
		computeOperation();
	}
	
	@FXML
	void tenSquareBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			decimals = getNumberOfDigits(argument);
			secondText.setText(" 10^ " + argument + " = ");
			mainText.setText(String.format("%." + decimals + "f", Math.pow(10, argument)));
			actionPerformed = true;
		}
	}
	
	@FXML
	void ExpBtnHandler(ActionEvent event) {
		if ((!mainText.getText().isEmpty()) && (!expPressed)) {
			String str = mainText.getText();
			mainText.setText(str + ".e+0");
			expPressed = true;
		}
	}
	
	@FXML
	void delBtnHandler(ActionEvent event) {
		if ((!mainText.getText().isEmpty()) && (!expPressed)) {
			String str = mainText.getText();
			str = str.substring(0, str.length() - 1);
			mainText.setText(str);
		}
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
			secondText.setText("Memorie: " + MEMORY);
			actionPerformed = true;
		} 
		else {
			secondText.setText("");
			MEMORY = "";
		}
	}
	
	@FXML
	void MMinusBtnHandler(ActionEvent event) {
		if (!((mainText.getText().equals("0")) || (mainText.getText()
				.equals("")))) {
			secondText.setText("");
			MEMORY = "";
			actionPerformed = true;
		} 
		else {
			System.out.println("else");
		}
	}
	
	@FXML
	void MRBtnHandler(ActionEvent event) {
		if (!MEMORY.equals("")) {
			mainText.setText(MEMORY);
			arg2 = Double.parseDouble(MEMORY);
			mainText.setText(""+arg2);
		}
	}
	
	@FXML
	void MSBtnHandler(ActionEvent event) {
		if (!mainText.equals("")) {
			arg2 = Double.parseDouble(mainText.getText());
			MEMORY = "" + arg2;
			mainText.setText("");
		}
	}
	
	@FXML
	void PIBtnHandler(ActionEvent event) {
		mainText.setText(String.format("%.8f", Math.PI));
	}
	
	@FXML
	void DEGbuttonHandler(ActionEvent event) {
			DRGStatus = "degrees";
			trigFactor = 0.017453292519943295;
	}
	
	@FXML
	void RADbuttonHandler(ActionEvent event) {
		DRGStatus = "radians";
		trigFactor = 1;
	}
	
	@FXML
	void GRAbuttonHandler(ActionEvent event) {
		DRGStatus = "grads";
		trigFactor = 0.015707963267948;

	}

	@FXML
	void sinBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			results = Math.sin(trigFactor * argument);
			ComputeTrigonometricalOperation("sin", DRGStatus);
			originator.setState(argument, 0.0, "sin");
			careTaker.add(originator.saveStateToMemento());
		}
	}
	
	@FXML
	void asinBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			if (-1 <= argument && 1 >= argument) {
				if(DRGStatus.equals("degrees")) {
					results = Math.toDegrees(Math.asin(argument));
					System.out.println("deg");
				}
				else if(DRGStatus.equals("grads"))
				{
					argument = Math.toDegrees(argument);
					results = Math.sin(Math.PI * argument / 200);
					results = Math.toDegrees(Math.asin(results));
					System.out.println("grd");
				}
				else {
					results = Math.asin(argument);
					System.out.println("rad");
				}
				ComputeTrigonometricalOperation("asin", "");
				originator.setState(argument, 0.0, "asin");
				careTaker.add(originator.saveStateToMemento());
			} 
			else {
				secondText.setText("argument must be between -1 and 1");
			}
		}
	}

	@FXML
	void cosBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			results = Math.cos(trigFactor * argument);
			ComputeTrigonometricalOperation("cos", DRGStatus);
			originator.setState(argument, 0.0, "cos");
			careTaker.add(originator.saveStateToMemento());
		}
	}
	
	@FXML
	void acosBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			if (-1 <= argument && 1 >= argument) {
				if(DRGStatus.equals("degrees")) {
					results = Math.toDegrees(Math.acos(Math.sin(argument)));
					System.out.println("deg");
				}
				else if(DRGStatus.equals("grads"))
				{
					System.out.println("grd");
					argument = Math.toDegrees(argument);
					results = Math.cos(Math.PI * argument / 200);
					results = Math.toDegrees(Math.acos(results));					
				}
				else {
					results = Math.acos(argument);
					System.out.println("rad");
				}
				ComputeTrigonometricalOperation("acos", "");
				originator.setState(argument, 0.0, "acos");
				careTaker.add(originator.saveStateToMemento());
			} 
			else {
				secondText.setText("argument must be between -1 and 1");
			}
		}
	}

	@FXML
	void tanBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			results = Math.tan(trigFactor * argument);
			ComputeTrigonometricalOperation("tan", DRGStatus);
			originator.setState(argument, 0.0, "tan");
			careTaker.add(originator.saveStateToMemento());
		}
	}
	
	@FXML
	void atanBtnHandler(ActionEvent event) {
		if (!mainText.getText().isEmpty()) {
			double argument = Double.parseDouble(mainText.getText());
			if(DRGStatus.equals("degrees")) {
				results = Math.toDegrees(Math.atan(argument));
				System.out.println("deg");
			}
			else if(DRGStatus.equals("grads"))
			{
				System.out.println("grd");
				argument = Math.toDegrees(argument);
				results = Math.tan(Math.PI * argument / 200);
				results = Math.toDegrees(Math.atan(results));

			}
			else {
				results = Math.atan(argument);
				System.out.println("rad");
			}
			ComputeTrigonometricalOperation("atan", "");
			originator.setState(argument, 0.0, "atan");
			careTaker.add(originator.saveStateToMemento());
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
			} 
			else {
				secondText.setText("argument must be greater than 0 !");
			}
			actionPerformed = true;
		}
	}
	
	@FXML
	void logBtnHandler(ActionEvent event) {
		if (!(mainText.getText().isEmpty() || mainText.getText().equals("-"))) {
			double argument = Double.parseDouble(mainText.getText());
			if (argument > 0) {
				decimals = Math.max(getNumberOfDigits(argument), 6);
				secondText.setText("ln(" + argument + ") = ");
				mainText.setText(String.format("%." + decimals + "f",
						Math.log10(argument)));
				actionPerformed = true;
			} 
			else {
				secondText.setText("argument must be greater than 0 !");
			}
			actionPerformed = true;
		}
	}

	@FXML
	void equalBtnHandler(ActionEvent event) {
		equalPressed();
		System.out.println("equal pressed");
	}
	
	@FXML
	void redoBtnHandler(ActionEvent event) {
		if(careTaker.getIndex()-1 != 0){
		originator.getStateFromMemento(careTaker.getNext());
		arg1= originator.getFirstNumber();
		arg2= originator.getSecondNumber();
		operator=originator.getOperator()+"";
		mainText.setEditable(true);
		secondText.setText("");

		if(operator.indexOf(trigFunctions.values().toString()) != -1){
			mainText.setText(operator + "(" + arg1 + ")");
		}
		else{
			mainText.setText(arg1 + operator + arg2);
		}
		flag=true;
		}
	}
	
	@FXML
	void undoBtnHandler(ActionEvent event) throws ScriptException {
		if(careTaker.getIndex() > 0){
		originator.getStateFromMemento(careTaker.getPrev());
		arg1= originator.getFirstNumber();
		arg2= originator.getSecondNumber();
		operator=originator.getOperator()+"";
		//equalPressed();
		mainText.setEditable(true);
		secondText.setText("");
		if(operator.indexOf(trigFunctions.values().toString()) != -1){
			
		     mainText.setText(operator + "(" + arg1 + ")");		
		}
		else{
    mainText.setText(arg1 + operator + arg2);
    
		}
		flag=true;
		
		}
	}
	// INITIALIZE
	@FXML
	void initialize() {
		AnchorPane.setFocusTraversable(true);
		if (AnchorPane.isFocused()) {
			deg.setSelected(true);
			System.out.println("anchor pane focused");
		}
	}
	// CLOSE BUTTON
	@FXML
	void closeBtnHandler(ActionEvent event) {
		System.exit(0);
	}// END CLOSE

	public void computeOperation() {
		if (!mainText.getText().equals("")) {
			memory = Double.parseDouble(mainText.getText());
			arg1 = Double.parseDouble(mainText.getText());				
			mainText.setText("");
		} 
		else {
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
		memory = 0;
		arg1 = arg2 = 0;
		results = 0;
		careTaker.emptyList();
		actionPerformed = false;

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
			if(flag == false){
				if (!mainText.getText().equals("") && !operator.equals("yroot")) {
					arg2 = Double.parseDouble(mainText.getText());
				} 
				else {
					arg2 = 0;
				}

				if (!secondText.getText().contains("=")) {
					secondText.setText(secondText.getText() + " " + arg2 + " = ");
				}
				else {
					secondText.setText(arg2 + " " + operator + arg1 + " =");
				}
			}

			if(flag == true){
				String str=mainText.getText();

				String[] splitString = (str.split("[+*/()]"));
				String[] splitString2 = (str.split("[0-9]+[.][0-9]+"));

				if(splitString[0].equals("cos")){
					double argument = 0;
					if (!mainText.getText().isEmpty()) {
						argument = Double.parseDouble(splitString[1]);
						if (firstFunction) {
							results = Math.cos(trigFactor * argument);
							ComputeTrigonometricalOperation("cos", DRGStatus);
							originator.setState(argument, 0.0, "cos");
							careTaker.add(originator.saveStateToMemento());
						}
						else {
								if (-1 <= argument && 1 >= argument) {
									if(DRGStatus.equals("degrees")) {
										results = Math.toDegrees(Math.acos(Math.sin(argument)));
										System.out.println("deg");
									}
									else if(DRGStatus.equals("grads"))
									{
										System.out.println("grd");
										argument = Math.toDegrees(argument);
										results = Math.cos(Math.PI * argument / 200);
										results = Math.toDegrees(Math.acos(results));					
									}
									else {
										results = Math.acos(argument);
										System.out.println("rad");
									}
									ComputeTrigonometricalOperation("acos", "");
									originator.setState(argument, 0.0, "acos");
									careTaker.add(originator.saveStateToMemento());
								} 
								else {
									secondText.setText("argument must be between -1 and 1");
								}
						}
					}

					secondText.setText("cos(" + argument + ")");
				}
				else if(splitString[0].equals("sin")){
					double argument = 0;
					if (!mainText.getText().isEmpty()) {
						argument = Double.parseDouble(splitString[1]);
						if (firstFunction) {
							results = Math.sin(trigFactor * argument);
							ComputeTrigonometricalOperation("sin", DRGStatus);
							originator.setState(argument, 0.0, "sin");
							careTaker.add(originator.saveStateToMemento());
						}
						else {
							if (-1 <= argument && 1 >= argument) {
								if(DRGStatus.equals("degrees")) {
									results = Math.toDegrees(Math.asin(argument));
									System.out.println("deg");
								}
								else if(DRGStatus.equals("grads"))
								{
									argument = Math.toDegrees(argument);
									results = Math.sin(Math.PI * argument / 200);
									results = Math.toDegrees(Math.asin(results));
									System.out.println("grd");
								}
								else {
									results = Math.asin(argument);
									System.out.println("rad");
								}
								ComputeTrigonometricalOperation("asin", "");
								originator.setState(argument, 0.0, "asin");
								careTaker.add(originator.saveStateToMemento());
							} 
							else {
								secondText.setText("argument must be between -1 and 1");
							}
						}
																																								
					}

					secondText.setText("sin(" + argument + ")");
				}
				else if(splitString[0].equals("tan")){
					double argument = 0;
					if (!mainText.getText().isEmpty()) {
						argument = Double.parseDouble(splitString[1]);
						if (firstFunction) {
							results = Math.tan(trigFactor * argument);
							ComputeTrigonometricalOperation("tan", DRGStatus);
							originator.setState(argument, 0.0, "tan");
							careTaker.add(originator.saveStateToMemento());
						}
						else {
							if (-1 <= argument && 1 >= argument) {
								if(DRGStatus.equals("degrees")) {
									results = Math.toDegrees(Math.atan(argument));
									System.out.println("deg");
								}
								else if(DRGStatus.equals("grads"))
								{
									argument = Math.toDegrees(argument);
									results = Math.tan(Math.PI * argument / 200);
									results = Math.toDegrees(Math.atan(results));
								}
								else {
									results = Math.atan(argument);
									System.out.println("rad");
								}
								ComputeTrigonometricalOperation("atan", "");
								originator.setState(argument, 0.0, "atan");
								careTaker.add(originator.saveStateToMemento());
							} 															
						}
																																								
					}

					secondText.setText("cos(" + argument + ")");
				}
				else {
					arg1=Double.parseDouble(splitString[0]);
					arg2=Double.parseDouble(splitString[1]);
					operator=splitString2[1];
					System.out.println(arg1 + "" + operator +"" + arg2);
					secondText.setText(arg1 + " " + operator + arg2 + " =");
				}
				flag = false;
			}
			if (operator.equals("+")) {
				if(percentPressed) {
					arg2 = arg1 * arg2 / 100;
				}
				results = arg1 + arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}

			if (operator.equals("-")) {
				if(percentPressed) {
					arg2 = arg1 * arg2 / 100;
				}
				results = arg1 - arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}


			if (operator.equals("/")) {
				if(percentPressed) {
					arg2 = arg1 * arg2 / 100;
				}
				if (arg2 == 0) {
					results = 0;
					nan = true;
				} 
				else {
					results = arg1 / arg2;
					decimals = Math.max(5, Math.max(getNumberOfDigits(arg1),
							getNumberOfDigits(arg2)));
				}
			}

			if (operator.equals("*")) {
				if(percentPressed) {
					arg2 = arg1 * arg2 / 100;
				}
				results = arg1 * arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}

			if (operator.equals("^")) {
				if(percentPressed) {
					arg2 = arg1 * arg2 / 100;
				}
				results = Math.pow(arg1, arg2);
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}
			
			if (operator.equals("mod")) {
				results = arg1 % arg2;
				decimals = Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2));
			}

			if (operator.equals("yroot")) {
				arg2 = Double.parseDouble(mainText.getText());
				if(arg2 != 0)
				{
					arg1 = 1.0 / arg1;
				}
//				else {
//					arg2 = 1.0 / arg2;
//				}
				results = Math.pow(arg2, arg1);
				arg1 = 1/arg1;
				secondText.setText(arg1 + " yroot " + arg2 );
			}
			
			if (operator.equals("3root")) {
				arg2 = 1.0 / 3.0;
				System.out.println("arg2 = " + arg2);
				results = Math.pow(arg1, arg2);
				secondText.setText(" 3root " + arg1 );
			}

			if (operator.equals("rad")) {
				results = Math.pow(arg1, (double) (1 / arg2));
				decimals = Math.max(Math.max(getNumberOfDigits(arg1),
						getNumberOfDigits(arg2)), 7);
			}

			originator.setState(arg1, arg2, operator);
			careTaker.add(originator.saveStateToMemento());
			if( nan == false){
			mainText.setText(String.format(("%." + decimals + "f"), results));
			}
			else{
				mainText.setText("Math Error");
				nan = false;
			}

			percentPressed = false;
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
	//Change screen size
	@FXML
	void onRegular(ActionEvent event) throws IOException {
		prevStage.setWidth(prevStage.getWidth()-318);
		closeBtn.setLayoutX(280);
		calc.setLayoutX(150);
		mainText.setPrefWidth(300);
		secondText.setPrefWidth(280);

	}
	//Change screen size
	@FXML
	void onScientific(ActionEvent event) throws IOException {
		prevStage.setWidth(sceneW);
		closeBtn.setLayoutX(593);
		calc.setLayoutX(285);
		mainText.setPrefWidth(609);
		secondText.setPrefWidth(598);

	}
	

}

	
	


