package com.bn.Sample17_4;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
public class LoadRigidBody extends BNThing
{
	LoadedObjectVertexNormal lovo;
	RigidBody body;//��Ӧ�ĸ������
	int mProgram;
	public LoadRigidBody(int mProgram,float mass,LoadedObjectVertexNormal lovo,float cx,float cy,float cz,DiscreteDynamicsWorld dynamicsWorld)
	{
		this.lovo=lovo;//������������������
		this.mProgram=mProgram;//������ɫ����������
		CollisionShape colShape=lovo.loadShape;//������ײ��״
		boolean isDynamic = (mass != 0f);//�����Ƿ���˶�
		Vector3f localInertia = new Vector3f(0f, 0f, 0f);//������������
		if (isDynamic) {
			colShape.calculateLocalInertia(mass, localInertia);//�������
		}
		Transform startTransform = new Transform();//��������ĳ�ʼ�任����
		startTransform.setIdentity();//��ʼ���任����
		startTransform.origin.set(new Vector3f(cx, cy, cz));//���ñ任�ĳ�ʼλ��
		//����������˶�״̬����
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);		
		//����������Ϣ����
		RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo
									(mass, myMotionState, colShape, localInertia);
		body = new RigidBody(cInfo);//��������		
		body.setRestitution(0.4f);//���÷���ϵ��
		body.setFriction(0.8f);//����Ħ��ϵ��
		dynamicsWorld.addRigidBody(body);//��������ӽ���������
	}
	public void drawSelf()
	{
		lovo.initShader(mProgram);//��ʼ����ɫ��
		MatrixState.pushMatrix();//�����ֳ�
		//��ȡ������ӵı任��Ϣ����
		Transform trans = body.getMotionState().getWorldTransform(new Transform());
		//������λ�任
		MatrixState.translate(trans.origin.x,trans.origin.y, trans.origin.z);
		Quat4f ro=trans.getRotation(new Quat4f());//��ȡ��ת����Ԫ������
		if(ro.x!=0||ro.y!=0||ro.z!=0)
		{
			float[] fa=SYSUtil.fromSYStoAXYZ(ro);//����Ԫ��ת����AXYZ��ʽ
			MatrixState.rotate(fa[0], fa[1], fa[2], fa[3]);//ִ����ת
		}
		lovo.drawSelf();//���ü����������Ļ��Ʒ��� 
		MatrixState.popMatrix();//�ָ��ֳ�
	}
}
