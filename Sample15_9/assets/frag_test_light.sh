precision mediump float;
uniform vec3 uLightLocation;//��Դλ��
uniform mat4 uMMatrix; //�任����
uniform vec3 uCamera;	//�����λ��
uniform vec3 uColor;    //������ɫ

varying vec3 vPosition;//���մӶ�����ɫ�������Ķ���λ��
varying vec3 vNormal;//���մӶ�����ɫ�������Ķ��㷨����

//��λ����ռ���ķ���
void pointLight(					//��λ����ռ���ķ���
  in vec3 normal,				//������
  inout vec4 ambient,			//����������ǿ��
  inout vec4 diffuse,				//ɢ�������ǿ��
  inout vec4 specular,			//���������ǿ��
  in vec3 lightLocation,			//��Դλ��
  in vec4 lightAmbient,			//������ǿ��
  in vec4 lightDiffuse,			//ɢ���ǿ��
  in vec4 lightSpecular			//�����ǿ��
){
  ambient=lightAmbient;			//ֱ�ӵó������������ǿ��  
  vec3 normalTarget=vPosition+normal;	//����任��ķ�����
  vec3 newNormal=(uMMatrix*vec4(normalTarget,1)).xyz-(uMMatrix*vec4(vPosition,1)).xyz;
  newNormal=normalize(newNormal); 	//�Է��������
  //����ӱ���㵽�����������
  vec3 eye= normalize(uCamera-(uMMatrix*vec4(vPosition,1)).xyz);  
  //����ӱ���㵽��Դλ�õ�����vp
  vec3 vp= normalize(lightLocation-(uMMatrix*vec4(vPosition,1)).xyz);  
  vec3 halfVector=normalize(vp+eye);	//����������ߵİ�����    
  float shininess=50.0;				//�ֲڶȣ�ԽСԽ�⻬
  float nDotViewPosition=max(0.0,dot(newNormal,vp)); 	//��������vp�ĵ����0�����ֵ
  diffuse=lightDiffuse*nDotViewPosition;				//����ɢ��������ǿ��
  float nDotViewHalfVector=dot(newNormal,halfVector);	//������������ĵ�� 
  float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess)); 	//���淴���ǿ������
  specular=lightSpecular*powerFactor;    			//���㾵��������ǿ��
}
void main()                         
{
   //������ɫ
   vec4 baseColor=vec4(uColor,1.0);   
   vec4 ambient, diffuse, specular;   
   pointLight(normalize(vNormal),ambient,diffuse,specular,uLightLocation,
   		vec4(0.15,0.15,0.15,1.0),vec4(0.9,0.9,0.9,1.0),vec4(0.7,0.7,0.7,1.0));
   //����ƬԪ��ɫֵ
   gl_FragColor=baseColor*ambient + baseColor*diffuse + baseColor*specular;
}     