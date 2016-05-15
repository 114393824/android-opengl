package com.bn.Sample15_1;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
 
class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��    
    
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����    
    
    //�����λ�����
    float cx=0;
    float cy=30;
    float cz=60;
    float cAngle=0;
    final float cR=60;
    
    //�ƹ�λ��
	float lx=60;
	float ly=80;
	float lz=60;   
	float lAngle=0;
	final float lR=1;
	//�ƹ�ͶӰUp����   
	float ux=0;
	float uy=0;
	float uz=1;
	
      
    //��Դ�ܱ任����
    float[] mMVPMatrixGY;
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
        
        new Thread()  
        {
        	public void run()
        	{
        		while(true)
        		{
        			lAngle += 0.5;//������x����ת�Ƕ�                    
                    ux=(float) Math.sin(Math.toRadians(lAngle))*lR;
                    uz=(float) Math.cos(Math.toRadians(lAngle))*lR;
                    try {
   					Thread.sleep(20);
	   				} catch (InterruptedException e) {
	   					e.printStackTrace();
	   				}
        		}
        	}
        }.start();
    }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) 
    {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            cAngle += dx * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
              
            cx=(float) Math.sin(Math.toRadians(cAngle))*cR;
            cz=(float) Math.cos(Math.toRadians(cAngle))*cR;
            
            cy+= dy/10.0f;//������z���ƶ�
            requestRender();//�ػ滭��
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {  
    	//��ָ����obj�ļ��м��ض���
		LoadedObjectVertexNormal lovo_pm;//ƽ��
		LoadedObjectVertexNormal lovo_ch;//���
		LoadedObjectVertexNormal lovo_cft;//������
		LoadedObjectVertexNormal lovo_qt;//����
		LoadedObjectVertexNormal lovo_yh;//Բ��		
		//����Id
		int tyTexId;
        
        public void onDrawFrame(GL10 gl)
        {        	   
        	//�����ƹ�λ�õ�ͶӰ�����������
            MatrixState.setCamera(lx,ly,lz,10f,0f,10f,ux,uy,uz);
            MatrixState.setProjectFrustum(-0.5f, 0.5f, -0.5f, 0.5f, 1f, 400);  
            mMVPMatrixGY=MatrixState.getViewProjMatrix();
        	
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(cx,cy,cz,0f,0f,0f,0f,1f,0f);
            MatrixState.setProjectFrustum(-ratio, ratio, -1.0f, 1.0f, 2, 1000);  
            MatrixState.setLightLocation(lx, ly, lz); 
            //�����������ƽ��
            lovo_pm.drawSelf(tyTexId,mMVPMatrixGY);  
            
            //���Ʋ��
            MatrixState.pushMatrix(); 
            MatrixState.translate(0, 0, 30);
            MatrixState.scale(2,2,2);
            //�����ص����岿λ�����������
            lovo_ch.drawSelf(tyTexId,mMVPMatrixGY);
            MatrixState.popMatrix();     
            
            //��������
            MatrixState.pushMatrix(); 
            MatrixState.translate(-30, 0, 0);
            MatrixState.scale(2,2,2);
            //�����ص����岿λ�����������
            lovo_qt.drawSelf(tyTexId,mMVPMatrixGY);
            MatrixState.popMatrix();    
            
            //����Բ��
            MatrixState.pushMatrix(); 
            MatrixState.translate(30, 0, 0);
            MatrixState.scale(2,2,2);
            MatrixState.rotate(30, 0, 1, 0);
            //�����ص����岿λ�����������
            lovo_yh.drawSelf(tyTexId,mMVPMatrixGY);
            MatrixState.popMatrix();  
            
            //���Ƴ�����
            MatrixState.pushMatrix(); 
            MatrixState.translate(0, 0, -30);
            MatrixState.scale(2,2,2);
            //�����ص����岿λ�����������
            lovo_cft.drawSelf(tyTexId,mMVPMatrixGY);
            MatrixState.popMatrix();
        }

        float ratio;
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            ratio = (float) width / height; 
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        }
       
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {     	
        	//������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);    
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            //����Ҫ���Ƶ�����
            lovo_ch=LoadUtil.loadFromFileVertexOnly("ch.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            lovo_pm=LoadUtil.loadFromFileVertexOnly("pm.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            lovo_cft=LoadUtil.loadFromFileVertexOnly("cft.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            lovo_qt=LoadUtil.loadFromFileVertexOnly("qt.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            lovo_yh=LoadUtil.loadFromFileVertexOnly("yh.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            //��ʾ��Ӱ��ͼ���������
            tyTexId=initTexture(R.raw.wl);
        }
    }
	
 	public int initTexture(int drawableId)//textureId
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
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
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
        //ͨ������������ͼƬ===============end=====================  
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        ); 
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
}
