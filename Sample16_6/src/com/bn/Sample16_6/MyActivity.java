package com.bn.Sample16_6;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MyActivity extends Activity {
	
	MySurfaceView surfaceView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        surfaceView = new MySurfaceView(this);
        setContentView(surfaceView);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
        Constant.flag=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.onPause();
        Constant.flag=false;
    }
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent e)
	{
		switch(keyCode)
	    	{
		case 4:
			System.exit(0);
			break;
	    	}
		return true;
	}
    
}