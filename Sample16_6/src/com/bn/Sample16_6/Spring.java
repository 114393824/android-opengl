package com.bn.Sample16_6;
/*
 * ������
 */
public class Spring {
	
	Mass mass1;//�ʵ�1
	Mass mass2;//�ʵ�2
	float springConstant;//����ϵ��
	float springLength;//���ɳ���
	float frictionConstant;//Ħ��ϵ��
	
	public Spring(Mass mass1, Mass mass2) {//������
		this.mass1 = mass1;	//ָ����һ���ʵ�	
		this.mass2 = mass2;	//ָ���ڶ����ʵ�
		this.springConstant = Constant.springConstant;//ָ������ϵ��	
		this.springLength = Constant.springLength;//ָ�����ɳ���
		this.frictionConstant = Constant.frictionConstant;//ָ������Ħ��ϵ��
	}

	public void calculateSpringForce(){	//����������������ķ���
		Vector3 springVector = mass1.pos.add(mass2.pos.multiConstant(-1));//���ɵ��쳤����
		float distance = springVector.length();//�����ʵ��ľ���
		Vector3 force;//������
		if(distance!=0){
			float deltaX = distance-this.springLength;//����ƫ��ƽ��λ�õľ���
			
			Vector3 normalV =springVector.multiConstant(1/distance).multiConstant(-1);//�����ɵķ����������;
			
			force = normalV.multiConstant(deltaX).multiConstant(springConstant);//���ɵ����� = k*deltaX*dir
			
			force = force.add(mass1.vel.add(mass2.vel.multiConstant(-1)).multiConstant(-frictionConstant));//�������  
			
			mass1.applyForce(force);//�Ե�һ���ʵ�ʩ����
			mass2.applyForce(force.multiConstant(-1));//�Եڶ����ʵ�ʩ����
		}	
	}
}
