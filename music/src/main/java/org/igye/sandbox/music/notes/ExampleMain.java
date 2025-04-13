package org.igye.sandbox.music.notes;

import javax.swing.*;

public class ExampleMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExampleMain::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
//        f.pack();
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }
}
