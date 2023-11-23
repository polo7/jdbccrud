package dev.lesechko.jdbccrud.controller;

import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Writer;
import dev.lesechko.jdbccrud.service.WriterService;

import java.util.List;


public class WriterController {
    private final WriterService writerService = new WriterService();

    public boolean add(String lastName, String firstName, List<Post> posts) {
        Writer newWriter = new Writer();
        newWriter.setLastName(lastName);
        newWriter.setFirstName(firstName);
        newWriter.setPosts(posts);
        newWriter.setStatus(Status.ACTIVE);
        return writerService.save(newWriter);
    }

    public List<Writer> getAll() {
//        return writerRepository.getAll();
        return null;
    }

    public Writer getById(Long id) {
//        return writerRepository.getById(id);
        return null;
    }

    public boolean update(Writer writer, String newLastName, String newFirstName, List<Post> newPosts, boolean changeStatus) {
        boolean changeLastName = (newLastName != null && !newLastName.isEmpty());
        boolean changeFirstName = (newFirstName != null && !newFirstName.isEmpty());
        boolean changePosts = newPosts != null;

        if (!changeLastName && !changeFirstName && !changePosts && !changeStatus) return false;
        if (changeLastName) writer.setLastName(newLastName);
        if (changeFirstName) writer.setFirstName(newFirstName);
        if (changePosts) writer.setPosts(newPosts);
        if (changeStatus) {
            Status newStatus = (writer.getStatus() == Status.DELETED) ? Status.ACTIVE : Status.DELETED;
            writer.setStatus(newStatus);
        }
//        return writerRepository.update(writer) != null;
        return false;
    }

    public boolean deleteById(Long id) {
//        return writerRepository.deleteById(id);
        return false;
    }
}
