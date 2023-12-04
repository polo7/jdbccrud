package dev.lesechko.jdbccrud.service;

import dev.lesechko.jdbccrud.model.Writer;
import dev.lesechko.jdbccrud.repository.WriterRepository;
import dev.lesechko.jdbccrud.repository.WriterRepositoryImpl;

import java.util.List;

public class WriterService {
    WriterRepository writerRepository; //= new WriterRepositoryImpl();

    public WriterService() {
        writerRepository = new WriterRepositoryImpl();
    }

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    public Writer getById(Long id) {
        return writerRepository.getById(id);
    }

    public Writer update(Writer writer) {
        return writerRepository.update(writer);
    }

    public boolean deleteById(Long id) {
        return writerRepository.deleteById(id);
    }
}
