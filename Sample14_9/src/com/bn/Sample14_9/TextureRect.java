package com.bn.Sample14_9;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
public class TextureRect 
{
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������   
    int maTexCoorHandle; //��������������������
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
    private FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ��� 
    int vCount;//��������
    
    float width;
    float height;
    
    float sEnd;//��ť���½ǵ�s��tֵ
    float tEnd;
	public TextureRect(MySurfaceView mv, 
			float width,float height,	//���εĿ��
			float sEnd, float tEnd //���½ǵ�s��tֵ
			)
	{

		this.width=width;
    	this.height=height;
    	this.sEnd = sEnd;
    	this.tEnd = tEnd;
		initVertexData();
        initShader(mv);
        
	}
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;//ÿ���������������Σ�ÿ��������3������        
        float vertices[]=
        {
            	-width/2.0f, height/2.0f, 0,
            	-width/2.0f, -height/2.0f, 0,
            	width/2.0f, height/2.0f, 0,
            	
            	-width/2.0f, -height/2.0f, 0,
            	width/2.0f, -height/2.0f, 0,
            	width/2.0f, height/2.0f, 0,
        };
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
      //���������������ݵĳ�ʼ��================begin============================
        float texCoor[]=new float[]//��������
        {
        		0,0, 0,tEnd, sEnd,0,
        		0,tEnd, sEnd,tEnd, sEnd,0        		
        };        
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //���������������ݵĳ�ʼ��================end============================
    }
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_tex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ���������� 
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition"); 
        //��ȡ�������ܱ任����id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ�����ж�������������������
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
    }
	public void drawSelf()
	{
		 //�ƶ�ʹ��ĳ����ɫ������
   	 	GLES20.glUseProgram(mProgram);
        //�����ձ任��������ɫ������
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);

		// �����������������ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer
		(
				maTexCoorHandle, 
				2, 
				GLES20.GL_FLOAT,
				false, 
				2 * 4, 
				mTexCoorBuffer
		);
		// ���ö���λ������
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glEnableVertexAttribArray(maTexCoorHandle);
		
        //�����㷨�������ݴ�����Ⱦ����
        GLES20.glVertexAttribPointer  
        (
        		maPositionHandle,   
        		3, 
        		GLES20.GL_FLOAT, 
        		false,
               3*4,   
               mVertexBuffer
        );          
        //���ƾ���
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
	}
	
}
