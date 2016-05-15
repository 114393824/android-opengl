package com.bn.Sample17_6;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.vecmath.Quat4f;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;

import android.opengl.GLES20;
import static com.bn.Sample17_6.Constant.*;
//Ĭ���Ǻ���XԲ��
public class Stick {
	int mProgram;//�Զ�����Ⱦ���߳���id 
    int muMVPMatrixHandle;//�ܱ任��������id   
    int muMMatrixHandle;//λ�á���ת�任����
    int maCameraHandle; //�����λ����������id  
    int maPositionHandle; //����λ����������id  
    int maNormalHandle; //���㷨������������id  
    int maLightLocationHandle;//��Դλ����������id  
        
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
    static float[] mMMatrix = new float[16];//����������ƶ���ת����
	  
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mNormalBuffer;//���������������ݻ���
	
    int vCount=0;    
	float length=10f;//Բ������
	float circle_radius=2f;//Բ�ػ��뾶
	float degreespan=18f;  //Բ�ػ�ÿһ�ݵĶ�����С
	
	RigidBody body;
	MySurfaceView mv;
    
    public Stick(MySurfaceView mv,float length,float circle_radius,float degreespan,float[] colorValue,
    		RigidBody body)
    {    	
    	//��ʼ��������������ɫ����
    	this.mv=mv;
    	initVertexData( length, circle_radius, degreespan,colorValue);
    	//��ʼ��shader        
    	intShader(mv);
    	this.body = body;
    } 
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float length,float circle_radius,float degreespan,float[] colorValue)
    {
    	//�����������ݵĳ�ʼ��================begin============================
		ArrayList<Float> val=new ArrayList<Float>();//�������б�
		ArrayList<Float> ial=new ArrayList<Float>();//����������б�
    	
    	this.length = length; //�볤
    	this.circle_radius = circle_radius;
    	this.degreespan = degreespan;
    	
    	for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//ѭ����
		{
				float x1 =(float)(-length);
				float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float a1=0;
				float b1=y1;
				float c1=z1;
				float l1=getVectorLength(a1, b1, c1);//ģ��
				a1=a1/l1;//���������
				b1=b1/l1;
				c1=c1/l1;
				
				float x2 =(float)(-length);
				float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float a2=0;
				float b2=y2;
				float c2=z2;
				float l2=getVectorLength(a2, b2, c2);//ģ��
				a2=a2/l2;//���������
				b2=b2/l2;
				c2=c2/l2;
				
				float x3 =(float)(length);
				float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float a3=0;
				float b3=y3;
				float c3=z3;
				float l3=getVectorLength(a3, b3, c3);//ģ��
				a3=a3/l3;//���������
				b3=b3/l3;
				c3=c3/l3;
				
				float x4 =(float)(length);
				float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float a4=0;
				float b4=y4;
				float c4=z4;
				float l4=getVectorLength(a4, b4, c4);//ģ��
				a4=a4/l4;//���������
				b4=b4/l4;
				c4=c4/l4;
				
				val.add(x1);val.add(y1);val.add(z1);//���������Σ���6�����������
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
				
				ial.add(a1);ial.add(b1);ial.add(c1);//�����Ӧ�ķ�����
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a4);ial.add(b4);ial.add(c4);
				
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a3);ial.add(b3);ial.add(c3);
				ial.add(a4);ial.add(b4);ial.add(c4);
		}
    	
    	vCount=val.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��float������
        float vertices[]=new float[vCount*3];
        for(int i=0;i<val.size();i++)
    	{
    		vertices[i]=val.get(i);
    	}
		  
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        
        //��alVertix�е�����ֵת�浽һ��float������
        float normals[]=new float[ial.size()];
    	for(int i=0;i<ial.size();i++)
    	{
    		normals[i]=ial.get(i);
    	}
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥����������
        mNormalBuffer.position(0);//���û�������ʼλ��
    }
  
    //��ʼ��shader
    public void intShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����       
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_color.sh", mv.getResources());
        ShaderUtil.checkGlError("==ss==");   
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_color.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        ShaderUtil.checkGlError("==ss==");      
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");   
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");  
        //��ȡ�����ж��㷨������������  
        maNormalHandle= GLES20.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES20.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        maCameraHandle=GLES20.glGetUniformLocation(mProgram, "uCamera");
    }
    
    public void drawSelf(float angle,float x,float y,float z) 
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	MatrixState.pushMatrix();
    		MySurfaceView.init=false;
    		Transform trans = body.getMotionState().getWorldTransform(new Transform());
     		MatrixState.translate(trans.origin.x,trans.origin.y, trans.origin.z);
     		Quat4f ro=trans.getRotation(new Quat4f());
     		if(ro.x!=0||ro.y!=0||ro.z!=0)
     		{
     			float[] fa=SYSUtil.fromSYStoAXYZ(ro);
     			if(isNumber(fa[0]+"") && isNumber(fa[1]+"") && isNumber(fa[2]+"")){
     				
     	 			MatrixState.rotate(fa[0], fa[1], fa[2], fa[3]);
     			}
     		}
    	
    	GLES20.glUseProgram(mProgram); 
    	MatrixState.rotate(angle, x, y, z);
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
         //��λ�á���ת�任��������ɫ������
         GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.currMatrix, 0);   
         //����Դλ�ô�����ɫ������   
         GLES20.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFBRed);
         //�������λ�ô�����ɫ������   
         GLES20.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         
         //Ϊ����ָ������λ������    
         GLES20.glVertexAttribPointer        
         (
         		maPositionHandle,   
         		3, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4, 
                mVertexBuffer   
         );
         //Ϊ����ָ�����㷨��������
         GLES20.glVertexAttribPointer  
         (
        		maNormalHandle, 
         		4, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4,   
                mNormalBuffer
         );            
         //��������λ����������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maNormalHandle);  
         
         //����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
         MatrixState.popMatrix();
    }
    
  //��������񻯣���ģ����
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
}