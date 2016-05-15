package com.bn.Sample16_1;

/*
 * ��Χ����
 */
public class AABBBox 
{
	float minX;//x����Сλ��
	float maxX;//x�����λ��
	float minY;//y����Сλ��
	float maxY;//y�����λ��
	float minZ;//z����Сλ��
	float maxZ;//z�����λ��
	
	public AABBBox(float[] vertices)
	{
		init();
		findMinAndMax(vertices);
	}
	
	public AABBBox(float minX,float maxX,float minY,float maxY,float minZ,float maxZ)
	{
		this.minX=minX;
		this.maxX=maxX;
		this.minY=minY;
		this.maxY=maxY;
		this.minZ=minZ;
		this.maxZ=maxZ;
	}
	//��ʼ����Χ�е���С�Լ���󶥵�����
	public void init()
	{
		minX=Float.POSITIVE_INFINITY;
		maxX=Float.NEGATIVE_INFINITY;
		minY=Float.POSITIVE_INFINITY;
		maxY=Float.NEGATIVE_INFINITY;
		minZ=Float.POSITIVE_INFINITY;
		maxZ=Float.NEGATIVE_INFINITY;	
	}
	//��ȡ��Χ�е�ʵ����С�Լ���󶥵�����
	public void findMinAndMax(float[] vertices)
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
	}
	//�������ƽ�ƺ��AABB��Χ��
	public AABBBox getCurrAABBBox(Vector3f currPosition)
	{
		AABBBox result=new AABBBox
		(
			this.minX+currPosition.x,
			this.maxX+currPosition.x,
			this.minY+currPosition.y,
			this.maxY+currPosition.y,
			this.minZ+currPosition.z,
			this.maxZ+currPosition.z
		);
		return result;
	}
}
