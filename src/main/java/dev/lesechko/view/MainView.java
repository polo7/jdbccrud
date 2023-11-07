package dev.lesechko.view;

import java.util.Scanner;


public class MainView {
    private final LabelView labelView = new LabelView();
    private final PostView postView = new PostView();
    private final WriterView writerView = new WriterView();

    public void show() {
        final String MAIN_MENU = """
                +------------ CRUD app Main Menu -----------+
                |      Enter digit to select menu item      |
                +-------------------------------------------+
                1) Labels
                2) Posts
                3) Writers
                0) Exit""";
        var sc = new Scanner(System.in);
        int selected;
        do {
            System.out.print(MAIN_MENU + "\nSelect item: ");
            selected = sc.nextInt();
            switch (selected) {
                case 1 -> labelView.show();
                case 2 -> postView.show();
                case 3 -> writerView.show();
            }
        } while (selected != 0);
    }
}
