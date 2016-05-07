package com.bn.Sample13_5;

import android.app.Activity;
import android.os.Bundle;

public class Sample13_5_Activity extends Activity 
{
	GameSurfaceView gameView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameView = new GameSurfaceView(this);
        setContentView(gameView);	
        gameView.requestFocus();//��ȡ����
        gameView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
    }


    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }    
}