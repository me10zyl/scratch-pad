package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by ZyL on 2016/6/3.
 */
public class Tab extends JTabbedPane {
    private void restoreTabColor() {
        for (int i = 0; i < this.getTabCount(); i++) {
            if (i > 0) {
                TabTitle tabTitle = (TabTitle) this.getTabComponentAt(i);
                tabTitle.setBackground(null);
            }
        }
    }

    public Tab() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    final int index = ((Tab) e.getComponent()).getUI().tabForCoordinate(Tab.this, e.getX(), e.getY());
                    final int count = ((Tab) e.getComponent()).getTabCount();
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem menuItem = new JMenuItem("关闭当前");
                    JMenuItem menuItem2 = new JMenuItem("关闭其他");
                    JMenuItem menuItem3 = new JMenuItem("关闭所有");
                    popupMenu.add(menuItem);
                    popupMenu.add(menuItem2);
                    popupMenu.add(menuItem3);
                    popupMenu.show(Tab.this, e.getX(), e.getY());

                    menuItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Tab.this.remove(index);
                        }
                    });

                    menuItem2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (int i = count - 1; i >= 0; i--) {
                                if (i != index) {
                                    Tab.this.remove(i);
                                }
                            }
                        }
                    });

                    menuItem3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (int i = count - 1; i >= 0; i--) {
                                Tab.this.remove(i);
                            }
                        }
                    });
                }
            }
        });
    }

    /* tab.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                restoreTabColor();
                int index = tab.getSelectedIndex();
                if (index > 0) {
                    TabTitle tabTitle = (TabTitle) tab.getTabComponentAt(index);
                    tabTitle.setBackground(Color.WHITE);
                }
            }
        });*/
}
