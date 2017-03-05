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
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;// �Ƕ����ű���
	private SceneRenderer mRenderer;// ������Ⱦ��
	private float mPreviousY;// �ϴεĴ���λ��Y����
	private float mPreviousX;// �ϴεĴ���λ��X����

	private int textureId;// ϵͳ���������id

	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setEGLContextClientVersion(2);
		this.mRenderer = new SceneRenderer(); // ����������Ⱦ��
		this.setRenderer(this.mRenderer); // ������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);// ������ȾģʽΪ������Ⱦ
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return true;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer {
		Triangle texRect;// �������

		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub
			// �����Ȼ�������ɫ����
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
					| GLES20.GL_COLOR_BUFFER_BIT);
			// �����������
			texRect.drawSelf(textureId);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			// �����Ӵ���С��λ��
			GLES20.glViewport(0, 0, width, height);
			// ����GLSurfaceView�Ŀ�߱�
			float ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
			MatrixState.setProject(-ratio, ratio, -1, 1, 1, 10);
			// ���ô˷������������9����λ�þ���
			MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			// ������Ļ����ɫRGBA
			GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

			// ���������ζԶ���
			texRect = new Triangle(MySurfaceView.this);
			// ����ȼ��
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			// ��ʼ������
			initTexture();
			// �رձ������
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		}

	}

	private void initTexture() {
		// ��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures(1, // ����������id������
				textures, // ����id������
				0 // ƫ����
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

		// ͨ������������ͼƬ===============begin===================
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
		// ͨ������������ͼƬ===============end=====================

		// ʵ�ʼ�������
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, // �������ͣ���OpenGL
													// ES�б���ΪGL10.GL_TEXTURE_2D
				0, // ����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
				bitmapTmp, // ����ͼ��
				0 // ����߿�ߴ�
		);
		bitmapTmp.recycle(); // ������سɹ����ͷ�ͼƬ
	}
}
