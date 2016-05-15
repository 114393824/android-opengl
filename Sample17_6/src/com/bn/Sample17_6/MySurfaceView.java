package com.bn.Sample17_6;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

import com.bn.Sample17_6.R;
import com.bulletphysics.BulletGlobals;
import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CylinderShape;
import com.bulletphysics.collision.shapes.CylinderShapeX;
import com.bulletphysics.collision.shapes.CylinderShapeZ;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SliderConstraint;
import com.bulletphysics.linearmath.MatrixUtil;
import com.bulletphysics.linearmath.Transform;

import static com.bn.Sample17_6.Constant.*;

public class MySurfaceView extends GLSurfaceView {

	DiscreteDynamicsWorld dynamicsWorld;
	CollisionShape boxShape;
	CollisionShape stickShape;
	CollisionShape stickFBSliderShape;
	CollisionShape stickLRSliderShape;
	//����
	RigidBodyHelper cubeBody;
	RigidBodyHelper stickFBSliderBody;
	RigidBodyHelper stickLRFSliderBody;
	RigidBodyHelper stickLRNSliderBody;
	//��ӻ���Լ��
	SliderConstraint sliderFB;
	SliderConstraint sliderLRF;//Զ�˵ĺ�����
	SliderConstraint sliderLRN;//���˵ĺ�����
	SliderConstraint[] sliders=new SliderConstraint[3];
	static boolean sliding=false;
	static boolean init=true;
	int currIndex;
	MyRenderer renderer; 
    public static int keyState=0;
	boolean flag=true;
	
	float screenWidth;
	float screenHeight;
	float buttonPixels;
	
