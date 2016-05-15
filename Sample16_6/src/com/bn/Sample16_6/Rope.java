package com.bn.Sample16_6;

import static com.bn.Sample16_6.Constant.springLength;

import java.util.ArrayList;
import java.util.List;

/*
 * ������
 */

public class Rope extends RopeSimulation {
	
	List<Spring> springList;//�����б�
	Vector3 gravity;//����
	Vector3 connectionPos;//��ͷ����λ��
	Vector3 connectionVel;//��ͷ�����ٶ�
	float groundRepulsionConstant;//����ķ���ϵ��
	float groundFrictionConstant;//�����Ħ��ϵ��
	float groundAbsorptionConstant;//����Ļ���ϵ��
	float groundHeight;//����ĸ߶�
	float airFrictionConstant;//������Ħ��ϵ��
	public Rope(
			int massCount,						//�ʵ������ 
			float m,							//�ʵ������
			Vector3 gravity,					//����
			float groundRepulsionConstant, 		//���淴��ϵ��
			float groundFrictionConstant,		//����Ħ��ϵ��
			float groundAbsorptionConstant,		//���滺��ϵ�� 
			float groundHeight,					//����߶�
			float airFrictionConstant,			//��������
			Vector3 connectionVel,				//��ͷ�ٶ�
			Vector3 connectionPos) {			//��ͷλ��	
		
		super(massCount, m);		//���ø���Ĺ�����������massCount������Ϊm���ʵ�Mass�Ķ���
		
		this.gravity = gravity;	//ָ������
		this.groundRepulsionConstant = groundRepulsionConstant;//ָ�����淴��ϵ��
		this.groundFrictionConstant = groundFrictionConstant;//ָ������Ħ��ϵ��
		this.groundAbsorptionConstant = groundAbsorptionConstant;//ָ�����滺��ϵ��
		this.groundHeight = groundHeight;	//ָ������߶�
		this.airFrictionConstant = airFrictionConstant;//ָ����������
		this.connectionVel=connectionVel;//��ͷ�ٶ�
		this.connectionPos=connectionPos;//��ͷλ��
		
		for(int i=0;i<massCount;i++){		//��ʼ�������ʵ��λ��
			massList.get(i).pos.x = i*springLength;		//ָ��x����
			massList.get(i).pos.y = groundHeight;		//ָ��y����
			massList.get(i).pos.z = 0;					//ָ��z����
		}
		springList = new ArrayList<Spring>();		//������ŵ��ɶ�����б�
		for(int i=0;i<massCount-1;i++){		//��ʼ���ʵ�֮��ĵ���(���ɵ��������ʵ��������һ��)
			Spring temp = new Spring(massList.get(i),massList.get(i+1));
			springList.add(temp);		//���뵽���ɶ�����б�
		}
	}
	//�������ӵ�ǰ�˶��켣�ķ���
	public void solve() {
		for(int i=0;i<massCount-1;i++){		
			springList.get(i).calculateSpringForce();	//�õ��ɶ��������ʵ�ʩ�ӵ���
		}
		for(int i=0;i<massCount;i++){	//�����ܵ���������
			Mass mass = massList.get(i);
			//ʩ����������
			mass.applyForce(gravity.multiConstant(mass.m));
			//ʩ�ӿ�������
			mass.applyForce(mass.vel.multiConstant(-1).multiConstant(airFrictionConstant));
			
			if(mass.pos.y<=groundHeight){
				Vector3 v = mass.vel.copy();
				v.y=0;
				//Ħ����
				mass.applyForce(v.multiConstant(-1).multiConstant(groundFrictionConstant));
				v = mass.vel.copy();
				v.x=0;
				v.z=0;
				if(v.y<0){
					mass.applyForce(v.multiConstant(-1).multiConstant(groundAbsorptionConstant));
				}
				//�������ķ�������
				Vector3 force = new Vector3(0,groundRepulsionConstant,0).multiConstant(groundHeight-mass.pos.y);
				mass.applyForce(force);
			}
		}
	}
	@Override
	public void simulateRope(float dt){ //ģ�������˶��ķ���
		super.simulateRope(dt);			//���ø����ģ�ⷽ��
		Mass head = massList.get(0);	//��ȡ��ͷ���ʵ�
		Vector3[] result = calCentripetalForceAndVel();//�������������ٶ�
		head.applyForce(result[0]);	//ʩ��������
		connectionVel = result[1];	//�����ٶ�
		connectionPos = connectionPos.add(   //������ͷ�ʵ��λ��
								connectionVel.multiConstant(dt));
		if(connectionPos.y<groundHeight){ //��֤��ͷ�ڵ���ĸ߶�֮��
			connectionPos.y=groundHeight;
			connectionVel.y=0;
		}
		head.pos=connectionPos; //������ͷ��λ��
		head.vel=connectionVel;//������ͷ���ٶ�
	}
	public Vector3[] calCentripetalForceAndVel(){
		Mass head = massList.get(0);  //��ȡ��ͷ�ʵ�
		Vector3 center = new Vector3(0,head.pos.y,-2);//��y����ת
		Vector3 forceDir = center.add(head.pos.multiConstant(-1)).normal();
		Vector3 force = forceDir.multiConstant(head.m); //������ F=m*v*v/r  �˴���v=1,r=1;
		Vector3 velDir = Vector3.yRotate(
				Math.toRadians(90), new double[]{forceDir.x,forceDir.y,forceDir.z,1});
		Vector3 vel = velDir.multiConstant(10);//�ٶȴ�СΪ10
		if(head.pos.y<1.5f){
			vel.y=1;//��ֱ�ٶ�Ϊ1
		}
		return new Vector3[]{force,vel};//�������������ٶ�
	}
}