precision mediump float;
varying vec2 vTextureCoord; //���������������
varying float vertexHeight;//���ܶ���ĸ߶�ֵ
uniform sampler2D sTextureSand;//������������   ----ɳ̲
uniform sampler2D sTextureGrass;//������������-----�ݵ�
void main()                         
{       
	float height1=15.0;  
	float height2=25.0;    
   vec4 finalSand=texture2D(sTextureSand, vTextureCoord);//ɳ̲
   vec4 finalGrass=texture2D(sTextureGrass, vTextureCoord);   //�ݵ�
   if(vertexHeight<height1)//����ɳ̲
   {
  	  gl_FragColor = finalSand;
   }
   else if(vertexHeight<height2)//���Ʋݵغ�ɳ̲��ϲ�
   {
      float ratio=(vertexHeight-height1)/(height2-height1);	
      finalSand *=(1.0-ratio); 
   	  finalGrass *=ratio;
      gl_FragColor =finalGrass+ finalSand;
   }
   else//���Ʋݵ�
   {
      gl_FragColor = finalGrass;
   }
}              