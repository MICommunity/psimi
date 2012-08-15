package psidev.psi.mi.tab.io;

import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.tab.model.BinaryInteraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 26/07/2012
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
public interface PsimiTabReader {

    Collection<BinaryInteraction> read(BufferedReader reader) throws IOException, PsimiTabException;

    Collection<BinaryInteraction> read(String s) throws IOException, PsimiTabException;

    Collection<BinaryInteraction> read(InputStream is) throws IOException, PsimiTabException;

    Collection<BinaryInteraction> read(File file) throws IOException, PsimiTabException;

    Collection<BinaryInteraction> read(URL url) throws IOException, PsimiTabException;

    BinaryInteraction readLine(String str) throws PsimiTabException;

    Iterator<BinaryInteraction> iterate(String s) throws IOException;

    Iterator<BinaryInteraction> iterate(InputStream is) throws IOException;

    Iterator<BinaryInteraction> iterate(File file) throws IOException;

    void handleError(String message, Throwable e) throws PsimiTabException;
}
