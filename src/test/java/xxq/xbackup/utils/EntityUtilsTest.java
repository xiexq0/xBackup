package xxq.xbackup.utils;

import org.junit.Assert;
import org.junit.Test;
import xxq.xbackup.model.Entity;

import java.io.File;
import java.io.IOException;

public class EntityUtilsTest {

    @org.junit.Test
    public void join() {
        Entity rootEntry = new Entity("/demo", Entity.Type.DIRECTORY);
        Entity child = new Entity("child", Entity.Type.FILE);
        Entity full = EntityUtils.join(rootEntry, child);
        Assert.assertEquals(full.getName(), "/demo/child");
        Assert.assertTrue(full.getType().equals(child.getType()));
    }

    @org.junit.Test
    public void separate() {
        Entity rootEntry = new Entity("/demo", Entity.Type.DIRECTORY);
        Entity full = new Entity("/demo/child", Entity.Type.FILE);
        Entity child = EntityUtils.separate(rootEntry, full);
        Assert.assertEquals(full.getName(), "child");
        Assert.assertTrue(full.getType().equals(child.getType()));
    }

    @Test
    public void createFromFile() throws IOException {
        File tmpFile = File.createTempFile("test", ".tmp");
        Entity entity = EntityUtils.createFromFile(tmpFile);
        Assert.assertTrue(entity.getType().equals(Entity.Type.FILE));
    }
}