package com.lilosoft.outsidescreen.common;

import android.content.SharedPreferences;

import com.lilosoft.outsidescreen.base.AppContext;


/**
 * SharedPreferences本地数据存储类
 * @author ray
 * @date 2015-03-15
 */
public class PrefUtils {

	private static final String PREF_NAME_CACHE = "com.outsidescreen.cache";
	private static final String PREF_NAME_VARS = "com.outsidescreen.vars";

	public static SharedPreferences cachePrefs() {
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0);
	}

	public static SharedPreferences varsPrefs() {
		return AppContext.get().getSharedPreferences(PREF_NAME_VARS, 0);
	}

	public static void putCacheLoginState(boolean value) {
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putBoolean("loginstate", value).commit();
	}

	public static boolean getCacheLoginState() {
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getBoolean("loginstate", false);
	}
	public static void saveUserInfo(String info){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("userinfo", info).commit();
	}
	public static String getUserInfo(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("userinfo", "");
	}

	public static void saveDeptDuty(String dept){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("dept", dept).commit();
	}
	public static String getDeptDuty(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("dept", "");
	}

	public static void saveNewContext(String dept){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("news", dept).commit();
	}
	public static String getNewContext(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("news", "");
	}

	public static void saveWorkWindow(String window){
		SharedPreferences.Editor editor = AppContext.get()
				.getSharedPreferences(PREF_NAME_CACHE, 0).edit();
		editor.putString("work_window", window).commit();
	}
	public static String getWorkWindow(){
		return AppContext.get().getSharedPreferences(PREF_NAME_CACHE, 0)
				.getString("work_window", "");
	}

}
