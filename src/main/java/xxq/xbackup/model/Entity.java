package xxq.xbackup.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity {
    public enum Type {
        FILE,
        DIRECTORY,
        UNKNOWN
    }

    public enum Attribute {
        MODIFY_DATE,
        CREATE_DATE,
        SIZE,
        HASH
    }

    public static final char PATH_SEPARATOR = '/';


    private String name;

    private Type type = Type.UNKNOWN;

    private Map<Attribute, String> attributes = new HashMap<>();

    public Entity() {

    }

    public Entity(String name, Type type) {
        this.type = type;
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (type.equals(Type.DIRECTORY) && name.charAt(name.length() - 1) != PATH_SEPARATOR) {
            this.name = name + PATH_SEPARATOR;
        } else {
            this.name = name;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setAttribute(Attribute key, String value) {
        this.attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(name, entity.name) && type == entity.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public Entity clone() {
        Entity entity = new Entity();
        entity.setName(name);
        entity.setType(type);
        entity.attributes = Map.copyOf(attributes);
        return entity;
    }
}
