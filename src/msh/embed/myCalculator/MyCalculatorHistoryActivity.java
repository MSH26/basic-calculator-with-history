package msh.embed.myCalculator;

//import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MyCalculatorHistoryActivity extends Activity {
	
	//SharedPreferences sharedPrefereces;
	//String HistoryLog = "";
	
	TableLayout tableLayout;
	Button button_back;
	Button button_clear;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*sharedPrefereces = getSharedPreferences("MyCalculatorPrefereces", 0);
        HistoryLog = sharedPrefereces.getString("HistoryLog", null);*/
    }
    
    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.mycalculatorhistory);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        tableLayout = (TableLayout)findViewById(R.id.historyTable);
        button_back = (Button)findViewById(R.id.button_back);
    	button_clear = (Button)findViewById(R.id.button_historyClear);
        
        addHeadersInTableOnView();
        addDataToTableRowOnView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        setVisible(false);
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
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	
        	Intent intent = new Intent(MyCalculatorHistoryActivity.this, MyCalculatorActivity.class);
    		startActivity(intent);
    		finish();
    		
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    
    public void onClickButtonBackMethod(View v){
    	if(button_back == (Button)v){
    		Intent intent = new Intent(MyCalculatorHistoryActivity.this, MyCalculatorActivity.class);
    		startActivity(intent);
    		finish();
    	}
    }
    
    public void onClickButtonHistoryClearMethod(View v){
    	if(button_clear == (Button)v){
    		File file = new File(getFilesDir(), "historyFile.txt");
    		file.delete();
    		tableLayout.removeAllViews();
    		addHeadersInTableOnView();
    		Toast.makeText(MyCalculatorHistoryActivity.this,"Record deleted",Toast.LENGTH_LONG).show();
    	}
    }
    
    private TextView getTextView(String title, int color, int typeface, int backGroundColor) {
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(color);
        textView.setPadding(0, 5, 0, 5);
        textView.setTypeface(Typeface.DEFAULT, typeface);
        textView.setBackgroundColor(backGroundColor);
        return textView;
    }
 
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1.0f);
        params.setMargins(2, 0, 0, 2);
        return params;
    }
    
    private void addHeadersInTableOnView() {
    	tableLayout.setColumnStretchable(0, true);
    	tableLayout.setColumnStretchable(1, true);
    	TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(getLayoutParams());
        tableRow.addView(getTextView("Expression", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tableRow.addView(getTextView(" ", Color.WHITE, Typeface.BOLD, Color.BLACK));
        tableRow.addView(getTextView("Result", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tableRow.addView(getTextView(" ", Color.WHITE, Typeface.BOLD, Color.BLACK));
        tableRow.addView(getTextView("DateTime", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tableLayout.addView(tableRow);
    }
 
    private void addDataToTableRowOnView() {
    	try{
	    	/*if(!HistoryLog.equals("")){
	    		String[] historyList =	HistoryLog.split("\n");
	    		TableRow tableRow;
	            for(int i=0; i<historyList.length; i++) {
	            	String[] history =	historyList[i].split("|");
	            	
	                tableRow = new TableRow(this);
	                tableRow.setLayoutParams(getLayoutParams());
	                tableRow.addView(getTextView(history[0], Color.BLUE, Typeface.NORMAL, Color.YELLOW));
	                tableRow.addView(getTextView(history[1], Color.BLUE, Typeface.NORMAL, Color.YELLOW));
	                tableRow.addView(getTextView(history[2], Color.BLUE, Typeface.NORMAL, Color.YELLOW));
	                tableLayout.addView(tableRow);
	            }
	    	}*/
    	    FileInputStream fileInputStream = openFileInput("historyFile.txt");
    	    InputStreamReader inputStreamReader =new InputStreamReader(fileInputStream);
    		BufferedReader  bufferedReaderForRecords = new BufferedReader(inputStreamReader);
	    	TableRow tableRow;
            String line = null;
            while ((line = bufferedReaderForRecords.readLine()) != null) {
            	String[] history =	line.split("\\|");

                tableRow = new TableRow(this);
                tableRow.setLayoutParams(getLayoutParams());
            	for(int i=0; i<history.length; i++){
                    if(i != 0){
                    	tableRow.addView(getTextView(" ", Color.BLUE, Typeface.NORMAL, Color.BLACK));
                    }
                    tableRow.addView(getTextView(history[i].trim(), Color.BLUE, Typeface.NORMAL, Color.YELLOW));
            	}
                tableLayout.addView(tableRow);
            }
    	}
    	catch(Exception e){
    		Toast.makeText(MyCalculatorHistoryActivity.this,e.toString(),Toast.LENGTH_LONG).show();
    	}
    }
    
}
