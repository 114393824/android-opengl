package com.opengl.sample7_1_mine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private MySurfaceView mGLSurfaceView;
	static boolean threadFlag;// ���������X����ת������־λ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ����Ϊȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ����Ϊ����ģʽ
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// �л���������

		// ��ʼ��GLSurfaceView
		mGLSurfaceView = new MySurfaceView(this);
		setContentView(mGLSurfaceView);
		mGLSurfaceView.requestFocus();// ��ȡ����
		mGLSurfaceView.setFocusableInTouchMode(true);// ����Ϊ�ɴ���
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		threadFlag = false;
		mGLSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		threadFlag = true;
		mGLSurfaceView.onResume();
	}

}
