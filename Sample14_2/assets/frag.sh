precision mediump float;
varying vec4 vAmbient;//���մӶ�����ɫ�������Ļ��������
varying vec4 vDiffuse;//���մӶ�����ɫ��������ɢ������
varying vec4 vSpecular;//���մӶ�����ɫ�������ľ��淴������
varying vec2 mcLongLat;//���մӶ�����ɫ�������Ĳ���
void main() {
   vec3 color;
   if(abs(mcLongLat.y)>75.0){
   	  color = vec3(1.0,1.0,1.0);//�����ǰ�ɫ
   }else{
      int colorNum = int(mcLongLat.x/45.0);//��ɫ��
      if(colorNum == 0){
      	color = vec3(1.0,0.0,0.0);//0����ɫ
      }else if(colorNum == 1){
      	color = vec3(0.0,1.0,0.0);//1����ɫ
      }else if(colorNum == 2){
      	color = vec3(0.0,0.0,1.0);//2����ɫ
      }else if(colorNum == 3){
      	color = vec3(1.0,1.0,0.0);//3����ɫ
      }else if(colorNum == 4){
      	color = vec3(1.0,0.0,1.0);//4����ɫ
      }else if(colorNum == 5){
      	color = vec3(0.0,1.0,1.0);//5����ɫ
      }else if(colorNum == 6){
      	color = vec3(0.3,0.4,0.7);//6����ɫ
      }else if(colorNum == 7){
      	color = vec3(0.3,0.7,0.2);//7����ɫ   
      }
   }
   vec4 finalColor = vec4(color,1.0);//�����������ɫ����ƬԪ
   //����ƬԪ��ɫֵ
   gl_FragColor=finalColor*vAmbient + finalColor*vDiffuse + finalColor*vSpecular;
}     