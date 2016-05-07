package com.bn.Sample8_6;

import java.util.ArrayList;
import java.util.List;

public class RegularPolygon {
    
    int vCount=0;    
    int iCount=0;

    float length;
    int borderCount;
    List<Float> verticesList;//����εĶ�������
    double[][] vectors;//ÿ����ķ���������ÿ���ߵ�����������ñ߷�������������һ��
    double[] initVector;//�����ʼ�ķ�������
    double[] pivot;//��ת��
    
    int[] vertices;//��¼Ҫ���Ƶ��������
    int[] borders;//��¼Ҫ���Ƶ�Բ�ܵ�����
    
    List<RegularPolygon> children;
    MySurfaceView mv;

    public RegularPolygon(MySurfaceView mv,
    		int borderCount,	//Բ�ܵı��
    		double angle,		//��ת�ĽǶ�
    		float length,		//����
    		double[] initPoint,	//��ʼ�ĵ�
    		double[] initVector,//��ʼ����
    		double[] pivot,		//��ת��
    		int[] vertices,		//�����������
    		int[] borders		//����Բ�ܵ�����
    		){    	
    	this.mv=mv;
    	this.borderCount=borderCount;
    	this.length=length;
    	this.vectors = new double[borderCount][3];
    	this.initVector=initVector;
    	this.vertices=vertices;
    	this.borders=borders;
    	this.pivot = pivot;//���������ת�ᣬ���������ת���ڸ��������ת��Ļ�������ת�õ� 
    	children = new ArrayList<RegularPolygon>();
    	this.verticesList = Utils.getRegularPentagonVertexData(
    			initPoint, initVector, length,angle,vectors,borderCount,pivot);

    } 
    
    public void drawSelf(float xOffset,float yOffset) 
    {  
    	//���ƶ���
    	for(int i=0;i<vertices.length;i++){
    		int index = vertices[i];
    		float x = verticesList.get(3*index);
    		float y = verticesList.get(3*index+1);
    		float z = verticesList.get(3*index+2);
    		MatrixState.pushMatrix();
    		//�ƶ��������λ�ã�������
    		MatrixState.translate(x, y, z);
    		mv.ball.drawSelf();
    		MatrixState.popMatrix();
    	}
    	//����Բ��
    	for(int i=0;i<borders.length;i++){
    		int index = borders[i];
    		//��ȡԲ�ܵ��������
    		float x = verticesList.get(3*index);
    		float y = verticesList.get(3*index+1);
    		float z = verticesList.get(3*index+2);
    		//��ȡԲ�ܵ�����
    		double[] vector = vectors[index];
    		MatrixState.pushMatrix();
    		//�����ƶ������
    		MatrixState.translate(x, y, z);
    		MatrixState.pushMatrix();
    		Utils.moveXToSomeVector(vector);	//x��任��ָ������������ϵ
    		MatrixState.translate(Constant.LENGTH/2, 0, 0);
    		mv.stick.drawSelf();			//����ľ��
    		MatrixState.popMatrix();
    		MatrixState.popMatrix();
    	}
         drawChildren( xOffset, yOffset);		//����
         

    }
	
    public RegularPolygon buildChild(
    		int borderCount,	//Բ�ܵ�����	
    		double angle,		//��ת�Ƕ�
    		int position,		
    		int[] vertices,		//������
    		int[] borders		//Բ������
    	){
    	double[] initPoint = new double[3];
    	for(int i=0;i<3;i++){
    		initPoint[i]=verticesList.get(3*position+i);
    	}
    	double[] initVector = vectors[position];
    	double[] tempPivot = Utils.copyVecor(this.pivot);
    	RegularPolygon child = new RegularPolygon(this.mv, 
    			borderCount, angle, length, initPoint, initVector,tempPivot,vertices,borders);
    	children.add(child);
    	return child;
    }
    
    private void drawChildren(float xOffset,float yOffset){
    	for(int i=0;i<children.size();i++){
    		RegularPolygon child = children.get(i);
    		MatrixState.pushMatrix();
    		child.drawSelf(xOffset, yOffset);
    		MatrixState.popMatrix();
    	}
    }
}
