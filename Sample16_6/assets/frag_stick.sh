precision mediump float;
varying vec4 ambient;
varying vec4 diffuse;
varying vec4 specular;

varying  vec4 aaColor; //���մӶ�����ɫ�������Ĳ���
void main()                         
{  
  //����ƬԪ�������в�������ɫֵ            
   vec4 finalColor = vec4(0.0,1.0,0.0,0.0); 
  //����ƬԪ��ɫֵ 
  gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;
}              