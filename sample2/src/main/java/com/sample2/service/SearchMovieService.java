package com.sample2.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Search;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

@Service
public class SearchMovieService {

	private static String PROPERTIES_FILENAME = "youtube.properties";
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final long NUMBER_OF_VIDEOS_RETURN = 25;
	private static YouTube youtube;
	
	public List<SearchResult> executeApi(String searchWord) {
		Properties properties = new Properties();
		
		//youtube.propertiesから認証情報取得
		try {
			InputStream in = Search.class.getResourceAsStream("/"+PROPERTIES_FILENAME);
			properties.load(in);
		}catch(IOException e) {
			System.out.println("File read error");
			System.exit(1);
		}
		
		List<SearchResult> searchResultList = null;
		
		//google API実行
		try {
			youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer(){
			public void initialize(HttpRequest request) throws IOException{}
			}).setApplicationName("sample2").build();
			
			//get search word from browser
			String searchTerm = getSearchTerm(searchWord);
			
			YouTube.Search.List search = youtube.search().list("id,snippet");
			
			//authentification information
			String apiKey = properties.getProperty("youtube.apikey");
			search.setKey(apiKey);
			search.setQ(searchTerm);
			
			//search type set
			search.setType("video");
			
			search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURN);
			SearchListResponse searchResponse = search.execute();
			
			searchResultList = searchResponse.getItems();
			
		}catch(GoogleJsonResponseException e) {
			System.out.println("Google API error");
			System.err.println(e.getDetails());
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println("There was I/O error");
		}catch (Throwable t) {
			// TODO: handle exception
			t.printStackTrace();
		}
		return searchResultList;
	}
	
	//get and check search word
	public String getSearchTerm(String searchWord) {
		
		if(searchWord.length()<1) {
			//if query is empty then return default
			return "YouTube Developers Live";
		}else {
			return searchWord;
		}
	}
}