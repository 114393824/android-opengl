package com.bn.Sample13_3;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;
//������
public class Constant 
{
	//ϵͳ����
	//��Ļ�Ŀ��
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	//�̱߳�־
	public static boolean flag_go=true;
	//��λ���ֵ
	public static final float UNIT_SIZE=100;
	//̫�����λ��
	public static final float[] sunPosition=
	{
		UNIT_SIZE*31*1.5f, 1000, UNIT_SIZE*31*1.2f
	};
	//--�紵������
	public static int wind=5;//��ǰ�ķ���
	public static float wind_force_init=-4.0f;
	public static float wind_force=wind_force_init*1.006f;;
	public static float wind_speed_init=200;
	public static float wind_speed=wind_speed_init;
	public static float bend_R_max=5000;
	public static float bend_R=bend_R_max;//����ָ���ǽڵ�Բ�����ĵ��������Ĵ��ĳ���
	public static float wind_direction=0;//����ָ���Ƿ���ĽǶ�  
	public static void setWindForce(int windForce)//���ݷ����������ü��ٶȺ��߳�����ʱ��
	{
		wind=windForce;
		switch(windForce)
		{
		case 0:
			bend_R=10000;
			break;
		case 1:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*1.1f;
			break;
		case 2:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*1.08f;
			break;
		case 3:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*1.05f;
			break;
		case 4:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*1.03f;
			break;
		case 5:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*1.006f;
			break;
		case 6:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*1.004f;
			break;
		case 7:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init;
			break;
		case 8:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*0.999f;
			break;
		case 9:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*0.998f;
			break;
		case 10:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*0.997f;
			break;
		case 11:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*0.996f;
			break; 
		case 12:
			bend_R=bend_R_max;
			wind_speed=wind_speed_init;
			wind_force=wind_force_init*0.9952f;
			break;
		}
	}
	//���������
	public static float DISTANCE=3000.0f;//�����λ�þ���۲�Ŀ���ľ���4700--600
	public static final float CAMERA_X=UNIT_SIZE*31*1.5f;//������Ĺ۲��
	public static final float CAMERA_HEIGHT=80;//������ĸ߶�
	public static final float CAMERA_Z=UNIT_SIZE*31*1.5f;//������Ĺ۲��
	public static float camera_direction=225;//������Ĺ۲췽��//������ķ���ǳ�ʼ������Z�ݵĸ�����,��ʱ����ת
	public static final float MOVE_SPAN=20f;//�����ÿ���ƶ���λ��
	//���׵���ز���
	public static final float FLOOR_WIDTH=UNIT_SIZE*31;//���׵Ŀ��
	public static final float FLOOR_HEIGHT=UNIT_SIZE*31;//���׵ĸ߶�
	public static final float[][] floor_array=//����ɽ�ص�����0��ʾ����1��ʾ��ɽ
	{
		{0,0,0},
		{0,1,0},
		{0,0,0} 
	};
	//���ɵ�����
	public static float bottom_radius=15f;//���ĵ׶˰뾶
	public static float joint_height=15f;//ÿ���ڵ�ĸ߶�
	public static int joint_num=20;//�ڵ���ܹ���Ŀ
	public static int joint_available_num=14;//�ڵ����Ч��Ŀ
	//------------Ҷ�ӵ�����
	public static float leaves_width=60f;//Ҷ�ӵĿ��;
	public static float leaves_height=60f;//Ҷ�ӵĸ߶�
	public static float leaves_offset=-leaves_height/1.4f;//Ҷ�������XZƽ���ƫ����
	public static float leaves_absolute_height=joint_height*joint_available_num;//Ҷ�ӵľ��Ը߶�
	public static float tree_YOffset=39.21f;
	//��ʾҬ����λ�õľ���
	public static final float[][] MAP_TREE=
	{
		{FLOOR_WIDTH*1.49f,tree_YOffset,FLOOR_WIDTH*1.54f},
		{FLOOR_WIDTH*1.48f,tree_YOffset,FLOOR_WIDTH*1.5f},
		{FLOOR_WIDTH*1.47f,tree_YOffset,FLOOR_WIDTH*1.4f},
		{FLOOR_WIDTH*1.5f,tree_YOffset,FLOOR_WIDTH*1.49f},
		{FLOOR_WIDTH*1.52f,tree_YOffset,FLOOR_WIDTH*1.4f},
		{FLOOR_WIDTH*1.48f,tree_YOffset,FLOOR_WIDTH*1.3f},
		{FLOOR_WIDTH*1.56f,tree_YOffset,FLOOR_WIDTH*1.35f},
		{FLOOR_WIDTH*1.48f,tree_YOffset,FLOOR_WIDTH*1.2f},
	};	
	//----------ˮ�������
	public static final int ROWS=31*3;//ˮ�������
	public static final int COLS=31*3;//ˮ�������
	public static final float WATER_SPAN=UNIT_SIZE;//ˮ����ӵĵ�λ���
	//��������ķ���
	public static int initTexture(Resources r,int drawableId)//textureId
	{
		//��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		int textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
        //ͨ������������ͼƬ===============begin===================
        InputStream is = r.openRawResource(drawableId);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
	//-----------�Ҷ�ͼ�������
	public static final float LAND_HIGH_ADJUST=0f;//½�صĸ߶ȵ���ֵ
	public static final float LAND_HIGHEST=100f;//½�����߲�
	public static float[][] LAND_ARRAY;//���صĻҶ�ͼ���ݵĸ߶�
	public static final float LAND_SPAN=UNIT_SIZE ;//½�صĵ�λ���
	public static void initLand(Resources r,int texId)
	{  
		//�ӻҶ�ͼƬ�м���½����ÿ������ĸ߶�
		LAND_ARRAY=loadLandforms(r,texId);
	}
	//�ӻҶ�ͼƬ�м���½����ÿ������ĸ߶�
	public static float[][] loadLandforms(Resources resources,int texId)
	{
		Bitmap bt=BitmapFactory.decodeResource(resources, texId);
		int colsPlusOne=bt.getWidth();
		int rowsPlusOne=bt.getHeight(); 
		float[][] result=new float[rowsPlusOne][colsPlusOne];
		for(int i=0;i<rowsPlusOne;i++)
		{   
			for(int j=0;j<colsPlusOne;j++)
			{
				int color=bt.getPixel(j,i);
				int r=Color.red(color);
				int g=Color.green(color); 
				int b=Color.blue(color);
				int h=(r+g+b)/3;
				result[i][j]=h*LAND_HIGHEST/255-LAND_HIGH_ADJUST;  
			}
		}		
		return result;
	}
	//�������������
	public static final float SKY_BALL_RADIUS=1.49F*FLOOR_WIDTH;//�����İ뾶
	public static float sky_rotation=0;
}
