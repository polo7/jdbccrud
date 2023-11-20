package dev.lesechko.jdbccrud.repository;

import dev.lesechko.jdbccrud.model.Post;

import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public List<Post> getAll() {
//        SELECT * FROM posts LEFT JOIN post_labels ON post_labels.postId = posts.id LEFT JOIN labels ON post_labels.labelId = labels.id;
        return null;
    }

    @Override
    public Post getById(Long aLong) {
        return null;
    }

    @Override
    public boolean save(Post post) {
        return false;
    }

    @Override
    public boolean update(Post post) {
        return false;
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
