package ui;

import logic.IndexManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by ZyL on 2016/6/2.
 */
public class TabContent extends JPanel{
    private JEditorPane editorPane;

    public TabContent(){
       this(null, null);
    }

    public TabContent(URL url, String tabName){
        this.editorPane = null;
        if(url == null){
            this.editorPane = new JEditorPane();
        }else{
            try {
                this.editorPane = new JEditorPane(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       /* if(IndexManager.getIndex() == 0){
            try {
                editorPane.setPage(this.getClass().getResource("/files/标签0.html"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        if(tabName == null){
            this.setName(IndexManager.next());
        }else{
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
}
