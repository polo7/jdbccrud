package dev.lesechko.jdbccrud.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Label;


class LabelServiceTest {
    private Label label = null;
    private LabelService labelService = new LabelService();
    private LabelService labelServiceMock = Mockito.mock(LabelService.class);

    @Test
    void shouldSaveTest() {
        label = new Label();
        label.setName("JUnit");
        label.setStatus(Status.ACTIVE);
        // Реально добавляет в БД. Какой объект надо мокать, если тестируем только Service?
        assertTrue(labelService.save(label));
    }

    @Test
    void shouldNotSaveTest() {
        assertFalse(labelService.save(null));
    }

    @Test
    void shouldGetByCorrectId() {
        label = new Label();
        label.setId(1);
        label.setName("Test");
        label.setStatus(Status.ACTIVE);
        Mockito.when(labelServiceMock.getById(1)).thenReturn(label);
        assertEquals(1,labelServiceMock.getById(1).getId());
        assertEquals("Test",labelServiceMock.getById(1).getName());
        assertEquals(Status.ACTIVE,labelServiceMock.getById(1).getStatus());
    }

    @Test
    void shouldNotGetByWrongId() {
        Mockito.when(labelServiceMock.getById(777)).thenReturn(null);
        assertEquals(null,labelServiceMock.getById(777));
    }

}