	public MySurfaceView(Context context) {
		super(context);
		this.setEGLContextClientVersion(2);
		initWorld();
		renderer = new MyRenderer();
		this.setRenderer(renderer);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	 @Override 
	    public boolean onTouchEvent(MotionEvent e) {
	        float y = e.getY();
	        float x = e.getX();
	        switch (e.getAction()) {	       
	        case MotionEvent.ACTION_DOWN:
	        	handleArrowDown(x,y);
	        	break;
	        case MotionEvent.ACTION_UP:
	        	handleArrowUp(x,y);
	        	break;
	        }
	        return true; 
	    }
	 public void handleArrowDown(float x,float y){
		 float buttonCenterX=screenWidth-buttonPixels/2;
		 float buttonCenterY=screenHeight-buttonPixels/2;
		 float baseUnit=buttonPixels/4;
		 
		 float upXMax=baseUnit+buttonCenterX;
		 float upXMin=-baseUnit+buttonCenterX;
		 float upYMin=-2*baseUnit+buttonCenterY;
		 float upYMax=-baseUnit+buttonCenterY;
		 
		 float downXMax=baseUnit+buttonCenterX;
		 float downXMin=-baseUnit+buttonCenterX;
		 float downYMax=2*baseUnit+buttonCenterY;
		 float downYMin=baseUnit+buttonCenterY;  
		 
		 float leftXMax=-baseUnit+buttonCenterX;
		 float leftXMin=-2*baseUnit+buttonCenterX;
		 float leftYMax=baseUnit+buttonCenterY;
		 float leftYMin=-baseUnit+buttonCenterY;  
		 
		 float rightXMax=2*baseUnit+buttonCenterX;
		 float rightXMin=baseUnit+buttonCenterX;
		 float rightYMax=baseUnit+buttonCenterY;
		 float rightYMin=-baseUnit+buttonCenterY;  
		 
		 if(upXMin<x && x<upXMax && upYMin<y && y<upYMax){
			 keyState=0x1;
		 }else if(rightXMin<x && x<rightXMax && rightYMin<y && y<rightYMax){
			 keyState=0x8;
		 }else if(downXMin<x && x<downXMax && downYMin<y && y<downYMax){
			 keyState=0x2;
		 }else if(leftXMin<x && x<leftXMax && leftYMin<y && y<leftYMax){
			 keyState=0x4;
		 }
	 }
	 public void handleArrowUp(float x,float y){ 
		 float buttonCenterX=screenWidth-buttonPixels/2;
		 float buttonCenterY=screenHeight-buttonPixels/2;
		 float baseUnit=buttonPixels/4;  
		 
		 float upXMax=baseUnit+buttonCenterX;
		 float upXMin=-baseUnit+buttonCenterX;
		 float upYMin=-2*baseUnit+buttonCenterY;
		 float upYMax=-baseUnit+buttonCenterY;
		 
		 float downXMax=baseUnit+buttonCenterX;  
		 float downXMin=-baseUnit+buttonCenterX;
		 float downYMax=2*baseUnit+buttonCenterY;
		 float downYMin=baseUnit+buttonCenterY;  
		 
		 float leftXMax=-baseUnit+buttonCenterX;
		 float leftXMin=-2*baseUnit+buttonCenterX;
		 float leftYMax=baseUnit+buttonCenterY;
		 float leftYMin=-baseUnit+buttonCenterY;  
		 
		 float rightXMax=2*baseUnit+buttonCenterX;
		 float rightXMin=baseUnit+buttonCenterX;
		 float rightYMax=baseUnit+buttonCenterY;
		 float rightYMin=-baseUnit+buttonCenterY;  
		 
		 if(upXMin<x && x<upXMax && upYMin<y && y<upYMax){
			 keyState=0;
			 stopSlide();
		 }else if(rightXMin<x && x<rightXMax && rightYMin<y && y<rightYMax){
			 keyState=0;
			 stopSlide();
		 }else if(downXMin<x && x<downXMax && downYMin<y && y<downYMax){
			 keyState=0;
			 stopSlide();
		 }else if(leftXMin<x && x<leftXMax && leftYMin<y && y<leftYMax){
			 keyState=0;
			 stopSlide();
		 }
	 }
	public void initWorld(){
		CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
		CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
		Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
		Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
		int maxProxies = 1024;
		AxisSweep3 overlappingPairCache =new AxisSweep3(worldAabbMin, worldAabbMax, maxProxies);
		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, overlappingPairCache, solver,collisionConfiguration);
		dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
		boxShape=new BoxShape(new Vector3f(cubeSize,cubeSize,cubeSize));
		stickShape = new CylinderShape(new Vector3f(Stick_R,Stick_Length,Stick_R));//Y����Բ��
		stickFBSliderShape = new CylinderShapeZ(new Vector3f(Stick_R,Stick_R,Stick_Length));//Z����Բ��
		stickLRSliderShape = new CylinderShapeX(new Vector3f(Stick_Length,Stick_R,Stick_R));//X����Բ��
		initRigidBody();
		//��ӳ�������ǰ����Ĺ�֮���Լ��
		Vector3f originA = new Vector3f(0, 0, 0);
		Vector3f originB = new Vector3f(0, 0, 0);
		addSliderConstraint(0,stickFBSliderBody.body,cubeBody.body,BulletGlobals.SIMD_PI/2,originA,originB,true);//BulletGlobals.SIMD_PI/2
		//���ǰ����Ĺ������ҷ���Զ�˵Ĺ�֮���Լ��
		originA = new Vector3f(0,0,0);
		originB = new Vector3f(0, 0, -Stick_Length);
		addSliderConstraint(1,stickLRFSliderBody.body,stickFBSliderBody.body,0,originA,originB,true);
		//���FB����Ĺ���LR������˵Ĺ�֮���Լ��
		originA = new Vector3f(0,0,0);
		originB = new Vector3f(0, 0, Stick_Length);
		addSliderConstraint(2,stickLRNSliderBody.body,stickFBSliderBody.body,0,originA,originB,true);
	}
	public void initRigidBody(){ //TODO
		cubeBody = new RigidBodyHelper(boxShape,1f,dynamicsWorld, 0.0f, 0.8f, new Vector3f(0,Ceiling_Height-5,0),true);
		stickFBSliderBody = new RigidBodyHelper(stickFBSliderShape, 1f, dynamicsWorld, 0.0f, 0.8f, new Vector3f(0,Ceiling_Height-5,0),true);
		stickLRFSliderBody = new RigidBodyHelper(stickLRSliderShape, 0, dynamicsWorld, 0.0f, 0.8f, new Vector3f(0,Ceiling_Height-5,-Stick_Length),true);
		stickLRNSliderBody = new RigidBodyHelper(stickLRSliderShape, 0, dynamicsWorld, 0.0f, 0.8f, new Vector3f(0,Ceiling_Height-5,Stick_Length),true);
	}
	public void addSliderConstraint(int index,RigidBody ra,RigidBody rb,float angle,Vector3f originA,Vector3f originB,boolean force){
		Transform localA = new Transform();
		Transform localB = new Transform();
		localA.setIdentity();
		localB.setIdentity();
		MatrixUtil.setEulerZYX(localA.basis, 0,angle, 0 );
		localA.origin.set(originA);
		MatrixUtil.setEulerZYX(localB.basis, 0, angle, 0);
		localB.origin.set(originB);	
		if(index==0){
			sliderFB = new SliderConstraint(ra, rb, localA, localB, force);
			//���ó�ʼ��limit
			sliderFB.setLowerLinLimit(-Stick_Length);//���ƻ�������С����
			sliderFB.setUpperLinLimit(Stick_Length);//���ƻ�����������
			sliderFB.setLowerAngLimit(0);
			sliderFB.setUpperAngLimit(0);
			sliderFB.setDampingDirLin(0.05f); //������������
			dynamicsWorld.addConstraint(sliderFB,true);
			sliders[index]=sliderFB;
		}
		if(index==1){
			sliderLRF = new SliderConstraint(ra, rb, localA, localB, force);
			//���ó�ʼ��limit
			sliderLRF.setLowerLinLimit(-Stick_Length);//���ƻ�������С����
			sliderLRF.setUpperLinLimit(Stick_Length);//���ƻ�����������
			sliderLRF.setLowerAngLimit(0);
			sliderLRF.setUpperAngLimit(0);
			sliderLRF.setDampingDirLin(0.5f); //������������
			dynamicsWorld.addConstraint(sliderLRF,true);
			sliders[index]=sliderLRF;
		}
		if(index==2){
			sliderLRN = new SliderConstraint(ra, rb, localA, localB, force);
			//���ó�ʼ��limit
			sliderLRN.setLowerLinLimit(-Stick_Length);//���ƻ�������С����
			sliderLRN.setUpperLinLimit(Stick_Length);//���ƻ�����������
			sliderLRN.setLowerAngLimit(0);
			sliderLRN.setUpperAngLimit(0);
			sliderLRN.setDampingDirLin(0.5f); //������������
			dynamicsWorld.addConstraint(sliderLRN,true);
			sliders[index]=sliderLRN;
		}
	}
	public void slideFB(float mulFactor){
		sliding=true;
		sliderFB.getRigidBodyB().activate();
		currIndex=0;
		sliderFB.setPoweredLinMotor(true);//����motor����
		sliderFB.setMaxLinMotorForce(1.0f);//���������˶����Ĵ�С
		sliderFB.setTargetLinMotorVelocity(5.0f*mulFactor);//���������˶����ٶ�
	}
	public void slideLR(float mulFactor){
		sliding=true;
		sliderLRF.getRigidBodyB().activate();
		currIndex=1;
		sliderLRF.setPoweredLinMotor(true);//����motor����
		sliderLRF.setMaxLinMotorForce(5.0f);//���������˶����Ĵ�С
		sliderLRF.setTargetLinMotorVelocity(5.0f*mulFactor);//���������˶����ٶ�
		
		sliderLRN.setPoweredLinMotor(true);//����motor����
		sliderLRN.setMaxLinMotorForce(5.0f);//���������˶����Ĵ�С
		sliderLRN.setTargetLinMotorVelocity(5.0f*mulFactor);//���������˶����ٶ�
	}
	public void stopSlide(){
		sliding=false;
		sliders[currIndex].setPoweredLinMotor(false);
		sliders[currIndex].setMaxLinMotorForce(0.0f);//���������˶����Ĵ�С
		sliders[currIndex].setTargetLinMotorVelocity(0.0f);//���������˶����ٶ�
		if(currIndex==1){
			sliders[currIndex+1].setPoweredLinMotor(false);
			sliders[currIndex+1].setMaxLinMotorForce(0.0f);//���������˶����Ĵ�С
			sliders[currIndex+1].setTargetLinMotorVelocity(0.0f);//���������˶����ٶ�
		}
	}
	private class MyRenderer implements GLSurfaceView.Renderer{
		float ratio;
		Cube cube;
		Stick stickFBSliderAxis;
		Stick stickLRFSliderAxis;
		Stick stickLRNSliderAxis;
		int activeTexId;
		int deactiveTexId;
	    int textureArrow;//ϵͳ�������Ϸǰ�����ⰴť����id
		int[] texIds = new int[2];
		TexRect button;//���ⰴť 
		@Override public void onDrawFrame(GL10 gl) {
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
			//���ô˷����������͸��ͶӰ����   
			MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1.5f, 100);
			//���ô˷������������9����λ�þ���
            MatrixState.setCamera(2,14,2f,0,0,0,0f,0f,-1f);   
            MatrixState.setLightLocationRed(5, 50, 15);
            
