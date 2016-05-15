package com.bn.Sample17_5;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Sample17_5_Activity extends Activity {
	
	MySurfaceView surfaceView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ������
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ����ͷ
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ǿ�ƺ���
        
        surfaceView = new MySurfaceView(this);
        setContentView(surfaceView);
    }
    
    @Override
	protected void onPause() {
		super.onPause();
		surfaceView.onPause();
		surfaceView.flag=false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		surfaceView.onResume();
		surfaceView.flag=true;
	}
}