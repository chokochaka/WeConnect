package com.wc.weconnect.Controller;

import com.wc.weconnect.models.Reels;
import com.wc.weconnect.models.User;
import com.wc.weconnect.service.ReelsService;
import com.wc.weconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController {
	
	@Autowired
	private ReelsService reelsService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/api/reels")
	public Reels createReels(@RequestBody Reels reel, @RequestHeader("Authorization")String jwt) {
		
		// from RequestHeader we find token and from token we have to find user
		User reqUser = userService.findUserByJwt(jwt);
		Reels createdReels = reelsService.createReels(reel, reqUser);
		
		return createdReels;		
	}
	
	@GetMapping("/api/reels")
	public List<Reels> findAllReels() {
	
		List<Reels> reels = reelsService.findAllReels(); 
		
		return reels;	
	}
	
	@GetMapping("/api/reels/user/{userId}")
	public List<Reels> findUsersReels(@PathVariable Integer userId) throws Exception {
	
		List<Reels> reels = reelsService.findUserReels(userId); 
		
		return reels;	
	}

}
