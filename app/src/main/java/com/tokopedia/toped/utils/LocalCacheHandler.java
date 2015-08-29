package com.tokopedia.toped.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalCacheHandler {
	
	private Editor editor;
	private SharedPreferences sharedPrefs;
	
	public LocalCacheHandler (Context context, String name) {
		sharedPrefs  = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		editor  = sharedPrefs.edit();
	}
	
	public void putString(String key, String value) {
		editor.putString(key, value);
	}
	
	public void putArrayString(String key, String[] value){
		try {
			if(value != null){
				String temp = "";
//				for(int i = 0; i<value.length; i++){
//					System.out.println("Magic value " + value[i]);
//					if(!value[i].equals("null")){
//						if(i == 0){
//							temp = value[i];
//						}
//						else{
//							temp = temp + "#separator#" + value[i];
//						}
//					}
//				}
				for(String string : value){
					if(string != null)
						if(!string.equals("null"))
							if(temp.length() == 0){
								temp = string;
							}
							else{
								temp = temp + "#separator#" + string;
							}
				}
				if(temp.length()>0)
					editor.putString(key, temp);
			}
			else
				System.out.println("Magic Checked as null");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getArrayString(String key){
		String[] temp = null;
		String tmp = sharedPrefs.getString(key, null);
		if(tmp!=null)
			temp = tmp.split("#separator#");
		return temp;
	}
	
	public void putInt(String key, int value) {
		editor.putInt(key, value);
	}
	
	public void putFloat(String key, float value) {
		editor.putFloat(key, value);
	}
	
	public void putBoolean(String key, Boolean value) {
		editor.putBoolean(key, value);
	}
	
	public void putLong(String key, Long value) {
		editor.putLong(key, value);
	}
	
	public void putArrayListString(String key, ArrayList<String> value) {
		for (int i = 0; i < value.size(); i++) {
			editor.putString(key+i, value.get(i));
		}
		editor.putInt(key+"_total", value.size());
	}
	
	public void putArrayListBoolean(String key, ArrayList<Boolean> value){
		for (int i = 0; i < value.size(); i++){
			editor.putBoolean(key+i, value.get(i));
		}
		editor.putInt(key+"_total", value.size());
	}
	
	public void putArrayListInteger(String key, ArrayList<Integer> value) {
		for (int i = 0; i < value.size(); i++) {
			editor.putInt(key+i, value.get(i));
		}
		editor.putInt(key+"_total", value.size());
	}
	
	public void putArrayListLong(String key, ArrayList<Long> value) {
		for (int i = 0; i < value.size(); i++) {
			editor.putLong(key+i, value.get(i));
		}
		editor.putInt(key+"_total", value.size());
	}

	
	public void commitEditor () {
		editor.commit();
	}
	
	public String getString(String key) {
		return sharedPrefs.getString(key, null);
	}
	
	public String getString(String key, String defValue) {
		return sharedPrefs.getString(key, defValue);
	}
	
	public Long getLong(String key) {
		return sharedPrefs.getLong(key, 0);
	}
	
	public Long getLong(String key, Long defVal) {
		return sharedPrefs.getLong(key, defVal);
	}
	
	public Long getLong(String key, int defVal) {
		return sharedPrefs.getLong(key, defVal);
	}
	
	public Integer getInt(String key) {
		return sharedPrefs.getInt(key, -1);
	}
	
	public Integer getInt(String key, int defVal) {
		return sharedPrefs.getInt(key, defVal);
	}
	
	public float getFloat(String key) {
		return sharedPrefs.getFloat(key, 0f);
	}
	
	public float getFloat(String key, float defVal) {
		return sharedPrefs.getFloat(key, defVal);
	}
	
	public Boolean getBoolean(String key) {
		return sharedPrefs.getBoolean(key, false);
	}
	
	public Boolean getBoolean(String key, boolean defValue) {
		return sharedPrefs.getBoolean(key, defValue);
	}
	
	public ArrayList<String> getArrayListString(String key) {
		int total = sharedPrefs.getInt(key+"_total", 0);
		ArrayList<String> value = new ArrayList<String>();
		for (int i = 0; i < total; i++) {
			value.add(getString(key+i));
		}
		return value;
	}
	
	public ArrayList<Integer> getArrayListInteger(String key) {
		int total = sharedPrefs.getInt(key+"_total", 0);
		ArrayList<Integer> value = new ArrayList<Integer>();
		for (int i = 0; i < total; i++) {
			value.add(getInt(key+i));
		}
		return value;
	}
	
	public ArrayList<Boolean> getArrayListBoolean(String key){
		int total = sharedPrefs.getInt(key+"_total", 0);
		ArrayList<Boolean> value = new ArrayList<Boolean>();
		for(int i = 0; i < total; i++){
			value.add(getBoolean(key+i));
		}
		return value;
	}
	
	public ArrayList<Long> getArrayListLong(String key) {
		int total = sharedPrefs.getInt(key+"_total", 0);
		ArrayList<Long> value = new ArrayList<Long>();
		for (int i = 0; i < total; i++) {
			value.add(getLong(key+i));
		}
		return value;
	}

	
	public void setExpire(int time) {
		putInt("expired_time", time);
		Long curr_time = System.currentTimeMillis()/1000;
		putLong("timestamp", curr_time);
		commitEditor();
	}
	
	public Boolean isExpired() {
		int interval = getInt("expired_time");
		Long time = getLong("timestamp");
		Long curr_time = System.currentTimeMillis()/1000;
		if ((curr_time - time) > interval) return true;
		return false;
	}
	
	public static void clearCache (Context context, String name) {
		SharedPreferences sharedPrefs  = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sharedPrefs.edit().clear().commit();
	}
	
	public static void clearSingleCacheKey(Context context, String prefName, String keyName){
		SharedPreferences sharedPrefs  = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		sharedPrefs.edit().remove(keyName).commit();
	}
	
	public int getSingleArrayListInteger(String key, int index) {
		ArrayList<Integer> value = getArrayListInteger(key);
		if (index < value.size()) {
			return value.get(index);
		} 
		return -1;
	}
	
	public Boolean modifyArrayListInteger(String key, int index, int newValue) {
		ArrayList<Integer> value = getArrayListInteger(key);
		if (index < value.size()) {
			value.set(index, newValue);
			putArrayListInteger(key, value);
			commitEditor();
			return true;
		} 
		return false;
	}
	
}
