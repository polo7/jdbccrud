package dev.lesechko.controller;

import java.util.List;

import dev.lesechko.simplecrud.model.Label;
import dev.lesechko.simplecrud.model.Post;
import dev.lesechko.simplecrud.model.Status;
import dev.lesechko.simplecrud.repository.PostRepository;
import dev.lesechko.simplecrud.repository.gson.GsonPostRepositoryImpl;


public class PostController {
    private final PostRepository postRepository = new GsonPostRepositoryImpl();

    public Post add(String title, String content, List<Label> labels) {
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setLabels(labels);
        newPost.setStatus(Status.ACTIVE);
        return postRepository.save(newPost);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public boolean update(Post post, String newTitle, String newContent, List<Label> newPostLabels, boolean changeStatus) {
        boolean changeTitle = (newTitle != null && !newTitle.isEmpty());
        boolean changeContent = (newContent != null && !newContent.isEmpty());
        boolean changeLabels = newPostLabels != null;

        if (!changeTitle && !changeContent && !changeLabels && !changeStatus) return false;
        if (changeTitle) post.setTitle(newTitle);
        if (changeContent) post.setContent(newContent);
        if (changeLabels) post.setLabels(newPostLabels);
        if (changeStatus) {
            Status newStatus = (post.getStatus() == Status.DELETED) ? Status.ACTIVE : Status.DELETED;
            post.setStatus(newStatus);
        }
        return postRepository.update(post) != null;
    }

    public boolean deleteById(Long id) {
        return postRepository.deleteById(id);
    }

}
