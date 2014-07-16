package ru.msinchevskaya.testvkclient.vkitems;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Post extends VkItem implements Parcelable{
	

	
	//Обязательные параметры
	private String id;
	private String fromId; //userId
	private String text;
	private int likes;
	private int reposts;
	private int comments;
	private String date;
	private List<String> photoUrl = new ArrayList<String>();
	private Post post; //может содержать пост, если это репост
	private String shortText; //Текст до первого переноса строки
	
	
	public String getId(){
		return id;
	}
	
	public String getFromId(){
		return fromId;
	}
	
	public String getText(){
		return text;
	}
	
	public String getShortText(){
		return shortText;
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
	
	public List<String> getListPhotos(){
		return photoUrl;
	}
	
	public Post getPost(){
		return post;
	}
	
	public static class Builder { 
		private String id;
		private String fromId; //userId
		private String text;
		private int likes;
		private int reposts;
		private int comments;
		private long dateInSec; //в секундах

		
		//Опциональные параметры
		private List<String> photoUrl = new ArrayList<String>();
		private Post post; //может содержать пост, если это репост
		
		//Служебные параметры
		
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
		this.shortText = builder.text.split("\n")[0];
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
	
	  public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
		    // распаковываем объект из Parcel
		    public Post createFromParcel(Parcel in) {
		      return new Post(in);
		    }

		    public Post[] newArray(int size) {
		      return new Post[size];
		    }
		  };
		  
			private Post(Parcel in){
				this.id = in.readString();
				this.fromId = in.readString();
				this.text = in.readString();
				this.shortText = in.readString();
				this.likes = in.readInt();
				this.reposts = in.readInt();
				this.comments = in.readInt();
				this.date = in.readString();
				in.readStringList(this.photoUrl);
				this.post = in.readParcelable(new ClassLoader(){});
			}

}
