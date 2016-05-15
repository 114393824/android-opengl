uniform mat4 uMVPMatrix; 		//�ܱ任����
uniform mat4 uMMatrix; 			//�任����
uniform vec3 uCamera;			//�����λ��
attribute vec3 aPosition;  		//����λ��
attribute vec3 aNormal;    		//���㷨����
varying vec3 eyeVary;			//���ڴ��ݸ�ƬԪ��ɫ������������
varying vec3 newNormalVary;		//���ڴ��ݸ�ƬԪ��ɫ���ı任������
void main()  { 
  gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��    
   //����任��ķ����������
  vec3 normalTarget=aPosition+aNormal;
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(aPosition,1)).xyz;
  newNormal=normalize(newNormal);  
  newNormalVary=newNormal;//���任��ķ��������ݸ�ƬԪ��ɫ��
  //����ӹ۲�㵽�����������(��������)
  vec3 eye = normalize(uCamera-(uMMatrix*vec4(aPosition,1)).xyz);
  eyeVary=eye;//�������������ݸ�ƬԪ��ɫ��
} 
