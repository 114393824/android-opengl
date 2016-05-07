package com.bn.Sample13_3;
import java.util.ArrayList;
import java.util.Collections;

import android.opengl.GLES20;

import static com.bn.Sample13_3.Constant.*;
//��ʾ���Ŀ�����,���ڿ������е���
public class TreesForControl 
{
	float height;
	//�洢���������б�
    ArrayList<TreeTrunkControl> treeTrunkList=new ArrayList<TreeTrunkControl>();
    ArrayList<TreeLeavesControl> treeLeavesList=new ArrayList<TreeLeavesControl>();
    public TreesForControl(TreeTrunk treeTrunk,TreeLeaves[] treeLeaves)
    {
    	//ɨ���ͼ���ɸ���λ�õ�Ҭ����
    	for(int i=0;i<MAP_TREE.length;i++)
    	{
    		//�������е�����
			treeTrunkList.add(new TreeTrunkControl(MAP_TREE[i][0],MAP_TREE[i][1],MAP_TREE[i][2],treeTrunk));
			//�������е���Ҷ
			for(TreeLeaves tempLeaves:treeLeaves)
			{
				treeLeavesList.add(new TreeLeavesControl(MAP_TREE[i][0],MAP_TREE[i][1],MAP_TREE[i][2],tempLeaves));
			}
    	}
    }
    //�����б��е�ÿһ����
    public void drawSelf(int tex_leavesId,int tex_treejointId,float bend_R,float wind_direction)
    {
    	//��������
    	for(TreeTrunkControl tempTrunk:treeTrunkList)
    	{
    		tempTrunk.drawSelf(tex_treejointId, bend_R, wind_direction);
    	}
    	//�����е�Ҷ�ӽ�������
    	Collections.sort(treeLeavesList);
    	//������� 
        GLES20.glEnable(GLES20.GL_BLEND);
        //���û������
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        //�رձ������
        GLES20.glDisable(GLES20.GL_CULL_FACE);
    	//�������е�Ҷ��
    	for(TreeLeavesControl tempLeaves:treeLeavesList)
    	{
    		tempLeaves.drawSelf(tex_leavesId,bend_R,wind_direction);
    	}
    	//�򿪱������
		GLES20.glEnable(GLES20.GL_CULL_FACE);
    	GLES20.glDisable(GLES20.GL_BLEND); 
    }
}
