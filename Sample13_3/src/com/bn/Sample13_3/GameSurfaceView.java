package com.bn.Sample13_3;
import static com.bn.Sample13_3.Constant.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.bn.Sample13_3.R;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
class GameSurfaceView extends GLSurfaceView
{
    private SceneRenderer mRenderer;//������Ⱦ��
    static Sample13_3_Activity activty;
    float mPreviousY;
    float mPreviousX;
    //-----����
    int tex_grassId;//ϵͳ����Ĳݵ�����id
    int tex_sandId;
    int tex_leavesId;//ϵͳ����Ĺ�ľ����id
    int tex_treejointId;//���ɵ�����id
    int tex_waterId;//ˮ������Id
    int tex_skyId;//�������id
    //-----����������
    TreesForControl treesForControl;//����Ҭ�����Ŀ�����
    TreeLeaves treeLeaves[];//Ҷ��ģ��
    TreeTrunk treeTrunk;//����ģ��
    FloorRect floorLand;	//�ݵ�
    SeaWater water;//ˮ��
    LandForm landForm;//�Ҷ�ͼɽ
    SkyBall skyBall;//�����
    static float tx=CAMERA_X;//�۲�Ŀ���x����  
    static float ty=CAMERA_HEIGHT;//�۲�Ŀ���y����
    static float tz=CAMERA_Z;//�۲�Ŀ���z����  
    static float cx=(float) (tx+Math.sin(Math.toRadians(camera_direction))*DISTANCE);//�����x����
    static float cy=CAMERA_HEIGHT;//�����y����
    static float cz=(float) (tz+Math.cos(Math.toRadians(camera_direction))*DISTANCE);//�����z����
	public GameSurfaceView(Context context) 
	{
        super(context);
        activty=(Sample13_3_Activity)context;
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
        this.setKeepScreenOn(true);
    }
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) 
    {    	
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) 
        {
	        case MotionEvent.ACTION_DOWN://���¶���
	        break;
	        case MotionEvent.ACTION_UP://̧����
	        break;
	        case MotionEvent.ACTION_MOVE:
        	    float dy = y - mPreviousY;//���㴥�ر�Yλ��
	            float dx = x - mPreviousX;//���㴥�ر�Xλ�� 
	        	if(dy>5&&DISTANCE>600)//ǰ��
	        	{
	        		DISTANCE-=20;
	        	}
	        	else if(dy<-5&&DISTANCE<4700)//����
	        	{
	        		DISTANCE+=20;
	        	}
	        	if(dx<-10)//��ת��
	        	{
	        		camera_direction-=dx*0.1f;
	        	}
	        	else if(dx>10)//��ת��
	        	{
	        		camera_direction-=dx*0.1f;
	        	}
	        break;
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        //���³�ʼ���������λ�úͷ���
        cx=(float) (tx+Math.sin(Math.toRadians(camera_direction))*DISTANCE);//�����x����
        cy=CAMERA_HEIGHT;//�����y����
        cz=(float) (tz+Math.cos(Math.toRadians(camera_direction))*DISTANCE);//�����z����
        return true;
    }
	class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		float oldTime=0;
        public void onDrawFrame(GL10 gl) 
        { 
        	float currTime=System.nanoTime();
        	System.out.println("FPS  ==   "+1000000000.0/(currTime-oldTime));
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            MatrixState.setCamera(cx,cy,cz,tx,ty,tz,0f,1.0f,0.0f);
            //�������ת
			sky_rotation=(sky_rotation+0.03f)%360;
            //�������
            skyBall.drawSelf(tex_skyId,sky_rotation);
            //����ɽ��
            drawLandForm();
            //����ˮ��
            drawWater();
            //����������Ҭ����
            treesForControl.drawSelf(tex_leavesId,tex_treejointId,bend_R,wind_direction);  
            oldTime=currTime;
        }  
        public void onSurfaceChanged(GL10 gl, int width, int height) 
        {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 50000);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(cx,cy,cz,tx,ty,tz,0f,1.0f,0.0f);    
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config)
        {
            //������Ļ����ɫRGBA
            GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);  
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            //��ʼ����Դλ��
            MatrixState.setLightLocation(sunPosition[0], sunPosition[1],sunPosition[2]);
            //����shader
            ShaderManager.loadCodeFromFile(GameSurfaceView.this.getResources());
            //����shader
            ShaderManager.compileShader();
            initAllObject();//��ʼ���������
            initAllTexture();//��ʼ������
            
            //����һ���̣߳���ʱ�ڶ�����
            new Thread()
            {
        		@Override
            	public void run()
            	{
        			while(flag_go)
        			{
        				//Ҭ������ת
        				if(0!=wind)//�������Ϊ0������
        				{
        					//����Ҭ���������Ұڶ�
            				wind_speed+=wind_force;//�ڶ����ٶ�  
            				bend_R-=wind_speed;//�ڶ��ķ���
            				if(bend_R>bend_R_max)//����״̬
            				{
            					wind_speed=wind_speed_init;
            					bend_R=bend_R_max;
            				}
        				}
        				try
        				{
        					Thread.sleep(50);
        				}
        				catch(Exception e)
        				{
        					e.printStackTrace();
        				}
        			}
            	}
            }.start();
        }
    }
	//����ɽ�صķ���
	public void drawLandForm()
	{
		for(int i=0;i<floor_array.length;i++)
		{
			for(int j=0;j<floor_array[0].length;j++)
			{
				MatrixState.pushMatrix();
				MatrixState.translate(j*FLOOR_WIDTH,0,i*FLOOR_HEIGHT);
				if(1==floor_array[i][j])//���Ƹ�ɽ
				{
					landForm.drawSelf(tex_grassId,tex_sandId);
				}
				MatrixState.popMatrix();
			}
		}
	}
	//����ˮ��
	public void drawWater()
	{
		MatrixState.pushMatrix();
    	MatrixState.translate(0, 3f, 0);
		 //����ˮ��
        water.drawSelf(tex_waterId);
        MatrixState.popMatrix();
	}
	//�������е������������
	public void initAllObject()
	{
		//��������
		treeTrunk=new TreeTrunk
		(
			ShaderManager.getTreeWaveShaderProgram(),
			bottom_radius,
			joint_height,
			joint_num,
			joint_available_num
		);
		//������Ƭ��Ҷ
		treeLeaves=new TreeLeaves[]
        {
			new TreeLeaves(ShaderManager.getLeavesShaderProgram(),leaves_width,leaves_height,leaves_offset,0),
			new TreeLeaves(ShaderManager.getLeavesShaderProgram(),leaves_width,leaves_height,leaves_offset,1),	
			new TreeLeaves(ShaderManager.getLeavesShaderProgram(),leaves_width,leaves_height,leaves_offset,2),
			new TreeLeaves(ShaderManager.getLeavesShaderProgram(),leaves_width,leaves_height,leaves_offset,3),
			new TreeLeaves(ShaderManager.getLeavesShaderProgram(),leaves_width,leaves_height,leaves_offset,4),
			new TreeLeaves(ShaderManager.getLeavesShaderProgram(),leaves_width,leaves_height,leaves_offset,5)
        };
		//�������Ŀ�����
		treesForControl=new TreesForControl(treeTrunk,treeLeaves);
	    //��������
		floorLand=new FloorRect 
        (
        	ShaderManager.getTextureShaderProgram(),
            FLOOR_WIDTH,
            FLOOR_HEIGHT
        ); 
		//����ˮ��
		water=new SeaWater(ShaderManager.getWaterShaderProgram());
		//���ػҶ�ͼ��ɽ
		initLand(getResources(),R.drawable.land);
		//�����Ҷ�ͼɽ
		landForm=new LandForm(LAND_ARRAY,ShaderManager.getLandFormShaderProgram());//�Ҷ�ͼɽ
		//�������
		skyBall=new SkyBall
		(
			SKY_BALL_RADIUS,
			ShaderManager.getTextureShaderProgram(),
			SKY_BALL_RADIUS, 
			0, 
			SKY_BALL_RADIUS 
		);
	}
	//��ʼ�����е�����ͼ��
	public void initAllTexture()
	{
		Resources r=this.getResources();//��ȡ��Դ
        tex_grassId=initTexture(r,R.drawable.caodi);//�ݵ�����
        tex_sandId=initTexture(r,R.drawable.sand);//ɳ̲����
        tex_leavesId=initTexture(r,R.drawable.coconut);//Ҷ������
        tex_treejointId=initTexture(r,R.raw.treetrunk);//���ڵ�����
        tex_waterId=initTexture(r,R.drawable.water);//ˮ������
        tex_skyId=initTexture(r,R.drawable.sky);//�������
	}
}
