package com.bn.Sample15_9;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import static com.bn.Sample15_9.Constant.*;
class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��   
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��	
        
        //������ȾģʽΪ=========ֻ��Ⱦһ֡=========
        //The renderer only renders when the surface is created, or when requestRender() is called
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
	    Camera cam;
	    Scene scn;
	    Light light;
		ColorRect rect;		
		
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����ɫ����
        	GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            /*
        	 * �˴���������ͶӰ���������λ�ã�
        	 * ֻ��Ϊ���ܽ�3D�����е�������ֱ�ӻ������ӿ���
        	 */
            //���ô˷��������������ͶӰ����
        	MatrixState.setProjectOrtho(-W, W, -H, H, 1, 2);
            //���û��ƾ���ʱ��cameraλ��
			MatrixState.setCamera(0, 0, 1, 0, 0, 0, 0, 1, 0);
			
			//���ù��߸����㷨�е���ʵ�����λ��
            cam.setMyCamera(CAM_X, CAM_Y, CAM_Z, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            //������������б任
            scn.transform();
            //��ʼ���߸�����Ⱦ����
            cam.raytrace(scn, rect);
        }
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, (int)nCols, (int)nRows); 
        	/*
        	 * �˴���������ͶӰ���������λ�ã�
        	 * ֻ��Ϊ���ܽ�3D�����е�������ֱ�ӻ������ӿ���
        	 */
            //���ô˷��������������ͶӰ����
        	MatrixState.setProjectOrtho(-W, W, -H, H, 1, 2);
            //����cameraλ��
			MatrixState.setCamera(0, 0, 1, 0, 0, 0, 0, 1, 0);
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0,0,0,1);
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST); 
            //��ʼ���任����
            MatrixState.setInitStack(); 
            rect = new ColorRect(MySurfaceView.this);

    	    light = new Light(new Point3(LIGHT_X,LIGHT_Y,LIGHT_Z));
            cam=new Camera(light);
    	    scn=new Scene(cam, light);
        }
    }
}
