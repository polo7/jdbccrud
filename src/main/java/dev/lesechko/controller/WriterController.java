package dev.lesechko.controller;

import dev.lesechko.simplecrud.model.Post;
import dev.lesechko.simplecrud.model.Status;
import dev.lesechko.simplecrud.model.Writer;
import dev.lesechko.simplecrud.repository.WriterRepository;
import dev.lesechko.simplecrud.repository.gson.GsonWriterRepositoryImpl;

import java.util.List;


public class WriterController {
    private final WriterRepository writerRepository = new GsonWriterRepositoryImpl();

    public Writer add(String lastName, String firstName, List<Post> posts) {
        Writer newWriter = new Writer();
        newWriter.setLastName(lastName);
        newWriter.setFirstName(firstName);
        newWriter.setPosts(posts);
        newWriter.setStatus(Status.ACTIVE);
        return writerRepository.save(newWriter);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer getById(Long id) {
        return writerRepository.getById(id);
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
        return writerRepository.update(writer) != null;
    }

    public boolean deleteById(Long id) {
        return writerRepository.deleteById(id);
    }
}
