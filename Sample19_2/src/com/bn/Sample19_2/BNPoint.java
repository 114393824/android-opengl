package com.bn.Sample19_2;

//���ڼ�¼���ص����ꡢ�����ƴ��ص����
public class BNPoint   
{
   //��һ��λ�õ�X��Y����
   float oldX;
   float oldY;	
   //�Ƿ��Ѿ�����һ��λ�õı�־λ
   boolean hasOld=false;
   //���ص�X��Y����
   float x;
   float y;    
   
   public BNPoint(float x,float y)
   {
	   this.x=x;
	   this.y=y;
   }
   
   public void setLocation(float x,float y)
   {
	   //��ԭ��λ�ü�¼Ϊ��λ��
	   oldX=this.x;
	   oldY=this.y;
	   //�����Ƿ��Ѿ�����һ��λ�õı�־λ
	   hasOld=true;
	   //������λ��
	   this.x=x;
	   this.y=y;
   }
   
   //�������������ı�־λ
   public static float calDistance(BNPoint a,BNPoint b)
   {
	   float result=0;
	   
	   result=(float)Math.sqrt(
	     (a.x-b.x)*(a.x-b.x)+
	     (a.y-b.y)*(a.y-b.y)
	   );	   
	   return result;
   }
}
