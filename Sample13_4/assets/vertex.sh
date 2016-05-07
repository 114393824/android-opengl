uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec3 bPosition;  //����λ��
attribute vec3 cPosition;  //����λ��
attribute vec2 aTexCoor;    //������������
uniform float uBfb;//�仯�ٷֱ�
varying vec2 vTextureCoord;  

void main()     
{ 
 	vec3 tv;         		
   if(uBfb<=1.0)
   {
   		tv=mix(aPosition,bPosition,uBfb);
   }
   else
   {
   		tv=mix(bPosition,cPosition,uBfb-1.0);
   }
   gl_Position = uMVPMatrix * vec4(tv,1);
   vTextureCoord = aTexCoor;//�����յ��������괫�ݸ�ƬԪ��ɫ��
}                      