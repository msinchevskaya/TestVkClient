package ru.msinchevskaya.testvkclient.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.msinchevskaya.testvkclient.post.PostController;
import ru.msinchevskaya.testvkclient.vkitems.Post;
import ru.msinchevskaya.testvkclient.vkitems.User;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;

public final class JSONParser {
	
	private JSONParser(){}
	
	static List<VkItem> parseUser(JSONObject jsonObject) throws JSONException{
		List<VkItem> listUser = new ArrayList<VkItem>();
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
			return listUser;
	}
	
	static List<VkItem> parsePosts(JSONObject jsonObject) throws JSONException {
		List<VkItem> listPost = new ArrayList<VkItem>();
		JSONObject object = jsonObject.getJSONObject("response");
		int count = object.getInt("count");
		
		PostController.setTotalPost(count);
		
		JSONArray items = object.getJSONArray("items");
		for (int i = 0; i < items.length(); i++){
			JSONObject obj = items.getJSONObject(i);
			String id = obj.getString("id");
			String fromId = obj.getString("from_id");
			String text = obj.getString("text");
			JSONObject likesObj = obj.getJSONObject("likes");
			int likes = likesObj.getInt("count");
			JSONObject repostsObj = obj.getJSONObject("reposts");
			int reposts = repostsObj.getInt("count");
			JSONObject commentsObj = obj.getJSONObject("comments");
			int comments = commentsObj.getInt("count");
			long dateInMillis = obj.getLong("date");
			List<String> photoUrl = new ArrayList<String>();
			if (obj.toString().contains("attachments") && !obj.toString().contains("copy_history")){
				JSONArray attachements = obj.getJSONArray("attachments");
				for (int k = 0; k < attachements.length(); k++){
					JSONObject attachObj = attachements.getJSONObject(k);
					String type = attachObj.getString("type");
					if (type.equals("photo")){
						JSONObject photo = attachObj.getJSONObject("photo");
						String photo604 = photo.getString("photo_604");
						photoUrl.add(photo604);
					}
				}
			}
		
			Post post = new Post.Builder(id, fromId, text, likes, reposts, comments, dateInMillis).listPhoto(photoUrl)
					.build();
			listPost.add(post);
		}
		return listPost;
	}
}
