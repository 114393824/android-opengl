package com.bn.Sample13_5;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.bn.Sample13_5.R;

import static com.bn.Sample13_5.Constant.*;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
class GameSurfaceView extends GLSurfaceView
{
    private SceneRenderer mRenderer;//������Ⱦ��
    float mPreviousY;
    float mPreviousX;
    //-----����
    int tex_trangleId;//ϵͳ����Ĳݵ�����id
    //-----����������
    MultiTrangle trangle;
	  static float tx=0;//�۲�Ŀ���x����  
	  static float ty=0;//�۲�Ŀ���y����
	  static float tz=0;//�۲�Ŀ���z����  
	  static float cx=0;//�����x����
	  static float cy=0;//�����y����
	  static float cz=30;//�����z����
	  float currY;
	  float rotation;
	  float twistingRatio;//������Ťת�����ű���
	  int symbol=1;
  float currRatio=0.05f;
	public GameSurfaceView(Context context) 
	{
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
        setKeepScreenOn(true);
    }
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) 
    {   
        float x = e.getX();
        switch (e.getAction()) 
        {
	        case MotionEvent.ACTION_DOWN://���¶���
	        break;
	        case MotionEvent.ACTION_UP://̧����
	        break;
	        case MotionEvent.ACTION_MOVE:
	            float dx = x - mPreviousX;//���㴥�ر�Xλ�� 
	        	if(dx<-10)//��ת��
	        	{
	        		rotation-=5f;
	        	}
	        	else if(dx>10)//��ת��
	        	{
	        		rotation+=5f;
	        	}
	        break;
        }
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
	class SceneRenderer implements GLSurfaceView.Renderer 
    {   
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            MatrixState.setCamera(cx,cy,cz,tx,ty,tz,0f,1.0f,0.0f);
            MatrixState.pushMatrix();
            //���������ƶ�����Ļ����
            float upOffset= (float) (triangle_edgeLength/2/Math.cos(Math.PI/6));
            MatrixState.translate(0, upOffset, 0);
            MatrixState.rotate(rotation, 0, 1, 0);
            trangle.drawSelf(tex_trangleId,twistingRatio);
            MatrixState.popMatrix();
        }  
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 1000);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(cx,cy,cz,tx,ty,tz,0f,1.0f,0.0f);    
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f); 
            //����ȼ�� 
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�򿪱������   
            GLES20.glDisable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            //����shader
            ShaderManager.loadCodeFromFile(GameSurfaceView.this.getResources());
            //����shader
            ShaderManager.compileShader();
            initAllObject();//��ʼ���������
            initAllTexture();//��ʼ������
            
            //����һ���̣߳���ʱ�ڶ�����
            new Thread()
            {
        		@Override
            	public void run()
            	{
        			while(true)
        			{
        				twistingRatio=twistingRatio+symbol*currRatio;
        				if(twistingRatio>1.0f)
        				{
        					twistingRatio=1.0f;
        					symbol=-symbol;
        				}
        				if(twistingRatio<-1.0f)
        				{
        					twistingRatio=-1.0f;
        					symbol=-symbol;
        				}
        				try
        				{
        					Thread.sleep(100);
        				}
        				catch(Exception e)
        				{
        					e.printStackTrace();
        				}
        			}
            	}
            }.start();
        }
    }
	//�������е������������
	public void initAllObject()
	{
		 trangle=new MultiTrangle(ShaderManager.getTrangleShaderProgram(),
				 triangle_edgeLength,triangle_levelNum);
	}
	//��ʼ�����е�����ͼ��
	public void initAllTexture()
	{
		Resources r=this.getResources();//��ȡ��Դ
        tex_trangleId=initTexture(r,R.drawable.android);//�ݵ�����
	}
}
