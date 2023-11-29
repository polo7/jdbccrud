package dev.lesechko.jdbccrud.service;

import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.repository.PostRepository;
import dev.lesechko.jdbccrud.repository.PostRepositoryImpl;

import java.util.List;


public class PostService {
    private final PostRepository postRepository;

    public PostService() {
        postRepository = new PostRepositoryImpl();
    }

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post postToSave) {
        return postRepository.save(postToSave);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public Post update(Post post) {
        return postRepository.update(post);
    }

    public boolean deleteById(Long id) {
        return postRepository.deleteById(id);
    }
}
