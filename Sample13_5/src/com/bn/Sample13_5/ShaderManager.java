package com.bn.Sample13_5;

import android.content.res.Resources;
/*
 * ��shader��������Ҫ�����ڼ���shader�ͱ���shader
 */
public class ShaderManager
{
	final static String[][] shaderName=
	{
		{"vertex_tex.sh","frag_tex.sh"},
	};
	static String[]mVertexShader=new String[shaderName.length];//������ɫ���ַ�������
	static String[]mFragmentShader=new String[shaderName.length];//ƬԪ��ɫ���ַ�������
	static int[] program=new int[shaderName.length];//��������
	//����shader�ַ���
	public static void loadCodeFromFile(Resources r)
	{
		for(int i=0;i<shaderName.length;i++)
		{
			//���ض�����ɫ���Ľű�����       
	        mVertexShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][0],r);
	        //����ƬԪ��ɫ���Ľű����� 
	        mFragmentShader[i]=ShaderUtil.loadFromAssetsFile(shaderName[i][1], r);
		}	
	}
	//����shader
	public static void compileShader()
	{
		for(int i=0;i<shaderName.length;i++)
		{
			program[i]=ShaderUtil.createProgram(mVertexShader[i], mFragmentShader[i]);
		}
	}
	//���ﷵ��������shader
	public static int getTrangleShaderProgram()
	{
		return program[0];
	}
}
