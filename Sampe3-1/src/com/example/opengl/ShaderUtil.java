package com.example.opengl;

import android.opengl.GLES20;

/**
 * ���ض���Shader��ƬԪShader�Ĺ�����
 * @author tyger
 *
 */
public class ShaderUtil {
	//�����ƶ�shader�ķ���
	public static int loadShader(
			int shaderType, //shader������  GLES20.GL_VERTEX_SHADER(����)   GLES20.GL_FRAGMENT_SHADER(ƬԪ)
			String source   //shader�Ľű��ַ���
	){
		//����һ����shader
		int shader = GLES20.glCreateShader(shaderType);
		return shader;
	}
}