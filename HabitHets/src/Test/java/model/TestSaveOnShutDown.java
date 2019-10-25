package model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Short check if saved data is being saved in the correct format
 */
public class TestSaveOnShutDown {
    /**
     * Checks if conflicting character combinations are being naturalized for txt write method
     */
    @Test
    public void testRemoveConflicts(){
        String test = SaveOnShutDown.removeConflicts("<end>");
        Assert.assertEquals("<_end>", test);
    }
}
