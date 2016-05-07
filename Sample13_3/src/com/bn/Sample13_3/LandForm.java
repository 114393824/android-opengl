package com.bn.Sample13_3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import static com.bn.Sample13_3.Constant.*;
/*
 * �Ҷ�ͼ���ɵ���
 */
public class LandForm 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������  
    int maTexCoorHandle; //��������������������  
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
    int uSandTexHandle;//����������������  
    int uGrassTexHandle;//�ݵ�������������
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    public LandForm(float[][]landArray,int mProgram)
    {    	
    	this.mProgram=mProgram;
    	//��ʼ��������������ɫ����
    	initVertexData(landArray);
    	//��ʼ��shader        
    	intShader();
    }
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float[][] landArray)
    {
    	int cols=landArray[0].length-1;//�ûҶ�ͼ������
    	int rows=landArray.length-1;//�ûҶ�ͼ������
    	vCount=cols*rows*2*3;//ÿ���������������Σ�ÿ��������3������   
        float vertices[]=new float[vCount*3];//ÿ������xyz��������
        int count=0;//���������
        for(int i=0;i<rows;i++)
        {
        	for(int j=0;j<cols;j++)
        	{        		
        		//���㵱ǰ�������ϲ������ 
        		float zsx=j*LAND_SPAN;
        		float zsz=i*LAND_SPAN;
        		//��������������
        	    	//���ϵ�
            		vertices[count++]=zsx;
            		vertices[count++]=landArray[i][j];
            		vertices[count++]=zsz;
            		//���µ�
            		vertices[count++]=zsx;
            		vertices[count++]=landArray[i+1][j];
            		vertices[count++]=zsz+LAND_SPAN;
            		//���ϵ�
            		vertices[count++]=zsx+LAND_SPAN;
            		vertices[count++]=landArray[i][j+1];
            		vertices[count++]=zsz;
        			//���ϵ�
            		vertices[count++]=zsx+LAND_SPAN;
            		vertices[count++]=landArray[i][j+1];
            		vertices[count++]=zsz;
            		//���µ�
            		vertices[count++]=zsx;
            		vertices[count++]=landArray[i+1][j];
            		vertices[count++]=zsz+LAND_SPAN;
            		//���µ�
            		vertices[count++]=zsx+LAND_SPAN;
            		vertices[count++]=landArray[i+1][j+1];
            		vertices[count++]=zsz+LAND_SPAN; 
        	}
        }
        //���������������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        float[] texCoor=generateTexCoor(cols,rows);
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
    }
    //��ʼ��shader
    public void intShader()
    {
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //����id
        uSandTexHandle=GLES20.glGetUniformLocation(mProgram, "sTextureSand");  
        uGrassTexHandle=GLES20.glGetUniformLocation(mProgram, "sTextureGrass");  
    }
    public void drawSelf(int tex_grassId,int tex_sandId)
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram); 
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //������λ�����ݴ�����Ⱦ����
         GLES20.glVertexAttribPointer  
         (
         		maPositionHandle,   
         		3, 
         		GLES20.GL_FLOAT, 
         		false,
                3*4,   
                mVertexBuffer
         );       
         //�����������������ݴ�����Ⱦ����
         GLES20.glVertexAttribPointer  
         (
        		maTexCoorHandle, 
         		2, 
         		GLES20.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );   
         //���ö���λ�á�������������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maTexCoorHandle);  
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex_sandId);
         GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tex_grassId);    
         GLES20.glUniform1i(uSandTexHandle, 0);
         GLES20.glUniform1i(uGrassTexHandle, 1);  
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES , 0, vCount); 
    }
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=8.0f/bw;//����
    	float sizeh=8.0f/bh;//����
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}
