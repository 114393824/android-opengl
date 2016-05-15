package com.bn.Sample17_2;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
public class CubeCylinder 
{
	Cylinder cl;//������ϵ�Բ��
	TexCube tc;//������ϵ�������
	float halfSize;
	RigidBody body;//��Ӧ�ĸ������
	MySurfaceView mv;//MySurfaceView������
	
	public CubeCylinder(MySurfaceView mv,float halfSize,CollisionShape[] csa,
			DiscreteDynamicsWorld dynamicsWorld,float mass,float cx,float cy,float cz,int[] mProgram)
	{	
		CompoundShape comShape=new CompoundShape(); //���������״
		Transform localTransform = new Transform();//�����任����
		localTransform.setIdentity();//��ʼ���任 
		localTransform.origin.set(new Vector3f(0, 0, 0));//���ñ任�����
		comShape.addChildShape(localTransform, csa[0]);//�������״
		comShape.addChildShape(localTransform, csa[1]);//�������״	
		localTransform = new Transform();//�����任����
		localTransform.basis.rotX((float)Math.toRadians(90));//��x��ת90
		comShape.addChildShape(localTransform, csa[2]);//�������״
		boolean isDynamic = (mass != 0f);//�жϸ����Ƿ���˶�
		Vector3f localInertia = new Vector3f(0, 0, 0);//������������
		if (isDynamic) //�����������˶�
		{
			comShape.calculateLocalInertia(mass, localInertia);//�������Ĺ���
		}
		Transform startTransform = new Transform();//��������ĳ�ʼ�任����
		startTransform.setIdentity();//��ʼ���任����
		startTransform.origin.set(new Vector3f(cx, cy, cz));//���ñ任�����
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);//����������˶�״̬����
		RigidBodyConstructionInfo rbInfo = new RigidBodyConstructionInfo//����������Ϣ����
		(
				mass, myMotionState, comShape, localInertia
		);
		body = new RigidBody(rbInfo);//�����������
		body.setRestitution(0.6f);//���÷���ϵ��
		body.setFriction(0.8f); //����Ħ��ϵ��
		dynamicsWorld.addRigidBody(body);//��������ӽ���������
		tc=new TexCube(halfSize,mProgram[0]);//����������
		cl=new Cylinder(halfSize/2,halfSize*3.6f,16,mProgram[0],mv);//����Բ��		
		this.halfSize=halfSize;//����볤
		this.mv=mv;//����MySurfaceView������
	}
	public void drawSelf(int[] texIda,int[] texIdb)//���Ʒ���
	{
		int texId1=texIda[0];//�������˶�ʱ������
		int texId2=texIdb[1];//Բ���˶�ʱ������
		if(!body.isActive())
		{
			texId1=texIda[1];//�����徲ֹʱ������
			texId2=texIdb[0];//Բ����ֹʱ������
		}
		MatrixState.pushMatrix();//�����ֳ�
		Transform trans=body.getMotionState().getWorldTransform(new Transform());//��ȡ�������ı任��Ϣ����
		MatrixState.translate(trans.origin.x,trans.origin.y, trans.origin.z);//������λ�任
		Quat4f ro=trans.getRotation(new Quat4f());//������ת�任
		if(ro.x!=0||ro.y!=0||ro.z!=0)
		{
			if(!Float.isNaN(ro.x)&&!Float.isNaN(ro.x)&&!Float.isNaN(ro.x)
					&&!Float.isInfinite(ro.x)&&!Float.isInfinite(ro.x)&&!Float.isInfinite(ro.x)){
				float[] fa=SYSUtil.fromSYStoAXYZ(ro);
				MatrixState.rotate(fa[0], fa[1], fa[2], fa[3]);
			}
		}
		tc.drawSelf( texId1);//����������
		MatrixState.pushMatrix();//�����ֳ�
		MatrixState.rotate(90,0, 0, 1);//ִ����ת
		MatrixState.translate(0, -halfSize*1.8f, 0);//ִ��ƽ��
		cl.drawSelf(texId2,  texId2,  texId2);//����XˮƽԲ��	
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.pushMatrix();//�����ֳ�
		MatrixState.translate(0, -halfSize*1.8f, 0);//ִ��ƽ��
		cl.drawSelf(texId2,  texId2,  texId2);//����Y��ֱԲ��
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.popMatrix();//�ָ��ֳ�
	}
}
