package com.example.opengl;

import android.opengl.GLES20;

/**
 * 加载顶点Shader与片元Shader的工具类
 * @author tyger
 *
 */
public class ShaderUtil {
	//加载制定shader的方法
	public static int loadShader(
			int shaderType, //shader的类型  GLES20.GL_VERTEX_SHADER(顶点)   GLES20.GL_FRAGMENT_SHADER(片元)
			String source   //shader的脚本字符串
	){
		//创建一个新shader
		int shader = GLES20.glCreateShader(shaderType);
		return shader;
	}
}
