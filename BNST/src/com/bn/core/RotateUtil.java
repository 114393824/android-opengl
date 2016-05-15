package com.bn.core;

import android.opengl.Matrix;

/*
	���ֻ���ǰ��yaw��pitch��row���������̬����ͨ���˹��������ת������ͧ��ת�����ݣ���ʵ�ֶ���ͧ�ķ�����ơ�
 */
public class RotateUtil
{
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������----��x����ת�任����
	static float[] pm=new float[16];
	static float[] rv=new float[4];
	public static float[] pitchRotate(float angle,float[] gVector)
	{		
		Matrix.setRotateM(pm, 0, angle, 1, 0, 0);
		Matrix.multiplyMV(rv,0, pm, 0, gVector, 0);
		return rv;
	}
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������--��y����ת�任����
	public static float[] rollRotate(float angle,float[] gVector)
	{
		Matrix.setRotateM(pm, 0, angle, 0, 1, 0);
		Matrix.multiplyMV(rv,0, pm, 0, gVector, 0);
		return rv;
	}		
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������--��z����ת�任����
	public static float[] yawRotate(float angle,float[] gVector)
	{
		Matrix.setRotateM(pm, 0, angle, 0, 0, 1);
		Matrix.multiplyMV(rv,0, pm, 0, gVector, 0);
		return rv;
	}
	
	static float[] gVector=new float[4];
	static int[] result=new int[2];
	public static int[] getDirectionDot(float[] values)
	{
		float yawAngle=-values[0];//��ȡYaw����ת�ǶȻ���
		float pitchAngle=-values[1];//��ȡPitch����ת�ǶȻ���
		float rollAngle=-values[2];//��ȡRoll����ת�ǶȻ���
		/*
		 * �㷨˼��Ϊ�ֻ���һ����̬�����������һ������������
		 * Ȼ������ѡװ���ֻ��ָ���ԭʼ��̬���ڼ�������������
		 * �仯����������������ֻ�ƽ����һͶӰ��
		 */
		
		//����һ����������
		gVector[0]=0;
		gVector[1]=0;
		gVector[2]=-100;
		gVector[3]=1;
		
		/*
		 * ��������Ҫע���������ռ䷽��x,y,z������ת�ĽǶȵĻָ�˳������Yaw��ʼ��ָ����ֱ���ϣ��������ٶȷ��򣩣���
		 * ��׼�Ŀռ�����ϵ��z��һ�������� ����ͨ��������תֱ�ӽ��нǶȻָ�����yaw�Ὣת���ĽǶȻָ��󣬴�ʱ��pitch��
		 * �ͱ���˿ռ�����ϵ�е�x�ᣬ��pitch��x���Ὣ ת�� �ĽǶȻָ�����ʱ��roll�������Ϊ�˿ռ�����ϵ�е�y�ᣬ���
		 * ����y�Ὣת���ĽǶȻָ������ʱ�ֻ�ƽ�����ڵ�ƽ�����˿ռ����� ϵ��x-yƽ�棬���������ֻ�ƽ���ϵ��������ٶ�
		 * ������һ�����ֻ�ƽ���ཻ����������������ͶӰ���ֻ�ƽ�棬ͨ��ͶӰ��Ϳ��Լ����С��Ҫ�����ķ���
		 * �������������˳����нǶȻָ�����ռ�����ļ���ת������ǳ����ӣ�������������ÿһ���ĽǶȻָ����ǻ��ڱ�׼
		 * ������ϵ�ᣬ���Ա�׼�������ת���ڼ����ͼ��
		 * ѧ�к�����ʵ��
		 */
		
		//yaw��ָ�
		gVector=RotateUtil.yawRotate(yawAngle,gVector);		
		//pitch��ָ�
		gVector=RotateUtil.pitchRotate(pitchAngle,gVector);			
		//roll��ָ�
		gVector=RotateUtil.rollRotate(rollAngle,gVector);
		
		result[0]=(int) gVector[0];
		result[1]=(int) gVector[1];
		return result;
	}	
}