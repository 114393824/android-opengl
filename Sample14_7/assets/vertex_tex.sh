uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTexPosition; //ת������������
void main() {                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   vTexPosition = (aTexCoor-0.5)*5.0;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}