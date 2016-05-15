uniform mat4 uMVPMatrix; 		//�ܱ任����
attribute vec3 aPosition;  		//����λ��
attribute vec2 aTexCoor;    		//������������
attribute vec3 aNormal;   		//������
attribute vec3 tNormal;   			//������
varying vec2 vTextureCoord;  		//���ڴ��ݸ�ƬԪ��ɫ������������
varying vec3 fNormal;    		//���ڴ��ݸ�ƬԪ��ɫ���ķ�����
varying vec3 ftNormal;    		//���ڴ��ݸ�ƬԪ��ɫ����������
varying vec3 vPosition;  			//���ڴ��ݸ�ƬԪ��ɫ���Ķ���λ��
void main() {     
   gl_Position = uMVPMatrix * vec4(aPosition,1); 	//�����ܱ任�������˴λ��ƴ˶����λ��
   vTextureCoord=aTexCoor;					//��������������괫��ƬԪ��ɫ��
   fNormal=aNormal;   						//������ķ���������ƬԪ��ɫ��
   ftNormal=tNormal; 						//�����������������ƬԪ��ɫ��
   vPosition=aPosition; 						//�������λ�ô���ƬԪ��ɫ��
}   
