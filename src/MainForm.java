import logic.LocalStorage;
import ui.Tabs;
import ui.Tab;
import ui.TabTitle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZyL on 2016/6/2.
 */
public class MainForm {
    private JPanel mainPanel;
    private JButton btnNew;
    private JPanel tabPanel;
    private JToggleButton btnMarkdown;
    private Tabs tab;


    public MainForm() {
        tab = new Tabs();
        tabPanel.add(tab);
        initToolBar();
        LocalStorage.init(tab);
    }

    public void initToolBar() {
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tab pad = new Tab();
                TabTitle tabTitle = new TabTitle(tab, pad.getName());
                tab.add(pad);
                tab.setSelectedComponent(pad);
                pad.getEditorPane().requestFocus();
                // tab.setTabComponentAt(tab.indexOfComponent(pad), tabTitle);
            }
        });
        btnMarkdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tab tabContent = (Tab) tab.getSelectedComponent();
                JEditorPane editorPane = tabContent.getEditorPane();
                String text = editorPane.getText();
                String tabTitle = tab.getTitleAt(tab.getSelectedIndex());
                try {
                    if (!text.contains("<html>")) {
                        String path = LocalStorage.saveMd(tabTitle, text);
                        editorPane.setPage("file://" + path);
                    } else {
                        File mdFile = new File(LocalStorage.getStorageDir() + "/" + tabTitle + ".md");
                        if (mdFile.exists()) {
                            editorPane.setPage(mdFile.toURL());
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Tab tabItem = (Tab) tab.getSelectedComponent();
                if(tabItem.isMarkdown()){
                    btnMarkdown.setSelected(true);
                }else{
                    btnMarkdown.setSelected(false);
                }
            }
        });
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        UIManager.setLookAndFeel(lookAndFeel);
        JFrame frame = new JFrame("MainForm");
        frame.setTitle("Scratch Pad");
        MainForm mainForm = new MainForm();
        frame.setContentPane(mainForm.mainPanel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                LocalStorage.save(mainForm.tab);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("selectedIndex", mainForm.tab.getSelectedIndex() + "");
                LocalStorage.saveMeta(map);
                System.exit(0);
            }
        });
        frame.pack();
        frame.setSize(635, 650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Tab tabContent = (Tab) mainForm.tab.getComponentAt(mainForm.tab.getTabCount() - 1);
        tabContent.getEditorPane().requestFocus();
    }
}