precision mediump float;
uniform samplerCube sTexture;//������������
varying vec3 eyeVary;		//���մӶ�����ɫ����������������
varying vec3 newNormalVary;	//���մӶ�����ɫ�������ı任������
vec4 zs(					//���ݷ�����������������˹�������ɼ�������ͼ��������ķ���
  in float zsl				//������
){  
  vec3 vTextureCoord=refract(-eyeVary,newNormalVary,zsl);//����˹�������ɼ���
  vec4 finalColor=textureCube(sTexture, vTextureCoord);     
  return finalColor;
}
void main(){
   gl_FragColor=zs(0.94); 
}    
