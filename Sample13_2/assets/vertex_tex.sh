uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
uniform float angleSpan;//Ť���ܽǶȿ��
uniform float yStart;//Y������ʼ��
uniform float ySpan;//Y������
void main()     
{
   //���㵱ǰ����Ƕȿ��
   float tempAS= angleSpan*(aPosition.y-yStart)/ySpan;
   vec3 tPosition=aPosition;
   //������������һ�Ŷ������XZλ��
   if(aPosition.y>yStart)
   {
     tPosition.x=(cos(tempAS)*aPosition.x-sin(tempAS)*aPosition.z);
     tPosition.z=(sin(tempAS)*aPosition.x+cos(tempAS)*aPosition.z);
   }
   gl_Position = uMVPMatrix * vec4(tPosition,1); //�����ܱ任�������˴λ��ƴ˶���λ��
   
   vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                      