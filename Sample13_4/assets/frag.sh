precision mediump float;
uniform sampler2D sTexture;//������������
//���մӶ�����ɫ�������Ĳ���
varying vec2 vTextureCoord;
void main()                         
{    
   //����ƬԪ��ɫֵ
   gl_FragColor = texture2D(sTexture, vTextureCoord); 

}   