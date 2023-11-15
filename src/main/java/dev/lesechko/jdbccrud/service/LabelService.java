package dev.lesechko.jdbccrud.service;

import java.util.List;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.repository.LabelRepository;
import dev.lesechko.jdbccrud.repository.LabelRepositoryImpl;


public class LabelService {
    private final LabelRepository labelRepository = new LabelRepositoryImpl();

    public boolean save(Label labelToSave) {
        return labelRepository.save(labelToSave);
    }

    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public Label getById(Integer id) {
        return labelRepository.getById(id);
    }

    public boolean update(Label label) {
        return labelRepository.update(label);
    }

    public boolean deleteById(Integer id) {
        return labelRepository.deleteById(id);
    }
}
