uniform mat4 uMVPMatrix; //�ܱ任����
uniform float uStartAngle;//��֡��ʼ�Ƕ�
uniform float uWidthSpan;//���򳤶��ܿ��
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()     
{                  
   
   //����X��Ƕ�          		
   float angleSpanH=30.0*3.14159265;//����Ƕ��ܿ��   
   float startX=0.0;//��ʼX����
   //���ݺ���Ƕ��ܿ�ȡ����򳤶��ܿ�ȼ���ǰ��X�����������ǰ��X�����Ӧ�ĽǶ�
   float currAngleH=uStartAngle+((aPosition.x-startX)/uWidthSpan)*angleSpanH;
   
   //�������z��չ��ʼ�Ƕȵ��Ŷ�ֵ
   float startZ=0.0;//��ʼz����
   //��������Ƕ��ܿ�ȡ����򳤶��ܿ�ȼ���ǰ��Y�����������ǰ��Y�����Ӧ�ĽǶ�
   float currAngleZ=((aPosition.z-startZ)/uWidthSpan)*angleSpanH;
      
   //����б����
   float tzH=sin(currAngleH-currAngleZ)*0.8;   
   //�����ܱ任�������˴λ��ƴ˶���λ��
   gl_Position = uMVPMatrix * vec4(aPosition.x,tzH,aPosition.z,1); 
   
  // gl_Position = uMVPMatrix * vec4(aPosition,1); 
   vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                      