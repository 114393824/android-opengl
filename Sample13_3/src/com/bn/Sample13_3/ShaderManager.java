package com.bn.Sample13_3;

import android.content.res.Resources;
/*
 * ��shader��������Ҫ�����ڼ���shader�ͱ���shader
 */
public class ShaderManager
{
	final static String[][] shaderName=
	{
		{"vertex_tree.sh","frag_tree.sh"},//Ҭ��������ɫ��
		{"vertex_tex.sh","frag_tex.sh"},//������ͼ����ɫ��
		{"vertex_water.sh","frag_water.sh"},//��ˮ����ɫ��
		{"vertex_landform.sh","frag_landform.sh"},//ɽ����ɫ��
		{"vertex_leaves.sh","frag_leaves.sh"},//Ҷ�ӵ���ɫ��
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
	//���ﷵ�ص���Ҭ����ҡ����shader����
	public static int getTreeWaveShaderProgram()
	{
		return program[0];
	}
	//���ﷵ�ص���������ͼ��shader
	public static int getTextureShaderProgram()
	{
		return program[1];
	}
	//���ﷵ�ص��Ǻ�ˮ��shader
	public static int getWaterShaderProgram()
	{
		return program[2];
	}
	//���ﷵ��ɽ����ɫ��shader
	public static int getLandFormShaderProgram()
	{
		return program[3];
	}
	//���ﷵ��Ҷ�ӵ���ɫ��shader
	public static int getLeavesShaderProgram()
	{
		return program[4];
	}
}
