package com.bn.Sample13_3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
/*
 * ���ڻ�����Ҷ����
 */
public class TreeLeaves 
{	
	int mProgram;//�Զ�����Ⱦ������ɫ������id   
    int muMVPMatrixHandle;//�ܱ任��������
    int maPositionHandle; //����λ����������  
    int maTexCoorHandle; //��������������������  
    String mVertexShader;//������ɫ��    	 
    String mFragmentShader;//ƬԪ��ɫ��
    
	FloatBuffer   mVertexBuffer;//�����������ݻ���
	FloatBuffer   mTexCoorBuffer;//���������������ݻ���
    int vCount=0;   
    float centerX;//��Ҷ���ĵ�X����
    float centerZ;//��Ҷ���ĵ�Z����
    int index;//��ǰ��������
    
    public TreeLeaves(int mProgram,float width,float height,float absolute_height,int index)
    {    	
    	this.mProgram=mProgram;
    	//��ʼ��������������ɫ����
    	initVertexData(width,height,absolute_height,index);
    	//��ʼ��shader        
    	intShader();
    	this.index=index;
    }
    //��ʼ��������������ɫ���ݵķ���
    public void initVertexData(float width,float height,float absolute_height,int index)
    {
        vCount=6;
        float vertices[]=null;//��������
        float texCoor[]=null;//��������
        switch(index)//�Զ�����ÿƬ��Ҷ
        {
        case 0://ƽ����X��������ʼ,��ʱ����ת,ÿ��60��,����һƬ��Ҷ
            vertices=new float[]
            {
        		0,height+absolute_height,0,
        		0,absolute_height,0,
        		width,height+absolute_height,0,
            	
        		width,height+absolute_height,0,
        		0,absolute_height,0,
        		width,absolute_height,0,
            };
            texCoor=new float[]
            {
            	1,0, 1,1, 0,0,
            	0,0, 1,1, 0,1
            };
            //ȷ�����ĵ�����
            centerX=width/2;
            centerZ=0;
        	break;
        case 1://60�Ƚǵ���Ҷ
           vertices=new float[]
           {
	       		0,height+absolute_height,0,
	       		0,absolute_height,0,
	       		width/2,height+absolute_height,(float) (-width*Math.sin(Math.PI/3)),
	           	
	       		width/2,height+absolute_height,(float) (-width*Math.sin(Math.PI/3)),
	       		0,absolute_height,0,
	       		width/2,absolute_height,(float) (-width*Math.sin(Math.PI/3))
           };
           texCoor=new float[]
           {
	           	1,0, 1,1, 0,0,
	           	0,0, 1,1, 0,1
           };
           //ȷ�����ĵ�����
           centerX=width/4;
           centerZ=(float) (-width*Math.sin(Math.PI/3))/2;
        	break;
        case 2:
        	vertices=new float[]
            {
        		-width/2,height+absolute_height,(float) (-width*Math.sin(Math.PI/3)),
        		-width/2,absolute_height,(float) (-width*Math.sin(Math.PI/3)),
        		0,height+absolute_height,0,
            	
        		0,height+absolute_height,0,
        		-width/2,absolute_height,(float) (-width*Math.sin(Math.PI/3)),
        		0,absolute_height,0,
            };
            texCoor=new float[]
            {
        		0,0, 0,1, 1,0,
            	1,0, 0,1, 1,1
            };
            //ȷ�����ĵ�����
            centerX=-width/4;
            centerZ=(float) (-width*Math.sin(Math.PI/3))/2;
        	break;
        case 3:
           vertices=new float[]
           {
	       		-width,height+absolute_height,0,
	       		-width,absolute_height,0,
	       		0,height+absolute_height,0,
	           	
	       		0,height+absolute_height,0,
	       		-width,absolute_height,0,
	       		0,absolute_height,0,
           };
           texCoor=new float[]
           {
	       		0,0, 0,1, 1,0,
	           	1,0, 0,1, 1,1
           };
           //ȷ�����ĵ�����
           centerX=-width/2;
           centerZ=0;
        	break;
        case 4:
           vertices=new float[]
           {
	       		-width/2,height+absolute_height,(float) (width*Math.sin(Math.PI/3)),
	       		-width/2,absolute_height,(float) (width*Math.sin(Math.PI/3)),
	       		0,height+absolute_height,0,
	           	
	       		0,height+absolute_height,0,
	       		-width/2,absolute_height,(float) (width*Math.sin(Math.PI/3)),
	       		0,absolute_height,0,
           };
           texCoor=new float[]
           {
	       		0,0, 0,1, 1,0,
	           	1,0, 0,1, 1,1
           };
           //ȷ�����ĵ�����
           centerX=-width/4;
           centerZ=(float) (width*Math.sin(Math.PI/3))/2;
           break;
        case 5:
           vertices=new float[]
	       {
		   		0,height+absolute_height,0,
		   		0,absolute_height,0,
		   		width/2,height+absolute_height,(float) (width*Math.sin(Math.PI/3)),
		       	
		   		width/2,height+absolute_height,(float) (width*Math.sin(Math.PI/3)),
		   		0,absolute_height,0,
		   		width/2,absolute_height,(float) (width*Math.sin(Math.PI/3))
	       };
	       texCoor=new float[]
	       {
		       	1,0, 1,1, 0,0,
		       	0,0, 1,1, 0,1
	       };
	       //ȷ�����ĵ�����
           centerX=width/4;
           centerZ=(float) (width*Math.sin(Math.PI/3))/2;
        	break;
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
         
         //�����������
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount); 
    }
}
