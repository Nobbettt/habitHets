package model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TestTxtDbCommunicator {
    /**
     * Adds a note on this day 2 years back and checks if the description is the same after a save and import
     */
    @Test
    public void testSave(){
        NoteOrganizer noteOrganizer = new NoteOrganizer();

        Note note_ = new Note(1, "THIS STRING IS BEEING TESTED", LocalDate.now().minusYears(2));
        List<Note> list = new ArrayList<>();
        list.add(note_);
        noteOrganizer.setNotesList(list);

        SaveOnShutDown.saveAll();
        TxtDbCommunicator.importDb();
        Note note = noteOrganizer.getNoteDate(LocalDate.now().minusYears(2));
        String desc = note.getDescription();

        Assert.assertEquals("THIS STRING IS BEEING TESTED", desc);
    }
}
