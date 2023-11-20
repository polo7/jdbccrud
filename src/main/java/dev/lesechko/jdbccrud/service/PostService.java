package dev.lesechko.jdbccrud.service;

import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.repository.PostRepository;
import dev.lesechko.jdbccrud.repository.PostRepositoryImpl;


public class PostService {
    private final PostRepository postRepository = new PostRepositoryImpl();

    public boolean save(Post postToSave) {
        return postRepository.save(postToSave);
    }
}
