package com.bn.Sample16_1;

import java.util.ArrayList;

public class LovoGoThread extends Thread{

	ArrayList<RigidBody> al;//�����б�
	boolean flag=true;//�߳̿��Ʊ�־λ
	
	public LovoGoThread(ArrayList<RigidBody> al)
	{
		this.al=al;
	}

	public void run()
	{
		while(flag)
		{
			int size=al.size();
			for(int i=0;i<size;i++)
			{
				al.get(i).go(al);			
			}
			try
			{
				sleep(20);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
