package dev.lesechko.controller;

import java.util.List;

import dev.lesechko.simplecrud.model.Label;
import dev.lesechko.simplecrud.model.Status;
import dev.lesechko.simplecrud.repository.gson.GsonLabelRepositoryImpl;
import dev.lesechko.simplecrud.repository.LabelRepository;

public class LabelController {
    private final LabelRepository labelRepository = new GsonLabelRepositoryImpl();

    public Label add(String labelName) {
        Label newLabel = new Label();
        newLabel.setName(labelName);
        newLabel.setStatus(Status.ACTIVE);
        return labelRepository.save(newLabel);
    }

    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    public Label getById(Long id) {
        return labelRepository.getById(id);
    }

    public boolean update(Label label, String newName, boolean changeStatus) {
        boolean changeName = (newName != null && !newName.isEmpty());
        if (!changeName && !changeStatus) return false;
        if (changeName) label.setName(newName);
        if (changeStatus) {
            Status newStatus = (label.getStatus() == Status.DELETED) ? Status.ACTIVE : Status.DELETED;
            label.setStatus(newStatus);
        }
        return labelRepository.update(label) != null;
    }

    public boolean deleteById(Long id) {
        return labelRepository.deleteById(id);
    }
}
