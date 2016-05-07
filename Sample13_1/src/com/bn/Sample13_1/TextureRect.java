package com.bn.Sample13_1;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;

//�в���Ч�����������
public class TextureRect 
{	
	int[] mPrograms=new int[3];//�Զ�����Ⱦ������ɫ������id
    int[] muMVPMatrixHandle=new int[3];//�ܱ任��������
    int[] maPositionHandle=new int[3]; //����λ����������  
    int[] maTexCoorHandle=new int[3]; //��������������������  
    int[] maStartAngleHandle=new int[3]; //��֡��ʼ�Ƕ���������
    int[] muWidthSpanHandle=new int[3];//���򳤶��ܿ������    
    int currIndex=0;//��ǰ��ɫ������
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    final float WIDTH_SPAN=3.3f;//2.8f;//���򳤶��ܿ��
    float currStartAngle=0;//��ǰ֡����ʼ�Ƕ�0~2PI
    
    public TextureRect(MySurfaceView mv)
    {    	
    	//��ʼ��������������ɫ����
    	initVertexData();
    	//��ʼ��shader        
    	initShader(mv,0,"vertex_tex_x.sh");
    	initShader(mv,1,"vertex_tex_xie.sh");
    	initShader(mv,2,"vertex_tex_xy.sh");
    	//����һ���̶߳�ʱ��֡
    	new Thread()
    	{
    		public void run()
    		{
    			while(Constant.threadFlag)
    			{
    				currStartAngle+=(float) (Math.PI/16);
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
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData()
    {
    	final int cols=12;//����
    	final int rows=cols*3/4;//����
    	final float UNIT_SIZE=WIDTH_SPAN/cols;//ÿ��ĵ�λ����
    	//�����������ݵĳ�ʼ��================begin============================
    	vCount=cols*rows*6;//ÿ���������������Σ�ÿ��������3������    
        float vertices[]=new float[vCount*3];//ÿ������xyz��������
        int count=0;//���������
        for(int j=0;j<rows;j++)
        {
        	for(int i=0;i<cols;i++)
        	{        		
        		//���㵱ǰ�������ϲ������ 
        		float zsx=-UNIT_SIZE*cols/2+i*UNIT_SIZE;
        		float zsy=UNIT_SIZE*rows/2-j*UNIT_SIZE;
        		float zsz=0;
       
        		vertices[count++]=zsx;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy-UNIT_SIZE;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=zsy;
        		vertices[count++]=zsz;
        		
        		vertices[count++]=zsx;
        		vertices[count++]=zsy-UNIT_SIZE;
        		vertices[count++]=zsz;
        		        		
        		vertices[count++]=zsx+UNIT_SIZE;
        		vertices[count++]=zsy-UNIT_SIZE;
        		vertices[count++]=zsz; 
        	}
        }
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //���������������ݵĳ�ʼ��================begin============================
        float texCoor[]=generateTexCoor(cols,rows);     
        //�������������������ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTexCoorBuffer = cbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTexCoorBuffer.put(texCoor);//�򻺳����з��붥����ɫ����
        mTexCoorBuffer.position(0);//���û�������ʼλ��
    }
    //��ʼ��shader
    public void initShader(MySurfaceView mv,int index,String vertexName)
    {
    	//���ض�����ɫ���Ľű�����
        String mVertexShader=ShaderUtil.loadFromAssetsFile(vertexName, mv.getResources());
        //����ƬԪ��ɫ���Ľű�����
        String mFragmentShader=ShaderUtil.loadFromAssetsFile("frag_tex.sh", mv.getResources());  
        //���ڶ�����ɫ����ƬԪ��ɫ����������
        mPrograms[index] = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //��ȡ�����ж���λ����������  
        maPositionHandle[index] = GLES20.glGetAttribLocation(mPrograms[index], "aPosition");
        //��ȡ�����ж�������������������  
        maTexCoorHandle[index]= GLES20.glGetAttribLocation(mPrograms[index], "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle[index] = GLES20.glGetUniformLocation(mPrograms[index], "uMVPMatrix");  
        //��ȡ��֡��ʼ�Ƕ���������
        maStartAngleHandle[index]=GLES20.glGetUniformLocation(mPrograms[index], "uStartAngle");  
        //��ȡ���򳤶��ܿ������
        muWidthSpanHandle[index]=GLES20.glGetUniformLocation(mPrograms[index], "uWidthSpan");  
    }
    public void drawSelf(int texId)
    {        
    	 //�ƶ�ʹ��ĳ��shader����
    	 GLES20.glUseProgram(mPrograms[currIndex]); 
         //�����ձ任������shader����
         GLES20.glUniformMatrix4fv(muMVPMatrixHandle[currIndex], 1, false, MatrixState.getFinalMatrix(), 0); 
         //����֡��ʼ�Ƕȴ���shader����
         GLES20.glUniform1f(maStartAngleHandle[currIndex], currStartAngle);
         //�����򳤶��ܿ�ȴ���shader����
         GLES20.glUniform1f(muWidthSpanHandle[currIndex], WIDTH_SPAN);  
         //������λ�����ݴ�����Ⱦ����
         GLES20.glVertexAttribPointer  
         (
         		maPositionHandle[currIndex],            
         		3, 
         		GLES20.GL_FLOAT,   
         		false,
                3*4,   
                mVertexBuffer
         );       
         //�����������������ݴ�����Ⱦ����
         GLES20.glVertexAttribPointer  
         (
        		maTexCoorHandle[currIndex], 
         		2, 
         		GLES20.GL_FLOAT, 
         		false,
                2*4,   
                mTexCoorBuffer
         );   
         //���ö���λ�á�������������
         GLES20.glEnableVertexAttribArray(maPositionHandle[currIndex]);  
         GLES20.glEnableVertexAttribArray(maTexCoorHandle[currIndex]);  
         //������
         GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
         GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//����
    	float sizeh=0.75f/bh;//����
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
