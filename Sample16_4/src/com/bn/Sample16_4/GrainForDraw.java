package com.bn.Sample16_4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;

//�û����Ƶ������ӵ���
public class GrainForDraw 
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
	private FloatBuffer   mColorBuffer;//������ɫ���ݻ���
    float scale;//���ǳߴ�
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
    
    int mProgram;//�Զ�����Ⱦ���߳���id 
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int uPointSizeHandle;//����ߴ��������
    int uColorHandle;//������ɫ��������
    
    public GrainForDraw(float scale,float red,float green,float blue,MySurfaceView mv)
    {
    	this.scale=scale;
    	initVertexData(red,green,blue);
    	initShader(mv);
    }
    
    //��ʼ����������ķ���
    public void initVertexData(float red,float green,float blue)
    {    	  	
    	//�����������ݵĳ�ʼ��   
        float vertices[]={0,0,0};		
        //���������������ݻ���
        //vertices.length*4����Ϊһ��Float�ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        //�����������ݵĳ�ʼ��
        float colors[]={red,green,blue};		
        //���������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mColorBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mColorBuffer.put(colors);//�򻺳����з��붥����������
        mColorBuffer.position(0);//���û�������ʼλ��
    }

    //��ʼ��shader
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����       
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_xk.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_xk.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");        
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
        //��ȡ����ߴ��������
        uPointSizeHandle = GLES20.glGetUniformLocation(mProgram, "uPointSize"); 
        //��ȡ������ɫ��������
        uColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor"); 
    }
    
    public void drawSelf()
    {  
    	//ָ��ʹ��ĳ��shader����
   	    GLES20.glUseProgram(mProgram);
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);  
        //������ߴ紫��Shader����
        GLES20.glUniform1f(uPointSizeHandle, scale);  
        //��������ɫ����Shader����
        GLES20.glUniform3fv(uColorHandle, 1,mColorBuffer); 
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
        //������λ����������
        GLES20.glEnableVertexAttribArray(maPositionHandle);         
        //�������ǵ�    
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1); 
    }
}