package xxq.xbackup.io;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xxq.xbackup.model.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NativeFsVisitorTest {

    private Entity root;

    private NativeSimpleFsVisitor fsVisitor = new NativeSimpleFsVisitor();

    @Before
    public void setUp() throws Exception {
        root = new Entity();
        root.setName(System.getProperty("java.io.tmpdir"));
        root.setType(Entity.Type.DIRECTORY);
        fsVisitor.setRoot(root);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void walk() throws IOException {
        Entity entity = new Entity("test", Entity.Type.DIRECTORY);
        fsVisitor.makeDirectory(entity);
        Assert.assertTrue(fsVisitor.walk(entity).count() > 0);
    }

    @Test
    public void createInputStream() throws IOException {
        Entity entity = new Entity("test", Entity.Type.FILE);
        try (InputStream inputStream = fsVisitor.createInputStream(entity);) {
            Assert.assertTrue(false);
        } catch (IOException e) {
            Assert.assertTrue(true);
        } finally {
            fsVisitor.delete(entity);
        }
    }

    @Test
    public void createOutputStream() {
        Entity entity = new Entity("test", Entity.Type.FILE);
        try (OutputStream outputStream = fsVisitor.createOutputStream(entity);) {
            outputStream.write(1);
        } catch (IOException e) {
            Assert.assertTrue(false);
        }
        try (InputStream inputStream = fsVisitor.createInputStream(entity);) {
            Assert.assertEquals(inputStream.read(), 1);
        } catch (IOException e) {
            Assert.assertTrue(false);
        } finally {
            fsVisitor.delete(entity);
        }
    }

    @Test
    public void delete() {
        Entity entity = new Entity("test", Entity.Type.FILE);
        try (OutputStream outputStream = fsVisitor.createOutputStream(entity);) {
            outputStream.write(1);
        } catch (IOException e) {
            Assert.assertTrue(false);
        }
        Assert.assertEquals(fsVisitor.delete(entity), true);
    }

    @Test
    public void makeDirectory() {
        Entity entity = new Entity("test", Entity.Type.DIRECTORY);
        Assert.assertTrue(fsVisitor.makeDirectory(entity));
        Assert.assertTrue(fsVisitor.exists(entity));
        Assert.assertTrue(fsVisitor.delete(entity));
    }

    @Test
    public void exists() {
        Entity entity = new Entity("test", Entity.Type.DIRECTORY);
        Assert.assertTrue(fsVisitor.makeDirectory(entity));
        Assert.assertTrue(fsVisitor.exists(entity));
        Assert.assertTrue(fsVisitor.delete(entity));
    }
}