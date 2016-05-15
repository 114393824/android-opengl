package com.bn.Sample16_6;

public class Vector3 {

	float x;
	float y;
	float z;
	
	public Vector3(){};
	
	public Vector3(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	//���������
	public Vector3 add(Vector3 temp) {
		Vector3 result = new Vector3();
		result.x=this.x+temp.x;
		result.y=this.y+temp.y;
		result.z=this.z+temp.z;
		return result;
	}
	//�������
	public Vector3 multiConstant(float constant) {
		Vector3 result = new Vector3();
		result.x = this.x*constant;
		result.y = this.y*constant;
		result.z = this.z*constant;
		return result;
	}
	
	public void init(){
		this.x=0;
		this.y=0;
		this.z=0;
	}

	public float length() {
		return (float) Math.sqrt(this.x*this.x+this.y*this.y+this.z*this.z);
	}
	
	@Override
	public String toString(){
		return "Vector:("+this.x+","+this.y+","+this.z+")";
	}
	
	public Vector3 copy(){
		return new Vector3(this.x,this.y,this.z);
	}
	
	//������--v1���v2
	public static double[] getCJ(double[] v1,double[] v2){
		double[] result = new double[3];
		result[0]=v1[1]*v2[2]-v1[2]*v2[1];
		result[1]=v1[2]*v2[0]-v1[0]*v2[2];
		result[2]=v1[0]*v2[1]-v1[1]*v2[0];
		return result;
	}
	
	public static int compare(double x,double y){
		if(Math.abs(x-y)<0.000001){
			return 0;
		}else if(x-y>0.000001){
			return 1;
		}else{
			return -1;
		}
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
	
	//�任����ϵ--��x��任��ָ��������λ��
	public static void moveXToSomeVector(double[] vector){
		double x[]={1,0,0};
		double angle = getAngleBetweenTwoVector(x,vector);//vector��x��ļн�
		//ͨ��x��vector�Ĳ���������ת�������
		double pivot[] = getCJ(x,vector);
		MatrixState.rotate((float)angle, (float)pivot[0], (float)pivot[1],(float)pivot[2]);
	}
	
	public static Vector3 yRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��y����ת�任����
		{
		   {Math.cos(angle),0,-Math.sin(angle),0},
		   {0,1,0,0},
		   {Math.sin(angle),0,Math.cos(angle),0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return new Vector3((float)gVector[0],(float)gVector[1],(float)gVector[2]);
	}		
	
	//angleΪ���� gVector  Ϊ��������[x,y,z,1]
	//����ֵΪ��ת�������
	public static Vector3 zRotate(double angle,double[] gVector)
	{
		double[][] matrix=//��z����ת�任����
		{
		   {Math.cos(angle),Math.sin(angle),0,0},		   
		   {-Math.sin(angle),Math.cos(angle),0,0},
		   {0,0,1,0},
		   {0,0,0,1}	
		};
		
		double[] tempDot={gVector[0],gVector[1],gVector[2],gVector[3]};
		for(int j=0;j<4;j++)
		{
			gVector[j]=(tempDot[0]*matrix[0][j]+tempDot[1]*matrix[1][j]+
			             tempDot[2]*matrix[2][j]+tempDot[3]*matrix[3][j]);    
		}
		
		return new Vector3((float)gVector[0],(float)gVector[1],(float)gVector[2]);
	}
	
	public Vector3 normal(){
		float module = (float) Math.sqrt(this.x*this.x+this.y*this.y+this.z*this.z);
		return new Vector3(this.x/module,this.y/module,this.z/module);
	}
}
