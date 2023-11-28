package dev.lesechko.jdbccrud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.lesechko.jdbccrud.repository.LabelRepository;
import dev.lesechko.jdbccrud.repository.LabelRepositoryImpl;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Label;


class LabelServiceTest {
    private Label label = null;
    //    private LabelService labelService = new LabelService();
    private LabelRepository repository = Mockito.mock(LabelRepositoryImpl.class);
    private LabelService labelServiceUnderTest = new LabelService(repository);

    @Test
    void shouldSaveTest() {
        label = new Label();
        label.setName("JUnit");
        label.setStatus(Status.ACTIVE);
        when(repository.save(any())).thenReturn(true);
        assertTrue(labelServiceUnderTest.save(label));
    }

    @Test
    void shouldNotSaveNullTest() {
        assertFalse(labelServiceUnderTest.save(null));
    }

    @Test
    void shouldGetByCorrectId() {
        label = new Label();
        label.setId(1L);
        label.setName("Test");
        label.setStatus(Status.ACTIVE);
        when(labelServiceUnderTest.getById(1L)).thenReturn(label);
        assertEquals(1,labelServiceUnderTest.getById(1L).getId());
        assertEquals("Test",labelServiceUnderTest.getById(1L).getName());
        assertEquals(Status.ACTIVE,labelServiceUnderTest.getById(1L).getStatus());
    }

    @Test
    void shouldNotGetByWrongId() {
        when(labelServiceUnderTest.getById(777L)).thenReturn(null);
        assertNull(labelServiceUnderTest.getById(777L));
    }

    void shouldUpdateLabel() {
        when(labelServiceUnderTest.update())
    }

}
