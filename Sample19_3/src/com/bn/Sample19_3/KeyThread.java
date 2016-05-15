package com.bn.Sample19_3;

import static com.bn.Sample19_3.MySurfaceView.*;
import static com.bn.Sample19_3.Sample19_3Activity.*;

public class KeyThread extends Thread
{
	//����״̬  1-up 2-down 4-left 8-right
    int keyState=0;
	boolean flag=true;
	@Override
	public void run()
	{
		while(flag)
		{
			if((keyState&0x1)!=0) 
			{//������ǰ�˶��ļ�
				xOffset-=0.3f;
			}
			else if((keyState&0x2)!=0)
			{//��������˶��ļ�
				xOffset+=0.3f;
			}
			if((keyState&0x4)!=0)
			{//���������˶��ļ�
				yAngle+=2.5f;
			}
			else if((keyState&0x8)!=0)
			{//���������˶��ļ�
				yAngle-=2.5f;
			}
			if(yAngle>=360||yAngle<=-360)
			{
				yAngle=0;
			}
			try
			{
				Thread.sleep(30);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	//��ⰴ�������ı���λ
	public void keyPress(float x,float y)
	{
		if(x>=0&&x<screenWidth/2&&y>=0&&y<screenHeight/2)
		{//��ǰ
			keyState=keyState|0x1;
		}
		else if(x>=screenWidth/2&&x<screenWidth&&y>=0&&y<screenHeight/2)
		{//���
			keyState=keyState|0x2;
		}
		else if(x>=0&&x<screenWidth/2&&y>=screenHeight/2&&y<screenHeight)
		{//����
			keyState=keyState|0x4;
		}
		else if(x>=screenWidth/2&&x<=screenWidth&&y>=screenHeight/2&&y<=screenHeight)
		{//����
			keyState=keyState|0x8;
		}
	}
	//̧�𰴼�ʱ���õķ���
	public void keyUp(float x,float y)
	{
		if(x>=0&&x<screenWidth/2&&y>=0&&y<screenHeight/2)
		{//��ǰ
			keyState=keyState&0xE;
		}
		else if(x>=screenWidth/2&&x<screenWidth&&y>=0&&y<screenHeight/2)
		{//���
			keyState=keyState&0xD;
		}
		else if(x>=0&&x<screenWidth/2&&y>=screenHeight/2&&y<screenHeight)
		{//����
			keyState=keyState&0xB;
		}
		else if(x>=screenWidth/2&&x<=screenWidth&&y>=screenHeight/2&&y<=screenHeight)
		{//����
			keyState=keyState&0x7;
		}
	}
	//���д��ص��̧��ʱ���õķ���������keyStateֵ��ֵΪ0
	public void clearKeyState()
	{
		keyState=keyState&0x0;
	}
}