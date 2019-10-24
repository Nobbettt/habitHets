package model;

import org.junit.Assert;
import org.junit.Test;

public class TestSaveOnShutDown {
    @Test
    public void testRemoveConflicts(){
        String test = SaveOnShutDown.removeConflicts("<end>");
        Assert.assertEquals("<_end>", test);
    }

}
