uniform mat4 uMVPMatrix; //�ܱ任����
uniform float uPointSize;//��ߴ�
uniform float uTime;
attribute vec3 aVelocity;  //�����ٶ�
void main()     
{
   float currTime=mod(uTime,10.0);
   float px=aVelocity.x*currTime;
   float py=aVelocity.y*currTime-0.5*1.5*currTime*currTime+3.0;
   float pz=aVelocity.z*currTime;
   //�����ܱ任�������˴λ��ƴ˶���λ��                         		
   gl_Position = uMVPMatrix * vec4(px,py,pz,1); 
   //�������ӳߴ�
   gl_PointSize=uPointSize;  
}