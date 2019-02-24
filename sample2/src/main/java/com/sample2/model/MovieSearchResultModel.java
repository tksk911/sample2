package com.sample2.model;

public class MovieSearchResultModel {

	private String kind;
	private String title;
	private String thumbnails;
	private StringBuilder titthum = new StringBuilder();
	//private Map<String, String> movieMap = new HashMap<>();
	//private String key;
	//public String key;
	
	public String getKind() {
		return kind;
	}
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getThumbnails() {
		return thumbnails;
	}
	
	public void setThumbnails(String thumbnails) {
		this.thumbnails = thumbnails;
	}
	
	public void setTitthum(String title,String thumbnails) {
		this.titthum.append(title);
		this.titthum.append("\n");
		this.titthum.append(thumbnails);
	}
	
	public StringBuilder getTitthum() {
		return titthum;
	}
	
//	public Map<String, String> getMovieMap(){
//		return movieMap;
//	}
//	
//	public void setMovieMap(String title,String thumbnails) {
//		this.movieMap.put(thumbnails, title);
//	}
//	
//	public String getkey() {
//		return key;
//	}
//	
//	public void setKey(String key) {
//		this.key = key;
//	}

}
