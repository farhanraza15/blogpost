package com.devskiller.tasks.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent
	 *         first
	 */
	public List<Comment> getCommentsForPost(Long postId) {
//		throw new UnsupportedOperationException(/*TODO*/);		
		List<Long> ids = new ArrayList<>();
		ids.add(postId);

		return commentRepository.findAllById(ids);

	}

	/**
	 * Creates a new comment
	 *
	 * @param postId        id of the post
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if postId is null or there is no blog post
	 *                                  for passed postId
	 */
	public Long addComment(Long postId, Comment comment) {
//		throw new UnsupportedOperationException(/* TODO */);

		PostDto postDto = postRepository.findById(postId)
				.map(post -> new PostDto(post.getTitle(), post.getContent(), post.getCreationDate())).orElse(null);

		if (null == postDto) {
			return null;
		}

		commentRepository.save(comment);
		return comment.getId();

	}
}
