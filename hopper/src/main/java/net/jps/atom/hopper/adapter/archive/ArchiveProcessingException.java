/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jps.atom.hopper.adapter.archive;

/**
 *
 * 
 */
public class ArchiveProcessingException extends RuntimeException {

    public ArchiveProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArchiveProcessingException(String message) {
        super(message);
    }
}
