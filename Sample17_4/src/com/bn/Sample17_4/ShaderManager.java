package com.bn.Sample17_4;
import android.content.res.Resources;

public class ShaderManager
{
	final static int shaderCount=2;
	final static String[][] shaderName=
	{
		{"vertex.sh","frag.sh"},
		{"vertex_color.sh","frag_color.sh"}
	};
	static String[]mVertexShader=new String[shaderCount];
	static String[]mFragmentShader=new String[shaderCount];
	static int[] program=new int[shaderCount];
	
	public static void loadCodeFromFile(Resources r)
	{
		for(int i=0;i<shaderCount;i++)
		{
			//���ض�����ɫ���Ľű�����       
	        mVertexShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][0],r);
	        //����ƬԪ��ɫ���Ľű����� 
	        mFragmentShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][1], r);
		}	
	}
	
	//����3D�����shader
	public static void compileShader()
	{
		for(int i=0;i<shaderCount;i++)
		{
			program[i]=ShaderUtil.createProgram(mVertexShader[i], mFragmentShader[i]);
			System.out.println("mProgram "+program[i]);
		}
	}
	//���ﷵ�ص�����������յ�shader����
	public static int getTextureLightShaderProgram()
	{
		return program[0];
	}
	//���ﷵ�ص�����ɫ��shader����
	public static int getColorShaderProgram()
	{
		return program[1];
	}
}
