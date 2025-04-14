package org.igye.sandbox.music.notes;

import org.igye.sandbox.music.notes.impl.NoteUtilsImpl;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class NotesMain {
    private final NoteUtils noteUtils;

    public NotesMain(NoteUtils noteUtils) {
        this.noteUtils = noteUtils;
    }

    public static void main(String[] args) {
        new NotesMain(new NoteUtilsImpl()).run();
    }

    private void run() {
        SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        JFrame f = new JFrame("Notes");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Notes", new NotesCmp(noteUtils));
        tabbedPane.addTab("Chords", new MyPanel());

        f.add(tabbedPane);
//        f.pack();
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }
}
