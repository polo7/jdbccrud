package dev.lesechko.jdbccrud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import dev.lesechko.jdbccrud.repository.PostRepository;
import dev.lesechko.jdbccrud.repository.PostRepositoryImpl;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Post;


public class PostServiceTest {
    private static Post correctPost = null;
    PostRepository postRepository = Mockito.mock(PostRepositoryImpl.class);
    PostService postServiceUnderTest = new PostService(postRepository);

    @BeforeAll
    static void init() {
        correctPost = new Post();
//        correctPost.setId(1L);
        correctPost.setTitle("Correct Post");
        correctPost.setContent("Content");
        correctPost.setStatus(Status.ACTIVE);
    }

    @Test
    void shouldSaveTest() {
        when(postRepository.save(any())).thenReturn(true);
        assertTrue(postServiceUnderTest.save(correctPost));
    }

    @Test
    void shouldGetByCorrectIdTest() {

    }
}
