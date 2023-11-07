package dev.lesechko.jdbccrud.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.lesechko.jdbccrud.controller.WriterController;
import dev.lesechko.jdbccrud.controller.PostController;
import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Writer;


public class WriterView {
    private final Scanner sc = new Scanner(System.in);
    private final WriterController writerController = new WriterController();
    private final PostController postController = new PostController();

    private void pauseDialog() {
        System.out.println("\nPress ENTER to continue");
        sc.nextLine();
    }


    public void show() {
        final String WRITERS_MENU = """
                +--------------- WRITERS Menu --------------+
                |      Enter digit to select menu item      |
                +-------------------------------------------+
                1) Create new Writer
                2) Show all Writers
                3) Show detailed information by ID
                4) Edit existing Writer
                5) Delete Writer
                0) Back to Main Menu""";
        int selected;

        do {
            System.out.print(WRITERS_MENU + "\nSelect item: ");
            selected = sc.nextInt();
            sc.nextLine();
            switch (selected) {
                case 1 -> showCreateWriter();
                case 2 -> showAllWriters();
                case 3 -> showFindWriterById();
                case 4 -> showEditWriter();
                case 5 -> showDeleteWriter();
            }
        } while (selected != 0);
    }

    private void showCreateWriter() {
        System.out.println("+--- New Writer ---+");

        System.out.print("Last name: ");
        String lastName = sc.nextLine();
        System.out.print("First name: ");
        String firstName = sc.nextLine();

        System.out.println("Add posts for the writer by ID.\nEnter -1 to finish");
        new PostView().showAllPosts();
        List<Post> writerPosts = new ArrayList<>();
        for (Long chosenPostId;;) {
            System.out.print("Post ID to add: ");
            chosenPostId = sc.nextLong();
            if (chosenPostId == -1) break;
            Post postToAdd = postController.getById(chosenPostId);
            if (postToAdd != null)
                writerPosts.add(postToAdd);
            else
                System.out.println("Wrond ID");
        }
        sc.nextLine();

        Writer createdWriter = writerController.add(lastName, firstName, writerPosts);
        System.out.println("Post is created with ID " + createdWriter.getId());
        pauseDialog();
    }

    private void showAllWriters() {
        System.out.println("+--- List of all Writers ---+");
        System.out.printf("%-19s %-30s %-7s\n", "ID", "NAME", "STATUS");

        List<Writer> writers = writerController.getAll();

        if (writers != null && !writers.isEmpty()) {
            for (var writer : writers)
                System.out.printf("%-19d %-30s %-7s\n", writer.getId(), writer.composeFullName(), writer.getStatus());
        } else {
            System.out.println("List is empty");
        }
        pauseDialog();
    }

    private void showFindWriterById() {
        System.out.println("+--- Detailed WRITER info by ID ---+");
        System.out.print("Show writer with ID: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.printf("%-19s %-30s %-7s\n", "ID", "NAME", "STATUS");
        Writer writer = writerController.getById(id);
        if (writer != null) {
            System.out.printf("%-19d %-30s %-7s\n", writer.getId(), writer.composeFullName(), writer.getStatus());

            System.out.println("Posts");
            System.out.printf("%-19s %-30s\n", "ID", "TITLE");
            List<Post> writerPosts = writer.getPosts();
            for (var post : writerPosts)
                System.out.printf("%-19d %-30s\n", post.getId(), post.getTitle());
            System.out.println();
        } else {
            System.out.println("ID " + id + " is not found");
        }
        pauseDialog();
    }

    private void showEditWriter() {
        System.out.println("+--- Edit Writer ---+");
        System.out.print("Edit writer with ID: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Writer writer = writerController.getById(id);
        if (writer != null) {
            // WRITER LAST and FIRST NAME
            System.out.println("Current last name: " + writer.getLastName());
            System.out.print("New last name (leave blank to skip): ");
            String newLastName = sc.nextLine();

            System.out.println("Current first name: " + writer.getFirstName());
            System.out.print("New first name (leave blank to skip): ");
            String newFirstName = sc.nextLine();

            // WRITER POSTS
            System.out.println("Current posts");
            System.out.printf("%-19s %-30s\n", "ID", "TITLE");
            List<Post> writerPosts = writer.getPosts();
            for (var post : writerPosts)
                System.out.printf("%-19d %-30s\n", post.getId(), post.getTitle());
            System.out.println();
            System.out.println("Enter new post IDs below (enter -1 to fisnish)");
            List<Post> newWriterPosts = new ArrayList<>();
            for (long chosenPostId;;) {
                System.out.print("Post ID to add: ");
                chosenPostId = sc.nextLong();
                if (chosenPostId == -1) break;
                Post postToAdd = postController.getById(chosenPostId);
                if (postToAdd != null)
                    newWriterPosts.add(postToAdd);
                else
                    System.out.println("Wrong ID");
            }
            sc.nextLine();

            // WRITER STATUS
            System.out.println("Current status: " + writer.getStatus());
            String action = (writer.getStatus() == Status.ACTIVE) ? "Delete" : "Restore";
            System.out.print(action + " element? (type [yes/no]): ");
            String statusReply = sc.nextLine();
            boolean changeStatus = "yes".equals(statusReply.trim().toLowerCase());

            // SAVING CHANGES
            if (writerController.update(writer, newLastName, newFirstName, newWriterPosts, changeStatus))
                System.out.println("Updating: OK");
            else
                System.out.println("Can't update or write to DB");

        } else {
            System.out.println("ID" + id + " is not found");
        }
    }

    private void showDeleteWriter() {
        System.out.println("+--- Delete Post ---+");
        System.out.print("ID to delete: ");
        Long id = sc.nextLong();
        sc.nextLine();
        if (writerController.deleteById(id))
            System.out.println(id + "is deleted");
        else
            System.out.println("ID " + id + " is not found");

        pauseDialog();
    }
}

