package org.jsmiparser.smi;

public interface AccessPermissions {
    boolean isReadable();
    boolean isWritable();
    boolean isCreateWritable();    
}
