package xxq.xbackup.comparator;

import xxq.xbackup.model.Entity;

public interface EntityComparator {
    int compare(Entity src, Entity dest);
}
