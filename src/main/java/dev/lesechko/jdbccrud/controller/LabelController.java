package dev.lesechko.jdbccrud.controller;

import java.util.List;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.service.LabelService;

public class LabelController {
    private final LabelService labelService = new LabelService();

    public boolean add(String labelName) {
        Label newLabel = new Label();
        newLabel.setName(labelName);
        newLabel.setStatus(Status.ACTIVE);
        return labelService.save(newLabel);
    }

    public List<Label> getAll() {
        return labelService.getAll();
    }

    public Label getById(Long id) {
        return labelService.getById(id);
    }

    public boolean update(Label label, String newName, boolean changeStatus) {
        boolean changeName = (newName != null && !newName.isEmpty());
        if (!changeName && !changeStatus) return false;
        if (changeName) label.setName(newName);
        if (changeStatus) {
            Status newStatus = (label.getStatus() == Status.DELETED) ? Status.ACTIVE : Status.DELETED;
            label.setStatus(newStatus);
        }
        return labelService.update(label);
    }

    public boolean deleteById(Long id) {
        return labelService.deleteById(id);
    }
}
