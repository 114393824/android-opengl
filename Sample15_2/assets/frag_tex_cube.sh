precision mediump float;
uniform samplerCube sTexture;//������������
varying vec3 vTextureCoord; //���մӶ�����ɫ�������Ĳ���
void main() {
   //�����������ɫ����ƬԪ
   gl_FragColor=textureCube(sTexture, vTextureCoord);    
}   
