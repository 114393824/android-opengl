precision mediump float;				//����Ĭ�ϸ��㾫��
uniform sampler2D sTexture;			//������������
uniform highp vec3 uLightLocation;		//��Դλ��
uniform highp mat4 uMVPMatrixGY; 	//�ܱ任����(��Դ)
varying vec4 ambient;				//���մӶ�����ɫ�������Ļ���������ǿ��
varying vec4 diffuse; 				//���մӶ�����ɫ��������ɢ�������ǿ��
varying vec4 specular; 				//���մӶ�����ɫ�������ľ��������ǿ��
varying vec4 vPosition;  				//���մӶ�����ɫ�������ı任�󶥵�λ��
void main(){   
   //��ƬԪ��λ��ͶӰ����Դ������������Ľ�ƽ����
   vec4 gytyPosition=uMVPMatrixGY * vec4(vPosition.xyz,1);
   gytyPosition=gytyPosition/gytyPosition.w;  		//����͸�ӳ���
   float s=(gytyPosition.s+1.0)/2.0; 				//��ͶӰ������껻��Ϊ��������
   float t=(gytyPosition.t+1.0)/2.0; 
   vec4 depth4=texture2D(sTexture, vec2(s,t)); 	//��ͶӰ����(��������)ͼ���в���
   //������������ɫֵ�������С����ֵZA
   float minDis=depth4.r*256.0*256.0+depth4.g*256.0+depth4.b+depth4.a/32.0;   
   float currDis=distance(vPosition.xyz,uLightLocation);	//�����Դ����ƬԪ�ľ���ZB
   vec4 finalColor=vec4(0.95,0.95,0.95,1.0); 			//�������ɫ
   if(s>=0.0&&s<=1.0&&t>=0.0&&t<=1.0) { 		//�����������ںϷ���Χ������ͶӰ��ͼ
   		if(minDis<=currDis-3.0) {				//��ʵ�ʾ��������С����������Ӱ��
   			gl_FragColor= finalColor*ambient*1.2;	//���û�������ɫ
   		} else{//������Ӱ��������ͨ��������ɫ
   			gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;
   		}
   } else{ 	//������Ӱ��������ͨ��������ɫ
         gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;
   }
}        
