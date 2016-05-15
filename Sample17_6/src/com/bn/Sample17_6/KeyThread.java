package com.bn.Sample17_6;
import static com.bn.Sample17_6.Constant.*;
public class KeyThread extends Thread //��������״̬���߳�
{
	MySurfaceView mv; 
	public KeyThread(MySurfaceView mv)
	{
		this.mv=mv;
	}
	public void run() 
	{
		while(keyFlag)
		{
			if((MySurfaceView.keyState&0x1)!=0) 
			{//��UP������
				mv.slideFB(1);
			}
			else if((MySurfaceView.keyState&0x2)!=0)
			{//��down������
				mv.slideFB(-1);
			}
			if((MySurfaceView.keyState&0x4)!=0)
			{//��left������
				mv.slideLR(-1);
			}
			else if((MySurfaceView.keyState&0x8)!=0)
			{//��right������
				mv.slideLR(1);
			}		
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
