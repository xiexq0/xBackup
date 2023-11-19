package xxq.xbackup.comparator;

import org.junit.Assert;
import org.junit.Test;
import xxq.xbackup.io.NativeSimpleFsVisitor;
import xxq.xbackup.model.Entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.*;

public class SrcPriorityFpComparatorTest {

    @Test
    public void compare() throws IOException {
        File tmpFile = File.createTempFile("hash_test", "tmp");
        Entity entity = new Entity(tmpFile.getCanonicalPath(), Entity.Type.FILE);
        NativeSimpleFsVisitor fsVisitor = new NativeSimpleFsVisitor();
        SrcPriorityFpComparator srcPriorityFpComparator = new SrcPriorityFpComparator(fsVisitor, fsVisitor);
        OutputStream outputStream = new FileOutputStream(tmpFile);
        outputStream.write(1);
        outputStream.close();
        entity.setAttribute(Entity.Attribute.SIZE, "1");
        Assert.assertEquals(srcPriorityFpComparator.compare(entity, entity), EntityComparator.Result.IGNORE);
    }
}