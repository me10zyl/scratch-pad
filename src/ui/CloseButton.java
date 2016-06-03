package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by ZyL on 2016/6/3.
 */
public class CloseButton extends JButton{
    public CloseButton(JTabbedPane tab, JPanel panel) {
        this.setText("×");
        this.setSize(3,3);
        setBorder(null);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tab.remove(tab.indexOfTabComponent(panel));
            }
        });
    }
}
