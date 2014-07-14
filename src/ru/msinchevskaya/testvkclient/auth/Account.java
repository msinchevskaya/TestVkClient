package ru.msinchevskaya.testvkclient.auth;

import ru.msinchevskaya.testvkclient.R;
import ru.msinchevskaya.testvkclient.vkitems.User;
import android.content.Context;
import android.content.SharedPreferences;

public class Account {
	
	private static Account mInstance;
	
	private Context mContext;
	private String accessToken;
	private int expiresIn;
	private String userId;
	private User mUser;
	
	private Account(Context context){
		mContext = context;
        //Получаем данные из настроек - по умолчанию пустая строка
		SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
		accessToken = prefs.getString("ACCESS_TOKEN", "");
		expiresIn = prefs.getInt("EXPIRES_IN", 0);
		userId = prefs.getString("USER_ID", "");
		
		String firstName = prefs.getString("FIRST_NAME", "");
		String lastName = prefs.getString("LAST_NAME", "");
		String photoUrl = prefs.getString("PHOTO_URL", "");
		
		mUser = new User(userId, firstName, lastName, photoUrl);
	}
	
	public boolean isInit(){
		if (!"".equals(accessToken))
			return true;
		return false;
	}
	
	public static Account getInstance(Context context){
		if (mInstance == null)
			mInstance = new Account(context.getApplicationContext());
		return mInstance;
	}
	
	public String getAccessToken(){
		return accessToken;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public int getExpiresIn(){
		return expiresIn;
	}
	
	public User getUser(){
		return mUser;
	}
	
	public void saveAccountParams(String accessToken, int expiresIn, String userId ){
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.userId = userId;
		
        //Сохраняем параметры аккаунта
		savePrefs();
	}
	
	public void setUser(User user){
		if (user != null)
			mUser = user;
        //Сохраняем параметры пользователя
		SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.shared_preferences), Context.MODE_PRIVATE).edit();
		editor.putString("FIRST_NAME", mUser.getFirstName());
		editor.putString("LAST_NAME", mUser.getLastName());
		editor.putString("PHOTO_URL", mUser.getPhotoUrl());
		editor.commit();
	}
	
	public void removeAccount(){
		this.accessToken = "";
		this.expiresIn = 0;
		this.userId = "";	
		mInstance = null;
		savePrefs();
	}
	
	private void savePrefs(){
		SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.shared_preferences), Context.MODE_PRIVATE).edit();
		editor.putString("ACCESS_TOKEN", accessToken);
		editor.putInt("EXPIRES_IN", expiresIn);
		editor.putString("USER_ID", userId);
		editor.commit();
	}

}
