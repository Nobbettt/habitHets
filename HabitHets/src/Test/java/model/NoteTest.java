package model;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoteTest{

    /**
     * test if the add method works.
     */
    @Test
    public void addNoteTest() {
        clearList();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
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
        clearList();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.addNote("hej",LocalDate.now());
        Assert.assertEquals(2,noteOrganizer.getNotes().size());
        noteOrganizer.remove(1);
        Assert.assertEquals(1,noteOrganizer.getNotes().size());
        Assert.assertEquals(LocalDate.now(),noteOrganizer.getNotes().get(0).getDay());
        noteOrganizer.getNotes().get(0).setDay(LocalDate.of(2019,1,1));
        Assert.assertEquals(LocalDate.of(2019,1,1), noteOrganizer.getNotes().get(0).getDay());
    }

    /**
     * Test if the setdescription method works.
     */

    @Test
    public void setDescriptionTest(){
        clearList();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.getNotes().get(0).setDescription("lol");
        Assert.assertEquals("lol",noteOrganizer.getNotes().get(0).getDescription());
    }

    /**
     * Test if setID works
     */

    @Test
    public void testSetId(){
        clearList();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.getNotes().get(0).setId(4);
        Assert.assertEquals(4, noteOrganizer.getNotes().get(0).getId());

    }

    /**
     * Test if method isNoteOnDay works.
     */
    @Test
    public void isNoteOnDayTest(){
        clearList();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertTrue(noteOrganizer.isNoteOnDay(LocalDateTime.now()));
    }


    /**
     * Test if the getNoteDate method works
     */
    @Test
    public void getNoteDateTest(){
        clearList();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.addNote("w",LocalDate.now());
        Assert.assertEquals(noteOrganizer.getNotes().get(0),noteOrganizer.getNoteDate(LocalDate.now()));
    }

    /**
     * Test if the setNotesList method works.
     */

    @Test
    public void setNotesListTest(){
        clearList();
        List<Note> notesList = new ArrayList<>();
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.setNotesList(notesList);
    }

    private void clearList(){
        NoteOrganizer noteOrganizer = new NoteOrganizer();
        noteOrganizer.getNotes().clear();
    }
}

