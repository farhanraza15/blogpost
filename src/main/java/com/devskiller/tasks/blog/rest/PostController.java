package com.devskiller.tasks.blog.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.service.CommentService;
import com.devskiller.tasks.blog.service.PostService;

@Controller
@RestController
@RequestMapping("/posts")
public class PostController {

	/*
	 * private final PostService postService;
	 * 
	 * 
	 * public PostController(PostService postService) { this.postService =
	 * postService; }
	 */
	@Autowired
	PostService postService;
	@Autowired
	CommentService commentService;
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PostDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}

	@PostMapping(value = "/{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Comment> postComment(@PathVariable Long id, @Validated @RequestBody Comment comment) {
		
		Long commentId = commentService.addComment(id, comment);
		
		if(null != commentId) {
			return new ResponseEntity<Comment>(comment, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<Comment>(comment, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Comment>> getAllComments(@PathVariable Long id) {
		
		List<Comment> comments = commentService.getCommentsForPost(id);
		
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
	}

}
