package com.wc.weconnect.repository;

import com.wc.weconnect.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Integer>{
	
	// list of story of user
	
	public List<Story> findByUserId(Integer userId);
	
}
