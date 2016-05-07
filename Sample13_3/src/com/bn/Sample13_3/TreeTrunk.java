package com.bn.Sample13_3;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import android.opengl.GLES20;
/*
 * ���ڻ�������
 */
public class TreeTrunk
{
	//----------��������ɵĽڵ����Ť��---------------------
	int fBendRHandle;//���������뾶�ľ��Id
	int fwind_direction_Handle;//����Ƕȵľ��
	//--------------------------------------------
	//�Զ�����Ⱦ������ɫ������id
	int mProgram;
	//�ܱ仯�������õ�id
	int muMVPMatrixHandle;
	//����λ����������
	int maPositionHandle;
	//��������������������
	int maTexCoorHandle;
	
	//�������ݻ���������������ݻ��嶥�㷨�������ݻ���
	FloatBuffer mVertexBuffer;
	FloatBuffer mTexCoorBuffer;
	//��������
	int vCount=0;
	//�����зֵĽǶ�
	float longitude_span=12;
	//������ ���ĵ�һ���ڵ�׶˰뾶����һ���ڵ�Ķ��˰뾶��ÿ���ڵ�ĸ߶ȣ��ڵ������
	public TreeTrunk(int mProgram,float bottom_Radius,float joint_Height,int jointNum,int availableNum)
	{
		this.mProgram=mProgram;
		initVertexData(bottom_Radius,joint_Height,jointNum,availableNum);
		initShader();
	}
	//��ʼ���������ݵķ���
	public void initVertexData(float bottom_radius,float joint_Height,int jointNum,int availableNum)//R����׶˰뾶��r�����˰뾶
	{
		List<Float> vertex_List=new ArrayList<Float>();//��������ļ���
		List<float[]> texture_List=new ArrayList<float[]>();//��������ļ���
		for(int num=0;num<availableNum;num++)//����ÿ���ڵ�Ķ�������
		{
			//ÿ���ڵ�׶˰뾶
			float temp_bottom_radius=bottom_radius*(jointNum-num)/(float)jointNum;
			//ÿ���ڵ㶥�˰뾶
			float temp_top_radius=bottom_radius*(jointNum-(num+1))/(float)jointNum;
			//ÿ���ڵ�ĵ׶˸߶�
			float temp_bottom_height=num*joint_Height;
			//ÿ���ڵ�Ķ��˸߶�
			float temp_top_height=(num+1)*joint_Height;
			
			for(float hAngle=0;hAngle<360;hAngle=hAngle+longitude_span)//ÿ���ڵ���������������
			{
				//���ϵ�
				float x0=(float) (temp_top_radius*Math.cos(Math.toRadians(hAngle)));
				float y0=temp_top_height;
				float z0=(float) (temp_top_radius*Math.sin(Math.toRadians(hAngle)));
				//���µ�
				float x1=(float) (temp_bottom_radius*Math.cos(Math.toRadians(hAngle)));
				float y1=temp_bottom_height;
				float z1=(float) (temp_bottom_radius*Math.sin(Math.toRadians(hAngle)));
				//���ϵ�
				float x2=(float) (temp_top_radius*Math.cos(Math.toRadians(hAngle+longitude_span)));
				float y2=temp_top_height;
				float z2=(float) (temp_top_radius*Math.sin(Math.toRadians(hAngle+longitude_span)));
				//���µ�
				float x3=(float) (temp_bottom_radius*Math.cos(Math.toRadians(hAngle+longitude_span)));
				float y3=temp_bottom_height;
				float z3=(float) (temp_bottom_radius*Math.sin(Math.toRadians(hAngle+longitude_span)));
				
				vertex_List.add(x0);vertex_List.add(y0);vertex_List.add(z0);
				vertex_List.add(x1);vertex_List.add(y1);vertex_List.add(z1);
				vertex_List.add(x2);vertex_List.add(y2);vertex_List.add(z2);
				
				vertex_List.add(x2);vertex_List.add(y2);vertex_List.add(z2);
				vertex_List.add(x1);vertex_List.add(y1);vertex_List.add(z1);
				vertex_List.add(x3);vertex_List.add(y3);vertex_List.add(z3);
			}
			//������������
			//�����������껺��
			float[] texcoor=generateTexCoor//��ȡ�з���ͼ����������                
	        (
	  			 (int)(360/longitude_span), //����ͼ�зֵ�����
	  			  1                    //(int)(180/ANGLE_SPAN)  //����ͼ�зֵ�����
	        );
			texture_List.add(texcoor);
		}
		//�������㻺��
		float[] vertex=new float[vertex_List.size()];
		for(int i=0;i<vertex_List.size();i++)
		{
			vertex[i]=vertex_List.get(i);
		}
		vCount=vertex_List.size()/3;
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertex.length*4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer=vbb.asFloatBuffer();
		mVertexBuffer.put(vertex);
		mVertexBuffer.position(0);
		//�����������껺��
		ArrayList<Float> al_temp=new ArrayList<Float>(); 
		for(float []temp:texture_List)
		{
			for(float tem:temp)
			{
				al_temp.add(tem);
			}
		}
		float[]texcoor=new float[al_temp.size()];	
		int num=0;
		for(float temp:al_temp)
		{
			texcoor[num]=temp;
			num++;
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(texcoor.length*4);
		tbb.order(ByteOrder.nativeOrder());
		mTexCoorBuffer=tbb.asFloatBuffer();
		mTexCoorBuffer.put(texcoor);
		mTexCoorBuffer.position(0);
	}
	//��ʼ��Shader�ķ���
	public void initShader() 
	{
		//------------------------------�ڶ�������-----------------------
        //��ȡ�����ж���λ����������  
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //��ȡ�����ж�������������������  
        maTexCoorHandle= GLES20.glGetAttribLocation(mProgram, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");  
        //��ȡ���������������뾶������
        fBendRHandle = GLES20.glGetUniformLocation(mProgram,"bend_R");
        //��ȡ�����з���ĽǶ�����
        fwind_direction_Handle = GLES20.glGetUniformLocation(mProgram,"direction_degree");
	}
	//�Զ���Ļ��Ʒ���drawSelf
	public void drawSelf(int texId,float bend_R,float wind_direction)
	{
		//�ƶ�ʹ��ĳ��shader����
   	 	GLES20.glUseProgram(mProgram); 
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //�������뾶����shader����
        GLES20.glUniform1f(fBendRHandle, bend_R);
        //��������shader����
        GLES20.glUniform1f(fwind_direction_Handle, wind_direction);
		
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
		//�������������ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer
		(
			maTexCoorHandle, 
			2, 
			GLES20.GL_FLOAT, 
			false, 
			2*4, 
			mTexCoorBuffer
		);
		//���ö���λ������
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
    	float sizew=1.0f/bw;//����
    	float sizeh=1.0f/bh;//����
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
