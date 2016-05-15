package com.bn.Sample16_6;

/*
 * �ʵ���
 * 
 * �����ʵ��ܵ���������������ʵ㵱ǰ��λ�����ٶ�
 * 
 */

public class Mass {
	float m;		//�ʵ������
	Vector3 pos;	//�ʵ��λ��
	Vector3 vel;	//�ʵ���ٶ�
	Vector3 force;	//�ʵ������
	public Mass(float m){//������
		this.m=m;		//ָ������
		pos = new Vector3();//��ʼ��λ��
		vel = new Vector3();//��ʼ���ٶ�
	}
	public Mass(float m,Vector3 pos,Vector3 vel){//������
		this.m=m;		//ָ������
		this.pos=pos;	//ָ��λ��
		this.vel=vel;	//ָ���ٶ�
	}
	
	//���ø÷��������ʵ�ʩ�������������������������ȣ�
	public void applyForce(Vector3 force){	//ʩ�����ķ���
		this.force=this.force.add(force);	
	}
	
	//�÷�����ʼ���ʵ��ܵ���������ʼʱ�ܵ�����Ϊ0
	public void initForce(){	
		force = new Vector3(0,0,0);
	}
	//�����ʵ㵱ǰλ�����ٶȵķ���
	public void calculateCurrPosAndVel(float dt){
		Vector3 a = force.multiConstant(1/m);	// ������ٶ� a = F/m 
		Vector3 deltaV = a.multiConstant(dt); 	//�����ٶ����� deltaV = at
		vel = vel.add(deltaV);					// �����ٶ�v = v + at
		pos = pos.add(vel.multiConstant(dt));	// ����λ�� pos = pos + vt
	}
}
