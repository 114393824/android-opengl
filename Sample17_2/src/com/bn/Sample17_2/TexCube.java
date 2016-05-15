package com.bn.Sample17_2;
import com.bulletphysics.dynamics.RigidBody;
public class TexCube 
{
	TextureRect tr;//���ڻ��Ƹ�������������
	float halfSize;//������İ�߳�
	RigidBody body;//��Ӧ�ĸ������
	int mProgram;
	MySurfaceView mv;
	public TexCube(float halfSize,int mProgram)
	{		
		tr=new TextureRect(halfSize);//�����������
		this.mProgram=mProgram;//������ɫ����������
		this.halfSize=halfSize;	//����볤
	}
	public void drawSelf(int texId)
	{
		tr.intShader(mv, mProgram);//������γ�ʼ����ɫ��
		MatrixState.pushMatrix();//�����ֳ�
		MatrixState.pushMatrix();//�����ֳ�
	    MatrixState.translate(0, halfSize, 0);//ִ��ƽ��
	    MatrixState.rotate(-90, 1, 0, 0);//ִ����ת
	    tr.drawSelf( texId);//��������
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.pushMatrix();//�����ֳ�
	    MatrixState.translate(0, -halfSize, 0);//ִ��ƽ��
	    MatrixState.rotate(90, 1, 0, 0);//ִ����ת
	    tr.drawSelf( texId);//��������
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.pushMatrix();//�����ֳ�
	    MatrixState.translate(-halfSize, 0, 0);//ִ��ƽ��
	    MatrixState.rotate(-90, 0, 1, 0);//ִ����ת
	    tr.drawSelf( texId);//��������
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.pushMatrix();//�����ֳ�
	    MatrixState.translate(halfSize, 0, 0);//ִ��ƽ��
	    MatrixState.rotate(90, 0, 1, 0);//ִ����ת
	    tr.drawSelf( texId);//��������
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.pushMatrix();//�����ֳ�
		MatrixState.translate(0, 0, halfSize);//ִ��ƽ��
	    tr.drawSelf(texId);//����ǰ��
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.pushMatrix();//�����ֳ�
		MatrixState.translate(0, 0, -halfSize);//ִ��ƽ��
		MatrixState.rotate(180, 0, 1, 0);//ִ����ת
	    tr.drawSelf( texId);//���ƺ���
		MatrixState.popMatrix();//�ָ��ֳ�
		MatrixState.popMatrix();//�ָ��ֳ�
	}
}
