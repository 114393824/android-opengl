package com.bn.Sample13_5;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.opengl.GLES20;

/*
 * �Զ�������������   �ȱ�������
 * ��ǰ������������ϱߵ�λ��ԭ��,���ҹ���Y�Ḻ������Գ�
 */
public class MultiTrangle
{
	int program;//�Զ�����Ⱦ������ɫ������id
    int maPositionHandle;//��ȡ�����ж���λ����������  
    int maTexCoorHandle;//��ȡ�����ж�������������������  
    int muMVPMatrixHandle;//��ȡ�������ܱ任��������
    int fuRatioHandle;//�����ε����ű���id
    
	FloatBuffer fVertexBuffer;//��������buffer
	FloatBuffer fTextureBuffer;//��������buffer
	int vCount;//����ĸ���
	public MultiTrangle(int program,float edgeLength,int levelNum)//����id,�����α߳�,�����εĲ���
	{
		this.program=program;
		initVertexData(edgeLength,levelNum);
		initShader();
	}
	//��ʼ��������Ϣ
	public void initVertexData(float edgeLength,int levelNum)
	{
		ArrayList<Float> al_vertex=new ArrayList<Float>();//�洢������Ϣ
		ArrayList<Float> al_texture=new ArrayList<Float>();//�洢������Ϣ
		float perLength = edgeLength/levelNum;
		for(int i=0;i<levelNum;i++)//ÿ�����ɨ��
		{
			//��ǰ�㶥�˱���
			int currTopEdgeNum=i;
			//��ǰ��׶˱���
			int currBottomEdgeNum=i+1;
			//ÿ�������εĸ߶�
			float currTrangleHeight=(float) (perLength*Math.sin(Math.PI/3));
			//��ǰ�㶥������ߵ������
			float topEdgeFirstPointX=-perLength*currTopEdgeNum/2;
			float topEdgeFirstPointY=-i*currTrangleHeight;
			float topEdgeFirstPointZ=0;
			
			//��ǰ��׶�����ߵ������
			float bottomEdgeFirstPointX=-perLength*currBottomEdgeNum/2;
			float bottomEdgeFirstPointY=-(i+1)*currTrangleHeight;
			float bottomEdgeFirstPointZ=0;
			//---------------����----------------
			float horSpan=1/(float)levelNum;//���������ƫ����
			float verSpan=1/(float)levelNum;//���������ƫ����
			//��ǰ�㶥�˵�һ������������ز�������
			float topFirstS=0.5f-currTopEdgeNum*horSpan/2;
			float topFirstT=i*verSpan;
			//��ǰ��׶˵�һ�������������ز���
			float bottomFirstS=0.5f-currBottomEdgeNum*horSpan/2;
			float bottomFirstT=(i+1)*verSpan;
			//�ײ������ξ��ƽ�ģ
			for(int j=0;j<currBottomEdgeNum;j++)//��ÿ�������ν��о���
			{
				//����
				float topX=topEdgeFirstPointX+j*perLength;
				float topY=topEdgeFirstPointY;
				float topZ=topEdgeFirstPointZ;
				float topS=topFirstS+j*horSpan;
				float topT=topFirstT;
				//���µ�
				float leftBottomX=bottomEdgeFirstPointX+j*perLength;
				float leftBottomY=bottomEdgeFirstPointY;
				float leftBottomZ=bottomEdgeFirstPointZ;
				float leftBottomS=bottomFirstS+j*horSpan;
				float leftBottomT=bottomFirstT;
				//���µ�
				float rightBottomX=leftBottomX+perLength;
				float rightBottomY=bottomEdgeFirstPointY;
				float rightBottomZ=bottomEdgeFirstPointZ;
				float rightBottomS=leftBottomS+horSpan;
				float rightBottomT=leftBottomT;
				//��ʱ�����----- ������Ʒ�ʽ
				al_vertex.add(topX);al_vertex.add(topY);al_vertex.add(topZ);
				al_vertex.add(leftBottomX);al_vertex.add(leftBottomY);al_vertex.add(leftBottomZ);
				al_vertex.add(rightBottomX);al_vertex.add(rightBottomY);al_vertex.add(rightBottomZ);
				//-------������Ʒ�ʽ
				al_texture.add(topS);al_texture.add(topT);
				al_texture.add(leftBottomS);al_texture.add(leftBottomT);
				al_texture.add(rightBottomS);al_texture.add(rightBottomT);
				
			}
			//���������ξ��ƽ�ģ
			for(int k=0;k<currTopEdgeNum;k++)
			{
				//���ϵ�
				float leftTopX=topEdgeFirstPointX+k*perLength;
				float leftTopY=topEdgeFirstPointY;
				float leftTopZ=topEdgeFirstPointZ;
				float leftTopS=topFirstS+k*horSpan;
				float leftTopT=topFirstT;
				//�׶˵�
				float bottomX=bottomEdgeFirstPointX+(k+1)*perLength;
				float bottomY=bottomEdgeFirstPointY;
				float bottomZ=bottomEdgeFirstPointZ;
				float bottomS=bottomFirstS+(k+1)*horSpan;
				float bottomT=bottomFirstT;
				//���ϵ�
				float rightTopX=leftTopX+perLength;
				float rightTopY=leftTopY;
				float rightTopZ=leftTopZ;
				float rightTopS=leftTopS+horSpan;
				float rightTopT=topFirstT;
				//��ʱ�����-----
				al_vertex.add(leftTopX);al_vertex.add(leftTopY);al_vertex.add(leftTopZ);
				al_vertex.add(bottomX);al_vertex.add(bottomY);al_vertex.add(bottomZ);
				al_vertex.add(rightTopX);al_vertex.add(rightTopY);al_vertex.add(rightTopZ);
				
				al_texture.add(leftTopS);al_texture.add(leftTopT);
				al_texture.add(bottomS);al_texture.add(bottomT);
				al_texture.add(rightTopS);al_texture.add(rightTopT);
			}
		}
		//���ؽ����㻺��
		int vertexSize=al_vertex.size();
		vCount=vertexSize/3;//ȷ������ĸ���
		float vertexs[]=new float[vertexSize];
		for(int i=0;i<vertexSize;i++)
		{
			vertexs[i]=al_vertex.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexSize*4);
		vbb.order(ByteOrder.nativeOrder());
		fVertexBuffer=vbb.asFloatBuffer();
		fVertexBuffer.put(vertexs);
		fVertexBuffer.position(0);
		al_vertex=null;
		//���ؽ�������
		int textureSize=al_texture.size();
		float textures[]=new float[textureSize];
		for(int i=0;i<textureSize;i++)
		{
			textures[i]=al_texture.get(i);
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textureSize*4);
		tbb.order(ByteOrder.nativeOrder());
		fTextureBuffer=tbb.asFloatBuffer();
		fTextureBuffer.put(textures);
		fTextureBuffer.position(0);
		al_texture=null;
	}
	//��ʼ����ɫ��
	public void initShader()
	{
		//��ȡ�����ж���λ����������  
        maPositionHandle = GLES20.glGetAttribLocation(program, "aPosition");
        //��ȡ�����ж�������������������  
        maTexCoorHandle= GLES20.glGetAttribLocation(program, "aTexCoor");
        //��ȡ�������ܱ任��������
        muMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");  
        //��ȡ�����������ε����ű���
        fuRatioHandle = GLES20.glGetUniformLocation(program, "ratio");
	}
	//���Ʒ���
	public void drawSelf(int texId,float twistingRatio)
	{
		//�ƶ�ʹ��ĳ��shader����
   	 	GLES20.glUseProgram(program); 
        //�����ձ任������shader����
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0); 
        //�����ű�������shader����
        GLES20.glUniform1f(fuRatioHandle, twistingRatio); 
        //������λ�����ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer
		(
			maPositionHandle, 
			3, 
			GLES20.GL_FLOAT, 
			false, 
			3*4, 
			fVertexBuffer
		);
		//�������������ݴ�����Ⱦ����
		GLES20.glVertexAttribPointer
		(
			maTexCoorHandle, 
			2, 
			GLES20.GL_FLOAT, 
			false, 
			2*4, 
			fTextureBuffer
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
}
