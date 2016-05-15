package com.bn.Sample15_9;
import java.util.ArrayList;
import java.util.List;
import static com.bn.Sample15_9.Constant.*;
//������
public class Scene {
	Camera cam;
	Light light;
	Ray feeler = new Ray();//��Ӱ̽����
	List<HitObject> hitObjects;//�����б�
	
	Ball ball1;//��1
	Ball ball2;//��2
	Square sqare;//����ƽ��
	
	public Scene(Camera cam, Light light){
		this.cam=cam;
		this.light = light;
		hitObjects = new ArrayList<HitObject>();
		//����һ����ɫ����
		ball1 = new Ball(cam, new Color3f(BALL1_COLOR));
		//����һ����ɫ����
		ball2 = new Ball(cam, new Color3f(BALL2_COLOR));
		//����һ����ɫƽ��
		sqare = new Square(cam, new Color3f(PLANE_COLOR));
		
		//��������볡����
		hitObjects.add(ball1);
		hitObjects.add(ball2);
		hitObjects.add(sqare);
		
	}
	
	//�����е�������б任�ķ���
	public void transform(){	
		//Ϊ���������ʼ���任����
		for(HitObject pObj:hitObjects){
			pObj.initMyMatrix();
		}
		
		//����ƽ��ı任
		sqare.rotate(-90, 1, 0, 0);
		sqare.scale(PLANE_WIDTH/2.0f, PLANE_HEIGHT/2.0f, 1);
		
		//������1�ı任
		ball1.translate(-CENTER_DIS, R, 0);
		ball1.scale(R, R, R);
		
		//������2�ı任
		ball2.translate(CENTER_DIS, R, 0);
		ball2.scale(R, R, R);
	}
	
	/*
	 * ���ع��߶�Ӧ�����ظ���Ϣ,
	 * 
	 * ����ֵ��
	 * -1��ʾû�н��㣬
	 * 0��ʾ�н��㣬�������ײ�㲻����Ӱ��
	 * 1��ʾ�н��㣬�������ײ������Ӱ��
	 */
	public int shade(
			Ray ray, //����
			Color3f color, //������ɫ
			Point3 vetex, //�任��Ķ���λ��
			Vector3 normal//�任��ķ�����
	){
		Intersection best = new Intersection();//���ڱ���ĿǰΪֹ��ѵ���ײ��¼		
		getFirstHit(ray, best);//��������ײ��¼
		if(best.numHits==0){//���û������������ཻ
			return -1;
		}		
		//���������������ཻ������ײ��ĸ���Ϣ
		color.set(best.hit[0].hitObject.getColor());//������ɫ
		vetex.set(best.hit[0].hitPoint);//����λ��
		//ͨ����ת�ñ任����任֮��ķ�����
		float[] inverTranspM = best.hit[0].hitObject.getInvertTransposeMatrix();//��ת�þ���		
		Vector3 preN = best.hit[0].hitNormal;//�任ǰ�ķ�����
		best.hit[0].hitObject.xfrmNormal(normal, inverTranspM, preN);//��任��ķ�����
		
		//̽���Ƿ�����Ӱ��
		Point3 hitPoint = best.hit[0].hitPoint;
		//��Ӱ̽���������Ϊ����ײ�㳯���۷����ƶ�һ��΢С�ľ���
		feeler.start.set(hitPoint.minus(ray.dir.multiConst(MNIMUM)));
		//��Ӱ̽�����ķ��򣬴���ײ��ָ���Դ
		feeler.dir = light.pos.minus(hitPoint);
		if(isInShadow(feeler)){
			return 1;//�н��㣬�������ײ������Ӱ��
		}
		return 0;//�н��㣬�������ײ�㲻����Ӱ��
	}
	
	public void getFirstHit(Ray ray, Intersection best){
		Intersection inter = new Intersection();//ʵ�����ཻ��¼
		best.numHits=0;//��û�н���
		/*
		 * �˴���������ÿ�������Ƿ��ཻ��
		 * ��ÿ�������ཻ����Ϣ����洢��best�С�
		 * ���ڹ����뵥�������ཻʱ��
		 * �ܻὫ����������������ཻ�㱣����best.hit[0]��(��ÿ�������hit��������)��
		 * ���ֻҪ����������ġ�����㡱��Ϣ���Ƚϣ��������ս������best.hit[0]�У�
		 * ���ɵó��������������������Ľ�����Ϣ
		 */
		for(HitObject pObj:hitObjects){//��鳡���е�ÿһ������
			if(!pObj.hit(ray, inter)){//�����Ƿ��pObj�ཻ�����˴��Ѿ�����hit������������ཻ���Ὣ�ཻ��Ϣ��¼��inter�У�
				continue;//�޽��㣺�����һ������
			}
			if(best.numHits==0 || //best�л�û�н�����Ϣ����best�еĽ��㲻�������
					inter.hit[0].hitTime<best.hit[0].hitTime){
				/*
				 * ע������һ���Ǹ���һ�ݣ�������ֱ�Ӹ������ã�
				 * ���������ֵһ��ᵼ��best��ֵҲ�䣡
				 */
				best.set(inter);//����inter��best
			}
		}
	}
	//����Ƿ�����Ӱ�еķ���������Ϊ���߸�����
	public boolean isInShadow(Ray feeler){
		for(HitObject pObj:hitObjects){
			if(pObj.hit(feeler)){//�������κ������ཻ������Ӱ��
				return true;
			}
		}
		return false;//û���ཻ���ﻯ��������Ӱ��
	}
}