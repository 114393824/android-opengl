package com.opengl.sample7_1_mine;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// 角度缩放比例
	private SceneRenderer mRenderer;// 场景渲染器
	private float mPreviousY;// 上次的触控位置Y坐标
	private float mPreviousX;// 上次的触控位置X坐标

	private int textureId;// 系统分配的纹理id

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setEGLContextClientVersion(2);
		this.mRenderer = new SceneRenderer(); // 创建场景渲染器
		this.setRenderer(this.mRenderer); // 设置渲染器
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);// 设置渲染模式为主动渲染
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return true;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		Triangle texRect;// 纹理矩形

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			// 清除深度缓冲与颜色缓冲
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);
			// 绘制纹理矩形
			texRect.drawSelf(textureId);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			// 设置视窗大小及位置
			GLES20.glViewport(0, 0, width, height);
			// 计算GLSurfaceView的宽高比
			float ratio = (float) width / height;
			// 调用此方法计算产生透视投影矩阵
			MatrixState.setProject(-ratio, ratio, -1, 1, 1, 10);
			// 调用此方法产生摄像机9参数位置矩阵
			MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			// 设置屏幕背景色RGBA
			GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

			// 创建三角形对对象
			texRect = new Triangle(MySurfaceView.this);
			// 打开深度检测
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			// 初始化纹理
			initTexture();
			// 关闭背面剪裁
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		}

	}

	private void initTexture() {
		// 生成纹理ID
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // 产生的纹理id的数量
				textures, // 纹理id的数组
				0 // 偏移量
		);
		textureId = textures[0];
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_CLAMP_TO_EDGE);

		// 通过输入流加载图片===============begin===================
		InputStream is = this.getResources().openRawResource(R.drawable.wall);
		Bitmap bitmapTmp;
		try {
			bitmapTmp = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 通过输入流加载图片===============end=====================

		// 实际加载纹理
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // 纹理类型，在OpenGL
													// ES中必须为GL10.GL_TEXTURE_2D
				0, // 纹理的层次，0表示基本图像层，可以理解为直接贴图
				bitmapTmp, // 纹理图像
				0 // 纹理边框尺寸
		);
		bitmapTmp.recycle(); // 纹理加载成功后释放图片
	}
}
