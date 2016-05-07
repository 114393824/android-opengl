uniform mat4 uMVPMatrix; //�ܱ任����
uniform float uStartAngle;//��֡��ʼ�Ƕ�
uniform float uWidthSpan;//���򳤶��ܿ��
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()     
{                  
   //����X��Ƕ�          		
   float angleSpanH=4.0*3.14159265;//����Ƕ��ܿ��   
   float startX=-uWidthSpan/2.0;//��ʼX����
   //���ݺ���Ƕ��ܿ�ȡ����򳤶��ܿ�ȼ���ǰ��X�����������ǰ��X�����Ӧ�ĽǶ�
   float currAngleH=uStartAngle+((aPosition.x-startX)/uWidthSpan)*angleSpanH;
   
   //�������Y��չ��ʼ�Ƕȵ��Ŷ�ֵ
   float angleSpanZ=4.0*3.14159265;//����Ƕ��ܿ�� 
   float uHeightSpan=0.75*uWidthSpan;//���򳤶��ܿ��
   float startY=-uHeightSpan/2.0;//��ʼY����
   //��������Ƕ��ܿ�ȡ����򳤶��ܿ�ȼ���ǰ��Y�����������ǰ��Y�����Ӧ�ĽǶ�
   float currAngleZ=((aPosition.y-startY)/uHeightSpan)*angleSpanZ;
      
   //����б����
   float tzH=sin(currAngleH-currAngleZ)*0.1;   
   //�����ܱ任�������˴λ��ƴ˶���λ��
   gl_Position = uMVPMatrix * vec4(aPosition.x,aPosition.y,tzH,1); 
   vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                      