package xxq.xbackup.comparator;

import xxq.xbackup.model.Entity;

import java.io.IOException;

public interface EntityComparator {
    enum Result {
        OVERRIDE,
        IGNORE
    }

    Result compare(Entity src, Entity dest) throws IOException;
}
