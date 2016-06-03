package ui;

import logic.IndexManager;
import logic.LocalStorage;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by ZyL on 2016/6/2.
 */
public class Tab extends JPanel {
    private JEditorPane editorPane;
    private boolean isMarkdown = false;

    public Tab() {
        this(null, null);
    }

    public Tab(File file, String tabName) {
        this.editorPane = null;
        if (file == null) {
            this.editorPane = new JEditorPane();
        } else {
                String content = LocalStorage.load(file);
                String contentType = "text/plain";
                if (content.contains("<html>")) {
                    this.setMarkdown(true);
                    contentType = "text/html";
                }
                this.editorPane = new JEditorPane(contentType, content);
        }
        if (tabName == null) {
            System.out.println(IndexManager.getIndex());
            this.setName(IndexManager.next());
        } else {
            this.setName(tabName);
        }
        this.setLayout(new BorderLayout());
        this.editorPane.requestFocus();
        this.add(editorPane);
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

    public void setEditorPane(JEditorPane editorPane) {
        this.editorPane = editorPane;
    }

    public boolean isMarkdown() {
        return isMarkdown;
    }

    public void setMarkdown(boolean markdown) {
        isMarkdown = markdown;
    }
}
