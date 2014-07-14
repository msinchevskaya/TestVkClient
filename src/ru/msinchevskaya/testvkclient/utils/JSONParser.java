package ru.msinchevskaya.testvkclient.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import ru.msinchevskaya.testvkclient.auth.User;

public final class JSONParser {
	
	private JSONParser(){}
	
	public static List<User> parseUser(JSONObject jsonObject){
		List<User> listUser = new ArrayList<User>();
		try {
			Log.i("VKClientTag", jsonObject.toString());
			JSONArray array = jsonObject.getJSONArray("response");
			for (int i = 0; i < array.length(); i++){
				JSONObject obj = array.getJSONObject(i);
				String userId = obj.getString("id");
				String firstName = obj.getString("first_name");
				String lastName = obj.getString("last_name");
				String photoUrl = obj.getString("photo_50");
				User user = new User(userId, firstName, lastName, photoUrl);
				listUser.add(user);
			}

			Log.i("VKClientTag", "user");
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("VKClientTag", "exception");
		}
		return listUser;
	}
}
