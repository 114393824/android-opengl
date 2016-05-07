package com.bn.Sample13_3;
//����Ϊ���ɵĿ�����
public class TreeTrunkControl 
{
	static int num=0;
	//��־
	int flag=0;
	//����λ��
	float positionX;
	float positionY;
	float positionZ;
	//���ɵ�ģ��
	TreeTrunk treeTrunk;
	public TreeTrunkControl(float positionX,float positionY,float positionZ,TreeTrunk treeTrunk)
	{
		flag=num++;
		this.positionX=positionX;
		this.positionY=positionY;
		this.positionZ=positionZ;
		this.treeTrunk=treeTrunk;
	}
	public void drawSelf(int tex_treejointId,float bend_R,float wind_direction)
	{
		MatrixState.pushMatrix();
		MatrixState.translate(positionX, positionY, positionZ);//�ƶ���ָ����λ��
		treeTrunk.drawSelf(tex_treejointId, bend_R, wind_direction);
		MatrixState.popMatrix();
	}
}
