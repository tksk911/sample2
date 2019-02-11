package com.sample2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.services.youtube.model.SearchResult;
import com.sample2.model.MovieSearchResultModel;
import com.sample2.service.SearchMovieService;

@Controller
public class SearchPageConrtoller {

	@GetMapping("/userpage")
	public String openSearch() {
		return ("/userpage/search");
	}
	
	@Autowired
	SearchMovieService searchMovieService;
	
	@PostMapping("search")
	public String searchMovie(@RequestParam("searchword")String searchWord,ModelMap modelMap){
		
		//API操作serviceを実行	
		List<SearchResult> searchResultList = searchMovieService.executeApi(searchWord);
		List<MovieSearchResultModel> resultList = new ArrayList<>();
		//Map<Integer,MovieSearchResultModel> resultMap = new HashMap<Integer,MovieSearchResultModel>();
		
		if(resultList ==null){
			//modelMap.addAttribute("error", error);
		}
		
		Integer key = 0;
		
		for(int i=0;i<searchResultList.size();i++) {
			
			MovieSearchResultModel movieSearchResultModel = new MovieSearchResultModel();
			SearchResult resultEle = searchResultList.get(i);
			movieSearchResultModel.setTitle(resultEle.getSnippet().getTitle());
			movieSearchResultModel.setThumbnails(resultEle.getSnippet().getThumbnails().get("default").getUrl());
			movieSearchResultModel.setKey(key.toString());
			resultList.add(movieSearchResultModel);
			//resultMap.put(key, movieSearchResultModel);
			key++;
		}
		
		modelMap.addAttribute("resultList",resultList);
		//modelMap.addAttribute("resultMap",resultMap);
		return "/userpage/search";
	}
	
	@PostMapping("regist")
	public String registMovie(@RequestParam("key")List<String> key) {
		
		for(String test:key) {
			System.out.println(test);
		}
		
		//System.out.println();
		return "/top";
	}
}