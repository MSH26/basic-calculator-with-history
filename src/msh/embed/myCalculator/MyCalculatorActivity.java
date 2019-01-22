package msh.embed.myCalculator;


import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyCalculatorActivity extends Activity {
    /** Called when the activity is first created. */
    
	/*SharedPreferences sharedPrefereces;
	String HistoryLog = "";
	Boolean historyLogstatus = false;*/
	FileOutputStream outputStream;
	/* Expression related variables*/
	String expression = "";
	Boolean zeroStatusFlag = false;
	/* Result related variables*/
	String result = "";
	/* Temporary memory related variables*/
	Double temporaryMemory = 0.0;
	String temporaryMemoryExpression = "";
	Boolean temporaryMemoryKeyPressFlag = false;
	
	EditText textBox;
	TextView textView;
	Button button_zero;
	Button button_one;
	Button button_two;
	Button button_three;
	Button button_four;
	Button button_five;
	Button button_six;
	Button button_seven;
	Button button_eight;
	Button button_nine;
	Button button_dot;
	Button button_summation;
	Button button_substraction;
	Button button_multiplication;
	Button button_division;
	Button button_equal;
	Button button_percentage;
	Button button_tutorial;
	Button button_delete;
	Button button_clear;
	Button button_history;
	Button button_result;
	Button button_memoryClear;
	Button button_memoryRetrieve;
	Button button_memorySave;
	Button button_memoryPush;
	Button button_memoryPop;
	//Button button_signAlter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        setContentView(R.layout.main);

        //sharedPrefereces = getSharedPreferences("MyCalculatorPrefereces", 0);
        
    	try {
    	    outputStream = openFileOutput("historyFile.txt", Context.MODE_APPEND);
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}  
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        textBox = (EditText)findViewById(R.id.text_textBox);
        textView = (TextView)findViewById(R.id.text_textView);
    	button_zero = (Button)findViewById(R.id.button_zero);
    	button_one = (Button)findViewById(R.id.button_one);
    	button_two = (Button)findViewById(R.id.button_two);
    	button_three = (Button)findViewById(R.id.button_three);
    	button_four = (Button)findViewById(R.id.button_four);
    	button_five = (Button)findViewById(R.id.button_five);
    	button_six = (Button)findViewById(R.id.button_six);
    	button_seven = (Button)findViewById(R.id.button_seven);
    	button_eight = (Button)findViewById(R.id.button_eight);
    	button_nine = (Button)findViewById(R.id.button_nine);
    	button_dot = (Button)findViewById(R.id.button_dot);
    	button_summation = (Button)findViewById(R.id.button_summation);
    	button_substraction = (Button)findViewById(R.id.button_substraction);
    	button_multiplication = (Button)findViewById(R.id.button_multiplication);
    	button_division = (Button)findViewById(R.id.button_division);
    	button_equal = (Button)findViewById(R.id.button_equal);
    	button_percentage = (Button)findViewById(R.id.button_percentage);
    	button_tutorial = (Button)findViewById(R.id.button_tutorial);
    	button_delete = (Button)findViewById(R.id.button_delete);
    	button_clear = (Button)findViewById(R.id.button_clear);
    	button_history = (Button)findViewById(R.id.button_history);
    	button_result = (Button)findViewById(R.id.button_result);
    	button_memoryClear = (Button)findViewById(R.id.button_memoryClear);
    	button_memoryRetrieve = (Button)findViewById(R.id.button_memoryRetrieve);
    	button_memorySave = (Button)findViewById(R.id.button_memorySave);
    	button_memoryPush = (Button)findViewById(R.id.button_memoryPush);
    	button_memoryPop = (Button)findViewById(R.id.button_memoryPop);
    	//button_signAlter = (Button)findViewById(R.id.button_signAlter);    	
    }

    @Override
    public void onPause() {
        super.onPause();
        
        textBox = null;
        textView = null;
    	button_zero = null;
    	button_one = null;
    	button_two = null;
    	button_three = null;
    	button_four = null;
    	button_five = null;
    	button_six = null;
    	button_seven = null;
    	button_eight = null;
    	button_nine = null;
    	button_dot = null;
    	button_summation = null;
    	button_substraction = null;
    	button_multiplication = null;
    	button_division = null;
    	button_equal = null;
    	button_percentage = null;
    	button_tutorial = null;
    	button_delete = null;
    	button_clear = null;
    	button_history = null;
    	button_result = null;
    	button_memoryClear = null;
    	button_memoryRetrieve = null;
    	button_memorySave = null;
    	button_memoryPush = null;
    	button_memoryPop = null;
    	//button_signAlter = null; 
    }

    @Override
    protected void onStop() {
        super.onStop();

        setVisible(false);

    	try {
    		outputStream.close();
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}  
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        setVisible(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    private void setDigitToExpressionAndScreen(int character){
		if(temporaryMemoryKeyPressFlag && expression.charAt(expression.length()-1) != ')'){
    		expression = Integer.toString(character);
    		textBox.setText(Integer.toString(character));
    		textBox.setSelection(textBox.getText().length());
    		zeroStatusFlag = false;
    		temporaryMemoryKeyPressFlag = false;
    	}
    	else{
        	String tempExpression = expression + character;
    		String tempText = textBox.getText().toString() + character;
    		if(zeroStatusFlag){
    			tempExpression = expression.substring(0, expression.trim().length() - 1) + character;
    			tempText = textBox.getText().toString().substring(0, textBox.getText().toString().trim().length() - 1) + character;
    			zeroStatusFlag = false;
    		}
    		expression = tempExpression;
    		textBox.setText(setCommaToTextExpression(tempText));
    		textBox.setSelection(textBox.getText().length());
    	}
    }
    
    private String setCommaToTextExpression(String stringTextExpression){
		String number = "";
		
    	char[] digit = stringTextExpression.toCharArray();
    	int index = 0;
		for(int i=(digit.length - 1); i>=0 ; i--){
			if(digit[i] == '+' || digit[i] == '-' ||digit[i] == '*' ||digit[i] == '/'){
				index = i+1;
				break;
			}
			index = i;
			if(digit[i] != ','){
				number = digit[i] + number;
			}
		}
		stringTextExpression = stringTextExpression.substring(0, index);
		
		String[] separatedNumberByDot = number.split("\\.");
		number = "";  // clearing number variable for reuse in another operation
		
		digit = separatedNumberByDot[0].toCharArray();
		for(int i=(digit.length - 1), counter=1; i>=0 ; i--, counter++){
			number = digit[i] + number;
			if(counter==3 && i!=0){
				number = "," + number;
				counter = 0;
			}
		}
		if(separatedNumberByDot.length == 1){
			return stringTextExpression = stringTextExpression + number;
		}
		else{
			return stringTextExpression = stringTextExpression + number + "." + separatedNumberByDot[1];
		}
    }
    
    public void onClickButtonZeroMethod(View v){
    	if(button_zero == (Button)v){
    		if(!expression.trim().equals("")){
    			if(expression.charAt(expression.length()-1) != ')'){
	    			String testNumber = "";
	        		int index = expression.length();
	        		for(int i=1; i<=expression.length(); i++){
	        			char character = expression.charAt(expression.length() - i);
	    				index = expression.length() - i;
	        			if(character == '+' || character == '-' || character == '/' || character == '*'){
	        				index = ( expression.length() - i ) + 1;
	        				break;
	        			}
	        		}
	        		testNumber = expression.substring(index);
	        		if(testNumber.length() == 0){
	        			zeroStatusFlag = true;
	        			expression = expression + "0";
	        			textBox.setText(setCommaToTextExpression(textBox.getText().toString() + "0"));
	        		}
	        		else{
	        			if(zeroStatusFlag == false){
	        				expression = expression + "0";
	        				textBox.setText(setCommaToTextExpression(textBox.getText().toString() + "0"));
	        			}
	        		}
    			}
    		}
    		else{
    			zeroStatusFlag = true;
    			expression = expression + "0";
    			textBox.setText(setCommaToTextExpression(textBox.getText().toString() + "0"));
    		}
    	}
    }
    
    public void onClickButtonOneMethod(View v){
    	if(button_one == (Button)v){
    		setDigitToExpressionAndScreen(1);
    	}
    }
    
    public void onClickButtonTwoMethod(View v){
    	if(button_two == (Button)v){
    		setDigitToExpressionAndScreen(2);
    	}
    }
    
    public void onClickButtonThreeMethod(View v){
    	if(button_three == (Button)v){
    		setDigitToExpressionAndScreen(3);
    	}
    }
    
    public void onClickButtonFourMethod(View v){
    	if(button_four == (Button)v){
    		setDigitToExpressionAndScreen(4);
    	}
    }
    
    public void onClickButtonFiveMethod(View v){
    	if(button_five == (Button)v){
    		setDigitToExpressionAndScreen(5);
    	}
    }
    
    public void onClickButtonSixMethod(View v){
    	if(button_six == (Button)v){
    		setDigitToExpressionAndScreen(6);
    	}
    }
    
    public void onClickButtonSevenMethod(View v){
    	if(button_seven == (Button)v){
    		setDigitToExpressionAndScreen(7);
    	}
    }
    
    public void onClickButtonEightMethod(View v){
    	if(button_eight == (Button)v){
    		setDigitToExpressionAndScreen(8);
    	}
    }
    
    public void onClickButtonNineMethod(View v){
    	if(button_nine == (Button)v){
    		setDigitToExpressionAndScreen(9);
    	}
    }
    
    public void onClickButtonDotMethod(View v){
    	if(button_dot == (Button)v){
    		if(!expression.trim().equals("")){
        		int counter = 0;
        		for(int i=1; i<=expression.length(); i++){
        			char character = expression.charAt(expression.length() - i);
        			if(character == '.'){
        				++counter;
        			}
        			if(character == '+' || character == '-' || character == '/' || character == '*'){
        				break;
        			}
        		}
        		if(counter == 0){
        			if(temporaryMemoryKeyPressFlag){
        				expression = ".";
            			textBox.setText(".");
            			textBox.setSelection(textBox.getText().length());
            			zeroStatusFlag = false;
                		temporaryMemoryKeyPressFlag = false;
        			}
        			else{
            			expression = expression + ".";
            			textBox.setText(textBox.getText().toString() + ".");
            			textBox.setSelection(textBox.getText().length());
            			zeroStatusFlag = false;
                		temporaryMemoryKeyPressFlag = false;
        			}
        		}
    		}
    		else{
    			if(temporaryMemoryKeyPressFlag){
        			expression = ".";
        			textBox.setText(".");
        			textBox.setSelection(textBox.getText().length());
        			zeroStatusFlag = false;
            		temporaryMemoryKeyPressFlag = false;
    			}
    			else{
        			expression = expression + ".";
        			textBox.setText(textBox.getText().toString() + ".");
        			textBox.setSelection(textBox.getText().length());
        			zeroStatusFlag = false;
            		temporaryMemoryKeyPressFlag = false;
        		}
    		}
    	}
    }
    
    public void onClickButtonSummationMethod(View v){
    	if(button_summation == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length() - 1);
        		if(character != '.' && character != '+' && character != '-' && character != '*' && character != '/'){
        			expression = expression + "+";
        			textBox.setText(textBox.getText().toString() + "+");
        			textBox.setSelection(textBox.getText().length());
            		temporaryMemoryKeyPressFlag = false;
        		}
    		}
    		else if(expression.trim().equals("")){
    			expression = expression + "+";
    			textBox.setText(textBox.getText().toString() + "+");
    			textBox.setSelection(textBox.getText().length());
        		temporaryMemoryKeyPressFlag = false;
    		}
    	}
    }
    
    public void onClickButtonSubstractionMethod(View v){
    	if(button_substraction == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length() - 1);
    			if(character != '.' && character != '+' && character != '-' && character != '*' && character != '/'){
    				expression = expression + "-";
        			textBox.setText(textBox.getText().toString() + "-");
        			textBox.setSelection(textBox.getText().length());
            		temporaryMemoryKeyPressFlag = false;
        		}
    		}
    		else if(expression.trim().equals("")){
    			expression = expression + "-";
    			textBox.setText(textBox.getText().toString() + "-");
    			textBox.setSelection(textBox.getText().length());
        		temporaryMemoryKeyPressFlag = false;
    		}
    	}
    }
    
    public void onClickButtonMultiplicationMethod(View v){
    	if(button_multiplication == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length() - 1);
    			if(character != '.' && character != '+' && character != '-' && character != '*' && character != '/'){
    				expression = expression + "*";
        			textBox.setText(textBox.getText().toString() + "*");
        			textBox.setSelection(textBox.getText().length());
            		temporaryMemoryKeyPressFlag = false;
        		}
    		}
    	}
    }
    
    public void onClickButtonDivisionMethod(View v){
    	if(button_division == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length() - 1);
    			if(character != '.' && character != '+' && character != '-' && character != '*' && character != '/'){
    				expression = expression + "/";
        			textBox.setText(textBox.getText().toString() + "/");
        			textBox.setSelection(textBox.getText().length());
            		temporaryMemoryKeyPressFlag = false;
        		}
    		}
    	}
    }
    

    public void onClickButtonTutorialMethod(View v){
    	if(button_tutorial == (Button)v){
    		zeroStatusFlag = false;
    		temporaryMemoryKeyPressFlag = false;
    		
    		Intent intent = new Intent(MyCalculatorActivity.this, GuidelineActivity.class);
    		startActivity(intent);
    		finish();
    	}
    }
    
    public void onClickButtonResultMethod(View v){
    	if(button_result == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length()-1);
        		if(character == '+' || character == '-'){
        			if(!result.equals("")){
        				if(result.charAt(0) != '+' && result.charAt(0) != '-'){
            				expression = expression + result;
            				textBox.setText(textBox.getText().toString() + result);
            				textBox.setSelection(textBox.getText().length());
            			}
            			else{
            				expression = expression.substring(0, expression.length()-1) + result;
            				textBox.setText(textBox.getText().toString().substring(0, textBox.getText().toString().length()-1) + result);
            				textBox.setSelection(textBox.getText().length());
            			}
        			}
        		}
        		else if(character == '*' || character == '/'){
        			if(!result.equals("")){
        				if(result.charAt(0) != '+' && result.charAt(0) != '-'){
            				expression = expression + result;
            				textBox.setText(textBox.getText().toString() + result);
            				textBox.setSelection(textBox.getText().length());
            			}
            			else{
            				if(result.charAt(0) == '-'){
    	        				expression = expression + "(" + result + ")";
    	        				textBox.setText(textBox.getText().toString() + "(" + result + ")");
            				}
    	    				else if(result.charAt(0) == '+'){
    	    					expression = expression.substring(0, expression.length()-1) + "(" + result + ")";
    	        				textBox.setText(textBox.getText().toString().substring(0, textBox.getText().toString().length()-1) + result.substring(1));
    	        				textBox.setSelection(textBox.getText().length());
    	    				}
            			}
        			}
        		}
        		else if(character != '+' && character != '-' && character != '*' && character != '/'){
        			if(!result.equals("")){
        				if(result.charAt(0) == '-'){
            				expression = expression + result;
            				textBox.setText(textBox.getText().toString() + result);
            				textBox.setSelection(textBox.getText().length());
            			}
            			else{
            				Toast.makeText(MyCalculatorActivity.this,"Please insert an operator first!!",Toast.LENGTH_LONG).show();
            			}
        			}
        		}
    		}
    		else{
    			if(!result.equals("")){
        			expression = result;
    				textBox.setText(result);
    				textBox.setSelection(textBox.getText().length());
    			}
    		}
    	}
    } 
    
    public void onClickButtonMemoryClearMethod(View v){
    	if(button_memoryClear == (Button)v){
    		temporaryMemory = 0.0;
    		temporaryMemoryExpression = "";
    	}
    }
    
    public void onClickButtonMemoryRetrieveMethod(View v){
    	if(button_memoryRetrieve == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length()-1);
        		if(character == '+' || character == '-'){
        			if(temporaryMemory < 0.0){
        	    		textBox.setText(textBox.getText().toString().substring(0, textBox.getText().toString().length()-1) + "-" + Double.toString(temporaryMemory));
        	    		textBox.setSelection(textBox.getText().length());
        	    		expression = expression.substring(0, expression.length()-1) + "-" + Double.toString(temporaryMemory);
        	    		temporaryMemoryKeyPressFlag = true;
        			}
        			else{
        	    		textBox.setText(textBox.getText().toString() + Double.toString(temporaryMemory));
        	    		textBox.setSelection(textBox.getText().length());
        	    		expression = expression + Double.toString(temporaryMemory);
        	    		temporaryMemoryKeyPressFlag = true;
        			}
        		}
        		else if(character == '*' || character == '/'){
        			if(temporaryMemory < 0.0){
        	    		textBox.setText(textBox.getText().toString() + "(-" + Double.toString(temporaryMemory) + ")");
        	    		textBox.setSelection(textBox.getText().length());
        	    		expression = expression + "(-" + Double.toString(temporaryMemory) + ")";
        	    		temporaryMemoryKeyPressFlag = true;
        			}
        			else{
        	    		textBox.setText(textBox.getText().toString() + Double.toString(temporaryMemory));
        	    		textBox.setSelection(textBox.getText().length());
        	    		expression = expression + Double.toString(temporaryMemory);
        	    		temporaryMemoryKeyPressFlag = true;
        			}
        		}
        		else if(character == ')'){
        			//Do nothing.
        		}
        		else{
        			char[] digit = expression.toCharArray();
    				int index = 0;
    				for(int i=(digit.length - 2); i>=0 ; i--){
    					if(digit[i] == '-' || digit[i] == '+' || digit[i] == '/' || digit[i] == '*'){
    						index = i;
    						break;
    					}
    				}
/*Caution sakib!*/  expression = expression.substring(0,index+1) + Double.toString(temporaryMemory);
    				
    				digit = textBox.getText().toString().toCharArray();
    				index = 0;
    				for(int i=(digit.length - 2); i>=0 ; i--){
    					if(digit[i] == '-' || digit[i] == '+' || digit[i] == '/' || digit[i] == '*'){
    						index = i;
    						break;
    					}
    				}
/*Caution sakib!*/ 	textBox.setText(textBox.getText().toString().substring(0,index+1) + Double.toString(temporaryMemory));
					textBox.setSelection(textBox.getText().length());
					temporaryMemoryKeyPressFlag = true;
        		}
    		}
    		else{
    			textBox.setText(Double.toString(temporaryMemory));
    			textBox.setSelection(textBox.getText().length());
	    		expression = Double.toString(temporaryMemory);
	    		temporaryMemoryKeyPressFlag = true;
    		}
    	}
    }
    
    public void onClickButtonMemorySaveMethod(View v){
    	if(button_memorySave == (Button)v){
    		try{
    			Pattern p = Pattern.compile("([0-9]+)((\\.([0-9]+))?)");
    			Matcher m = p.matcher(expression);
    			if(m.matches()){
    				temporaryMemory = Double.parseDouble(expression);
    	    		temporaryMemoryExpression = expression;
    	    		expression = "";
    	    		textBox.setText("");
    	    		textBox.setSelection(textBox.getText().length());
    			}
    			else{
    				if(!expression.trim().equals("")){
    	    			char character = expression.charAt(expression.length() - 1);
    					if(character == '.' || character == '+' || character == '-' || character == '*' || character == '/'){
    						Toast.makeText(MyCalculatorActivity.this,"Invalid expression!!",Toast.LENGTH_LONG).show();
    					}
    					else{
    						temporaryMemory = calculate();
    	    	    		temporaryMemoryExpression = expression;
    	    	    		expression = "";
    	    	    		textBox.setText("");
    	    	    		textBox.setSelection(textBox.getText().length());
    					}
    	    		}
    			}
    		}
    		catch(PatternSyntaxException e){
    			Toast.makeText(MyCalculatorActivity.this,"Pattern syntax exception occurs!!"+expression,Toast.LENGTH_LONG).show();
    		}
    		catch(Exception e){
    			Toast.makeText(MyCalculatorActivity.this,"Exception occurs!!",Toast.LENGTH_LONG).show();
    		}
    	}
    }
    
    public void onClickButtonMemoryPushMethod(View v){
    	if(button_memoryPush == (Button)v){
    		try{
    			Pattern p = Pattern.compile("([0-9]+)((\\.([0-9]+))?)");
    			Matcher m = p.matcher(expression);
    			if(m.matches()){
    				temporaryMemory += Double.parseDouble(expression);
    				temporaryMemoryExpression = temporaryMemoryExpression + "+" + expression;
    	    		expression = "";
    	    		textBox.setText("");
    	    		textBox.setSelection(textBox.getText().length());
    			}
    			else{
    				if(!expression.trim().equals("")){
    	    			char character = expression.charAt(expression.length() - 1);
    					if(character == '.' || character == '+' || character == '-' || character == '*' || character == '/'){
    						Toast.makeText(MyCalculatorActivity.this,"Invalid expression!!",Toast.LENGTH_LONG).show();
    					}
    					else{
    						temporaryMemory += calculate();
    	    				temporaryMemoryExpression = temporaryMemoryExpression + "+" + expression;
    	    				expression = "";
    	    	    		textBox.setText("");
    	    	    		textBox.setSelection(textBox.getText().length());
    					}
    	    		}
    			}
    		}
    		catch(PatternSyntaxException e){
    			Toast.makeText(MyCalculatorActivity.this,"Pattern syntax exception occurs!!"+expression,Toast.LENGTH_LONG).show();
    		}
    		catch(Exception e){
    			Toast.makeText(MyCalculatorActivity.this,"Exception occurs!!",Toast.LENGTH_LONG).show();
    		}
    	}
    }
    
    public void onClickButtonMemoryPopMethod(View v){
    	if(button_memoryPop == (Button)v){
    		try{
    			Pattern p = Pattern.compile("([0-9]+)((\\.([0-9]+))?)");
    			Matcher m = p.matcher(expression);
    			if(m.matches()){
    				temporaryMemory -= Double.parseDouble(expression);
    				temporaryMemoryExpression = temporaryMemoryExpression + "-" + expression;
    				expression = "";
    	    		textBox.setText("");
    	    		textBox.setSelection(textBox.getText().length());
    			}
    			else{
    				if(!expression.trim().equals("")){
    	    			char character = expression.charAt(expression.length() - 1);
    					if(character == '.' || character == '+' || character == '-' || character == '*' || character == '/'){
    						Toast.makeText(MyCalculatorActivity.this,"Invalid expression!!",Toast.LENGTH_LONG).show();
    					}
    					else{
    						temporaryMemory -= calculate();
    	    				temporaryMemoryExpression = temporaryMemoryExpression + "-" + expression;
    	    				expression = "";
    	    	    		textBox.setText("");
    	    	    		textBox.setSelection(textBox.getText().length());
    					}
    	    		}
    			}
    		}
    		catch(PatternSyntaxException e){
    			Toast.makeText(MyCalculatorActivity.this,"Pattern syntax exception occurs!!"+expression,Toast.LENGTH_LONG).show();
    		}
    		catch(Exception e){
    			Toast.makeText(MyCalculatorActivity.this,"Exception occurs!!",Toast.LENGTH_LONG).show();
    		}
    	}
    }
    
    /*public void onClickButtonSignAlterMethod(View v){
    	if(button_signAlter == (Button)v){
    		try{
    			Pattern p = Pattern.compile("((\\+|\\-)?)([0-9]+)((\\.([0-9]+))?)");
    			Matcher m = p.matcher(expression);
    			if(m.matches()){
    				if(expression.charAt(0) == '-'){
        				expression = "+" + expression.substring(1);
        				textBox.setText("+" + textBox.getText().toString().substring(1));
    				}
    				else if(expression.charAt(0) == '+'){
        				expression = "-" + expression.substring(1);
        				textBox.setText("-" + textBox.getText().toString().substring(1));
    				}
    				else{
        				expression = "-" + expression;
        				textBox.setText("-" + textBox.getText().toString());
    				}
    			}
    			else{
    				Toast.makeText(MyCalculatorActivity.this,"Invalid operation!!",Toast.LENGTH_LONG).show();
    			}
    		}
    		catch(PatternSyntaxException e){
    			Toast.makeText(MyCalculatorActivity.this,"Pattern syntax exception occurs!!"+expression,Toast.LENGTH_LONG).show();
    		}
    		catch(Exception e){
    			Toast.makeText(MyCalculatorActivity.this,"Exception occurs!!",Toast.LENGTH_LONG).show();
    		}
    	}
    }*/
    
    public void onClickButtonPercentageMethod(View v){
    	if(button_percentage == (Button)v){
    		try{
    			Pattern p = Pattern.compile("([0-9]+)((\\.([0-9]+))?)(\\*)([0-9]+)((\\.([0-9]+))?)");
    			Matcher m = p.matcher(expression);
    			if(m.matches()){
    				String[] numbers = expression.split("\\*");
    				result = Double.toString((Double.parseDouble(numbers[0]) * Double.parseDouble(numbers[1])) / 100);
    				
    				DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
		    		String fileContents = "(" + expression.trim() + ")%|" + result.trim() + "|" + dateFormat.format(Calendar.getInstance().getTime()).trim() + "\n";
		        	try {
			    	    outputStream.write(fileContents.getBytes());
		        	} 
		        	catch (Exception e) {
		        	    e.printStackTrace();
		        	}
    				
    				/*if(historyLogstatus == false){
    					HistoryLog = expression.trim() + ":" + result;
    					historyLogstatus = true;
    				}
    				else{
    					HistoryLog = HistoryLog + ";" + expression.trim() + ":" + result;
    				}*/
    		    	textView.setText(expression);
    				textBox.setText(result);
    		    	expression = "" + result;
    			}
    			else{
    				Toast.makeText(MyCalculatorActivity.this,"Invalid expression!!",Toast.LENGTH_LONG).show();
    			}
    		}
    		catch(PatternSyntaxException e){
    			Toast.makeText(MyCalculatorActivity.this,"Pattern syntax exception occurs!!"+expression,Toast.LENGTH_LONG).show();
    		}
    		catch(Exception e){
    			Toast.makeText(MyCalculatorActivity.this,"Exception occurs!!",Toast.LENGTH_LONG).show();
    		}
    	}
    }
    
    public void onClickButtonDeleteMethod(View v){
    	if(button_delete == (Button)v){
    		String textExpression = textBox.getText().toString();
    		if(textExpression.equals("") == false)
    		{
    			if(expression.charAt(expression.length()-1) == ')'){
    				char[] digit = expression.toCharArray();
    				int index = expression.length();
    				for(int i=(digit.length - 2); i>=0 ; i--){
    					if(digit[i] == '('){
    						index = i;
    						break;
    					}
    				}
    				expression = expression.substring(0,index);
    				
    				digit = textExpression.toCharArray();
    				index = textExpression.length();
    				for(int i=(digit.length - 2); i>=0 ; i--){
    					if(digit[i] == '('){
    						index = i;
    						break;
    					}
    				}
    				textBox.setText(textExpression.substring(0,index));
    				textBox.setSelection(textBox.getText().length());
    	    		zeroStatusFlag = false;
            		temporaryMemoryKeyPressFlag = false;
    			}
    			else{
        			if(textExpression.charAt(textExpression.length()-1) != ','){
            			expression = expression.substring(0, (expression.length() - 1));
        			}
        			textBox.setText(textExpression.substring(0, (textExpression.length() - 1)));
        			textBox.setSelection(textBox.getText().length());
            		zeroStatusFlag = false;
            		temporaryMemoryKeyPressFlag = false;
    			}
    		}
    	}
    }
    
    public void onClickButtonClearMethod(View v){
    	if(button_clear == (Button)v){
    		expression = "";
    		textBox.setText("");
    		textBox.setSelection(textBox.getText().length());
    		zeroStatusFlag = false;
    		temporaryMemoryKeyPressFlag = false;
    	}
    }
    
    public void onClickButtonHistoryMethod(View v){
    	if(button_history == (Button)v){
    		zeroStatusFlag = false;
    		temporaryMemoryKeyPressFlag = false;
    		
    		/*Editor editor = sharedPrefereces.edit();
    		editor.putString("HistoryLog", HistoryLog);
    		editor.commit();*/
    		
    		Intent intent = new Intent(MyCalculatorActivity.this, MyCalculatorHistoryActivity.class);
    		startActivity(intent);
    		finish();
    	}
    }
    
    public void onClickButtonEqualMethod(View v){
    	if(button_equal == (Button)v){
    		if(!expression.trim().equals("")){
    			char character = expression.charAt(expression.length() - 1);
				if(character == '.' || character == '+' || character == '-' || character == '*' || character == '/'){
					Toast.makeText(MyCalculatorActivity.this,"Invalid expression!!",Toast.LENGTH_LONG).show();
				}
				else{
		    		result = Double.toString(calculate());
		    		
		    		DateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy, HH:mm");
		    		String fileContents = expression.trim() + "|" + result.trim() + "|" + dateFormat.format(Calendar.getInstance().getTime()).trim() + "\n";
		        	try {
			    	    outputStream.write(fileContents.getBytes());
		        	} 
		        	catch (Exception e) {
		        	    e.printStackTrace();
		        	}
		    		
		    		/*if(historyLogstatus == false){
    					HistoryLog = expression.trim() + ":" + result.trim();
    					historyLogstatus = true;
    				}
    				else{
    					HistoryLog = HistoryLog + ";" + expression.trim() + ":" + result.trim();
    				}*/
		        	textView.setText(expression);
		    		textBox.setText(result);
		    		textBox.setSelection(textBox.getText().length());
		        	expression = "" + result;
		    		zeroStatusFlag = false;
		    		temporaryMemoryKeyPressFlag = true;
				}
    		}
    		/*else{
    			Toast.makeText(MyCalculatorActivity.this,"Please insert an expression!!",Toast.LENGTH_LONG).show();
    		}*/
    	}
    }
    
    private Double calculate(){
    	try{
    		char[] digit = expression.toCharArray();
    		Boolean firstDigitStatus = true;
    		if(digit[0] == '+' || digit[0] == '-'){
    			firstDigitStatus = false;
    		}
    		
    		Stack <Double> operandStack = new Stack<Double>();
    		Stack <Character> operatorStack = new Stack<Character>();
    		String number = "";
    		Boolean signFlag = false;
    		for(int i=(digit.length - 1); i>=0 ; i--){
    			if(digit[i] == ')'){
    				signFlag = true;
    			}
    			if(digit[i]=='+' || digit[i]=='-' || digit[i]=='/' || digit[i]=='*'){
    				if(signFlag == true){
    					number = Character.toString(digit[i]) + number;
    					signFlag = false;
    				}
    				else{
        				operandStack.push(Double.parseDouble(number));
        				number = "";
        				operatorStack.push(new Character(digit[i]));
    				}
    			}
    			else{
    				if(digit[i] != '(' && digit[i] != ')'){
            			number = Character.toString(digit[i]) + number;
    				}
    			}
    		}
    		if(!number.equals("")){
    			operandStack.push(Double.parseDouble(number));
    		}
    		if(operandStack.size() == 1 && operatorStack.size() == 1){
    			char operatorWithSinglenumber = operatorStack.pop();
    			if(operatorWithSinglenumber == '-'){
    				Double num = operandStack.pop();
    				operandStack.push(substraction(0.0, num));
    			}
    		}
    		
    		if(!operandStack.empty()){
    			if(!operatorStack.empty()){
    				Double result = operandStack.pop();
    			    Double secondNumber = operandStack.pop();
    			    Character operator;
    			    if(!firstDigitStatus){
    			    	operator = operatorStack.pop();
    			    	if(operator == '+'){
    			    		result = summation(0.0, result);
    			    	}
    			    	else if(operator == '-'){
    			    		result = substraction(0.0, result);
    			    	}
    			    	firstDigitStatus = true;
    			    }
    			    while(!operatorStack.empty()){
    					operator = operatorStack.pop();
    					if(operator == '*'){
    	    	    		result = multiplication(result, secondNumber);
    	    	    		if(!operandStack.empty()){
    	    	    			secondNumber = operandStack.pop();
    	    	    		}
    	    	    	}
    	    	    	else if(operator == '/'){
    	    	    		result = division(result, secondNumber);
    	    	    		if(!operandStack.empty()){
    	    	    			secondNumber = operandStack.pop();
    	    	    		}
    	    	    	}
    	    	    	else if(operator == '+'){
    	    	    		if(!operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/'))){
    	    	    			Double thirdNumber = operandStack.pop();
    	    	    			Boolean priorityFlag = true;
    	    	    			do{
    		    	    			Character priorityOperator = operatorStack.pop();
    		    	    			if(priorityOperator == '*'){
    		    	    				secondNumber = multiplication(secondNumber, thirdNumber);
    		    	    				if(!operandStack.empty() && !operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/'))){
    		    	    	    			thirdNumber = operandStack.pop();
    		    	    	    		}
    		    	    	    		else{
    		    	    	    			priorityFlag = false;
    		    	    	    		}
    		    	    	    	}
    		    	    	    	else if(priorityOperator == '/'){
    		    	    	    		secondNumber = division(secondNumber, thirdNumber);
    		    	    	    		if(!operandStack.empty() && !operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/'))){
    		    	    	    			thirdNumber = operandStack.pop();
    		    	    	    		}
    		    	    	    		else{
    		    	    	    			priorityFlag = false;
    		    	    	    		}
    		    	    	    	}
    		    	    			if(priorityFlag == false){
    		    	    				result = summation(result, secondNumber);
    		    	    				if(!operandStack.empty()){
    		    	    					secondNumber = operandStack.pop();
    		    	    				}
    		    	    				priorityFlag =true;
    		    	    			}
    		    	    		}while(!operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/')));
    	    	    		}
    	    	    		else{
    	    	    			result = summation(result, secondNumber);
    		    	    		if(!operandStack.empty()){
    		    	    			secondNumber = operandStack.pop();
    		    	    		}
    	    	    		}
    	    	    	}
    	    	    	else if(operator == '-'){
    	    	    		if(!operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/'))){
    	    	    			Double thirdNumber = operandStack.pop();
    	    	    			Boolean priorityFlag = true;
    	    	    			do{
    		    	    			Character priorityOperator = operatorStack.pop();
    		    	    			if(priorityOperator == '*'){
    		    	    				secondNumber = multiplication(secondNumber, thirdNumber);
    		    	    				if(!operandStack.empty() && !operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/'))){
    		    	    	    			thirdNumber = operandStack.pop();
    		    	    	    		}
    		    	    	    		else{
    		    	    	    			priorityFlag = false;
    		    	    	    		}
    		    	    	    	}
    		    	    	    	else if(priorityOperator == '/'){
    		    	    	    		secondNumber = division(secondNumber, thirdNumber);
    		    	    	    		if(!operandStack.empty() && !operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/'))){
    		    	    	    			thirdNumber = operandStack.pop();
    		    	    	    		}
    		    	    	    		else{
    		    	    	    			priorityFlag = false;
    		    	    	    		}
    		    	    	    	}
    		    	    			if(priorityFlag == false){
    		    	    				result = substraction(result, secondNumber);
    		    	    				if(!operandStack.empty()){
    		    	    					secondNumber = operandStack.pop();
    		    	    				}
    		    	    				priorityFlag =true;
    		    	    			}
    		    	    		}while(!operatorStack.empty() && (operatorStack.peek().equals('*') || operatorStack.peek().equals('/')));
    	    	    		}
    	    	    		else{
    		    	    		result = substraction(result, secondNumber);
    		    	    		if(!operandStack.empty()){
    		    	    			secondNumber = operandStack.pop();
    		    	    		}
    	    	    		}
    	    	    	}
    				}
    			    
    			    return result;
    			}
    			return operandStack.pop();
    		}
    		return 0.0;
    	}
    	catch(EmptyStackException e){
    		showDialog(R.string.dialog_message_EmptyStackException, R.string.dialog_title, R.string.ok);
    		return 0.0;
		}
    	catch(IllegalArgumentException e){
			showDialog(R.string.dialog_message_IllegalArgumentException, R.string.dialog_title, R.string.ok);
			return 0.0;
		}
		catch(Exception e){
			showDialog(R.string.dialog_message_exception, R.string.dialog_title, R.string.ok);
			return 0.0;
		}
    }
    
    private Double summation(Double x, Double y){
    	return x + y;
    }
    
    
    private Double substraction(Double x, Double y){
    	return x - y;
    }
    
   
    private Double multiplication(Double x, Double y){
    	return x * y;
    }
    
    
    private Double division(Double x, Double y){
    	try{
    		return x / y;
    	}
    	catch(ArithmeticException e){
			showDialog(R.string.dialog_message_ArithmeticException, R.string.dialog_title, R.string.ok);
			return 0.0;
		}
		catch(Exception e){
			showDialog(R.string.dialog_message_exception, R.string.dialog_title, R.string.ok);
			return 0.0;
		}
    }
    
   
    private void showDialog(int message, int title, int ok){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setTitle(title);
		builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User clicked OK button
	           }
	       });
		builder.create().show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
    		finish();
    		
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    
}