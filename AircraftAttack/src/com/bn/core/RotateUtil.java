package com.bn.core;
/*
 * ����Ϊ��̬�������ľ�̬�����࣬�ṩ��̬����������
 */
public class RotateUtil
{
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] pitchRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��x����ת�任����
		{
		   {1,0,0,0},
		   {0,Math.cos(angle),Math.sin(angle),0},		   
		   {0,-Math.sin(angle),Math.cos(angle),0},		   //ԭ��Ϊ��{0,-Math.sin(angle),Math.cos(angle),0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] rollRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��y����ת�任����
		{
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}		
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static double[] yawRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��z����ת�任����
		{
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;
	}
	public static float[] getDirectionDot(double[] values)
	{
		double yawAngle=-Math.toRadians(values[0]);//��ȡYaw����ת�ǶȻ���
		double pitchAngle=-Math.toRadians(values[1]);//��ȡPitch����ת�ǶȻ���
		double rollAngle=-Math.toRadians(values[2]);//��ȡRoll����ת�ǶȻ���
		/*
		 * �㷨˼��Ϊ�ֻ���һ����̬�����������һ������������
		 * Ȼ������ѡװ���ֻ��ָ���ԭʼ��̬���ڼ�������������
		 * �仯����������������ֻ�ƽ����һͶӰ��
		 */
		//����һ����������
		double[] gVector={0,0,-100,1};
		
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
		double mapX=gVector[0];
		double mapY=gVector[1];		
		float[] result={(float)mapX,(float)mapY};
		return result;
	}	
}