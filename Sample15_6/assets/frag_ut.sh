precision mediump float;		//����Ĭ�ϸ��㾫��
varying vec2 vTextureCoord;			//���մӶ�����ɫ�����ݹ�������������
varying vec3 fNormal;    			//���մӶ�����ɫ�����ݹ����ķ�����
varying vec3 ftNormal;   				//���մӶ�����ɫ�����ݹ�����������
varying vec3 vPosition;  				//���մӶ�����ɫ�����ݹ����Ķ���λ��
uniform sampler2D sTextureWg;		//�����������ݣ���ۣ�
uniform sampler2D sTextureNormal;		//�����������ݣ����ߣ�
uniform mat4 uMMatrix; 				//�任����
uniform vec3 uCamera;				//�����λ��
uniform vec3 uLightLocationSun;		//��Դλ��
void pointLight(					//��λ����ռ���ķ���
  in vec3 normal,				//�Ŷ�������
  out vec4 ambient,				//���ջ�����ǿ��
  out vec4 diffuse,				//����ɢ���ǿ��
  out vec4 specular,				//���վ����ǿ��  
  in vec3 vp,					//�任����׼��������������ϵ�ı���㵽��Դλ�õ�����
  in vec3 eye,					//�任����׼��������������ϵ����������
  in vec4 lightAmbient,			//������ǿ��
  in vec4 lightDiffuse,			//ɢ���ǿ��
  in vec4 lightSpecular			//�����ǿ��
){   
  ambient=lightAmbient; 						//ֱ�ӵó������������ǿ��  
  vec3 halfVector=normalize(vp+eye);			//����������ߵİ�����    
  float shininess=50.0;						//�ֲڶȣ�ԽСԽ�⻬
  float nDotViewPosition=max(0.0,dot(normal,vp)); 	//��������vp�ĵ����0�����ֵ
  diffuse=lightDiffuse*nDotViewPosition;			//����ɢ��������ǿ��
  float nDotViewHalfVector=dot(normal,halfVector);	//��������������ĵ�� 
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess)); 	//���淴���ǿ������
  specular=lightSpecular*powerFactor;    		//���㾵��������ǿ��	
}
void main(){   
   vec4 ambient,diffuse,specular;	  //������������ͨ������ǿ�ȵı��� 
   vec4 normalColor = texture2D(sTextureNormal, vTextureCoord); //�ӷ�������ͼ�ж���ֵ
   //��ֵ�ָ���-1��+1��Χ
   vec3 cNormal=vec3(2.0*(normalColor.r-0.5),2.0*(normalColor.g-0.5),2.0*(normalColor.b-0.5));   
   cNormal=normalize(cNormal);   //���Ŷ�����������
   //����任��ķ�����
   vec3 normalTarget=vPosition+fNormal;
   vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(vPosition,1)).xyz;
   newNormal=normalize(newNormal);  
   //����任��������� 
   vec3 tangentTarget=vPosition+ftNormal;
   vec3 newTangent=(uMMatrix*vec4(tangentTarget,1)).xyz-(uMMatrix*vec4(vPosition,1)).xyz;
   newTangent=normalize(newTangent);    
   vec3 binormal=normalize(cross(newTangent,newNormal));	//���㸱������
   //����������������������������任���󣬴˾������ڽ�����
   //��ʵ������ϵ�任����׼��������������ϵ
   mat3 rotation=mat3(newTangent,binormal,newNormal);    
   vec3 newPosition=(uMMatrix*vec4(vPosition,1)).xyz; 		//�任���ƬԪλ��   
   vec3 vp= normalize(uLightLocationSun-newPosition);		//�����㵽��Դλ�õ�����vp�����
   vp=normalize(rotation*vp);							//�任�����vp���� 	
   //����ӱ���㵽������������������б任�����  				  
   vec3 eye= normalize(rotation*normalize(uCamera-newPosition));
   pointLight(cNormal,ambient,diffuse,specular,vp,eye,
   	vec4(0.05,0.05,0.05,1.0),vec4(1.0,1.0,1.0,1.0),vec4(0.3,0.3,0.3,1.0));   
   vec4 finalColor=texture2D(sTextureWg, vTextureCoord);	//�����������������ƬԪ��ɫֵ 
   //�ۺ�����ͨ���������ǿ�ȼ�ƬԪ����ɫ���������ƬԪ����ɫ�����ݸ�����
   gl_FragColor = finalColor*ambient+finalColor*specular+finalColor*diffuse;
}        
