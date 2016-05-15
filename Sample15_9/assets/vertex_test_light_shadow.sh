uniform mat4 uMVPMatrix; //�ܱ任����
uniform int isShadow;//��Ӱ���Ʊ�־
uniform vec3 uLightLocation;	//��Դλ��
uniform mat4 uMMatrix; //�任����
uniform mat4 uMProjCameraMatrix; //ͶӰ���������Ͼ���

attribute vec3 aPosition;  //����λ��
attribute vec3 aNormal;    //������

varying vec3 vPosition;//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
varying vec3 vNormal;//���ڴ��ݸ�ƬԪ��ɫ���Ķ��㷨����

void main()     
{
   if(isShadow==1)
   {//���Ʊ�Ӱ��������Ӱ����λ��
      vec3 A=vec3(0.0,0.0,0.0);//ͶӰƽ������һ������
      vec3 n=vec3(0.0,1.0,0.0);//ͶӰƽ�淨����
      vec3 S=uLightLocation; //��Դλ��
      vec3 V=(uMMatrix*vec4(aPosition,1)).xyz;  //����ƽ�ƺ���ת�任��ĵ������    
      vec3 VL=S+(V-S)*(dot(n,(A-S))/dot(n,(V-S)));//��õ�ͶӰ������
      gl_Position = uMProjCameraMatrix*vec4(VL,1); //�����ܱ任�������˴λ��ƴ˶���λ��   
   }
   else
   {
	  gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   }
   //�������λ�ô���ƬԪ��ɫ��
   vPosition = aPosition; 
   //������ķ���������ƬԪ��ɫ��
   vNormal = aNormal;
}