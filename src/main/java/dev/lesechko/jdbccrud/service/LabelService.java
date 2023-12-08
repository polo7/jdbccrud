package dev.lesechko.jdbccrud.service;

import java.util.List;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.repository.LabelRepository;
import dev.lesechko.jdbccrud.repository.jdbc.JdbcLabelRepositoryImpl;


public class LabelService {
//    private final LabelRepository labelRepository = new LabelRepositoryImpl();
    private final LabelRepository labelRepository;

    public LabelService() {
        labelRepository = new JdbcLabelRepositoryImpl();
    }

    // Used for tests
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label save(Label labelToSave) {
        return labelRepository.save(labelToSave);
    }

    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }

    public Label update(Label label) {
        return labelRepository.update(label);
    }

    public boolean deleteById(Long id) {
        return labelRepository.deleteById(id);
    }
}
