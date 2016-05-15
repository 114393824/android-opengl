package com.bn.Sample15_9;
//һ���������Ϣ
public class HitInfo{

	double hitTime;//�ཻʱ��
	HitObject hitObject;//�ཻ������
	boolean isEntering;//�����ǽ��뻹�ǳ���
	int surface;//�ཻ���ĸ�����
	Point3 hitPoint;//��������꣬�任���
	Vector3 hitNormal;//���㴦�ķ��������任ǰ��
	
	public HitInfo(){
		hitPoint = new Point3();
		hitNormal = new Vector3();
	}
	/* 
	 * �˷������ܻ᲻�ԣ�����������ܳ���
	 * ����н�����˵�������Ի�����
	 */
	public void set(HitInfo hit){
		this.hitTime=hit.hitTime;
		this.hitObject=hit.hitObject;//ָ������岻�ø���
		this.isEntering=hit.isEntering;
		this.surface=hit.surface;
		this.hitPoint.set(hit.hitPoint);
		this.hitNormal.set(hit.hitNormal);
	}
	@Override
	public String toString() {
		return "hitTime"+hitTime+",hitPoint"+hitPoint;
	}
}
