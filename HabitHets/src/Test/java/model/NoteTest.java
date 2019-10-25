package model;

import org.junit.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class test the logic of the note and notehandler
 */
public class NoteTest{
    static NoteOrganizer noteOrganizer = new NoteOrganizer();
    static List<Note> notes;

    /**
     * Preparations for the test be creating a copy of the database and creating a temporary list in the organizer
     */
    @BeforeClass
    public static void setUp() {
        TxtDbCommunicator.importDb();
        notes = copyList();
        noteOrganizer.setNotesList(new ArrayList<>());
    }
    /**
     * Clears temporary list in organizer
     */
    @Before
    public void clear(){
        noteOrganizer.getNotes().clear();
    }

    /**
     * Resets the organizers list to the actual list and removes the temporary
     */
    @AfterClass
    public static void resetClass(){
        NoteOrganizer.setNotesList(notes);
    }

    /**
     * Copies list temporary to after test reset it
     * @return
     */
    private static List<Note> copyList(){
        List<Note> tmpList = new ArrayList<>();
        for (Note note : noteOrganizer.getNotes()){
            tmpList.add(note);
        }
        return tmpList;
    }

    /**
     * test if the add method works.
     */
    @Test
    public void addNoteTest() {
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.addNote("he", LocalDate.now());
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertEquals(3, noteOrganizer.getNotes().size());
        Assert.assertEquals(noteOrganizer.getNotes().get(1).getDescription(), "he");
    }


    /**
     * Test if the remove method works. Also test the getters and setters.
     */
    @Test
    public void removeNoteTest(){
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.addNote("hej",LocalDate.now());
        Assert.assertEquals(2,noteOrganizer.getNotes().size());
        int id = noteOrganizer.getNotes().get(1).getId();
        noteOrganizer.remove(id);
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
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.getNotes().get(0).setDescription("lol");
        Assert.assertEquals("lol",noteOrganizer.getNotes().get(0).getDescription());
    }

    /**
     * Test if setID works
     */

    @Test
    public void testSetId(){
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.getNotes().get(0).setId(4);
        Assert.assertEquals(4, noteOrganizer.getNotes().get(0).getId());

    }

    /**
     * Test if method isNoteOnDay works.
     */
    @Test
    public void isNoteOnDayTest(){
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertTrue(noteOrganizer.isNoteOnDay(LocalDateTime.now()));
    }


    /**
     * Test if the getNoteDate method works
     */
    @Test
    public void getNoteDateTest(){
        noteOrganizer.addNote("w",LocalDate.now());
        Assert.assertEquals(noteOrganizer.getNotes().get(0),noteOrganizer.getNoteDate(LocalDate.now()));
    }
}

