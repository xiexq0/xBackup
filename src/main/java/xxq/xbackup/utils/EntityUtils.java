package xxq.xbackup.utils;

import xxq.xbackup.model.Entity;

import java.io.File;
import java.io.IOException;

public class EntityUtils {

    public static Entity createFromFile(File file) throws IOException {
        Entity entity = new Entity();
        String name = file.getCanonicalPath();
        if (File.separatorChar != Entity.PATH_SEPARATOR) {
            name = name.replace(File.separatorChar, Entity.PATH_SEPARATOR);
        }
        entity.setName(name);
        entity.setType(file.isFile() ? Entity.Type.FILE : Entity.Type.DIRECTORY);
        return entity;
    }

    public static Entity join(Entity root, Entity child) {
        Entity result = child.clone();
        result.setName(root.getName() + child.getName());
        return result;
    }

    public static Entity separate(Entity root, Entity entity) {
        Entity result = entity.clone();
        String fullName = entity.getName();
        result.setName(fullName.substring(root.getName().length()));
        return result;
    }
}
