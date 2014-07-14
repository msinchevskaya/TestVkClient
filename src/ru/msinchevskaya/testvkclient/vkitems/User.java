package ru.msinchevskaya.testvkclient.vkitems;


public class User extends VkItem{

	private String id;
	private String firstName;
	private String lastName;
	private String photoUrl;
	
	public User(String id, String firstName, String lastName, String photoUrl){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photoUrl = photoUrl;
	}
	
	public String getId(){
		return id;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getPhotoUrl(){
		return photoUrl;
	}
	
	public String getFullName(){
		return firstName + " " + lastName;
	}
}
