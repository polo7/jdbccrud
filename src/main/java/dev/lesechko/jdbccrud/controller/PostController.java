package dev.lesechko.jdbccrud.controller;

import java.util.List;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.service.PostService;


public class PostController {
    private final PostService postService = new PostService();

    public boolean add(String title, String content, List<Label> labels) {
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setLabels(labels);
        newPost.setStatus(Status.ACTIVE);
//        return postRepository.save(newPost);
        return postService.save(newPost);
    }

    public List<Post> getAll() {
        return postService.getAll();
    }

    public Post getById(Long id) {
        return postService.getById(id);
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
//        return postRepository.update(post) != null;
        return postService.update(post);
    }

    public boolean deleteById(Long id) {
        return postService.deleteById(id);
    }

}
