package com.bn.Sample16_6;

import java.util.ArrayList;
import java.util.List;

/*
 * �������ʵ���ɵ�һ��ģ�������˶��ĳ���������
 */

public abstract class RopeSimulation {
	List<Mass> massList;	//��������ʵ������б�
	int massCount;	//�ʵ�����
	
	
	public RopeSimulation(int massCount,float m){	//����Ϊ�ʵ��������ÿ���ʵ������
		this.massCount=massCount;	//�ʵ�������ֵ
		massList = new ArrayList<Mass>();	//������������ʵ������б�
		
		for(int i=0;i<massCount;i++){
			Mass mass = new Mass(m);	//����massCount����ͬ������Ϊm���ʵ�
			massList.add(mass);			//���뵽�ʵ���б���	
		}
	}
	//��ȡ�ʵ�����б���ĳ���ʵ�Ķ���
	public Mass getMass(int index){
		if(index<0||index>=massCount){
			return null;
		}
		return massList.get(index);
	}
	//��ʼ���ʵ��б���ÿһ���ʵ�����ܵ��������������ʼ��ʱÿ���ʵ㣬����Ϊ0��
	public void init(){
		for(int i=0;i<massList.size();i++){
			massList.get(i).initForce();	//��ʼ���ʵ���������
		}
	}
	//�������ӵ�ǰ�˶��켣�ĳ��󷽷�
	public abstract void solve();
	
	//�����ʵ��б��У�ÿһ���ʵ㵱ǰλ���뵱ǰ�ٶȵķ�����ʹ�����е��ʵ��ģ�������˶�
	public void simulateRope(float dt){
		for(int i=0;i<massList.size();i++){
			massList.get(i).calculateCurrPosAndVel(dt);	//���¸��ʵ���ٶȺ�����
		}
	}
	//���ϸ���������ӵ�ÿ���ʵ�λ�����ٶȣ��Ӷ�ʹ�������˶�����
	public void operate(float dt){
		init();								
		solve();
		simulateRope(dt);
	}
	
}
