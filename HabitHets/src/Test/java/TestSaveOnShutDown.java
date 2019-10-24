import model.SaveOnShutDown;
import org.junit.Assert;
import org.junit.Test;

public class TestSaveOnShutDown {
    @Test
    public void testSave(){
        String test = SaveOnShutDown.removeConflicts("<end>");
        Assert.assertEquals("<_end>", test);
    }

}
