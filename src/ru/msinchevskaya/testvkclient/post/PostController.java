package ru.msinchevskaya.testvkclient.post;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class PostController {
	
	private static int postVisible; //Постов отображено
	private static int totalPost; //Всего постов
	private final static List<String> listPostId = new ArrayList<String>();
	public static final int POST_COUNT = 10; // загрузка по 10 постов
	
	private PostController(){
		
	}
	
	public static int getTotalPost(){
		return totalPost;
	}
	
	public static void setTotalPost(int value){
		totalPost = value;
	}
	
	public static int getPostVisible(){
		return postVisible;
	}
	
	public static void setPostVisible(int value){
		postVisible = value;
	}
	
	public static void addPost(String postId){
		listPostId.add(postId);
	}
	
	public static boolean isPostExist(String postId){
		return listPostId.contains(postId);
	}
}
