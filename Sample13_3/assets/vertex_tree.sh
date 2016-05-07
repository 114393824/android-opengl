//���������ɽڵ�Ķ�����ɫ��
//��������е�ƫת�Ƕ��������Z����������˵�ģ���ʱ����ת��
uniform mat4 uMVPMatrix; //�ܱ任����
uniform float bend_R;//����ָ�������������뾶
uniform float direction_degree;//����ָ�ķ���,�ýǶȱ�ʾ�ģ���Z����������ʱ����ת
attribute vec3 aPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
varying vec2 vTextureCoord;  //���ڴ��ݸ�ƬԪ��ɫ���ı���
void main()     
{      
   	//���㵱ǰ�Ļ���
	float curr_radian=aPosition.y/bend_R;
	//���㵱ǰ��Ľ���߶�
	float result_height=bend_R*sin(curr_radian);
	//���㵱ǰ������ӵĳ���
	float increase=bend_R-bend_R*cos(curr_radian);
	//���㵱ǰ������x����
	float result_X=aPosition.x+increase*sin(radians(direction_degree));
	//���㵱ǰ������z����
	float result_Z=aPosition.z+increase*cos(radians(direction_degree));
	//�����
	vec4 result_point=vec4(result_X,result_height,result_Z,1.0);
    gl_Position = uMVPMatrix * result_point; //�����ܱ任�������˴λ��ƴ˶���λ��
   	vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}



                      