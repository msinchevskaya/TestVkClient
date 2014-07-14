package ru.msinchevskaya.testvkclient.vkitems;

import java.util.Date;
import java.util.List;

public class Post extends VkItem{
	
	//ќб€зательные параметры
	private final String id;
	private final String fromId; //userId
	private final String text;
	private final int likes;
	private final int reposts;
	private final int comments;
	private final long dateInMillis; //в миллисекундах
	private List<String> photoUrl;
	private Post post; //может содержать пост, если это репост
	private final String shortText; //“екст до первого переноса строки
	
	
	public int getLikes(){
		return likes;
	}
	
	public static class Builder { 
		private String id;
		private String fromId; //userId
		private String text;
		private int likes;
		private int reposts;
		private int comments;
		private long dateInMillis; //в миллисекундах

		
		//ќпциональные параметры
		private List<String> photoUrl;
		private Post post; //может содержать пост, если это репост
		
		//—лужебные параметры
		private String shortText; //“екст до первого переноса строки
		
		public Builder(String id, 
				String fromId, 
				String text,
				int likes, 
				int reposts, 
				int comments,
				long dateInMillis){
			this.id = id;
			this.fromId = id;
			this.text = text;
			this.shortText = text;
			this.likes = likes;
			this.reposts = reposts;
			this.comments = comments;
			this.dateInMillis = dateInMillis;
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
		this.dateInMillis = builder.dateInMillis;
	}
}
