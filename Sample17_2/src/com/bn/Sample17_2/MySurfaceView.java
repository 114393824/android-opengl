package com.bn.Sample17_2;
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
import com.bn.Sample17_2.R;
import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.util.ObjectArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CylinderShapeX;
import com.bulletphysics.collision.shapes.CylinderShapeZ;
import com.bulletphysics.collision.shapes.StaticPlaneShape;

class MySurfaceView extends GLSurfaceView 
{
	private SceneRenderer mRenderer;//������Ⱦ��	
	DiscreteDynamicsWorld dynamicsWorld;//�������
	ObjectArrayList<CollisionShape> collisionShapes = new ObjectArrayList<CollisionShape>();
	ArrayList<CubeCylinder > tca=new ArrayList<CubeCylinder >();
	ArrayList<CubeCylinder > tcaForAdd=new ArrayList<CubeCylinder>();	
	CollisionShape boxShape;//���õ�������	
	CollisionShape cyShapeX;//���õ�XԲ��
	CollisionShape cyShapeZ;//���õ�ZԲ��
	CollisionShape planeShape;//���õ�ƽ����״
	CollisionShape[] csa=new CollisionShape[3];//Բ�������������
	Sample17_2_Activity activity;
	public MySurfaceView(Context context) 
	{
        super(context);
        activity=(Sample17_2_Activity) context;
        this.setEGLContextClientVersion(2);//����GLES�汾Ϊ2.0
        //��ʼ����������
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
		CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);		
		//����������������ı߽���Ϣ
		Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
		Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
		int maxProxies = 1024;
		//������ײ���ֲ�׶εļ����㷨����
		AxisSweep3 overlappingPairCache =new AxisSweep3(worldAabbMin, worldAabbMax, maxProxies);
		//�����ƶ�Լ������߶���
		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
		//���������������
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, overlappingPairCache, solver,collisionConfiguration);
		//�����������ٶ�
		dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
		//�������õ�������
		boxShape=new BoxShape(new Vector3f(Constant.UNIT_SIZE,Constant.UNIT_SIZE,Constant.UNIT_SIZE));
		//�������õ�ƽ����״
		planeShape=new StaticPlaneShape(new Vector3f(0, 1, 0), 0);		
		//�������õ�XԲ��
		cyShapeX=new CylinderShapeX(new Vector3f(Constant.UNIT_SIZE*1.8f,Constant.UNIT_SIZE/2,Constant.UNIT_SIZE/2));
		//�������õ�ZԲ��
		cyShapeZ=new CylinderShapeZ(new Vector3f(Constant.UNIT_SIZE/2,Constant.UNIT_SIZE/2,Constant.UNIT_SIZE*1.8f));
		
		//�������õ���״����
		csa[0]=boxShape;
		csa[1]=cyShapeX;
		csa[2]=cyShapeZ;
	}

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		int[] cubeTextureId=new int[2];//����������
		int[] cyTextureId=new int[2];//Բ������
		int floorTextureId;//��������
		TexFloor floor;//�������1		
		
        public void onDrawFrame(GL10 gl) {            
            
        	//�����ɫ��������Ȼ���
        	GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            //��������
            synchronized(tca)
			{
	            for(CubeCylinder tc:tca)
	            {
	            	MatrixState.pushMatrix();
	                tc.drawSelf(cubeTextureId,cyTextureId);
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
            		-1f,   //����λ�õ�X
            		2f, 	//����λ�õ�Y
            		6.0f,   //����λ�õ�Z
            		0, 	//�����򿴵ĵ�X
            		2f,   //�����򿴵ĵ�Y
            		0,   //�����򿴵ĵ�Z
            		0, 
            		1, 
            		0);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫ��ɫRGBA
            GLES20.glClearColor(0,0,0,0);            
            //������Ȳ���
            GLES20.glEnable(GL10.GL_DEPTH_TEST);  
            //����Ϊ�򿪱������
            GLES20.glEnable(GL10.GL_CULL_FACE);
            MatrixState.setInitStack();
            ShaderManager.loadCodeFromFile(activity.getResources());
            ShaderManager.compileShader();
            //��ʼ������
            cubeTextureId[0]=initTexture(R.drawable.wood_bin2);
            cubeTextureId[1]=initTexture(R.drawable.wood_bin1);
            floorTextureId=initTextureRepeat(R.drawable.f6);            
            cyTextureId[0]=initTexture(R.drawable.cyh);
            cyTextureId[1]=initTexture(R.drawable.cy);
            
            //�����������
            floor=new TexFloor(ShaderManager.getTextureShaderProgram(),80*Constant.UNIT_SIZE,-Constant.UNIT_SIZE,planeShape,dynamicsWorld);
            //����������       
            int size=2;   
            float xStart=(-size/2.0f+0.5f)*(3.8f)*Constant.UNIT_SIZE;
            float yStart=0.84f*Constant.UNIT_SIZE;
            float zStart=(-size/2.0f+0.5f)*(2+0.4f)*Constant.UNIT_SIZE-4f;
            for(int i=0;i<size;i++)
            {
            	for(int j=0;j<size;j++)
            	{
            		for(int k=0;k<size;k++)
            		{
            			CubeCylinder tcTemp=new CubeCylinder 
            			(
                				MySurfaceView.this,
            					Constant.UNIT_SIZE,
                				csa ,
                				dynamicsWorld,
                				1,
                				xStart+i*(3.8f)*Constant.UNIT_SIZE,
                				yStart+j*(3.64f)*Constant.UNIT_SIZE,         
                				zStart+k*(2+0.4f)*Constant.UNIT_SIZE,
                				ShaderManager.getPrograms()
                		);            			
            			tca.add(tcTemp);
            			//ʹ��������һ��ʼ�ǲ������
            			tcTemp.body.forceActivationState(RigidBody.WANTS_DEACTIVATION);            			
            		}
            	}
            }
            
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
            						for(CubeCylinder tc:tcaForAdd)
                	                {
                	            		tca.add(tc);
                	                }
            					}            	            	
            	            	tcaForAdd.clear();
            	            }           
            				//ģ��
                			dynamicsWorld.stepSimulation(1f/60.f, 5);
							Thread.sleep(20);
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
    @Override public boolean onTouchEvent(MotionEvent e) 
    {
        switch (e.getAction()) 
        {
           case MotionEvent.ACTION_DOWN:
        	CubeCylinder  tcTemp=new CubeCylinder 
   			(
   					MySurfaceView.this,
       				Constant.UNIT_SIZE,
       				csa,
       				dynamicsWorld,
       				1,
       				0,
       				2,         
       				4,
       				ShaderManager.getPrograms()
       		);        
        	//�������ӵĳ�ʼ�ٶ�
        	tcTemp.body.setLinearVelocity(new Vector3f(0,2,-12));
        	tcTemp.body.setAngularVelocity(new Vector3f(0,0,2)); 
        	//������������뵽�б���
        	synchronized(tcaForAdd)
            {
        	   tcaForAdd.add(tcTemp);
            }
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
