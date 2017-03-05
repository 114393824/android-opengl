package com.bn.st.d2;

import com.bn.clp.Constant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback {

	MyActivity activity;
	Paint paint; //
	Bitmap[] logos = new Bitmap[2];//
	Bitmap currentLogo; //
	float currentX; // 
	float currentY;
	int currentAlpha = 0; // 
	int sleepSpan = 150; //

	public WelcomeView(MyActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.getHolder().addCallback(this);
		this.paint = new Paint();// 
		this.paint.setAntiAlias(true);// 
		//
		this.logos[0] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.heli);
		this.logos[1] = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.androidheli);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				for (Bitmap bm : logos) {
					currentLogo = bm;
					currentX = Constant.SCREEN_WIDTH / 2 - bm.getWidth() / 2;// 
					currentY = Constant.SCREEN_HEIGHT / 2 - bm.getHeight() / 2;
					for (int i = 255; i > -10; i = i - 10) {
						// 
						currentAlpha = i;
						if (currentAlpha < 0)//
						{
							currentAlpha = 0;//
						}
					}
				}
			}
		}.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	public void onDraw(Canvas canvas) {

		paint.setColor(Color.BLACK);//
		paint.setAlpha(255);//
		canvas.drawRect(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT,
				paint);
		// 
		if (currentLogo == null)
			return;
		paint.setAlpha(currentAlpha);
		canvas.drawBitmap(currentLogo, currentX, currentY, paint);
	}
}
