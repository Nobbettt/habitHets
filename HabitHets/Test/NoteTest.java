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
        noteOrganizer.addNote("he", LocalDate.now());
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertEquals(3, noteOrganizer.getNotes().size());
        Assert.assertEquals(noteOrganizer.getNotes().get(1).getDescription(), "he");
        Assert.assertEquals(1, noteOrganizer.getNotes().get(0).getId());
        Assert.assertEquals(2, noteOrganizer.getNotes().get(1).getId());
        Assert.assertEquals(3, noteOrganizer.getNotes().get(2).getId());
    }


    /**
     * Test if the remove method works. Also test the getters and setters.
     */
    @Test
    public void removeNoteTest(){
        NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.addNote("hej",LocalDate.now());
        Assert.assertEquals(2,noteOrganizer.getNotes().size());
        noteOrganizer.remove(1);
        Assert.assertEquals(1,noteOrganizer.getNotes().size());
        Assert.assertEquals(LocalDate.now(),noteOrganizer.getNotes().get(0).getDay());
        noteOrganizer.getNotes().get(0).setId(4);
        Assert.assertEquals(4, noteOrganizer.getNotes().get(0).getId());
        noteOrganizer.getNotes().get(0).setDay(LocalDate.of(2019,1,1));
        Assert.assertEquals(LocalDate.of(2019,1,1), noteOrganizer.getNotes().get(0).getDay());
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

