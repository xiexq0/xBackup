package xxq.xbackup.io;

import xxq.xbackup.model.Entity;
import xxq.xbackup.utils.EntityUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class NativeSimpleFsVisitor implements Visitor {
    private Entity root;

    @Override
    public Stream<Entity> walk(Entity root) throws IOException {
        return Files.walk(Paths.get(buildFile(root).toURI())).map(path -> {
            try {
                return createEntryByFile(path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Stream<Entity> walk(int depth) throws IOException {
        return Files.walk(Paths.get(root.getName()), depth).map(path -> {
            try {
                return createEntryByFile(path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setRoot(Entity dir) {
        root = dir;
    }

    @Override
    public InputStream createInputStream(Entity entity) throws IOException {
        return new FileInputStream(buildFile(entity));
    }

    @Override
    public OutputStream createOutputStream(Entity entity) throws IOException {
        return new FileOutputStream(buildFile(entity));
    }

    @Override
    public boolean delete(Entity entity) {
        return buildFile(entity).delete();
    }

    @Override
    public boolean makeDirectory(Entity entity) {
        return buildFile(entity).mkdirs();
    }

    @Override
    public boolean exists(Entity entity) {
        return buildFile(entity).exists();
    }

    private File buildFile(Entity entity) {
        return new File(EntityUtils.join(root, entity).getName());
    }

    private Entity createEntryByFile(File file) throws IOException {
        Entity entity = new Entity();
        entity.setName(file.getCanonicalPath());
        entity.setType(file.isFile() ? Entity.Type.FILE : Entity.Type.DIRECTORY);
        entity.setAttribute(Entity.Attribute.MODIFY_DATE, String.valueOf(file.lastModified()));
        return entity;
    }
}
