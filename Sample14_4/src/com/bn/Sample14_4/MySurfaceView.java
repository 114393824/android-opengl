package com.bn.Sample14_4;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��  	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		TextureRect pmBase;
		TextureRectJJ pmJJ;
		int pmTexId;
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);            
            MatrixState.pushMatrix(); 
            MatrixState.translate(-8, 0, 0); 
            pmBase.drawSelf(pmTexId);//ƽ��
            MatrixState.popMatrix();    
            
            MatrixState.pushMatrix();  
            MatrixState.translate(8, 0, 0); 
            pmJJ.drawSelf(pmTexId);//ƽ��
            MatrixState.popMatrix();    
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //����cameraλ��
            MatrixState.setCamera
            (
            		0,	//����λ�õ�X
            		0, //����λ�õ�Y
            		20, //����λ�õ�Z
            		0, 	//�����򿴵ĵ�X
            		0,  //�����򿴵ĵ�Y
            		0,  //�����򿴵ĵ�Z
            		0, 	//up����
            		1, 
            		0
            );
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
        	GLES20.glClearColor(1.f,1.0f,1.0f,1.0f);    
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();  
    		pmBase = new TextureRect(MySurfaceView.this,15, 15, 1, 1);
    		pmJJ = new TextureRectJJ(MySurfaceView.this,15, 15, 1, 1);
    		pmTexId=initTexture(R.drawable.pm);
        }
    }

	//��ʼ������
	public int initTexture(int drawableId)
	{
		//��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		int textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		//��Mipmap����������˲���	
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		
		//ST�����������췽ʽ
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);		
        
        //ͨ������������ͼƬ===============begin===================
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);        	
        }
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        } 
        //ʵ�ʼ�������,����������������ͼƬ��ʽ�����⣬���׳�ͼƬ��ʽ�쳣�����ٻ�����ʾ�����쳣
	   	GLUtils.texImage2D
	    (
	    		GLES20.GL_TEXTURE_2D, //��������
	     		0, 
	     		GLUtils.getInternalFormat(bitmapTmp), 
	     		bitmapTmp, //����ͼ��
	     		GLUtils.getType(bitmapTmp), 
	     		0 //����߿�ߴ�
	     );   
        //�Զ�����Mipmap����
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        //�ͷ�����ͼ
        bitmapTmp.recycle();
        //��������ID
        return textureId;
	}	
}
