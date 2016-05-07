precision mediump float;
varying vec2 vTexPosition; //ת������������
const float maxIterations = 999.0;//����������
const float zoom = 0.6;//����ϵ��  
const float xCenter = 0.0;//��������λ��  
const float yCenter = 0.0;
const vec3 innerColor = vec3(0.0, 0.0, 1.0);//�ڲ���ɫ
const vec3 outerColor1 = vec3(1.0, 0.0, 0.0);//�ⲿ��ɫ1
const vec3 outerColor2 = vec3(0.0, 1.0, 0.0);//�ⲿ��ɫ2
void main() {  
	float real = vTexPosition.x * zoom + xCenter;//�任��ǰλ�� 
	float imag = vTexPosition.y * zoom + yCenter;	
	float cReal = 0.32;//c��ʵ��
	float cImag =0.043;//c���鲿	
	float r2 = 0.0;//�뾶��ƽ��
	float i;//��������  
	for(i=0.0; i<maxIterations && r2<4.0; i++){//ѭ������
		float tmpReal = real;//���浱ǰʵ��ֵ		
		real = (tmpReal * tmpReal) - (imag * imag) +cReal;//������һ�ε�����ʵ����ֵ
		imag = 2.0 *tmpReal * imag +cImag;//������һ�ε������鲿��ֵ
		r2 = (real * real) + (imag * imag);//����뾶��ƽ��
	}
    vec3 color;    
    if(r2 < 4.0){//���r2δ�ﵽ4���˳���ѭ�����������������Ѵﵽ���ֵ
    	color = innerColor;//Ϊ�ڲ���ɫ��ֵ
    }else{//�����r2����4.0���˳�ѭ����������λ�����ⲿ
    	color = mix(outerColor1, outerColor2, fract(i * 0.07));//����������Ϊ�ⲿ��ɫ����ͬ��ֵ
    }    
   	gl_FragColor = vec4(color, 1.0);//Ϊ������ɫ��ֵ   
}              