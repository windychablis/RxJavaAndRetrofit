package com.lilosoft.outsidescreen.common;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
	
	public static long  getCurrentTime(){
		Date date = new Date();
		return date.getTime();
	}
	
	public static String getProssessTime(long t1, long t2){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ((t2-t1)/1000)+1+"";
	}
	
	/**
	 * ��ȡͼƬ���ű���
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,   
	        int reqWidth, int reqHeight) {   
	    // ԴͼƬ�ĸ߶ȺͿ��  
	    final int height = options.outHeight;   
	    final int width = options.outWidth;   
	    int inSampleSize = 1;   
	    if (height > reqHeight || width > reqWidth) {   
	        // �����ʵ�ʿ�ߺ�Ŀ���ߵı���  
	        final int heightRatio = Math.round((float) height / (float) reqHeight);   
	        final int widthRatio = Math.round((float) width / (float) reqWidth);   
	        // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�  
	        // һ��������ڵ���Ŀ��Ŀ�͸ߡ�  
	        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;   
	    }   
	    return inSampleSize;   
	}
	
	public static int dip2px(Context context,float dipValue){
	     final float scale=context.getResources().getDisplayMetrics().density;
	     return (int)(dipValue*scale+0.5f);
	}

	public static int px2dp(Context context,float pxValue){
	    final float scale = context.getResources().getDisplayMetrics().density; 
	    return (int)(pxValue/scale+0.5f); 
	}
	
	public static void saveLog(String time, String strs){
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); 
		if(sdCardExist){
			try {
				String path = Environment.getExternalStorageDirectory().toString();
				File file = new File(path+"/DBSLog.txt");
				if(!file.exists()){
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
//				String info = time+","+strs[1]+","+strs[2]+"\n";
				String info = time+","+strs+"\n";
				bw.write(info);
				bw.flush();
				
				fw.close();
				bw.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
	
}
