import main.model.NoteHandler;
import org.junit.Assert;
import org.junit.Test;

public class NoteTest{
    @Test
    public void noteTest(){
        NoteHandler noteHandler = NoteHandler.getInstance();
        noteHandler.add();
        noteHandler.add();
        noteHandler.add();
        Assert.assertEquals(3, noteHandler.getNotes().size());
        Assert.assertEquals(0, noteHandler.getNotes().get(0).getId());
        Assert.assertEquals(1, noteHandler.getNotes().get(1).getId());
        Assert.assertEquals(2, noteHandler.getNotes().get(2).getId());
    }





}