package com.bn.Sample16_6;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import static com.bn.Sample16_6.Constant.*;

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
		Rope rope;
		Stick stick;
		
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            
            MatrixState.pushMatrix();
            
            for(int i=0;i<rope.massCount-1;i++){
            	Mass mass1 = rope.massList.get(i);
            	Mass mass2 = rope.massList.get(i+1);
            	Vector3 stickVector = mass1.pos.add(mass2.pos.multiConstant(-1));
            	
            	MatrixState.pushMatrix();
            	MatrixState.translate(mass2.pos.x, mass2.pos.y, mass2.pos.z);
            	Vector3.moveXToSomeVector(new double[]{stickVector.x,stickVector.y,stickVector.z});
            	stick.drawSelf(springLength/2,0);
            	MatrixState.popMatrix();
            }
            MatrixState.popMatrix();
        }   

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
        	float ratio=0;
            ratio= (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1.5f, 10);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(0,0,2.2f,0f,0f,0f,0f,1.0f,0.0f);    
            //�򿪱������
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //���õƹ�ĳ�ʼλ��
            MatrixState.setLightLocation(0,2,2);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(1f,1f,1f, 1.0f);  
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            MatrixState.setInitStack();
            rope = new Rope(
            		40, //massCount
            		0.1f, //m
            		new Vector3(0,-9.8f,0),//G 
            		10.0f, //groundRepulsion���浯��
            		0.2f, //friction ����Ħ����
            		20f, //����Ļ���ϵ��
            		groundHeight, //����ĸ߶�
            		0.2f,//��������
            		new Vector3(-1,0,0),//��ͷ���ٶ� 
            		 new Vector3(0,groundHeight,-1)//��ͷ�ĳ�ʼλ��
            		);
            stick = new Stick(MySurfaceView.this,springLength,springR,10);//������
            
            new Thread(){
        	   float time=0;
        	   float dt = 0.01f;
        	   public void run(){
        		   while(flag && time<deadTime){
        			   time+=0.01f;
        			   rope.operate(dt);
        			   try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		   }
        	   }
            }.start();
        }
    }
}
