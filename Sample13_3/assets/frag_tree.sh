precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
void main()                         
{           
   //�����������ɫ����ƬԪ
   gl_FragColor=texture2D(sTexture, vTextureCoord);
}              