uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
uniform float ratio;
void main()     
{       
	float pi = 3.1415926; 
	//-----------------------����Ҫ���ж�άŤ��----------------
	//���ĵ��X�����Y����
	float centerX=0.0;
	float centerY=-5.0;
	//��ȡ��ǰ���X�����Y����
	float currX = aPosition.x;
	float currY = aPosition.y;
	//����X��Y��ƫ����
	float spanX = currX - centerX;
	float spanY = currY - centerY;
	//���㼫��
	float currRadius = sqrt(spanX * spanX + spanY * spanY);
	//���㵱ǰ��ļ���
	float currRadians;//�û��ȱ�ʾ
	if(spanX != 0.0)
	{
		currRadians = atan(spanY , spanX);
	}
	else
	{
		currRadians = spanY > 0.0 ? pi/2.0 : 3.0*pi/2.0; 
	}
	//����Ť��
	float resultRadians = currRadians + ratio*currRadius;
	//��������
	float resultX = centerX + currRadius * cos(resultRadians);
	float resultY = centerY + currRadius * sin(resultRadians);
	//��������
    gl_Position = uMVPMatrix * vec4(resultX,resultY,0.0,1); //�����ܱ任�������˴λ��ƴ˶���λ��
    vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                       