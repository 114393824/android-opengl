package com.bn.Sample17_5;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

//��ʾ�������ε��ࣨ��������ͼ��
public class TexRect  
{	
	int mProgram;//�Զ�����Ⱦ���߳���id 
    int muMVPMatrixHandle;//�ܱ任��������id   
    int muMMatrixHandle;//λ�á���ת�任����
    int maCameraHandle; //�����λ����������id  
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    
    int uTexHandle;//���������������id  
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTextureBuffer;//���������������ݻ���
    int vCount=0;   
    
    public TexRect(MySurfaceView mv,float size,float width,float height)
    {    	
    	//��ʼ��������������ɫ����
    	initVertexData(size,width,height);
    	//��ʼ��shader        
    	intShader(mv);
    } 
    
    //��ʼ�������������������ݵķ���
    public void initVertexData(float UNIT_SIZE,float width,float height)
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
        float vertices[]=new float[]
        {
        	-width*UNIT_SIZE,height*UNIT_SIZE,0,
        	-width*UNIT_SIZE,-height*UNIT_SIZE,0,
        	width*UNIT_SIZE,height*UNIT_SIZE,0,
        	
        	-width*UNIT_SIZE,-height*UNIT_SIZE,0,
        	width*UNIT_SIZE,-height*UNIT_SIZE,0,
        	width*UNIT_SIZE,height*UNIT_SIZE,0
        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //�����������ݵĳ�ʼ��================begin============================
        float textures[]=new float[]
        {
        	0,0,0,1,1,0,
        	0,1,1,1,1,0
        };

        
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
    }  
      
    //��ʼ��shader
    public void intShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����       
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        ShaderUtil.checkGlError("==ss==");   
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        ShaderUtil.checkGlError("==ss==");      
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж��㾭γ����������id   
        maTexCoorHandle=GLES20.glGetAttribLocation(mProgram, "aTexCoor");  
        //��ȡ�������ܱ任��������id 
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");   
        //��ȡλ�á���ת�任��������id
    }
    
    public void drawSelf(int texId) 
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram);
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);  
         
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
         //Ϊ����ָ������������������
         GLES20.glVertexAttribPointer  
         (  
        		maTexCoorHandle,  
         		2, 
         		GLES20.GL_FLOAT, 
         		false,
                2*4,   
                mTextureBuffer
         );   
         //��������λ����������
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