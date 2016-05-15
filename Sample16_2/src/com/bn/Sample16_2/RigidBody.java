package com.bn.Sample16_2;
import java.util.ArrayList;

public class RigidBody 
{
	LoadedObjectVertexNormal renderObject;//��Ⱦ��
	AABBBox collObject;//��ײ��
	boolean isStatic;
	Vector3f currLocation;
	Vector3f currV;
	Orientation currOrientation;
	
	public RigidBody(LoadedObjectVertexNormal renderObject,boolean isStatic,Vector3f currLocation,Vector3f currV,Orientation currOrientation)
	{
		this.renderObject=renderObject;
		collObject=new AABBBox(renderObject.vertices);
		this.isStatic=isStatic;
		this.currLocation=currLocation;
		this.currV=currV;
		this.currOrientation=currOrientation;
	}
	
	public void drawSelf()
	{
		MatrixState.pushMatrix();
		MatrixState.translate(currLocation.x, currLocation.y, currLocation.z);
		MatrixState.insertSelfMatrix(currOrientation.orientationData);
		renderObject.drawSelf();
		MatrixState.popMatrix();
	}	
	
	public void go(ArrayList<RigidBody> al)
	{
		if(isStatic) return;
		currLocation.add(currV);		
		for(int i=0;i<al.size();i++)
		{
			RigidBody rb=al.get(i);
			if(rb!=this)
			{			
				if(check(this,rb))//������ײ
				{
					this.currV.x=-this.currV.x;//�ĸ���������ٶȣ��÷����ϵ��ٶ��÷�					
				}
			}
		}
	}
	
	final float V_UNIT=0.02f;
	public boolean check(RigidBody ra,RigidBody rb)//trueΪײ��
	{		
		float[] over=calOverTotal
		(
			ra.collObject.getCurrAABBBox(ra.currLocation,ra.currOrientation),
			rb.collObject.getCurrAABBBox(rb.currLocation,rb.currOrientation)
		);
		return over[0]>V_UNIT&&over[1]>V_UNIT&&over[2]>V_UNIT;
	}
	
	public float[] calOverTotal(AABBBox a,AABBBox b)
	{
		float xOver=calOverOne(a.maxX,a.minX,b.maxX,b.minX);
		float yOver=calOverOne(a.maxY,a.minY,b.maxY,b.minY);
		float zOver=calOverOne(a.maxZ,a.minZ,b.maxZ,b.minZ);
		return new float[]{xOver,yOver,zOver};
	}
	
	public float calOverOne(float amax,float amin,float bmax,float bmin)
	{
		float minMax=0;
		float maxMin=0;
		if(amax<bmax)//a������b�������
		{
			minMax=amax;
			maxMin=bmin;
		}
		else //a������b�����Ҳ�
		{
			minMax=bmax;
			maxMin=amin;
		}
		
		if(minMax>maxMin)
		{
			return minMax-maxMin;
		}
		else
		{
			return 0;
		}
	}
}
