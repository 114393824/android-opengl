package com.bn.Sample16_5;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;

//�û���������ϵͳ����
public class GrainForDraw {
	
	private FloatBuffer   mVelocityBuffer;//�����ٶ����ݻ���
    float scale;	//��ߴ�
    
    String mVertexShader;	//������ɫ��    	 
    String mFragmentShader;	//ƬԪ��ɫ��
    
    int mProgram;			//�Զ�����Ⱦ������ɫ������id
    int muMVPMatrixHandle;	//�ܱ任��������   
    int uPointSizeHandle;	//����ߴ��������
    int uColorHandle;		//������ɫ��������
    int uTimeHandle;		//������ɫ��������
    int vCount=0;

    int maVelocityHandle; 	//�����ٶ��������� 
    float timeLive=0;
    long timeStamp=0;
    
    public GrainForDraw(MySurfaceView mv,float scale,int vCount)
    {
    	this.scale=scale;
    	this.vCount=vCount;
    	initVertexData(vCount);	//���ó�ʼ���������ݵ�initVertexData����
    	initShader(mv);		//���ó�ʼ����ɫ����initShader����
    }
    
    //��ʼ���������ݵ�initVertexData����
    public void initVertexData(int vCount){
        float[] velocity=new float[vCount*3];
        for(int i=0;i<vCount;i++){
        	double fwj=2*Math.PI*Math.random();
        	double yj=0.35*Math.PI*Math.random()+0.15*Math.PI;
        	final double vTotal=1.5+1.5*Math.random();		//�ܵ��ٶ�
        	double vy=vTotal*Math.sin(yj);		//y�����ϵ��ٶ�
        	double vx=vTotal*Math.cos(yj)*Math.sin(fwj);	//x�����ϵ��ٶ�
        	double vz=vTotal*Math.cos(yj)*Math.cos(fwj);	//z�����ϵ��ٶ�
        	velocity[i*3]=(float)vx;
        	velocity[i*3+1]=(float)vy;
        	velocity[i*3+2]=(float)vz;
        }
    	
        //���������ٶ����ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(velocity.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVelocityBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVelocityBuffer.put(velocity);//�򻺳����з��붥����������
        mVelocityBuffer.position(0);//���û�������ʼλ��
    }

    //��ʼ����ɫ����initShader����
    public void initShader(MySurfaceView mv)
    {
    	//���ض�����ɫ���Ľű�����       
        mVertexShader=ShaderUtil.loadFromAssetsFile("vertex_yh.sh", mv.getResources());
        ShaderUtil.checkGlError("==ss==");   
        //����ƬԪ��ɫ���Ľű�����
        mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_yh.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        ShaderUtil.checkGlError("==ss==");      
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж����ٶ���������id  
        maVelocityHandle = GLES20.glGetAttribLocation(mProgram, "aVelocity");        
        //��ȡ�������ܱ任��������id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix"); 
        //��ȡ����ߴ��������
        uPointSizeHandle = GLES20.glGetUniformLocation(mProgram, "uPointSize"); 
        //��ȡ������ɫ��������
        uColorHandle = GLES20.glGetUniformLocation(mProgram, "uColor"); 
        //��ȡ������ɫ��������
        uTimeHandle=GLES20.glGetUniformLocation(mProgram, "uTime"); 

    }
    public void drawSelf()
    {  
    	long currTimeStamp=System.nanoTime()/1000000;
    	if(currTimeStamp-timeStamp>=10){
    		timeLive+=0.02f;
    		timeStamp=currTimeStamp;
    	}
    	
    	//�ƶ�ʹ��ĳ����ɫ������
   	    GLES20.glUseProgram(mProgram);
        //�����ձ任��������ɫ������
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);  
        //������ߴ紫����ɫ������
        GLES20.glUniform1f(uPointSizeHandle, scale);
        //��ʱ�䴫����ɫ������
        GLES20.glUniform1f(uTimeHandle, timeLive);    
        //��������ɫ������ɫ������
        GLES20.glUniform3fv(uColorHandle, 1, new float[]{1,1,1}, 0);
        //���붥���ٶ�����    
        GLES20.glVertexAttribPointer(
        		maVelocityHandle,   
        		3, 
        		GLES20.GL_FLOAT, 
        		false,
                3*4, 
                mVelocityBuffer   
        );
        //������λ����������
        GLES20.glEnableVertexAttribArray(maVelocityHandle);         
        //���Ƶ�    
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vCount); 
    }
}
