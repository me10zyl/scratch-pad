package ui;

import javax.swing.*;

/**
 * Created by ZyL on 2016/6/3.
 */
public class TabTitle extends JPanel{
    JLabel title;
    CloseButton closeBtn;

    public TabTitle(JTabbedPane tab, String title) {
        this.title = new JLabel(title);
        this.closeBtn = new CloseButton(tab,this);
        this.add(this.title);
    }
}
