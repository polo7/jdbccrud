package dev.lesechko.jdbccrud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import dev.lesechko.jdbccrud.repository.PostRepository;
import dev.lesechko.jdbccrud.repository.jdbc.JdbcPostRepositoryImpl;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Post;


public class PostServiceTest {
    private static Post correctPost = null;
    PostRepository postRepository = Mockito.mock(JdbcPostRepositoryImpl.class);
    PostService postServiceUnderTest = new PostService(postRepository);

    @BeforeAll
    static void init() {
        correctPost = new Post();
        correctPost.setId(1L);
        correctPost.setTitle("Correct Post");
        correctPost.setContent("Content");
        correctPost.setStatus(Status.ACTIVE);
    }

    @Test
    void shouldSaveTest() {
        when(postRepository.save(any())).thenReturn(correctPost);
        assertEquals(correctPost, postServiceUnderTest.save(correctPost));
    }

    @Test
    void shouldGetByCorrectIdTest() {
        when(postRepository.getById(1L)).thenReturn(correctPost);
        assertEquals(correctPost, postRepository.getById(1L));
    }
}
