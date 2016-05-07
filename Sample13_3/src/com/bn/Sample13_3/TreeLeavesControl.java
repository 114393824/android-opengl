package com.bn.Sample13_3;
/*
 * Ҷ�ӵĿ����� 
 */
public class TreeLeavesControl implements Comparable<TreeLeavesControl>
{
	//Ҷ�ӵ�λ��
	float positionX;
	float positionY;
	float positionZ;
	//Ҷ��
	TreeLeaves treeLeaves;
	public TreeLeavesControl(float positionX,float positionY,float positionZ,TreeLeaves treeLeaves)
	{
		this.positionX=positionX;
		this.positionY=positionY;
		this.positionZ=positionZ;
		this.treeLeaves=treeLeaves;
	}
	public void drawSelf(int tex_leavesId,float bend_R,float wind_direction)
	{
		MatrixState.pushMatrix();
		MatrixState.translate(positionX, positionY, positionZ);
		//��������Ҫ����Ҷ�������������˵��ƫ��λ�ú���ת����
		//---------------------------
		float curr_height=Constant.leaves_absolute_height;
		//������ߵ��λ�� 
		float result[]=resultPoint(wind_direction,bend_R,0,curr_height,0);
		//��Ҫ����ת��
		//��Ҫ��ת�ĽǶ�
		MatrixState.translate(result[0], result[1], result[2]);
		MatrixState.rotate(result[5], result[3],0,-result[4]);
		//---------------------------------
		treeLeaves.drawSelf(tex_leavesId);
		MatrixState.popMatrix();
	}
	 //�����ÿƬ��Ҷ�����������Զ��������� ����, �Ӵ�С����
	@Override
	public int compareTo(TreeLeavesControl another) 
	{
		//��ǰ��Ҷ����������ľ���
		float distanceX=(this.positionX+this.treeLeaves.centerX-GameSurfaceView.cx)*(this.positionX+this.treeLeaves.centerX-GameSurfaceView.cx);
		float distanceZ=(this.positionZ+this.treeLeaves.centerZ-GameSurfaceView.cz)*(this.positionZ+this.treeLeaves.centerZ-GameSurfaceView.cz);
		//�Ƚϵ����������ľ���
		float distanceOX=(another.positionX+another.treeLeaves.centerX-GameSurfaceView.cx)*(another.positionX+another.treeLeaves.centerX-GameSurfaceView.cx);
		float distanceOZ=(another.positionZ+another.treeLeaves.centerZ-GameSurfaceView.cz)*(another.positionZ+another.treeLeaves.centerZ-GameSurfaceView.cz);
		return (distanceX+distanceZ)>(distanceOX+distanceOZ)?-1:1;
	}
	//������󶥵��λ��
	public float[] resultPoint(float direction_degree,float currBend_R,float pointX,float pointY,float pointZ)//currBend_R����ǰ�ķ���,pointHeight��ǰ��ĸ߶�
	{
		float []position=new float[6];
		//���㵱ǰ�Ļ���
		float curr_radian=pointY/currBend_R;
		//���㵱ǰ��Ľ���߶�
		float result_Y=(float) (currBend_R*Math.sin(curr_radian));
		//���㵱ǰ������ӵĳ���
		float increase=(float) (currBend_R-currBend_R*Math.cos(curr_radian));
		//���㵱ǰ������x����
		float result_X=(float) (pointX+increase*Math.sin(Math.toRadians(direction_degree)));
		//���㵱ǰ������z����
		float result_Z=(float) (pointZ+increase*Math.cos(Math.toRadians(direction_degree)));
		//�����
		position[0]=result_X;
		position[1]=result_Y;
		position[2]=result_Z;
		//x����ת��
		position[3]=(float) Math.cos(Math.toRadians(direction_degree));
		//z����ת��
		position[4]=(float) Math.sin(Math.toRadians(direction_degree));
		//��ת�Ƕ�
		position[5]= (float) Math.toDegrees(curr_radian);
		return position;
	}

}
