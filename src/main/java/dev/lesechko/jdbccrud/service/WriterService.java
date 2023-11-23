package dev.lesechko.jdbccrud.service;

import dev.lesechko.jdbccrud.model.Writer;
import dev.lesechko.jdbccrud.repository.WriterRepository;
import dev.lesechko.jdbccrud.repository.WriterRepositoryImpl;

public class WriterService {
    WriterRepository writerRepository = new WriterRepositoryImpl();

    public boolean save(Writer writer) {
        return writerRepository.save(writer);
    }
}
