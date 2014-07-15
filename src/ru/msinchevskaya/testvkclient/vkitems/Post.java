package ru.msinchevskaya.testvkclient.vkitems;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Post extends VkItem implements Parcelable{
	
	//ќб€зательные параметры
	private String id;
	private String fromId; //userId
	private String text;
	private int likes;
	private int reposts;
	private int comments;
	private String date;
	private List<String> photoUrl = new ArrayList<String>();
	private Post post; //может содержать пост, если это репост
	private String shortText; //“екст до первого переноса строки
	
	
	public String getId(){
		return id;
	}
	
	public String getFromId(){
		return fromId;
	}
	
	public String getText(){
		return text;
	}
	
	public int getLikes(){
		return likes;
	}
	
	public int getReposts(){
		return reposts;
	}
	
	public int getComments(){
		return comments;
	}
	
	public String getDate(){
		return date;
	}
	
	public static class Builder { 
		private String id;
		private String fromId; //userId
		private String text;
		private int likes;
		private int reposts;
		private int comments;
		private long dateInSec; //в секундах

		
		//ќпциональные параметры
		private List<String> photoUrl = new ArrayList<String>();
		private Post post; //может содержать пост, если это репост
		
		//—лужебные параметры
		private String shortText; //“екст до первого переноса строки
		
		public Builder(String id, 
				String fromId, 
				String text,
				int likes, 
				int reposts, 
				int comments,
				long dateInSec){
			this.id = id;
			this.fromId = fromId;
			this.text = text;
			this.shortText = text;
			this.likes = likes;
			this.reposts = reposts;
			this.comments = comments;
			this.dateInSec = dateInSec;
		}
		
		public Builder listPhoto(List<String> photoUrl){
			this.photoUrl.addAll(photoUrl);
			return this;
		}
		
		public Builder post(Post post){
			this.post = post;
			return this;
		}
		
		public Post build(){
			return new Post(this);
		}
	}
	
	private Post(Builder builder){
		this.id = builder.id;
		this.fromId = builder.fromId;
		this.text = builder.text;
		this.shortText = builder.text;
		this.likes = builder.likes;
		this.reposts = builder.reposts;
		this.comments = builder.comments;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		Date postDate = new Date(builder.dateInSec * 1000);
		this.date = sdf.format(postDate);
		this.photoUrl.addAll(builder.photoUrl);
		this.post = builder.post;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(fromId);
		dest.writeString(text);
		dest.writeString(shortText);
		dest.writeInt(likes);
		dest.writeInt(comments);
		dest.writeInt(reposts);
		dest.writeString(date);
		dest.writeStringList(photoUrl);
		dest.writeParcelable(post, PARCELABLE_WRITE_RETURN_VALUE);
	}
}
