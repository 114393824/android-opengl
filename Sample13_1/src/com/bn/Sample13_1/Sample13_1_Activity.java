package com.bn.Sample13_1;
import com.bn.Sample13_1.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Sample13_1_Activity extends Activity {
	private MySurfaceView mGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);         
        //����Ϊȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//�л���������
			
		//�л���������
		setContentView(R.layout.main);		
		//��ʼ��GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();//��ȡ����
        mGLSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
        //���Զ����GLSurfaceView��ӵ����LinearLayout��
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); 
        ll.addView(mGLSurfaceView);  
        //ΪRadioButton��Ӽ���������ɫ��ѡ�����
        RadioButton rb=(RadioButton)findViewById(R.id.x1);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{//���ò���ΪX����
     					mGLSurfaceView.mRenderer.texRect.currIndex=0;
     				}
     			}        	   
            }         		
        );    
        
        rb=(RadioButton)findViewById(R.id.x2);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{//���ò���Ϊб��
     					mGLSurfaceView.mRenderer.texRect.currIndex=1;
     				}
     			}        	   
            }         		
        );   
        
        rb=(RadioButton)findViewById(R.id.x3);
        rb.setOnCheckedChangeListener(
            new OnCheckedChangeListener()
            {  
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) 
     			{
     				if(isChecked)
     				{//���ò���ΪXY˫����
     					mGLSurfaceView.mRenderer.texRect.currIndex=2;
     				}
     			}        	   
            }         		
        );  
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
        Constant.threadFlag=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
        Constant.threadFlag=false;
    }    
}



