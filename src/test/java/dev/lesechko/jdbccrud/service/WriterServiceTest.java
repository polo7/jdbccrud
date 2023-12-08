package dev.lesechko.jdbccrud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import dev.lesechko.jdbccrud.repository.WriterRepository;
import dev.lesechko.jdbccrud.repository.jdbc.JdbcWriterRepositoryImpl;
import dev.lesechko.jdbccrud.model.Writer;
import dev.lesechko.jdbccrud.model.Status;


public class WriterServiceTest {
    private static Writer correctWriter = null;
    WriterRepository writerRepository = Mockito.mock(JdbcWriterRepositoryImpl.class);
    WriterService writerServiceUnderTest = new WriterService(writerRepository);

    @BeforeAll
    static void init() {
        correctWriter = new Writer();
        correctWriter.setId(1L);
        correctWriter.setLastName("Pushkin");
        correctWriter.setFirstName("Alexander");
        correctWriter.setStatus(Status.ACTIVE);
    }

    @Test
    void shouldSaveTest() {
        when(writerRepository.save(any())).thenReturn(correctWriter);
        assertEquals(correctWriter, writerServiceUnderTest.save(correctWriter));
    }

    @Test
    void shouldGetByCorrectIdTest() {
        when(writerRepository.getById(1L)).thenReturn(correctWriter);
        assertEquals(correctWriter, writerRepository.getById(1L));
    }
}
