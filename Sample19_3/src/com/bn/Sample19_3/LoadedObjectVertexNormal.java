package com.bn.Sample19_3;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;

//���غ�����塪��Я��������Ϣ���Զ�������ƽ��������
public class LoadedObjectVertexNormal
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id  
    int muMVPMatrixHandle;//�ܱ任��������
    int muMMatrixHandle;//λ�á���ת�任����
    int muColorHandle;//������ɫ
    int maPositionHandle; //����λ����������  
    int maNormalHandle; //���㷨������������  
    int maLightLocationHandle;//��Դλ����������  
    int maCameraHandle; //�����λ���������� 
    
    String mVertexShader;//������ɫ������ű�    	 
    String mFragmentShader;//ƬԪ��ɫ������ű�    
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���  
	FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount=0;
    float vertices[];
	float minX;//x����Сλ��
	float maxX;//x�����λ��
	float minY;//y����Сλ��
	float maxY;//y�����λ��
	float minZ;//z����Сλ��
	float maxZ;//z�����λ��
	float midX;//���ĵ�����
	float midY;
	float midZ;
	//������ɫ
	float[] color=new float[]
    {
		1,1,1,1
    };
	
    public LoadedObjectVertexNormal(MySurfaceView mv,float[] vertices,float[] normals)
    {    	
    	//��ʼ��������������ɫ����
    	initVertexData(vertices,normals);
    	//��ʼ��shader        
    	initShader(mv);
    }
    
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float[] vertices,float[] normals)
    {
    	//�����������ݵĳ�ʼ��================begin============================
    	vCount=vertices.length/3;   
    	this.vertices=new float[vertices.length];
    	this.vertices=vertices;
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        
        //���㷨�������ݵĳ�ʼ��================begin============================  
        ByteBuffer cbb = ByteBuffer.allocateDirect(normals.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================
    }

    //��ʼ��shader
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж��㷨������������  
        maNormalHandle= GLES20.glGetAttribLocation(mProgram, "aNormal");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡλ�á���ת�任��������
        muMMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMMatrix"); 
        //��ȡ�����й�Դλ������
        maLightLocationHandle=GLES20.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        maCameraHandle=GLES20.glGetUniformLocation(mProgram, "uCamera");
        muColorHandle=GLES20.glGetUniformLocation(mProgram, "aColor");
    }
    
    public void drawSelf()
    {        
    	 //�ƶ�ʹ��ĳ����ɫ������
    	 GLES20.glUseProgram(mProgram);
         //�����ձ任��������ɫ������
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
         //��λ�á���ת�任��������ɫ������
         GLES20.glUniformMatrix4fv(muMMatrixHandle, 1, false, MatrixState.getMMatrix(), 0);   
         //����Դλ�ô�����ɫ������   
         GLES20.glUniform3fv(maLightLocationHandle, 1, MatrixState.lightPositionFB);
         //�������λ�ô�����ɫ������   
         GLES20.glUniform3fv(maCameraHandle, 1, MatrixState.cameraFB);
         //���붥����ɫ����
         GLES20.glUniform4fv(muColorHandle, 1, color, 0);
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
         //�����㷨�������ݴ�����Ⱦ����
         GLES20.glVertexAttribPointer 
         (
        		maNormalHandle, 
         		3,   
         		GLES20.GL_FLOAT,
         		false,
                3*4,   
                mNormalBuffer
         );
         //���ö���λ�á�����������
         GLES20.glEnableVertexAttribArray(maPositionHandle);  
         GLES20.glEnableVertexAttribArray(maNormalHandle);
         //���Ƽ��ص�����
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
    //��ȡ�����Χ�еĳ������Լ���
	public float[] findMinMax()
	{
		for(int i=0;i<vertices.length/3;i++)
		{
			//�ж�X�����С�����λ��
			if(vertices[i*3]<minX)
			{
				minX=vertices[i*3];
			}
			if(vertices[i*3]>maxX)
			{
				maxX=vertices[i*3];
			}
			//�ж�Y�����С�����λ��
			if(vertices[i*3+1]<minY)
			{
				minY=vertices[i*3+1];
			}
			if(vertices[i*3+1]>maxY)
			{
				maxY=vertices[i*3+1];
			}
			//�ж�Z�����С�����λ��
			if(vertices[i*3+2]<minZ)
			{
				minZ=vertices[i*3+2];
			}
			if(vertices[i*3+2]>maxZ)
			{
				maxZ=vertices[i*3+2];
			}
		}
		float[] length={maxX-minX,maxZ-minZ,maxY-minY};
		return length;
	}
	//��ȡ��������ĵ�����
    public float[] findMid(float xOffset,float yOffset,float zOffset)
	{
		midX=(minX+maxX)/2+xOffset;
		midY=(minY+maxY)/2+yOffset;
		midZ=(minZ+maxZ)/2+zOffset;
		float[] mid={midX,midY,midZ};
		return mid;
	}
}