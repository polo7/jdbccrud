package dev.lesechko.jdbccrud.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.lesechko.jdbccrud.controller.LabelController;
import dev.lesechko.jdbccrud.controller.PostController;
import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Status;


public class PostView {
    private final Scanner sc = new Scanner(System.in);
    private final PostController postController = new PostController();
    private final LabelController labelController = new LabelController();

    private void pauseDialog() {
        System.out.println("\nPress ENTER to continue");
        sc.nextLine();
    }

    private String multilineStringReader() {
        String content = "";
        String newLine = "";
        int exitCounter = 0;
        do {
            newLine = sc.nextLine();
            content += newLine + "\n";
            exitCounter = newLine.isEmpty() ? ++exitCounter : 0;
        } while (exitCounter < 2);
        int l = content.length();
        return (content != null && l > 2) ? content.substring(0, l-2) : "";
    }

    public void show() {
        final String POSTS_MENU = """
                +---------------- POSTS Menu ---------------+
                |      Enter digit to select menu item      |
                +-------------------------------------------+
                1) Create new Post
                2) Show all Posts
                3) Show detailed information by ID
                4) Edit existing Post
                5) Delete Post
                0) Back to Main Menu""";
        int selected;

        do {
            System.out.print(POSTS_MENU + "\nSelect item: ");
            selected = sc.nextInt();
            sc.nextLine();
            switch (selected) {
                case 1 -> showCreatePost();
                case 2 -> showAllPosts();
                case 3 -> showFindPostById();
                case 4 -> showEditPost();
                case 5 -> showDeletePost();
            }
        } while (selected != 0);

    }

    private void showCreatePost() {
        System.out.println("+--- New Post ---+");
        System.out.print("Title: ");
        String postTitle = sc.nextLine();

        System.out.print("Content (double ENTER to finish): ");
        String postContent = multilineStringReader();

        System.out.println("Add labels to the post by ID.\nEnter -1 to finish");
        new LabelView().showAllLabels();
        List<Label> postLabels = new ArrayList<>();
        for (Long chosenLabelId;;) {
            System.out.print("Label ID to add: ");
            chosenLabelId = sc.nextLong();
            if (chosenLabelId == -1) break;
            Label labelToAdd = labelController.getById(chosenLabelId);
            if (labelToAdd != null) {
                postLabels.add(labelToAdd);
            } else {
                System.out.println("Wrong ID");
            }
        }
        sc.nextLine();

        Post newPost = postController.add(postTitle, postContent, postLabels);
        if (newPost != null) {
            System.out.println("Added 1 new pos with ID " + newPost.getId());
        } else {
            System.out.println("Error occured while adding new post.");
        }
        pauseDialog();
    }

    void showAllPosts() {
        System.out.println("+--- List of all Posts ---+");
        System.out.printf("%-19s %-30s %-7s\n", "ID", "TITLE", "STATUS");

        List<Post> posts = postController.getAll();

        if (posts != null && !posts.isEmpty()) {
            for (var post : posts)
                System.out.printf("%-19d %-30s %-7s\n", post.getId(), post.getTitle(), post.getStatus());
        } else {
            System.out.println("List is empty");
        }
        pauseDialog();
    }

    private void showFindPostById() {
        System.out.println("+--- Detailed POST info by ID ---+");
        System.out.print("Show post with ID: ");
        Long id = sc.nextLong();
        sc.nextLine();
        System.out.printf("%-19s %-30s %-7s\n", "ID", "TITLE", "STATUS");
        Post post = postController.getById(id);
        if (post != null) {
            System.out.printf("%-19d %-30s %-7s\n", post.getId(), post.getTitle(), post.getStatus());

            System.out.print("Labels: ");
            List<Label> postLabels = post.getLabels();
            for (Label label : postLabels) {
                System.out.print("[" + label.getName() + "] ");
            }
            System.out.println();

            System.out.println("Content");
            System.out.print(post.getContent());
        } else {
            System.out.println("ID " + id + " is not found");
        }
        pauseDialog();
    }

    private void showEditPost() {
        System.out.println("+--- Edit Post ---+");
        System.out.print("Edit post with ID: ");
        Long id = sc.nextLong();
        sc.nextLine();

        Post post = postController.getById(id);
        if (post != null) {
            // POST TITLE
            System.out.println("Current title: " + post.getTitle());
            System.out.print("New title (leave blank to skip): ");
            String newTitle = sc.nextLine();

            // POST CONTENT
            System.out.println("Current content: " + post.getContent());
            System.out.println("Enter new content below (leave blank to skip - double ENTER)");
            String newContent = multilineStringReader();

            // POST LABELS
            System.out.print("Current labels: ");
            List<Label> currentLabels = post.getLabels();
            for (Label label : currentLabels) {
                System.out.print("[" + label.getName() + "] ");
            }
            System.out.println();
            System.out.println("Enter new label IDs below (enter -1 to finish)");
            List<Label> newPostLabels = new ArrayList<>();
            for (Long chosenLabelId;;) {
                System.out.print("Label ID to add: ");
                chosenLabelId = sc.nextLong();
                if (chosenLabelId == -1) break;
                Label labelToAdd = labelController.getById(chosenLabelId);
                if (labelToAdd != null)
                    newPostLabels.add(labelToAdd);
                else
                    System.out.println("Wrong ID");
            }
            sc.nextLine();

            // POST STATUS
            System.out.println("Current status: " + post.getStatus());
            String action = (post.getStatus() == Status.ACTIVE) ? "Delete" : "Restore";
            System.out.print(action + " element? (type [yes/no]): ");
            String statusReply = sc.nextLine();
            boolean changeStatus = "yes".equalsIgnoreCase(statusReply.trim());

            // SAVING CHANGES
            Post updatedPost = postController.update(post, newTitle, newContent, newPostLabels, changeStatus);
            if (updatedPost != null) {
                System.out.println("Updating post with ID " + updatedPost.getId() + ": OK");
            } else {
                System.out.println("Can't update or write to DB");
            }
        } else {
            System.out.println("ID " + id + " is not found");
        }
    }

    private void showDeletePost() {
        System.out.println("+--- Delete Post ---+");
        System.out.print("ID to delete: ");
        Long id = sc.nextLong();
        sc.nextLine();
        if (postController.deleteById(id)) {
            System.out.println(id + " is deleted");
        } else {
            System.out.println("ID " + id + " is not found");
        }
        pauseDialog();
    }
}