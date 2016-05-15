package com.bn.Sample15_9;

/*
 * λ��xoyƽ�����ģ��߳�Ϊ2��������
 */
public class Square extends HitObject {
	public Square(Camera cam, Color3f color){		
		this.cam = cam;
		this.color = color;
	}
	@Override
	public boolean hit(Ray r,Intersection inter) {
		/*
		 * ������S+ct��任������Ľ�����Ҫ���²��裺
		 * 1�������任����S'+c't
		 * 2�������任������ͨ���������ײʱ��t
		 * 3������ײʱ��t�����ʽS+ct�õ�ʵ�ʵĽ�������
		 * 
		 * 
		 * ��ˣ�genRayֻ�Ǳ任��Ĺ��ߣ�
		 * ֻ���������ײʱ��t��
		 * ��t�󽻵�ʱ�ñ任ǰ�Ĺ���r
		 */
		Ray genRay=new Ray();//���������任��Ĺ���		
		xfrmRay(genRay, getInvertMatrix(), r);//��ȡ�任��Ĺ��ߣ���r����任����任��ֵ��genRay��
		double denom = genRay.dir.z;//��ĸ
		
		if(Math.abs(denom)<0.0001){//���ߺ�ƽ��ƽ��:�޽���
			return false;
		}
		double time=-genRay.start.z/denom;//�ཻʱ��
		if(time<=0.0){//���������ӵ��
			return false;
		}
		
		
		
		double hx=genRay.start.x+genRay.dir.x*time;//�����x����
		double hy=genRay.start.y+genRay.dir.y*time;//�����y����
		if (hx > 1.0 || hx < -1.0) {//x���ڷ�Χ��
			return false;
		}
		if (hy > 1.0 || hy < -1.0) {//y���ڷ�Χ��
			return false;
		}
		
		inter.numHits=1;//��һ����Ч����
		
		//�����ߺ�������ཻ��Ϣ����inter��
		inter.hit[0].hitTime=time;
		inter.hit[0].hitObject=this;
		inter.hit[0].isEntering=true;
		inter.hit[0].surface=0;
		Point3 P = rayPos(r,time);//��������(ʹ�ñ任ǰ�Ĺ���)
		inter.hit[0].hitPoint.set(P);//�任��Ķ���λ��
		inter.hit[0].hitNormal.set(0,0,1);//�任ǰ�ķ�����
		return true;
	}
	@Override
	public boolean hit(Ray r) {
		Ray genRay=new Ray();//���������任��Ĺ���		
		xfrmRay(genRay, getInvertMatrix(), r);//��ȡ�任��Ĺ��ߣ���r����任����任��ֵ��genRay��
		double denom = genRay.dir.z;//��ĸ
		
		if(Math.abs(denom)<0.0001){//���ߺ�ƽ��ƽ��:�޽���
			return false;
		}
		double time=-genRay.start.z/denom;//�ཻʱ��
		//ֻ���ܴ�0��1֮�����ײ����Ϊ�ڹ�Դ����һ�಻�������Ӱ
		if(time<0.0 ||time>1){//���������ӵ��
			return false;
		}
		
		double hx=genRay.start.x+genRay.dir.x*time;//�����x����
		double hy=genRay.start.y+genRay.dir.y*time;//�����y����
		if (hx > 1.0 || hx < -1.0) {//x���ڷ�Χ��
			return false;
		}
		if (hy > 1.0 || hy < -1.0) {//y���ڷ�Χ��
			return false;
		}
		return true;
	}
}
