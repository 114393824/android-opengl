uniform mat4 uMVPMatrix; //�ܱ任����
uniform mat4 uMMatrix; //�任����
uniform vec3 uCamera;	//�����λ��
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;	//������ɫ������������


void main()     
{                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   
   vTextureCoord=aTexCoor;  
}                 