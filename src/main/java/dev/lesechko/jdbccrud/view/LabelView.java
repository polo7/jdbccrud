package dev.lesechko.jdbccrud.view;

import java.util.Scanner;
import java.util.List;

import dev.lesechko.jdbccrud.controller.LabelController;
import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Status;


public class LabelView {
    private final Scanner sc = new Scanner(System.in);
    private final LabelController labelController = new LabelController();

    private void pauseDialog() {
        System.out.println("\nPress ENTER to continue");
        sc.nextLine();
    }

    public void show() {
        final String LABELS_MENU = """
                +--------------- LABELS Menu ---------------+
                |      Enter digit to select menu item      |
                +-------------------------------------------+
                1) Create new Label
                2) Show all Labels
                3) Show detailed information by ID
                4) Edit existing Label
                5) Delete Label
                0) Back to Main Menu""";
        int selected;

        do {
            System.out.print(LABELS_MENU + "\nSelect item: ");
            selected = sc.nextInt();
            sc.nextLine();
            switch (selected) {
                case 1 -> showCreateLabel();
                case 2 -> showAllLabels();
                case 3 -> showFindLabelById();
                case 4 -> showEditLabel();
                case 5 -> showDeleteLabel();
            }
        } while (selected != 0);
    }

    private void showCreateLabel() {
        System.out.println("+--- New Label ---+");
        System.out.print("Name: ");
        String labelName = sc.nextLine();
        if (labelController.add(labelName))
            System.out.println("Added 1 new label.");
        else
            System.out.println("Error occured while adding new label.");

        pauseDialog();
    }

    void showAllLabels() {
        System.out.println("+--- List of all Labels ---+");
        System.out.printf("%-19s %-30s %-6s\n", "ID", "NAME", "STATUS");

        List<Label> labels = labelController.getAll();

        if (labels != null && !labels.isEmpty())
            for (var label : labels) {
                System.out.printf("%-19d %-30s %-6s\n", label.getId(), label.getName(), label.getStatus());
            }
        else
            System.out.println("List is empty.");

        pauseDialog();
    }

    private void showFindLabelById() {
        System.out.println("+--- Detailed LABEL info by ID ---+");
        System.out.print("Show label with ID: ");

        Integer id = sc.nextInt();
        sc.nextLine();
        System.out.printf("%-19s %-30s %-7s\n", "ID", "NAME", "STATUS");

        Label label = labelController.getById(id);
        if (label != null)
            System.out.printf("%-19d %-30s %-7s\n", label.getId(), label.getName(), label.getStatus());
        else
            System.out.println("ID " + id + " is not found");

        pauseDialog();
    }

    private void showEditLabel() {
        System.out.println("+--- Edit Label ---+");
//        System.out.print("Edit label with ID: ");
//
//        Long id = sc.nextLong();
//        sc.nextLine();
//
//        Label label = labelController.getById(id);
//        if (label != null) {
//            // LABEL NAME
//            System.out.println("Current name: " + label.getName());
//            System.out.print("New name (leave blank to skip): ");
//            String newName = sc.nextLine();
//
//            // LABEL STATUS
//            System.out.println("Current status: " + label.getStatus());
//            String action = (label.getStatus() == Status.ACTIVE) ? "Delete" : "Restore";
//            System.out.print(action + " element? (type [yes/no]): ");
//            String statusReply = sc.nextLine();
//            boolean changeStatus = "yes".equalsIgnoreCase(statusReply);
//            if (labelController.update(label, newName, changeStatus))
//                System.out.println("Updating: OK");
//            else
//                System.out.println("Can't update or write to DB");
//
//        }
//        else
//            System.out.println("ID " + id + " is not found");

        pauseDialog();
    }

    private void showDeleteLabel() {
        System.out.println("+--- Delete Label ---+");
        System.out.print("\"ID\" to delete: ");
        Integer id = sc.nextInt();
        sc.nextLine();
        if (labelController.deleteById(id)) System.out.println(id + " is deleted");
        else System.out.println("ID " + id + " is not found");
        pauseDialog();
    }
}
