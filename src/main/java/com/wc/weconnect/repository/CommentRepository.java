package com.wc.weconnect.repository;

import com.wc.weconnect.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
}
