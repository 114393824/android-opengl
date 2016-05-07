uniform mat4 uMVPMatrix; //�ܱ任����
uniform float uStartAngle;//��֡��ʼ�Ƕ�
uniform float uWidthSpan;//���򳤶��ܿ��
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()     
{            
   //����X����                		
   float angleSpanH=4.0*3.14159265;//����Ƕ��ܿ��   
   float startX=-uWidthSpan/2.0;//��ʼX����
   //���ݺ���Ƕ��ܿ�ȡ����򳤶��ܿ�ȼ���ǰ��X�����������ǰ��X�����Ӧ�ĽǶ�
   float currAngle=uStartAngle+((aPosition.x-startX)/uWidthSpan)*angleSpanH;
   float tz=sin(currAngle)*0.1;      
   
   //�����ܱ任�������˴λ��ƴ˶���λ��
   gl_Position = uMVPMatrix * vec4(aPosition.x,aPosition.y,tz,1); 
   vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                      