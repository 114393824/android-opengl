precision mediump float;
uniform samplerCube sTexture;//������������
varying vec3 eyeVary;		//���մӶ�����ɫ����������������
varying vec3 newNormalVary;	//���մӶ�����ɫ�������ı任������
vec4 zs(					//���ݷ�����������������˹�������ɼ�������ͼ��������ķ���
  in float zsl				//������
){  
  vec3 vTextureCoord=refract(-eyeVary,newNormalVary,zsl);//����˹�������ɼ���
  vec4 finalColor=textureCube(sTexture, vTextureCoord);  //��������ͼ�������     
  return finalColor;
}
void main(){
   vec4 finalColor=vec4(0.0,0.0,0.0,0.0);
   //������ɫɢRGB����ɫ��ͨ��������������
   finalColor.r=zs(0.97).r;  
   finalColor.g=zs(0.955).g;  
   finalColor.b=zs(0.94).b;  
   gl_FragColor=finalColor; 
}    
