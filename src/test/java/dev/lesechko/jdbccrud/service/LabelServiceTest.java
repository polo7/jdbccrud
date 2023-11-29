package dev.lesechko.jdbccrud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import dev.lesechko.jdbccrud.repository.LabelRepository;
import dev.lesechko.jdbccrud.repository.LabelRepositoryImpl;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Label;

class LabelServiceTest {
    private static Label correctLabel = null;
    private LabelRepository repository = Mockito.mock(LabelRepositoryImpl.class);
    private LabelService labelServiceUnderTest = new LabelService(repository);

    @BeforeAll
    static void init() {
        correctLabel = new Label();
        correctLabel.setName("Correct Label");
        correctLabel.setStatus(Status.ACTIVE);
    }

    @Test
    void shouldSaveTest() {
        when(repository.save(any())).thenReturn(true);
        assertTrue(labelServiceUnderTest.save(correctLabel));
    }

    @Test
    void shouldNotSaveNullTest() {
        assertFalse(labelServiceUnderTest.save(null));
    }

    @Test
    void shouldGetByCorrectIdTest() {
        correctLabel = new Label();
        correctLabel.setId(1L);
        when(labelServiceUnderTest.getById(1L)).thenReturn(correctLabel);
        assertAll("label",
                () -> assertEquals(1,labelServiceUnderTest.getById(1L).getId()),
                () -> assertEquals("Test",labelServiceUnderTest.getById(1L).getName()),
                () -> assertEquals(Status.ACTIVE,labelServiceUnderTest.getById(1L).getStatus())
        );
    }

    @Test
    void shouldNotGetByWrongIdTest() {
        when(labelServiceUnderTest.getById(777L)).thenReturn(null);
        assertNull(labelServiceUnderTest.getById(777L));
    }

    @Test
    void shouldUpdateLabelTest() {
        correctLabel = new Label();
        correctLabel.setId(1L);
        correctLabel.setName("Test");
        correctLabel.setStatus(Status.ACTIVE);
        when(labelServiceUnderTest.update(any())).thenReturn(true);
        assertTrue(labelServiceUnderTest.update(correctLabel));
    }

    @Test
    void shouldGetAllTest() {
        when(labelServiceUnderTest.getAll()).thenReturn(new ArrayList<>());
        assertTrue(labelServiceUnderTest.getAll() instanceof List);
    }
}
