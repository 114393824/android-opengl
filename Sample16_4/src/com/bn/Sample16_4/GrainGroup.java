package com.bn.Sample16_4;
import java.util.ArrayList;

//��������ϵͳ����
public class GrainGroup {
	//���ڻ��Ƶ�����
	static GrainForDraw gfd;     
	//�������ӵ��б�
	ArrayList<SingleGrain> al=new ArrayList<SingleGrain>();
 	static final float SPEED_SPAN=(float) (1.5f+1.5f*Math.random());//���ӳ��ٶ�
	static final float SPEED=0.02f;//�����ƶ�ÿһ����ģ��ʱ�ӣ�Ҳ����ʱ������
	
     public GrainGroup(MySurfaceView mv)
     {
    	//��ʼ�����ڻ��Ƶ�������ͬ��ɫ������
    	gfd=new GrainForDraw(4,1,1,1,mv);
    	//����������
 		for(int i=0;i<400;i++)
 		{
 			//����������ӵķ�λ�Ǽ�����
 			double elevation=0.35f*Math.random()*Math.PI+Math.PI*0.15f;//����
 			double direction=Math.random()*Math.PI*2;//��λ��
 			//�����������XYZ�᷽����ٶȷ���
 			float vy=(float)(SPEED_SPAN*Math.sin(elevation));	
 			float vx=(float)(SPEED_SPAN*Math.cos(elevation)*Math.cos(direction));	
 			float vz=(float)(SPEED_SPAN*Math.cos(elevation)*Math.sin(direction));	
 			//�������Ӷ�����ӽ������б�
 			al.add(new SingleGrain(vx,vy,vz));
 		}
     }
     
     long timeStamp=0;
     public void drawSelf()
     {
    	 long currTimeStamp=System.nanoTime()/1000000;
    	 if(currTimeStamp-timeStamp>10)
    	 {
    		 for(SingleGrain sp:al)
        	 {//ɨ�������б����޸�����ʱ���
        		sp.timeSpan=sp.timeSpan+SPEED;
        		if(sp.timeSpan>10)  
        		{
        			sp.timeSpan=0;
        		}
        	 }
    		 timeStamp=currTimeStamp;
    	 }
    	 
		 int size=al.size();
		 //ѭ��ɨ���������ӵ��б����Ƹ�������
		 for(int i=0;i<size;i++)
    	 {
			 try
	    	 {
    		   al.get(i).drawSelf();
	    	 }
			 catch(Exception e){}
    	 } 
     }
}