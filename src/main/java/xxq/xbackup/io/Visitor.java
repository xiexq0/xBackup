package xxq.xbackup.io;

import xxq.xbackup.model.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public interface Visitor {
    Stream<Entity> walk(Entity root) throws IOException;

    Stream<Entity> walk(int depth) throws IOException;

    void setRoot(Entity dir);

    InputStream createInputStream(Entity entity) throws IOException;

    OutputStream createOutputStream(Entity entity) throws IOException;

    boolean delete(Entity entity);

    boolean makeDirectory(Entity entity);

    boolean exists(Entity entity);
}
