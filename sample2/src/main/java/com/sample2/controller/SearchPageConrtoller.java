package com.sample2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.services.youtube.model.SearchResult;
import com.sample2.model.MovieRegistModel;
import com.sample2.model.MovieSearchResultModel;
import com.sample2.service.RegistMovieService;
import com.sample2.service.SearchMovieService;
import com.sample2.model.MorvieRegistForm;

@Controller
public class SearchPageConrtoller {

	@GetMapping("/userpage")
	public String openSearch() {
		return ("/userpage/search");
	}
	
	@Autowired
	SearchMovieService searchMovieService;
	
	@Autowired
	RegistMovieService registMovieService;
	
	@ModelAttribute
	MorvieRegistForm setUpForm() {
		return new MorvieRegistForm();
	}
	
	@PostMapping("search")
	public String searchMovie(@RequestParam("searchword")String searchWord,ModelMap modelMap){
		
		//API操作serviceを実行	
		List<SearchResult> searchResultList = searchMovieService.executeApi(searchWord);
		List<MovieSearchResultModel> resultList = new ArrayList<>();
		//Map<Integer,MovieSearchResultModel> resultMap = new HashMap<Integer,MovieSearchResultModel>();
		
		if(searchResultList == null){
			//modelMap.addAttribute("error", error);
			return "/userpage/search";
		}
		
		//Integer key = 0;
		
		for(int i=0;i<searchResultList.size();i++) {
			
			MovieSearchResultModel movieSearchResultModel = new MovieSearchResultModel();
			SearchResult resultEle = searchResultList.get(i);
			movieSearchResultModel.setTitle(resultEle.getSnippet().getTitle());
			movieSearchResultModel.setThumbnails(resultEle.getSnippet().getThumbnails().get("default").getUrl());
			movieSearchResultModel.setTitthum(resultEle.getSnippet().getTitle(), resultEle.getSnippet().getThumbnails().get("default").getUrl());
			resultList.add(movieSearchResultModel);
			//resultMap.put(key, movieSearchResultModel);
			//key++;
		}
		
		modelMap.addAttribute("resultList",resultList);
		//modelMap.addAttribute("resultMap",resultMap);
		return "/userpage/search";
	}
	
	@PostMapping("regist")
	public String registMovie(@RequestParam("titthum")List<String> titthum) {
		
		//call regist service
		registMovieService.registMovie(titthum);
		
		return "/top";
	}
}