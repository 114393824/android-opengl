package com.bn.Sample17_6;

import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

public class RigidBodyHelper {
	
	RigidBody body;
	boolean noGravity;
  
	 public RigidBodyHelper(CollisionShape shape,float mass,DiscreteDynamicsWorld dynamicsWorld,
			 float restitution,float friction,Vector3f origin,boolean noGravity){
		 	this.noGravity=noGravity;
	    	boolean isDynamic = (mass!=0);
			Vector3f localInertia = new Vector3f(0,0,0);
			if(isDynamic){
				shape.calculateLocalInertia(mass, localInertia);
			}
	    	//��������ĳ�ʼ�任����
			Transform groundTransform = new Transform();
			groundTransform.setIdentity();
			groundTransform.origin.set(origin);		
			//����������˶�״̬����
			DefaultMotionState myMotionState = new DefaultMotionState(groundTransform);
			//����������Ϣ����
			RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);
			//��������
			body = new RigidBody(rbInfo);
			//���÷���ϵ��
			body.setRestitution(restitution);
			//����Ħ��ϵ��
			body.setFriction(friction);
			
			//��������ӽ���������
			//bullet����add�����ʱ��Ϊ����������������
			dynamicsWorld.addRigidBody(body);
			//һ��Ҫ��add����֮���ٴ��������������ܽ�ԭ��������
			if(noGravity){
		 		body.setGravity(new Vector3f(0,0,0));
		 	}
	    } 
}
