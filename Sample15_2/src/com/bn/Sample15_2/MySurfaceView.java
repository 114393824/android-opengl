package com.bn.Sample15_2;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import static com.bn.Sample15_2.Constant.*;

class MySurfaceView extends GLSurfaceView 
{
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��    
    
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��X����
    
    //�������λ�ýǶ�
    float cx=0;
    float cy=2;
    float cz=24;
    float cAngle=0;

    int textureIdCM;//ϵͳ�����Cube Map����
    int[] textureIdA=new int[6];//��պ����������
	
	public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		        
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) 
    {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Xλ��
            cAngle+=dx * TOUCH_SCALE_FACTOR;
            cx=(float) (Math.sin(Math.toRadians(cAngle))*24f);
            cz=(float) (Math.cos(Math.toRadians(cAngle))*24f);
            cy+=dy/10.0f;
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(cx,cy,cz,0f,0f,0f,0f,1.0f,0.0f);
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {  
		float yAngle;//��Y����ת�ĽǶ�
    	float zAngle; //��Z����ת�ĽǶ�
    	//��ָ����obj�ļ��м��ض���
		LoadedObjectVertexNormalTexture lovo;
		TextureRect texRect;//�������
        public void onDrawFrame(GL10 gl) 
        { 
        	//�����Ȼ�������ɫ����
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            
            //����ϵ��Զ
            MatrixState.pushMatrix();
            //��Y�ᡢZ����ת
            MatrixState.rotate(yAngle, 0, 1, 0);
            MatrixState.rotate(zAngle, 1, 0, 0);            
            //�����ص����岻Ϊ�����������
            if(lovo!=null)
            {
            	lovo.drawSelf(textureIdCM);
            }   
            MatrixState.popMatrix();               
            //��պ��������ֵ
            final float tzz=0.4f;            
            //������պк���
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -UNIT_SIZE+tzz);
            texRect.drawSelf(textureIdA[0]);
            MatrixState.popMatrix();              
            //������պ�ǰ��
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, UNIT_SIZE-tzz);
            MatrixState.rotate(180, 0, 1, 0);
            texRect.drawSelf(textureIdA[5]);
            MatrixState.popMatrix(); 
            //������ǽ
            MatrixState.pushMatrix();
            MatrixState.translate(-UNIT_SIZE+tzz, 0, 0);
            MatrixState.rotate(90, 0, 1, 0);
            texRect.drawSelf(textureIdA[1]);
            MatrixState.popMatrix(); 
            //������ǽ
            MatrixState.pushMatrix();
            MatrixState.translate(UNIT_SIZE-tzz, 0, 0);
            MatrixState.rotate(-90, 0, 1, 0);
            texRect.drawSelf(textureIdA[2]);
            MatrixState.popMatrix();
            //������ǽ
            MatrixState.pushMatrix();
            MatrixState.translate(0, -UNIT_SIZE+tzz, 0);
            MatrixState.rotate(-90, 1, 0, 0);
            texRect.drawSelf(textureIdA[3]);
            MatrixState.popMatrix(); 
            //������ǽ
            MatrixState.pushMatrix();
            MatrixState.translate(0, UNIT_SIZE-tzz, 0);
            MatrixState.rotate(90, 1, 0, 0);
            texRect.drawSelf(textureIdA[4]);
            MatrixState.popMatrix(); 
        }  

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 1000);
            //���ô˷������������9����λ�þ���
            MatrixState.setCamera(cx,cy,cz,0f,0f,0f,0f,1.0f,0.0f);
            
            new Thread()
            {
            	public void run()
            	{
            		while(true)
            		{
            			mRenderer.yAngle += 5;//������x����ת�Ƕ�
            			mRenderer.zAngle+= 3;//������z����ת�Ƕ�
            			  
            			try {
							Thread.sleep(100);
						} catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
            		}
            	}
            }.start();
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
            //����Ҫ���Ƶ�����
            lovo=LoadUtil.loadFromFileVertexOnly("ch.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            //����������ζԶ��� 
            texRect=new TextureRect(MySurfaceView.this);   
            //��������
            int[] cubeMapResourceIds = new int[]
            {
                    R.raw.skycubemap_right, R.raw.skycubemap_left, R.raw.skycubemap_up_cube,
                    R.raw.skycubemap_down, R.raw.skycubemap_front, R.raw.skycubemap_back
            };
            textureIdCM=generateCubeMap(cubeMapResourceIds); 
            
            textureIdA[0]=initTexture(R.raw.skycubemap_back);
            textureIdA[1]=initTexture(R.raw.skycubemap_left);
            textureIdA[2]=initTexture(R.raw.skycubemap_right);
            textureIdA[3]=initTexture(R.raw.skycubemap_down);
            textureIdA[4]=initTexture(R.raw.skycubemap_up);
            textureIdA[5]=initTexture(R.raw.skycubemap_front);  
        }
    }
  	public int initTexture(int drawableId)//textureId
	{
		//��������ID
		int[] textures = new int[1];
		GLES20.glGenTextures
		(
				1,          //����������id������
				textures,   //����id������
				0           //ƫ����
		);    
		int textureId=textures[0];    
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);
        
        //ͨ������������ͼƬ===============begin===================
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        //ͨ������������ͼƬ===============end=====================  
        
        //ʵ�ʼ�������
        GLUtils.texImage2D
        (
        		GLES20.GL_TEXTURE_2D,   //�������ͣ���OpenGL ES�б���ΪGL10.GL_TEXTURE_2D
        		0, 					  //����Ĳ�Σ�0��ʾ����ͼ��㣬�������Ϊֱ����ͼ
        		bitmapTmp, 			  //����ͼ��
        		0					  //����߿�ߴ�
        );
        bitmapTmp.recycle(); 		  //������سɹ����ͷ�ͼƬ
        return textureId;
	}
  	
  	//��������ͼ����
    public int generateCubeMap(int[] resourceIds) 
    {
        int[] ids = new int[1];
        GLES20.glGenTextures(1, ids, 0);
        int cubeMapTextureId = ids[0];
        
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, cubeMapTextureId);     
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_CUBE_MAP,GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_CUBE_MAP,GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_REPEAT);

        for (int face = 0; face < 6; face++) 
        {
            InputStream is = getResources().openRawResource(resourceIds[face]);
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(is);
            } finally {
                try {
                    is.close();
                } catch(IOException e) {
                    Log.e("CubeMap", "Could not decode texture for face " + Integer.toString(face));
                }
            }
            GLUtils.texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, 0,bitmap, 0);
            bitmap.recycle();
        }
        return cubeMapTextureId;  
    }
}
