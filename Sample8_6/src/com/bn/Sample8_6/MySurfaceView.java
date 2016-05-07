package com.bn.Sample8_6;

import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

class MySurfaceView extends GLSurfaceView {
    
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
	private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
	
	private SceneRenderer mRenderer;//������Ⱦ��
    
    boolean lightFlag=true;		//������ת�ı�־λ
    
    float yAngle=0;//��y����ת�ĽǶ�       
    float xAngle=0;//��x����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
    
    Ball ball;
    Stick stick;
    
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            yAngle += dx * TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
            zAngle+= dy * TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
    
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		
		RegularPolygon first;
        
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -6f);
            MatrixState.pushMatrix();       
   	   	 	MatrixState.rotate(xAngle, 1, 0, 0);
   	   	 	MatrixState.rotate(yAngle, 0, 1, 0);
   	   	 	MatrixState.rotate(zAngle, 0, 0, 1);
        	 for(int i=0;i<5;i++){		//�岿��ѭ��
         		MatrixState.pushMatrix();
         	 	MatrixState.rotate(72*i,0,0,1);	//���ݻ��Ƶ�Ϊ�ڼ����֣���ת72*i��
         	 	first.drawSelf(0, 0);	//��������̼�����֮һ���֣�������������岿������γɣ����Բ��û���
         	 	MatrixState.popMatrix();
         	}
        	 MatrixState.popMatrix();
        	 MatrixState.popMatrix();
        	 Utils.drawnVertices.clear();
            
        }   

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio= (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 4f, 100);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,8.0f,0f,0f,0f,0f,1.0f,0.0f); 
            
	        //��ʼ����Դ
	        MatrixState.setLightLocation(10 , 0 , -10);
	                      
	        //����һ���̶߳�ʱ�޸ĵƹ��λ��
	        new Thread()
	        {
				public void run()
				{
					float redAngle = 0;
					while(lightFlag)
					{	
						//���ݽǶȼ���ƹ��λ��
						redAngle=(redAngle+5)%360;
						float rx=(float) (15*Math.sin(Math.toRadians(redAngle)));
						float rz=(float) (15*Math.cos(Math.toRadians(redAngle)));
						MatrixState.setLightLocation(rx, 0, rz);
						
						try {
								Thread.sleep(100);
							} catch (InterruptedException e) {				  			
								e.printStackTrace();
							}
					}
				}
	        }.start();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.9f,0.9f,0.9f, 1.0f);    
            //������Ȳ���
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    		//����Ϊ�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            
            float[] colorValue = {1,0,0,1};	//������ɫ����
            ball = new Ball(MySurfaceView.this,Constant.BALL_R,colorValue);//���������
            colorValue = new float[]{1,1,0,1};
            stick = new Stick(MySurfaceView.this,Constant.LENGTH,Constant.R,Constant.ANGLE_SPAN,colorValue);//����Բ�ܶ���
            
            
            double[] initPoint = Utils.getFirstPoint(Constant.LENGTH);//�õ���һ����������µ������
            double[] initVector={1,0,0,1};	//��ʼ����������
            double[] zPivot = {0,0,1,1};	//��z��Ϊ��ת��
            int[] vertices = {0,1,2,3,4};	//�������
            int[] borders = {0,1,2,3,4};	//Բ�ܵ�����
            first = new RegularPolygon(MySurfaceView.this, 5,72 , 
            		Constant.LENGTH, initPoint, initVector,zPivot,vertices,borders);//1
            
            vertices = new int[]{2,3,4};	//�������
            borders = new int[]{1,2,3,4};	//Բ�ܵ�����
            RegularPolygon rp2 = first.buildChild( 6, -60,1,vertices,borders);//2
            
            vertices = new int[]{2,3,4,5};
            borders = new int[]{1,2,3,4,5};
            RegularPolygon rp4 = rp2.buildChild( 6, 60,3,vertices,borders);//4
            
            
            vertices = new int[]{};
            borders = new int[]{1,5};
            rp4.buildChild( 6, -60,2,vertices,borders);//5
            
            vertices = new int[]{2};
            borders = new int[]{1,2};
            RegularPolygon rp6 = rp4.buildChild( 5, -72,3,vertices,borders);//6
            
            vertices = new int[]{3,4,5};
            borders = new int[]{2,3,4,5};
            rp6.buildChild( 6, 60,2,vertices,borders);//7
            
        }
    }
}
