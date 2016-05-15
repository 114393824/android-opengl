package com.bn.Sample15_9;

//������ 
public class Constant {
	// ������Ļ����
	public static final float SCREEN_WIDTH = 800;// ��Ļ�Ŀ��
	public static final float SCREEN_HEIGHT = 480;// ��Ļ�ĸ߶�
	// ������Ⱦʱ����
	public static final float blockSize = 8f;//������ĳߴ�
	public static final float W = SCREEN_WIDTH / 2.0f;// �ӿڰ���
	public static final float H = SCREEN_HEIGHT / 2.0f;
	public static final float ratio = W / H;//�ӿڿ�߱�
	public static final float nRows = SCREEN_HEIGHT;// ������������
	public static final float nCols = SCREEN_WIDTH;
	//������ʵ�����н�ƽ�����
	public static final float N_3D = 24;// ��ƽ�浽������ľ���
	public static final float W_3D = ratio;//��ƽ����
	public static final float H_3D = 1.0f;//��ƽ����
	//������ʵ�����и��������
	public static final float R = 0.6f;//��İ뾶
	public static final float CENTER_DIS = 0.7f;//�������ĵľ���
	public static final float PLANE_WIDTH = 3.5f;//ƽ����
	public static final float PLANE_HEIGHT = 4f;//ƽ��߶�
	
	public static final float[] BALL1_COLOR = {0.8f,0.2f,0.2f};//��1����ɫ
	public static final float[] BALL2_COLOR = {0.2f,0.2f,0.8f};//��2����ɫ
	public static final float[] PLANE_COLOR = {0.2f,0.8f,0.2f};//ƽ�����ɫ
	//����������Ĳ���
	public static final float CAM_X = 15;
	public static final float CAM_Y = 7;
	public static final float CAM_Z = 32;
	//���ڹ�Դ�Ĳ���
	public static final float LIGHT_X = 100;
	public static final float LIGHT_Y = 80;
	public static final float LIGHT_Z = 0;
	//������Ӱʱ�õļ�С������
	public static final float MNIMUM = 0.00001f;
}