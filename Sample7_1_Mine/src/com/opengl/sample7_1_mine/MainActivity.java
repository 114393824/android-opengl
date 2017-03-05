package com.opengl.sample7_1_mine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private MySurfaceView mGLSurfaceView;
	static boolean threadFlag;// 纹理矩形绕X轴旋转工作标志位

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置为全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 设置为竖屏模式
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		// 切换到主界面

		// 初始化GLSurfaceView
		mGLSurfaceView = new MySurfaceView(this);
		setContentView(mGLSurfaceView);
		mGLSurfaceView.requestFocus();// 获取焦点
		mGLSurfaceView.setFocusableInTouchMode(true);// 设置为可触控
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
