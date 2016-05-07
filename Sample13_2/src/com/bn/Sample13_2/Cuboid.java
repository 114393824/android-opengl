package com.bn.Sample13_2;
import static com.bn.Sample13_2.ShaderUtil.createProgram;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
//�������ǵĳ�����
public class Cuboid 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������id
    int maPositionHandle; //����λ����������id  
    int maTexCoorHandle; //��������������������id  
    int uAngleSpanHandle;//Ť���ܽǶȿ������id  
    int uYStartHandle;//Y������ʼ����id
    int uYSpanHandle;//Y����������id
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    final float Y_MAX=1.5f;
    final float Y_MIN=-1.5f;
    final int FD=6;
    final float hw=0.575f;
    
    float angleSpan=0;
    float angleStep=2f;
    
    public Cuboid(MySurfaceView mv)
    {    	
    	//��ʼ��������������ɫ����
    	initVertexData();
    	//��ʼ��shader        
    	intShader(mv);
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=FD*4*6;

        float vertices[]=new float[vCount*3];
        float texCoor[]=new float[vCount*2];
        float yStart=Y_MIN;
        float ySpan=(Y_MAX-Y_MIN)/FD;
        int count=0;
        int tCount=0;
        for(int i=0;i<FD;i++)
        {
        	float x1=-hw;
        	float y1=yStart;
        	float z1=hw;
        	
        	float x2=hw;
        	float y2=yStart;
        	float z2=hw;
        	
        	float x3=hw;
        	float y3=yStart;
        	float z3=-hw;
        	
        	float x4=-hw;
        	float y4=yStart;
        	float z4=-hw;
        	
        	float x5=-hw;
        	float y5=yStart+ySpan;
        	float z5=hw;
        	
        	float x6=hw;
        	float y6=yStart+ySpan;
        	float z6=hw;
        	
        	float x7=hw;
        	float y7=yStart+ySpan;
        	float z7=-hw;
        	
        	float x8=-hw;
        	float y8=yStart+ySpan;
        	float z8=-hw;
        	//512
        	vertices[count++]=x5;
        	vertices[count++]=y5;
        	vertices[count++]=z5;
        	vertices[count++]=x1;
        	vertices[count++]=y1;
        	vertices[count++]=z1;
        	vertices[count++]=x2;
        	vertices[count++]=y2;
        	vertices[count++]=z2;
        	//526
        	vertices[count++]=x5;
        	vertices[count++]=y5;
        	vertices[count++]=z5;        	
        	vertices[count++]=x2;
        	vertices[count++]=y2;
        	vertices[count++]=z2;
        	vertices[count++]=x6;
        	vertices[count++]=y6;
        	vertices[count++]=z6;
        	//
        	vertices[count++]=x6;
        	vertices[count++]=y6;
        	vertices[count++]=z6;        	
        	vertices[count++]=x2;
        	vertices[count++]=y2;
        	vertices[count++]=z2;
        	vertices[count++]=x3;
        	vertices[count++]=y3;
        	vertices[count++]=z3;
        	
        	vertices[count++]=x6;
        	vertices[count++]=y6;
        	vertices[count++]=z6;        	
        	vertices[count++]=x3;
        	vertices[count++]=y3;
        	vertices[count++]=z3;
        	vertices[count++]=x7;
        	vertices[count++]=y7;
        	vertices[count++]=z7;
        	
        	vertices[count++]=x7;
        	vertices[count++]=y7;
        	vertices[count++]=z7;        	
        	vertices[count++]=x3;
        	vertices[count++]=y3;
        	vertices[count++]=z3;
        	vertices[count++]=x4;
        	vertices[count++]=y4;
        	vertices[count++]=z4;
        	
        	vertices[count++]=x7;
        	vertices[count++]=y7;
        	vertices[count++]=z7;   
        	vertices[count++]=x4;
        	vertices[count++]=y4;
        	vertices[count++]=z4;
        	vertices[count++]=x8;
        	vertices[count++]=y8;
        	vertices[count++]=z8;
        	
        	vertices[count++]=x8;
        	vertices[count++]=y8;
        	vertices[count++]=z8;   
        	vertices[count++]=x4;
        	vertices[count++]=y4;
        	vertices[count++]=z4;
        	vertices[count++]=x1;
        	vertices[count++]=y1;
        	vertices[count++]=z1;
        	
        	vertices[count++]=x8;
        	vertices[count++]=y8;
        	vertices[count++]=z8;           	
        	vertices[count++]=x1;
        	vertices[count++]=y1;
        	vertices[count++]=z1;
        	vertices[count++]=x5;
        	vertices[count++]=y5;
        	vertices[count++]=z5;

        	yStart=yStart+ySpan;
        	
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=0;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=1;
        	texCoor[tCount++]=0;
        }
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //���������������ݵĳ�ʼ��================begin============================       
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

    //��ʼ��shader
    public void intShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_tex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������id  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������id  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ��������Ť���Ƕȿ��
        uAngleSpanHandle = GLES20.glGetUniformLocation(mProgram, "angleSpan"); 
        //��ȡ������Y������ʼ����id
        uYStartHandle = GLES20.glGetUniformLocation(mProgram, "yStart");
        //��ȡ������Y����������id
        uYSpanHandle = GLES20.glGetUniformLocation(mProgram, "ySpan");
    }
    
    public void drawSelf(int texId)
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
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         
         angleSpan=(float) (angleSpan+Math.toRadians(angleStep));   
         if(Math.toDegrees(angleSpan)>90) 
         {
        	 angleStep=-2f;      
         }
         else if(Math.toDegrees(angleSpan)<-90)
         {
        	 angleStep=2f;           
         }
         GLES20.glUniform1f(uAngleSpanHandle , angleSpan);
         GLES20.glUniform1f(uYStartHandle , Y_MIN);
         GLES20.glUniform1f(uYSpanHandle , Y_MAX-Y_MIN); 
         try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 

    }
}
