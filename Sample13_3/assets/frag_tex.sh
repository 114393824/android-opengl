precision mediump float;
varying vec2 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture;//������������
varying vec3 vVertexCoord; 
void main()                         
{          
   //����ƬԪ�������в�������ɫֵ            
   gl_FragColor = texture2D(sTexture, vTextureCoord); 
}              