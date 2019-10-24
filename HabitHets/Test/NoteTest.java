import main.model.Note;
import main.model.NoteOrganizer;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NoteTest{
    /**
     * test if the add method works.
     */
    @Test
    public void addNoteTest() {
        NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertEquals(3, noteOrganizer.getNotes().size());
        Assert.assertEquals(1, noteOrganizer.getNotes().get(0).getId());
        Assert.assertEquals(2, noteOrganizer.getNotes().get(1).getId());
        Assert.assertEquals(3, noteOrganizer.getNotes().get(2).getId());
    }


    /**
     * Test if the remove method works.
     */
    @Test
    public void removeNoteTest(){
        NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.addNote("hej",LocalDate.now());
        Assert.assertEquals(2,noteOrganizer.getNotes().size());
        noteOrganizer.remove(1);
        Assert.assertEquals(1,noteOrganizer.getNotes().size());
    }

    @Test
    public void isNoteOnDayTest(){
        NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertTrue(noteOrganizer.isNoteOnDay(LocalDateTime.now()));
    }

    @Test
    public void getNoteDateTest(){
        NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
        noteOrganizer.addNote("w",LocalDate.now());
        Note n = noteOrganizer.getNoteDate(LocalDate.now());
        Assert.assertEquals(noteOrganizer.getNotes().get(0),n);
    }
}

