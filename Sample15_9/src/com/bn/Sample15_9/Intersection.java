package com.bn.Sample15_9;
//�������ÿ��ĳ�������ཻ��Ϣ����
public class Intersection {

	int numHits;//�������ཻʱ���ཻ�������Ŀ
	HitInfo[] hit=new HitInfo[8];//�����б��Ժ������Ҫ����8������
	
	public Intersection(){		
		for(int i=0; i<8; i++){//��ʼ��HitInfo����
			hit[i]=new HitInfo();
		}
	}
	public void set(Intersection inter){
		for(int i=0; i<8; i++){//����һ�ݣ���������ֱ�Ӹ�����
			this.hit[i].set(inter.hit[i]);
		}
		
		this.numHits = inter.numHits;
	}
}
