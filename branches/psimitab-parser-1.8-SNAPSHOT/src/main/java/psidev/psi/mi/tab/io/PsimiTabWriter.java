package psidev.psi.mi.tab.io;

import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.io.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 27/07/2012
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public interface PsimiTabWriter {

    void write(Collection<BinaryInteraction> interactions, Writer writer) throws IOException;

    void write(Collection<BinaryInteraction> interactions, OutputStream os) throws IOException;

    void write(Collection<BinaryInteraction> interactions, PrintStream ps) throws IOException;

    void write(Collection<BinaryInteraction> interactions, File file) throws IOException;

    void write(BinaryInteraction interaction, Writer writer) throws IOException;

    void write(BinaryInteraction interaction, OutputStream os) throws IOException;

    void write(BinaryInteraction interaction, PrintStream ps) throws IOException;

    void write(BinaryInteraction interaction, File file) throws IOException;

    void writeMitabHeader(Writer bw) throws IOException;

    void writeMitabHeader(OutputStream os) throws IOException;

    void writeMitabHeader(PrintStream ps) throws IOException;

    void writeMitabHeader(File file) throws IOException;

    void handleError(String message, Throwable e) throws PsimiTabException;
}
