package com.bn.Sample15_9;
import static com.bn.Sample15_9.Constant.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;

//����
public class ColorRect 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ���������� 
    //3D�����е���
    int muColorHandle; //ƬԪ��ɫ�������� 
    int mu3DPosHandle; //3D�����ж���λ���������� 
    int muNormalHandle; //���㷨������������
    int muLightLocationHandle;//��Դλ����������
    int muCameraHandle; //�����λ����������
    int muIsShadow;//�Ƿ������Ӱ��������  
    
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
	
	FloatBuffer   mVertexBuffer;//�����������ݻ���
    int vCount=0;   
    float[] color3 = new float[3];//3D�����ж�����ɫ
    float[] vertexPos3D = new float[3];//3D�����ж���λ��
    float[] normal3D = new float[3];//3D�����ж��㷨����
    float[] lightPos3D = new float[3];//3D�����й�Դλ��
    float[] cameraPos3D = new float[3];//3D�����������λ��
    int isShadow;//�Ƿ�����Ӱ�еı�־
    
    float u;//���������ӿ��ϵ�λ��
    float v;
    public ColorRect(MySurfaceView mv)
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
        vCount=6;
       
        float vertices[]=new float[]
        {	
        	0,0,0,//0
        	Constant.blockSize,0,0,//1
        	Constant.blockSize,Constant.blockSize,0,//2
        	  
        	0,0,0,//0
        	Constant.blockSize,Constant.blockSize,0,//2
        	0,Constant.blockSize,0//3
        };
		
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
    }

    //��ʼ��shader
    public void intShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex.sh", mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ���������� 
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");       
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        //3D�����е���
        //��ȡ������3D�����ж�����ɫ�������� 
        muColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor");
        //��ȡ������3D�����ж���λ���������� 
        mu3DPosHandle = GLES20.glGetUniformLocation(mProgram, "uPosition");
        //��ȡ�����ж��㷨������������  
        muNormalHandle = GLES20.glGetUniformLocation(mProgram, "uNormal");
        //��ȡ�����й�Դλ������
        muLightLocationHandle = GLES20.glGetUniformLocation(mProgram, "uLightLocation");
        //��ȡ�����������λ������
        muCameraHandle = GLES20.glGetUniformLocation(mProgram, "uCamera");
        //��ȡ�������Ƿ������Ӱ��������
        muIsShadow=GLES20.glGetUniformLocation(mProgram, "isShadow"); 
    }
	public void drawSelf() {
		MatrixState.pushMatrix();
		MatrixState.translate(u, v, 0);
		// �ƶ�ʹ��ĳ����ɫ������
		GLES20.glUseProgram(mProgram);
		// �����ձ任��������ɫ������
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
				MatrixState.getFinalMatrix(), 0);
		
		//3D�����е���
		// ��3D�����ж�����ɫ������ɫ������
		GLES20.glUniform3fv(muColorHandle, 1, color3, 0);	
		// ��3D�����ж����λ�ô�����ɫ������
		GLES20.glUniform3fv(mu3DPosHandle, 1, vertexPos3D, 0);
		// ��3D�����ж���ķ�����������ɫ������
		GLES20.glUniform3fv(muNormalHandle, 1, normal3D, 0);
		// ��3D�����еƹ�λ�ô�����ɫ������
		GLES20.glUniform3fv(muLightLocationHandle, 1, lightPos3D, 0);
		// ��3D�������������λ�ô�����ɫ������
		GLES20.glUniform3fv(muCameraHandle, 1, cameraPos3D, 0);	 
        //���Ƿ������Ӱ���Դ�����ɫ������ 
        GLES20.glUniform1i(muIsShadow, isShadow);      
		
		// ������λ�����ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
				false, 3 * 4, mVertexBuffer);
		// ������λ����������
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		// ���ƾ���
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
		
		MatrixState.popMatrix();
	}

    public void setColor(float r,float g,float b){
    	this.color3[0] = r;
    	this.color3[1] = g;
    	this.color3[2] = b;
    }
    public void setPos3D(float x,float y,float z){
    	this.vertexPos3D[0] = x;
    	this.vertexPos3D[1] = y;
    	this.vertexPos3D[2] = z;
    }
    public void setNormal3D(float x,float y,float z){
    	this.normal3D[0] = x;
    	this.normal3D[1] = y;
    	this.normal3D[2] = z;
    }
    public void setLightPos3D(float x,float y,float z){
    	this.lightPos3D[0] = x;
    	this.lightPos3D[1] = y;
    	this.lightPos3D[2] = z;
    }
    public void setCameraPos3D(float x,float y,float z){
    	this.cameraPos3D[0] = x;
    	this.cameraPos3D[1] = y;
    	this.cameraPos3D[2] = z;
    }
    public void setShadow(int isShadow){
    	this.isShadow = isShadow;
    }
    
    public void setPos(float u,float v){
    	this.u=u;
    	this.v=v;
    }
    public void setColRow(int col,int row){
    	//�����������������������Ļ�ϵ�λ��
    	float u=-W+W*(2*col/nCols);
        float v=-H+H*(2*row/nRows);
        this.setPos(u, v);//���û�����λ��
    }
}
