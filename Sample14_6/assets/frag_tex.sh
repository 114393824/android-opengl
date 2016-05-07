precision mediump float;
varying vec2 vTextureCoord;//���մӶ�����ɫ�������Ĳ���
uniform sampler2D sTexture1;//������������1
uniform sampler2D sTexture2;//������������2
uniform float uT;
void main() {           
    vec4 color1 = texture2D(sTexture1, vTextureCoord); 	//�������в�������ɫֵ1  
    vec4 color2 = texture2D(sTexture2, vTextureCoord); 	//�������в�������ɫֵ2
    gl_FragColor = color1*(1.0-uT) + color2*uT;// ���������ɫֵ
}              