package com.bn.Sample16_1;

/*
 * ��ά������
 */
public class Vector3f
{
	float x;//��ά�����е�xֵ	
	float y;//��ά�����е�yֵ
	float z;//��ά�����е�zֵ
	
	public Vector3f(float x,float y,float z)
	{
		this.x=x;		
		this.y=y;		
		this.z=z;
	}
	
	public void add(Vector3f temp)
	{
		this.x+=temp.x;		
		this.y+=temp.y;		
		this.z+=temp.z;
	}
}
