precision mediump float;
//���մӶ�����ɫ�������Ĳ���
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;
uniform vec4 aColor;
void main()                         
{    
   //�����������ɫ����ƬԪ
   //vec4 finalColor=vec4(0.9,0.9,0.9,1.0);
   gl_FragColor = aColor*ambient+aColor*specular+aColor*diffuse;//����ƬԪ��ɫֵ
}   