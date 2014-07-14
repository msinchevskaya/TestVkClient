package ru.msinchevskaya.testvkclient.vkitems;

import java.util.Date;
import java.util.List;


public class Post extends VkItem{
	
	//Обязательные параметры
	private String id;
	private String fromId; //userId
	private String text;
	private int likes;
	private int reposts;
	private int comments;
	private long dateInMillis; //в миллисекундах

	
	//Опциональные параметры
	private List<String> photoUrl;
	private Post post; //может содержать пост, если это репост
	
	//Служебные параметры
	private String shortText; //Текст до первого переноса строки
	
	public static class Builder { 
		
	}
}
