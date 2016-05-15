package com.bn.Sample16_4;

import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.util.Log;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��
    long olds;
	long currs;
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		GrainGroup grainGroup;//�������ϵͳ
        public void onDrawFrame(GL10 gl) 
        { 
        	currs=System.nanoTime();
        	Log.d("FPS", (1000000000.0/(currs-olds)+"FPS"));
			olds=currs;
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //�����ֳ�
            MatrixState.pushMatrix();
            MatrixState.translate(0, -2, 0);
            grainGroup.drawSelf();
            //�ָ��ֳ�
            MatrixState.popMatrix();
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,2,7,0,0,0,0f,1.0f,0.0f);
        }
        
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
            //�����������ϵͳ
            grainGroup=new GrainGroup(MySurfaceView.this);
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�رձ������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
        }
    }
}