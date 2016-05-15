precision mediump float;			//����Ĭ�Ͼ���
uniform sampler2D sTexture;		//������������
varying vec4 ambient;			//���մӶ�����ɫ�����ݹ����Ļ��������
varying vec4 diffuse;				//���մӶ�����ɫ�����ݹ�����ɢ������
varying vec4 specular;			//���մӶ�����ɫ�����ݹ����ľ�������
varying vec4 vPosition;  			//���մӶ�����ɫ�����ݹ�����ƬԪλ��
uniform highp mat4 uMVPMatrixGY; //��Դλ�ô�����������۲켰ͶӰ��Ͼ��� 
void main() {   
   //��ƬԪ��λ��ͶӰ����Դ������������Ľ�ƽ����
   vec4 gytyPosition=uMVPMatrixGY * vec4(vPosition.xyz,1);
   gytyPosition=gytyPosition/gytyPosition.w;	//����͸�ӳ���   
   float s=gytyPosition.s+0.5;				//��ͶӰ������껻��Ϊ��������
   float t=gytyPosition.t+0.5;    
   vec4 finalColor=vec4(0.8,0.8,0.8,1.0); 		   //���屾�����ɫ
   if(s>=0.0&&s<=1.0&&t>=0.0&&t<=1.0){	   //�����������ںϷ���Χ������ͶӰ��ͼ
		vec4 projColor=texture2D(sTexture, vec2(s,t)); //��ͶӰ����ͼ���в���
   		vec4 specularTemp=projColor*specular;	//����ͶӰ��ͼ�Ծ�����Ӱ��
   		vec4 diffuseTemp=projColor*diffuse;		//����ͶӰ��ͼ��ɢ����Ӱ��
		//��������ƬԪ��ɫ
   		gl_FragColor = finalColor*ambient+finalColor*specularTemp+finalColor*diffuseTemp;
   } else {//��������ƬԪ��ɫ
         gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse; 
   }   
}       
