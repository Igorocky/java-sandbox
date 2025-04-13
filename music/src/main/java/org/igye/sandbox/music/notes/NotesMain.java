package org.igye.sandbox.music.notes;

import javax.swing.*;

public class NotesMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NotesMain::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Notes", new MyPanel());
        tabbedPane.addTab("Chords", new MyPanel());

        f.add(tabbedPane);
//        f.pack();
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }
}
