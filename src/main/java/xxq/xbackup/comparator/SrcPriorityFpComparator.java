package xxq.xbackup.comparator;

import org.apache.commons.codec.digest.DigestUtils;
import xxq.xbackup.io.Visitor;
import xxq.xbackup.model.Entity;

import java.io.IOException;
import java.util.Base64;

public class SrcPriorityFpComparator implements EntityComparator {

    private Visitor srcVisitor;

    private Visitor destVisitor;

    public SrcPriorityFpComparator(Visitor srcVisitor, Visitor destVisitor) {
        this.srcVisitor = srcVisitor;
        this.destVisitor = destVisitor;
    }

    @Override
    public Result compare(Entity src, Entity dest) throws IOException {
        if (src.getAttribute(Entity.Attribute.SIZE) != null && !src.getAttribute(Entity.Attribute.SIZE).equals(dest.getAttribute(Entity.Attribute.SIZE))) {
            return Result.OVERRIDE;
        }
        String srcHash = src.getAttribute(Entity.Attribute.HASH);
        String destHash = dest.getAttribute(Entity.Attribute.HASH);
        if (srcHash == null) {
            srcHash = Base64.getEncoder().encodeToString(DigestUtils.sha256(srcVisitor.createInputStream(src)));
            src.setAttribute(Entity.Attribute.HASH, srcHash);
        }
        if (destHash == null) {
            destHash = Base64.getEncoder().encodeToString(DigestUtils.sha256(destVisitor.createInputStream(dest)));
            dest.setAttribute(Entity.Attribute.HASH, destHash);
        }
        if (!srcHash.equals(destHash)) {
            return Result.OVERRIDE;
        }
        return Result.IGNORE;
    }
}
