package com.bn.Sample17_4;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import com.bn.Sample17_4.R;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.extras.gimpact.GImpactCollisionAlgorithm;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import static com.bn.Sample17_4.Constant.*;
 
class MySurfaceView extends GLSurfaceView 
{
	private SceneRenderer mRenderer;//������Ⱦ��	
	DiscreteDynamicsWorld dynamicsWorld;//�������
	ArrayList<BNThing> tca=new ArrayList<BNThing>();
	ArrayList<BNThing> tcaForAdd=new ArrayList<BNThing>();	
	CollisionDispatcher dispatcher;
	CollisionShape boxShape;//���õ�������
	CollisionShape planeShape;//���õ�ƽ����״
	//��ָ����obj�ļ��м��ض���
	LoadedObjectVertexNormal[] lovoa=new LoadedObjectVertexNormal[3];
	int[] cubeTextureId=new int[2];//����������
	Sample17_4_Activity activity;
	
	public MySurfaceView(Context context) 
	{
        super(context);
        activity = (Sample17_4_Activity) context;
        //��ʼ����������
        this.setEGLContextClientVersion(2);
        initWorld();     
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	
	//��ʼ����������ķ���
	public void initWorld()
	{
		//������ײ���������Ϣ����
		CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
		//������ײ����㷨�����߶����书��Ϊɨ�����е���ײ���ԣ���ȷ�����õļ����Զ�Ӧ���㷨
		dispatcher = new CollisionDispatcher(collisionConfiguration);
		BroadphaseInterface overlappingPairCache = new DbvtBroadphase();
		//�����ƶ�Լ������߶���
		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
		//���������������
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, overlappingPairCache, solver, collisionConfiguration);
		//�����������ٶ�
		dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
		
		//�������õ�������
		boxShape=new BoxShape(new Vector3f(Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE));
		//�������õ�ƽ����״
		planeShape=new StaticPlaneShape(new Vector3f(0, 1, 0), 0);		
	}

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {		
		int floorTextureId;//��������
		TexFloor floor;//�������1			
		
        public void onDrawFrame(GL10 gl) {            
            
        	//�����ɫ��������Ȼ���
        	GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            
            //��������
            synchronized(tca)
			{
	            for(BNThing tc:tca)
	            {
	            	MatrixState.pushMatrix();
	                tc.drawSelf();
	                MatrixState.popMatrix();  
	            }            
			}
            //���Ƶذ�
            MatrixState.pushMatrix();
            floor.drawSelf(floorTextureId);
            MatrixState.popMatrix();   
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height);
            //����͸��ͶӰ�ı���
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
            MatrixState.setCamera(
            		CAMERA_X,   //����λ�õ�X
            		CAMERA_Y, 	//����λ�õ�Y
            		CAMERA_Z,   //����λ�õ�Z
            		TARGET_X, 	//�����򿴵ĵ�X
            		TARGET_Y,   //�����򿴵ĵ�Y
            		TARGET_Z,   //�����򿴵ĵ�Z
            		0, 
            		1, 
            		0);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //�رտ����� 
        	GLES20.glDisable(GL10.GL_DITHER);
            //������Ļ����ɫ��ɫRGBA
        	GLES20.glClearColor(0,0,0,0);            
            //������Ȳ���
        	GLES20.glEnable(GL10.GL_DEPTH_TEST);  
            //����Ϊ�򿪱������
        	GLES20.glEnable(GL10.GL_CULL_FACE);
        	  //��ʼ���任����
            MatrixState.setInitStack();
            //��ʼ����Դλ��
            MatrixState.setLightLocationRed(CAMERA_X*2, 50, CAMERA_Z*2);
            ShaderManager.loadCodeFromFile(activity.getResources());
            ShaderManager.compileShader();
            
            lovoa[0]=LoadUtil.loadFromFile("table.obj", MySurfaceView.this.getResources(), MySurfaceView.this);
            lovoa[1]=LoadUtil.loadFromFile("yh.obj", MySurfaceView.this.getResources(), MySurfaceView.this);
            lovoa[2]=LoadUtil.loadFromFile("ch.obj", MySurfaceView.this.getResources(), MySurfaceView.this);
            
            //��ʼ������
            cubeTextureId[0]=initTexture(R.drawable.wood_bin2);
            cubeTextureId[1]=initTexture(R.drawable.wood_bin1);
            floorTextureId=initTextureRepeat(R.drawable.f6);              
            
            //�����������
            floor=new TexFloor(ShaderManager.getTextureLightShaderProgram(),80*Constant.UNIT_SIZE,
            		-Constant.UNIT_SIZE,planeShape,dynamicsWorld);
              
            new Thread()
            {
            	public void run()
            	{
            		while(true)
            		{            			
            			try 
            			{
            				synchronized(tcaForAdd)
            	            {
            					synchronized(tca)
            					{
            						for(BNThing tc:tcaForAdd)
                	                {
                	            		tca.add(tc);
                	                }
            					}            	            	
            	            	tcaForAdd.clear();
            	            }           
            				//ģ��
                			dynamicsWorld.stepSimulation(1f/200.f, 5);
							Thread.sleep(5);
						} catch (Exception e) 
						{
							e.printStackTrace();
						}
            		}
            	}
            }.start();
        } 
    }
	
	//�����¼��ص�����
	int index=0;
    @Override public boolean onTouchEvent(MotionEvent e) 
    {    	
        switch (e.getAction()) 
        {
           case MotionEvent.ACTION_DOWN:
        	if(index==0)
        	{
        		TexCube tcTemp=new TexCube
       			(
       					MySurfaceView.this,
           				Constant.UNIT_SIZE,
           				boxShape,
           				dynamicsWorld,
           				1,
           				0,
           				2,         
           				4,
           				cubeTextureId,
           				ShaderManager.getTextureLightShaderProgram()
           		);        
            	//�������ӵĳ�ʼ�ٶ�
            	tcTemp.body.setLinearVelocity(new Vector3f(0,2,-6));
            	tcTemp.body.setAngularVelocity(new Vector3f(5,0,0)); 
            	//������������뵽�б���
            	synchronized(tcaForAdd)
                {
            	   tcaForAdd.add(tcTemp);
                }
        	}
        	else
        	{        		
        		LoadRigidBody tcTemp=new LoadRigidBody  
       			(
       					ShaderManager.getColorShaderProgram(),
           				1,
           				lovoa[index-1],  
           				0,
           				2,         
           				4,
           				dynamicsWorld  
           		);        
            	//��������ĳ�ʼ�ٶ�  
            	tcTemp.body.setLinearVelocity(new Vector3f(0,2,-6));
            	tcTemp.body.setAngularVelocity(new Vector3f(5,0,0)); 
            	//������������뵽�б���
            	synchronized(tcaForAdd)
                {
            	   tcaForAdd.add(tcTemp);
                }
            	GImpactCollisionAlgorithm.registerAlgorithm(dispatcher);
        	}   
        	index=(index+1)%4;
           break;
        }        
        return true;
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
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
        
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
	public int initTextureRepeat(int drawableId)//textureId
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
