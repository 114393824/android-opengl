package com.bn.Sample15_9;

//������
public class Ray {

	Point3 start;//��ʼλ��
	Vector3 dir;//���߷���
	public Ray(){
		start = new Point3();
		dir = new Vector3();
	}
	//���ù�����ʼλ�õķ���
	public void setStart(Point3 start){
		this.start.x = start.x;
		this.start.y = start.y;
		this.start.z = start.z;
	}
	//���ù��߷��䷽��ķ���
	public void setDir(Vector3 dir){
		this.dir.x = dir.x;
		this.dir.y = dir.y;
		this.dir.z = dir.z;
	}
}
