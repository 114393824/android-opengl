package com.bn.Sample13_3;
import static com.bn.Sample13_3.Constant.SCREEN_HEIGHT;
import static com.bn.Sample13_3.Constant.SCREEN_WIDTH;
import static com.bn.Sample13_3.Constant.flag_go;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
public class Sample13_3_Activity extends Activity 
{
	private GameSurfaceView mGLSurfaceView;
	AudioManager mgr;// ��Ƶ������
	SoundPool soundPool;// ������
	MediaPlayer bgMusic;// ��Ϸ�������ֲ�����
	HashMap<Integer,Integer> soundMap;//����������е�����ID��Map
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);   
        initScreen();
        initSound();//��activity�е�onCreate�����е���
		//��ʼ��GLSurfaceView
        mGLSurfaceView = new GameSurfaceView(this);
        setContentView(mGLSurfaceView);	
        mGLSurfaceView.requestFocus();//��ȡ����
        mGLSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
        bgMusic.start();
    }
    //��ʼ����Ļ
    public void initScreen()
    {
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
		              WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//����Ϊ����ģʽ
		SCREEN_WIDTH=getWindowManager().getDefaultDisplay().getWidth();
		SCREEN_HEIGHT=getWindowManager().getDefaultDisplay().getHeight();
    }
    @Override
    protected void onResume() 
    {
        super.onResume();
        mGLSurfaceView.onResume();
        flag_go=true;
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        mGLSurfaceView.onPause();
        flag_go=false;
    }  
    //����ѡ��˵�
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	  menu.add(0, 0, 0, "����")
    	   .setIcon(R.drawable.icon);
    	  menu.add(0, 1, 0, "����")
    	   .setIcon(R.drawable.icon);
    	  return super.onCreateOptionsMenu(menu);
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	  switch(item.getItemId())
	  {
	  case 0: 
		  showDialog(0);
	   break;
	  case 1:   
		  showDialog(1);
	   break;
	  }
	  return super.onOptionsItemSelected(item);
	}
    @Override
    public Dialog onCreateDialog(int id)
    {
    	Dialog dialog=null;
    	switch(id)
    	{
    	  case 0://������ͨ�Ի���Ĵ���
    		  String msg="��ǰ�ķ���Ϊ: "+Constant.wind_direction+" ��";
    		  LayoutInflater factory = LayoutInflater.from(this);  
    		  View view = factory.inflate(R.layout.seekbar, null);  
    		  final TextView tv=(TextView)view.findViewById(R.id.seekbar_tv);
    		  tv.setText(msg);
    		  final SeekBar sb=(SeekBar)view.findViewById(R.id.seekbar_sb);
    		  sb.setMax(359);
    		  sb.setProgress((int)Constant.wind_direction);
    		  sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
    		  {
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) 
				{
					Constant.wind_direction=sb.getProgress();
					tv.setText("��ǰ�ķ���Ϊ: "+(float)sb.getProgress()+" ��");
				}
			});
    		  Builder b=new AlertDialog.Builder(this);  
    		  b.setIcon(R.drawable.icon);//����ͼ��
    		  b.setTitle("���÷���");//���ñ���
    		  b.setView(view);
    		  b.setPositiveButton//Ϊ�Ի������ð�ť
    		  (
    				"ȷ��", 
    				new DialogInterface.OnClickListener()
	        		{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							Constant.wind_direction=sb.getProgress();
						}      			
	        		}
    		  );
    		  dialog=b.create();
    	  break;
    	  case 1://������ͨ�Ի���Ĵ���
    		  msg="��ǰ�ķ���Ϊ: "+Constant.wind+" ��";
    		  factory = LayoutInflater.from(this);
    		  view = factory.inflate(R.layout.seekbar, null);  
    		  final TextView tv1=(TextView)view.findViewById(R.id.seekbar_tv);
    		  tv1.setText(msg);
    		  final SeekBar sb1=(SeekBar)view.findViewById(R.id.seekbar_sb);
    		  sb1.setMax(12);
    		  sb1.setProgress(Constant.wind);
    		  sb1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
    		  {
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				}
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
				}
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) 
				{
					Constant.setWindForce(sb1.getProgress());
					tv1.setText("��ǰ�ķ���Ϊ: "+sb1.getProgress()+" ��");
				}
			});
    		  b=new AlertDialog.Builder(this);  
    		  b.setIcon(R.drawable.icon);//����ͼ��
    		  b.setTitle("���÷���");//���ñ���
    		  b.setView(view);
    		  b.setPositiveButton//Ϊ�Ի������ð�ť
    		  (
    				"ȷ��", 
    				new DialogInterface.OnClickListener()
	        		{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							Constant.setWindForce(sb1.getProgress());
						}      			
	        		}
    		  );
    		  dialog=b.create();
    	  break;
    	}
    	return dialog;
    }
   //����������Դ
	public void initSound() 
	{
		// ��ȡ��Ƶ������
		mgr = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
		// ��ʼ��ý�岥����
		bgMusic = MediaPlayer.create(this, R.raw.gamebg_music);
		bgMusic.setLooping(true);// �Ƿ�ѭ��
		// ��ʼ��������
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 5);// ����������
		soundMap = new HashMap<Integer, Integer>();// ����map
		soundMap.put(0, soundPool.load(this, R.raw.wind, 1));
	}
    //���������صķ���
    public void playSound(int sound,int loop)
    {
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
        float volume = streamVolumeCurrent / streamVolumeMax;   
        soundPool.play
        (
         soundMap.get(sound), //������Դid
         volume,      //����������
         volume,      //����������
         1,        //���ȼ�     
         loop,       //ѭ������ -1������Զѭ��
         0.5f      //�ط��ٶ�0.5f��2.0f֮��
        );
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent e)  
    { 
    	 //����������ֻ�ܿ���ý�������Ĵ�С
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN||keyCode==KeyEvent.KEYCODE_VOLUME_UP)
        {
              setVolumeControlStream(AudioManager.STREAM_MUSIC);
              return super.onKeyDown(keyCode, e);
        }
    	if(keyCode==4)
    	{
    		System.exit(0);
    		return true;
    	}
		return super.onKeyDown(keyCode, e);
    }
}