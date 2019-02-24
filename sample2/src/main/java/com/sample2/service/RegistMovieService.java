package com.sample2.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sample2.model.MovieRegistModel;

@Service
public class RegistMovieService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	private final int BATCH_SIZE=50;
	private final String sql ="insert into movie_list_data (title,user_id,thumbnails) values(?,?,?)";
	
	public void registMovie(List<String> titthum) {
		
		List<MovieRegistModel> registList = new ArrayList<>();
		
		//a 改行コードで分割してリストを一つに
		for(int i=0;i<titthum.size();i++) {
			MovieRegistModel movieRegistModel = new MovieRegistModel();
			String[] splited = titthum.get(i).split("\n",2);
			movieRegistModel.setTitle(splited[0]);
			movieRegistModel.setThumbnails(splited[1]);
			registList.add(movieRegistModel);
		}

		for(int i=0;i<registList.size();i+=BATCH_SIZE) {

			//a バッチインサートのサイズごとに分ける(バッチサイズより大きければバッチサイズに区切る)
			List<MovieRegistModel> batRegistList = registList.subList(i,i+BATCH_SIZE>registList.size()?registList.size():i+BATCH_SIZE);
			
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
						
				@Override
				public void setValues(PreparedStatement ps, int j) throws SQLException {
					// TODO Auto-generated method stub
					MovieRegistModel regist = batRegistList.get(j);
					ps.setString(1, regist.getTitle());
					ps.setString(2, regist.getUserId());
					ps.setString(3, regist.getThumbnails());
				}
				
				@Override
				public int getBatchSize() {
					// TODO Auto-generated method stub
					return batRegistList.size();
				}
			});
		}
		
	}

}
