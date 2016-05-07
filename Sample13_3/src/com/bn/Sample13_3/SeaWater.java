package com.bn.Sample13_3;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.bn.Sample13_3.Constant.*;
import android.opengl.GLES20;

/*
 * ���ƺ�ˮ
 * �ܹ����в���
 */
public class SeaWater 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������  
    int maTexCoorHandle; //��������������������  
    int maStartAngleHandle; //��֡��ʼ�Ƕ���������\
    int muWidthSpanHandle;//���򳤶��ܿ������    
    
    int currIndex=0;//��ǰ��ɫ������
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    float currStartAngle=0;//��ǰ֡����ʼ�Ƕ�0~2PI
    float texSize=16;
    public SeaWater(int mProgram)
    {    	
    	this.mProgram=mProgram;
    	//��ʼ����������
    	initVertexData();
    	//��ɫ����
    	intShader();
    	//����һ���̶߳�ʱ��֡
    	new Thread()
    	{
    		public void run()
    		{
    			while(flag_go)
    			{
    				currStartAngle+=(Math.PI/16);
        			try 
        			{
    					Thread.sleep(50);
    				} catch (InterruptedException e) 
    				{
    					e.printStackTrace();
    				}
    			}     
    		}    
    	}.start();  
    }
    public void initVertexData()
    {
    	vCount=COLS*ROWS*2*3;//ÿ���������������Σ�ÿ��������3������        
        float vertices[]=new float[vCount*3];//ÿ������xyz��������
        int count=0;//���������
        for(int i=0;i<ROWS;i++)//����ɨ��
        {
        	for(int j=0;j<COLS;j++)//����ɨ��
        	{        		
        		//���㵱ǰ�������ϲ������ 
        		float zsx=j*WATER_SPAN;
        		float zsy=0;
        		float zsz=i*WATER_SPAN;
        		//���ϵ�
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		//���µ�
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz+WATER_SPAN;
        		//���ϵ�
        		vertices[count++]=zsx+WATER_SPAN;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		//���ϵ�
        		vertices[count++]=zsx+WATER_SPAN;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		//���µ�
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz+WATER_SPAN;
        		//���µ�
        		vertices[count++]=zsx+WATER_SPAN;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz+WATER_SPAN; 
        	}
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        float texCoor[]=generateTexCoor(COLS,ROWS);     
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
        maTexCoorHandle = GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ��֡��ʼ�Ƕ���������
        maStartAngleHandle =GLES20.glGetUniformLocation(mProgram, "uStartAngle");  
        //��ȡ���򳤶��ܿ������
        muWidthSpanHandle =GLES20.glGetUniformLocation(mProgram, "uWidthSpan");  
    }
    public void drawSelf(int texId)
    {        
    	
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mProgram); 
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //����֡��ʼ�Ƕȴ���shader����
         GLES20.glUniform1f(maStartAngleHandle, currStartAngle); 
         //�����򳤶��ܿ�ȴ���shader����
         GLES20.glUniform1f(muWidthSpanHandle, UNIT_SIZE*31*3);  
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
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
        
    }
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=texSize/bw;//����
    	float sizeh=texSize/bh;//����
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
