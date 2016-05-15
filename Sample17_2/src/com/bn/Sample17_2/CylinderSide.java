package com.bn.Sample17_2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
//���㷨��Բ�������ƶ�����
public class CylinderSide {
	
	int mProgram;//�Զ�����Ⱦ���߳���id 
    int muMVPMatrixHandle;//�ܱ任��������id   
    int muMMatrixHandle;//λ�á���ת�任����
    int uTexHandle;//���������������id
    
    int maCameraHandle; //�����λ����������id  
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
	private FloatBuffer   vertexBuffer;//�����������ݻ���
	private FloatBuffer   textureBuffer;//�����������ݻ���
	
    int vCount=0;//�������
    float angdegSpan;//ÿ�������ζ���
    float xAngle=0;//��z����ת�ĽǶ�
    float yAngle=0;//��y����ת�ĽǶ�
    float zAngle=0;//��z����ת�ĽǶ�
	public CylinderSide(float r, float h, int n,int mProgram) {//��С���뾶���߶ȣ�����
		this.mProgram=mProgram;
		angdegSpan=360.0f/n;
		vCount=3*n*4;//�������������3*n*4�������Σ�ÿ�������ζ�����������
		//�������ݳ�ʼ��
		float[] vertices=new float[vCount*3];
		float[] textures=new float[vCount*2];//��������S��T����ֵ����
		float[] normals = new float[vCount*3];//������
		//�������ݳ�ʼ��
		int count=0;
		int stCount=0;
		int normalCount=0;
		for(float angdeg=0;Math.ceil(angdeg)<360;angdeg+=angdegSpan)//����
		{
			
			double angrad=Math.toRadians(angdeg);//��ǰ����
			double angradNext=Math.toRadians(angdeg+angdegSpan);//��һ����
			//��Բ��ǰ��---0
			vertices[count++]=(float) (-r*Math.sin(angrad));
			vertices[count++]=0;
			vertices[count++]=(float) (-r*Math.cos(angrad));
			
			normals[normalCount++]=(float) (-r*Math.sin(angrad));
			normals[normalCount++]=0;
			normals[normalCount++]=(float) (-r*Math.cos(angrad));
			
			textures[stCount++]=(float) (angrad/(2*Math.PI));//st����
			textures[stCount++]=1;
			//��Բ��һ��---3
			vertices[count++]=(float) (-r*Math.sin(angradNext));
			vertices[count++]=h;
			vertices[count++]=(float) (-r*Math.cos(angradNext));
			
			normals[normalCount++]=(float) (-r*Math.sin(angradNext));
			normals[normalCount++]=0;
			normals[normalCount++]=(float) (-r*Math.cos(angradNext));
			
			textures[stCount++]=(float) (angradNext/(2*Math.PI));//st����
			textures[stCount++]=0;
			//��Բ��ǰ��---2
			vertices[count++]=(float) (-r*Math.sin(angrad));
			vertices[count++]=h;
			vertices[count++]=(float) (-r*Math.cos(angrad));
			
			normals[normalCount++]=(float)(-r*Math.sin(angrad));
			normals[normalCount++]=0;
			normals[normalCount++]=(float)(-r*Math.cos(angrad));
			
			textures[stCount++]=(float) (angrad/(2*Math.PI));//st����
			textures[stCount++]=0;
			
			//��Բ��ǰ��---0
			vertices[count++]=(float) (-r*Math.sin(angrad));
			vertices[count++]=0;
			vertices[count++]=(float) (-r*Math.cos(angrad));
			
			normals[normalCount++]=(float)(-r*Math.sin(angrad));
			normals[normalCount++]=0;
			normals[normalCount++]=(float)(-r*Math.cos(angrad));
			
			textures[stCount++]=(float) (angrad/(2*Math.PI));//st����
			textures[stCount++]=1;
			//��Բ��һ��---1
			vertices[count++]=(float) (-r*Math.sin(angradNext));
			vertices[count++]=0;
			vertices[count++]=(float) (-r*Math.cos(angradNext));
			
			normals[normalCount++]=(float)(-r*Math.sin(angradNext));
			normals[normalCount++]=0;
			normals[normalCount++]=(float)(-r*Math.cos(angradNext));
			
			textures[stCount++]=(float) (angradNext/(2*Math.PI));//st����
			textures[stCount++]=1;
			//��Բ��һ��---3
			vertices[count++]=(float) (-r*Math.sin(angradNext));
			vertices[count++]=h;
			vertices[count++]=(float) (-r*Math.cos(angradNext));
			
			normals[normalCount++]=(float)(-r*Math.sin(angradNext));
			normals[normalCount++]=0;
			normals[normalCount++]=(float)(-r*Math.cos(angradNext));
			
			textures[stCount++]=(float) (angradNext/(2*Math.PI));//st����
			textures[stCount++]=0;
		}
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);//���������������ݻ���
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        vertexBuffer = vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        vertexBuffer.put(vertices);//�򻺳����з��붥����������
        vertexBuffer.position(0);//���û�������ʼλ��
        
        //st�������ݳ�ʼ��
        ByteBuffer cbb = ByteBuffer.allocateDirect(textures.length*4);//���������������ݻ���
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        textureBuffer = cbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        textureBuffer.put(textures);//�򻺳����з��붥����������
        textureBuffer.position(0);//���û�������ʼλ��
	}
	
	//��ʼ��shader
    public void intShader(MySurfaceView mv)
    {
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж��㾭γ����������id   
        maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");  
        //��ȡ�������ܱ任��������id 
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");   
        //��ȡλ�á���ת�任��������id
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix");  
        //��ȡ�����������λ������id
        maCameraHandle=GLES20.glGetUniformLocation(mProgram, "uCamera"); 
        uTexHandle=GLES20.glGetUniformLocation(mProgram, "sTexture"); 
    }
	
    public void drawSelf(int texId) 
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram);
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
       //��λ�á���ת�任������shader����
         GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);  
         //�������λ�ô���shader����   
         GLES20.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         
         //Ϊ����ָ������λ������    
         GLES20.glVertexAttribPointer        
         (
         		maPositionHandle,   
         		3, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4, 
                vertexBuffer   
         );       
         //Ϊ����ָ������������������
         GLES20.glVertexAttribPointer  
         (  
        		maTexCoorHandle,  
         		2, 
         		GLES20.GL_FLOAT, 
         		false,
                2*4,   
                textureBuffer
         );   
         //������λ����������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);    
         GLES20.glUniform1i(uTexHandle, 0);
           
         //����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}
