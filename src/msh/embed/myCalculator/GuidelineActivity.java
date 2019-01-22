package msh.embed.myCalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class GuidelineActivity extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        setContentView(R.layout.tutorial);
    }
    
    @Override
    public void onResume() {
        super.onResume();  	
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
        	
        	Intent intent = new Intent(GuidelineActivity.this, MyCalculatorActivity.class);
    		startActivity(intent);
    		finish();
    		
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
