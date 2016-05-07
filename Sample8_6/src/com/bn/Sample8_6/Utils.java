package com.bn.Sample8_6;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	static boolean first=true;
	
	//��һ��������񻯵ķ���
	public static float[] normalizeVector(float x, float y, float z){
		float mod=module(x,y,z);
		return new float[]{x/mod, y/mod, z/mod};//���ع�񻯺������
	}
	//��������ģ�ķ���
	public static float module(float x, float y, float z){
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
	public static double[] nRotate(
			double angle,		//��ת�Ƕ�		
			double n[],			//��ת��
			double gVector[]	//��ת����
			){
		
		angle = Math.toRadians(angle);
		double[][] matrix=//����������ת�任����
		{
		   {n[0]*n[0]*(1-Math.cos(angle))+Math.cos(angle),n[0]*n[1]*(1-Math.cos(angle))+n[2]*Math.sin(angle),n[0]*n[2]*(1-Math.cos(angle))-n[1]*Math.sin(angle),0},		   
		   {n[0]*n[1]*(1-Math.cos(angle))-n[2]*Math.sin(angle),n[1]*n[1]*(1-Math.cos(angle))+Math.cos(angle),n[1]*n[2]*(1-Math.cos(angle))+n[0]*Math.sin(angle),0},
		   {n[0]*n[2]*(1-Math.cos(angle))+n[1]*Math.sin(angle),n[1]*n[2]*(1-Math.cos(angle))-n[0]*Math.sin(angle),n[2]*n[2]*(1-Math.cos(angle))+Math.cos(angle),0},
		   {0,0,0,1}	
		};
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		return gVector;		//���ؽ��
	}
	//������ʼ�㣬��ʼ�������߳�����ȡ������εĶ�������
	public static List<Float> getRegularPentagonVertexData(
			double[] initPoint,	//���
			double[] initVector, //��ʼ����(��������)
			double length, //�߳�
			double angle,	//��ת�Ƕ�
			double[][] vectors,		//������һ���ߵķ�������
			int borderCount,
			double[] pivot			//��ת��
			){
		List<Float> verticesList = new ArrayList<Float>();	//�½�һ��ArrayList
		
		double[] startPoint=initPoint;//���
		double[] endPoint;				//�յ�����
		double[] vector = copyVecor(initVector);//���Ƶ�һ���ߵķ�������
		int index=0;
		
		double[] vectorS = copyVecor(vector);	//����������һ��
		vectors[index++]=vectorS;		//����һ����������
		
		for(int i=0;i<initPoint.length;i++){	//����һ�����������ӵ�list��
			verticesList.add((float) initPoint[i]);
		}
		
		while(index<borderCount){	//ѭ����������ĵ������
			
			endPoint = new double[3];//������ǰ�߶ε��յ�
			//�յ�������������ϳ����뷽�������ĵ��
			endPoint[0]=startPoint[0]+length*vector[0];//�����յ�x
			endPoint[1]=startPoint[1]+length*vector[1];//�����յ�y
			endPoint[2]=startPoint[2]+length*vector[2];//�����յ�z
			//�������������յ���ڵ�һ���㣬�������ϣ�ѭ���˳�
			if(		
				compare(endPoint[0],initPoint[0])==0 	//����compare�������бȽ�
				&& compare(endPoint[1],initPoint[1])==0 
				&& compare(endPoint[2],initPoint[2])==0
			){
				break;
			}
			for(int i=0;i<endPoint.length;i++){		//���յ��������ӵ�list��
				float value = (float) endPoint[i];
				if(Math.abs(value)<0.000001){
					verticesList.add(new Float(0.0f));
					continue;
				}else{
					verticesList.add((float) endPoint[i]);
				}
			}
			//������һ���ߵķ�������
			if(index==1){
				vector = nRotate(angle,pivot,vector);//�Ƹ��������ת�����ɵڶ����������븸������ͬһƽ���
				if(!first){//������ǵ�һ�������
					double tempAngle = 39*angle/Math.abs(angle);//getDihedralAngle()*(angle/Math.abs(angle));
					vector = nRotate(tempAngle,initVector,vector);//���ڶ��������Ƶ�һ��������ת
					pivot = nRotate(tempAngle,initVector,pivot);//�����µ���ת��
				}
				first=false;
			}else{
				vector = nRotate(angle,pivot,vector);//����ǰ�ķ���������ת�õ��µķ�������
			}
			
			vectorS = copyVecor(vector);//����������һ��
			
			vectors[index++]=vectorS;//���µ���������

			startPoint = endPoint;//����ǰ�߶ε��յ���Ϊ�����߶ε����
		}

		return verticesList;
	}
	
	public static double[] copyVecor(double[] vector){	//��������������ķ���
		double[] copy = new double[vector.length];
		for(int i=0;i<vector.length;i++){
			copy[i]=vector[i];
		}
		return copy;
	}
	
	//�Ƚ��������ķ���
	public static int compare(double x,double y){
		if(Math.abs(x-y)<0.000001){
			return 0;
		}else if(x-y>0.000001){
			return 1;
		}else{
			return -1;
		}
	}
	//�����һ���������--����ε����½ǵ㣬ʹ��һ�����������Ϊ����ԭ��
	public static double[] getFirstPoint(
			float length	//������εı߳�
			){
		double first[] = new double[3];		//����������µ���������
		first[0]=-length/2;		//x����ֵ
		first[1]=-length/(2*Math.tan(Math.toRadians(36))); //y����ֵ
		first[2]=0;	//������xyƽ���ϣ�z��ȻΪ0

		return first;
	}
	//������--�׽����ʽ
	public static double getDihedralAngle(){

		return Math.toDegrees(Math.acos(Math.sqrt(5)/3));
	}
	//�������������ļн�--���Ϊ��
	public static double getAngleBetweenTwoVector(double[] vector1,double[] vector2){
		double angle=0;
		double DJ = vector1[0]*vector2[0]+vector1[1]*vector2[1]+vector1[2]*vector2[2];//������
		double mode = getMode(vector1)*getMode(vector2);//������ģ�Ļ�
		double cosa = DJ/mode;
		if(compare(cosa,1)==0){
			return 0;
		}
		angle = Math.toDegrees(Math.acos(cosa));
		return angle;
	}
	
	//��������ģ
	public static double getMode(double[] vector){
		return Math.sqrt(vector[0]*vector[0]+vector[1]*vector[1]+vector[2]*vector[2]);
	}
	
	public static double[] getCJ(double[] v1,double[] v2){//������--v1���v2
		double[] result = new double[3];
		result[0]=v1[1]*v2[2]-v1[2]*v2[1];
		result[1]=v1[2]*v2[0]-v1[0]*v2[2];
		result[2]=v1[0]*v2[1]-v1[1]*v2[0];
		return result;
	}
	
	//�任����ϵ--��x��任��ָ��������λ��
	public static void moveXToSomeVector(double[] vector){
		double x[]={1,0,0};
		double angle = getAngleBetweenTwoVector(x,vector);//vector��x��ļн�
		//ͨ��x��vector�Ĳ���������ת�������
		double pivot[] = getCJ(x,vector);	//�������˵ķ���
		MatrixState.rotate((float)angle, (float)pivot[0], (float)pivot[1],(float)pivot[2]);
	}
	
	static List<Float> drawnVertices = new ArrayList<Float>();//�Ѿ����Ƶĵ������
	
	public static boolean isExist(float x,float y,float z){

		for(int i=0;i<drawnVertices.size()/3;i++){
			float tempx = drawnVertices.get(3*i);
			float tempy = drawnVertices.get(3*i+1);
			float tempz = drawnVertices.get(3*i+2);
			double[] tempp=new double[]{tempx,tempy,tempz};
			double[] p = new double[]{x,y,z};
			if(getDistanceSquare(tempp, p)<=0.2*0.2*4){

				return true;
			}
		}

		return false;
	}
	
	public static double getDistanceSquare(double[] p1,double[] p2){
		return getSquare(p1[0]-p2[0])+getSquare(p1[1]-p2[1])+getSquare(p1[2]-p2[2]);
	}
	public static double getSquare(double x){
		return x*x;
	}
	
}
