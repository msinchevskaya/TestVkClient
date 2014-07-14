package ru.msinchevskaya.testvkclient.utils;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;

import ru.msinchevskaya.testvkclient.auth.Account;
import ru.msinchevskaya.testvkclient.vkitems.VkItem;

public class VkItemLoader {
	
	private static VkItemLoader mInstance;
	private Context mContext;
	
	private VkItemLoader(Context context){
		mContext = context;
	}
	
	public static VkItemLoader getInstance(Context context){
		if (mInstance == null)
			mInstance = new VkItemLoader(context.getApplicationContext());
		return mInstance;
	}
	
	public interface IVkItemLoadListener{
		public void loadingSuccess(List<VkItem> item);
		public void loadingError(String message);
	}
	
	public void loadPost(final int ofset, final int count, final IVkItemLoadListener listener){
		RequestQueue queue = Volley.newRequestQueue(mContext);
//		String url = "https://api.vk.com/method/wall.get?owner_id=" 
//			+ Account.getInstance(mContext).getUserId() +
//			"&v=5.2";
		
		String url = "https://api.vk.com/method/users.get?user_ids=" 
				+ Account.getInstance(mContext).getUserId() +
				"&fields=photo_50&&v=5.2";
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

			  @Override
			  public void onResponse(JSONObject response) {
				  try {
					JSONParser.parseUser(response);
					listener.loadingSuccess(JSONParser.parseUser(response));
				} catch (JSONException e) {
					listener.loadingError(e.getLocalizedMessage());
				}
					
			  }
			 }, new Response.ErrorListener() {

			  @Override
			  public void onErrorResponse(VolleyError error) {
				  listener.loadingError(error.getLocalizedMessage());
			  }
			 });
		
		queue.add(jsObjRequest);
		
	}

}