			MatrixState.pushMatrix();
			cube.drawSelf(texIds, 1);
			stickFBSliderAxis.drawSelf(90,0,1,0);//����������
			stickLRFSliderAxis.drawSelf(0,1, 0, 0);//����Զ�˺�����
			stickLRNSliderAxis.drawSelf(0,1, 0, 0);//���ƽ��˺�����
			MatrixState.popMatrix();
			 //�������ⰴť
            //�������
			MatrixState.setCamera(0,0,10,0,0,0,0f,1.0f,0.0f); 
			MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 1.5f, 100);
			MatrixState.pushMatrix();
			GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            GLES20.glEnable(GLES20.GL_BLEND);
            //���û������
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
            //���ư�ť
            MatrixState.pushMatrix();
            MatrixState.translate(ratio-0.5f,-(1-0.5f),0);  
            button.drawSelf(textureArrow);
            MatrixState.popMatrix();
            //�رջ��
            GLES20.glDisable(GLES20.GL_BLEND);
            MatrixState.popMatrix();
		}
		@Override public void onSurfaceChanged(GL10 gl, int width, int height) {
			 //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            ratio= (float) width / height;   
            screenWidth= width;
            screenHeight=height;
            buttonPixels=screenHeight/2;
		}
		@Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {//TODO
			  //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f, 1.0f);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            MatrixState.setInitStack();
            activeTexId = initTexture(R.drawable.wood_bin1);
            textureArrow = initTexture(R.drawable.arrow_small);
            texIds[0]=deactiveTexId;
            texIds[1]=activeTexId;
            cube = new Cube(MySurfaceView.this,cubeSize,cubeBody.body);
            stickFBSliderAxis = new Stick(MySurfaceView.this,Stick_Length,Stick_R,11.25f,
					new float[]{1,0,0,1},
					stickFBSliderBody.body);
            stickLRFSliderAxis = new Stick(MySurfaceView.this,Stick_Length,Stick_R,11.25f,
					new float[]{1,0,0,1},
					stickLRFSliderBody.body);
            stickLRNSliderAxis = new Stick(MySurfaceView.this,Stick_Length,Stick_R,11.25f,
					new float[]{1,0,0,1},
					stickLRNSliderBody.body);
            button = new TexRect(MySurfaceView.this, 0.5f,1f,1f);
            
            new Thread(){
            	public void run(){
            		while(flag){            			
            			try{
            				//ģ��
                			dynamicsWorld.stepSimulation(1f/60.f, 5);
							Thread.sleep(20);
						} catch (Exception e){
							e.printStackTrace();
						}
            		}
            	}
            }.start();
            new KeyThread(MySurfaceView.this).start();
		}
	}
	public int initTexture(int drawableId){//textureId
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
}
