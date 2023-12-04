package dev.lesechko.jdbccrud.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        correctLabel.setId(1L);
        correctLabel.setName("Correct Label");
        correctLabel.setStatus(Status.ACTIVE);
    }

    @Test
    void shouldSaveTest() {
        when(repository.save(any())).thenReturn(correctLabel);
        assertEquals(correctLabel, labelServiceUnderTest.save(correctLabel));
    }

    @Test
    void shouldNotSaveNullTest() {
        assertNull(labelServiceUnderTest.save(null));
    }

    @Test
    void shouldGetByCorrectIdTest() {
        when(repository.getById(1L)).thenReturn(correctLabel);
        assertAll("label",
                () -> assertEquals(1,labelServiceUnderTest.getById(1L).getId()),
                () -> assertEquals("Correct Label",labelServiceUnderTest.getById(1L).getName()),
                () -> assertEquals(Status.ACTIVE,labelServiceUnderTest.getById(1L).getStatus())
        );
    }

    @Test
    void shouldNotGetByWrongIdTest() {
        when(repository.getById(777L)).thenReturn(null);
        assertNull(labelServiceUnderTest.getById(777L));
    }

    @Test
    void shouldUpdateLabelTest() {
        Label updatedLabel = new Label();
        updatedLabel.setId(correctLabel.getId());
        updatedLabel.setName(correctLabel.getName());
        updatedLabel.setStatus(correctLabel.getStatus());
        when(repository.update(any())).thenReturn(updatedLabel);
        assertEquals(updatedLabel, labelServiceUnderTest.update(updatedLabel));
    }

    @Test
    void shouldGetAllTest() {
        when(repository.getAll()).thenReturn(new ArrayList<>());
        assertTrue(labelServiceUnderTest.getAll() instanceof List);
    }
}
