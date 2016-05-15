package com.bn.Sample16_4;
//�洢����ϵͳ�е�ĳ������������Ϣ����
public class SingleGrain {
	float vx;//x���ٶȷ���
	float vy;//y���ٶȷ���
	float vz;//z���ٶȷ���
	float timeSpan=0;//�ۼ�ʱ��
	
	public SingleGrain(float vx,float vy,float vz)
	{
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;		
	}
	
	public void drawSelf()
	{
		MatrixState.pushMatrix();	
		//���ݵ�ǰʱ������������λ��
		float x=vx*timeSpan;
		float z=vz*timeSpan;
		float y=vy*timeSpan-0.5f*timeSpan*timeSpan*1.5f+3.0f;		
		MatrixState.translate(x, y, z);
		//��������
		GrainGroup.gfd.drawSelf();
		MatrixState.popMatrix();
	}
}