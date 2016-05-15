package com.bn.Sample19_3;

import java.util.HashMap;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;

public class MySurfaceView extends GLSurfaceView 
{
    private SceneRenderer mRenderer;//������Ⱦ��
    //�����ƫ����
    static float xOffset=0;
    //���ת���ĽǶ�
    static float yAngle=0;
	HashMap<Integer,BNPoint> hm=new HashMap<Integer,BNPoint>();
	//ģ����̼����߳�
	KeyThread kt;
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//��ȡ���صĶ������
		int action=event.getAction()&MotionEvent.ACTION_MASK;
		//��ȡ��������id��downʱ������id����ȷ��upʱ����id��ȷ������idҪ��ѯMap��ʣ�µ�һ�����id��
		int id=(event.getAction()&MotionEvent.ACTION_POINTER_ID_MASK)>>>MotionEvent.ACTION_POINTER_ID_SHIFT;
		switch(action)
		{
			case MotionEvent.ACTION_DOWN: //����down
			case MotionEvent.ACTION_POINTER_DOWN: //����down	
				hm.put(id, new BNPoint(event.getX(id),event.getY(id)));
				kt.keyPress(event.getX(id), event.getY(id));
			break;
			case MotionEvent.ACTION_POINTER_UP:
				float x=hm.get(id).x;
				float y=hm.get(id).y;
				hm.remove(id);
				kt.keyUp(x, y);
			break;
			case MotionEvent.ACTION_UP:
				hm.clear();
				kt.clearKeyState();
			break;
		}
		return true;
	}
	
	public MySurfaceView(Context context)
	{
        super(context);
        this.setEGLContextClientVersion(2); //����ʹ��OPENGL ES2.0
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ 
    }
	
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
    	//��ָ����obj�ļ��м��ض���
		LoadedObjectVertexNormal ch;
		LoadedObjectVertexNormal pm;
    	
        public void onDrawFrame(GL10 gl) 
        {
        	//�����Ȼ�������ɫ����
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            MatrixState.copyMVMatrix();
            MatrixState.pushMatrix();//�����ֳ�
            //�����ص����岻Ϊ�����������
            if(pm!=null)
            {
            	pm.drawSelf();
            }
            MatrixState.popMatrix();
            MatrixState.pushMatrix();
            MatrixState.translate(xOffset, 0, 0);
            MatrixState.rotate(yAngle, 0, 1, 0);
            if(ch!=null)
            {
            	ch.drawSelf();
            }
            MatrixState.popMatrix();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	GLES20.glViewport(0, 0, width, height); 
        	//����GLSurfaceView�Ŀ�߱�
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
            MatrixState.setCamera(0,5,25,0,0,0,0,1,0);
            //��ʼ����Դλ��
        	MatrixState.setLightLocation(70, 30, 70);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //������Ļ����ɫRGBA
        	GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
            //����ȼ��
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //�򿪱������   
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            //��ʼ���任����
            MatrixState.setInitStack();
            //����Ҫ���Ƶ�����
            ch=LoadUtil.loadFromFile("ch.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            pm=LoadUtil.loadFromFile("pm.obj", MySurfaceView.this.getResources(),MySurfaceView.this);
            kt=new KeyThread();
            kt.start();
        }
    }
}