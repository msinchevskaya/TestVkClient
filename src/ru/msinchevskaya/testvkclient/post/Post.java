package ru.msinchevskaya.testvkclient.post;

import java.util.Date;
import java.util.List;

public class Post {
	
	//������������ ���������
	private String id;
	private String fromId; //userId
	private String text;
	private int likes;
	private int reposts;
	private int comments;
	private long dateInMillis; //� �������������

	
	//������������ ���������
	private List<String> photoUrl;
	private Post post; //����� ��������� ����, ���� ��� ������
	
	//��������� ���������
	private String shortText; //����� �� ������� �������� ������
	
	public static class Builder { 
		
	}
}