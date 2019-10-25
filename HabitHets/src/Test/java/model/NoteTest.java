package model;

import org.junit.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoteTest{
    static NoteOrganizer noteOrganizer = new NoteOrganizer();
    static List<Note> notes;


    @BeforeClass
    public static void setUp() {
        TxtDbCommunicator.importDb();
        notes = copyList();
        noteOrganizer.setNotesList(new ArrayList<>());
    }

    @Before
    public void clear(){
        noteOrganizer.getNotes().clear();
    }

    @AfterClass
    public static void resetClass(){
        NoteOrganizer.setNotesList(notes);
    }

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
        clearList();
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.addNote("he", LocalDate.now());
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertEquals(3, noteOrganizer.getNotes().size());
        Assert.assertEquals(noteOrganizer.getNotes().get(1).getDescription(), "he");
        resetList(notes);
    }


    /**
     * Test if the remove method works. Also test the getters and setters.
     */
    @Test
    public void removeNoteTest(){
        clearList();
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.addNote("hej",LocalDate.now());
        Assert.assertEquals(2,noteOrganizer.getNotes().size());
        int id = noteOrganizer.getNotes().get(1).getId();
        noteOrganizer.remove(id);
        Assert.assertEquals(1,noteOrganizer.getNotes().size());
        Assert.assertEquals(LocalDate.now(),noteOrganizer.getNotes().get(0).getDay());
        noteOrganizer.getNotes().get(0).setDay(LocalDate.of(2019,1,1));
        Assert.assertEquals(LocalDate.of(2019,1,1), noteOrganizer.getNotes().get(0).getDay());
        resetList(notes);
    }

    /**
     * Test if the setdescription method works.
     */

    @Test
    public void setDescriptionTest(){
        clearList();
        noteOrganizer.addNote("hej", LocalDate.now());
        noteOrganizer.getNotes().get(0).setDescription("lol");
        Assert.assertEquals("lol",noteOrganizer.getNotes().get(0).getDescription());
        resetList(notes);
    }

    /**
     * Test if setID works
     */

    @Test
    public void testSetId(){
        clearList();
        noteOrganizer.addNote("hej",LocalDate.now());
        noteOrganizer.getNotes().get(0).setId(4);
        Assert.assertEquals(4, noteOrganizer.getNotes().get(0).getId());
        resetList(notes);

    }

    /**
     * Test if method isNoteOnDay works.
     */
    @Test
    public void isNoteOnDayTest(){
        clearList();
        noteOrganizer.addNote("hej", LocalDate.now());
        Assert.assertTrue(noteOrganizer.isNoteOnDay(LocalDateTime.now()));
        resetList(notes);
    }


    /**
     * Test if the getNoteDate method works
     */
    @Test
    public void getNoteDateTest(){
        clearList();
        noteOrganizer.addNote("w",LocalDate.now());
        Assert.assertEquals(noteOrganizer.getNotes().get(0),noteOrganizer.getNoteDate(LocalDate.now()));
        resetList(notes);
    }

    private void clearList(){
        noteOrganizer.getNotes().clear();
    }

    private void resetList(List<Note> noteList){
        noteOrganizer.setNotesList(noteList);
    }
}

