import model.NoteOrganizer;
import org.junit.Assert;
import org.junit.Test;

public class NoteTest{
    @Test
    public void noteTest(){
        NoteOrganizer noteOrganizer = NoteOrganizer.getInstance();
        noteOrganizer.add();
        noteOrganizer.add();
        noteOrganizer.add();
        Assert.assertEquals(3, noteOrganizer.getNotes().size());
        Assert.assertEquals(0, noteOrganizer.getNotes().get(0).getId());
        Assert.assertEquals(1, noteOrganizer.getNotes().get(1).getId());
        Assert.assertEquals(2, noteOrganizer.getNotes().get(2).getId());
    }





}