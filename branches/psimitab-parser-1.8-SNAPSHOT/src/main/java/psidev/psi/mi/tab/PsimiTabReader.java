package psidev.psi.mi.tab;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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

    Collection<BinaryInteraction> read(Reader reader) throws IOException, ConverterException;

    Collection<BinaryInteraction> read(String s) throws IOException, ConverterException;

    Collection<BinaryInteraction> read(InputStream is) throws IOException, ConverterException;

    Collection<BinaryInteraction> read(File file) throws IOException, ConverterException;

    Collection<BinaryInteraction> read(URL url) throws IOException, ConverterException;

    BinaryInteraction readLine(String str) throws ConverterException;

    Iterator<BinaryInteraction> iterate(Reader r) throws IOException, ConverterException;

    Iterator<BinaryInteraction> iterate(String s) throws IOException, ConverterException;

    Iterator<BinaryInteraction> iterate(InputStream is) throws IOException, ConverterException;

    Iterator<BinaryInteraction> iterate(File file) throws IOException, ConverterException;
}
