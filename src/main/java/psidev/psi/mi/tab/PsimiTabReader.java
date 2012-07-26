package psidev.psi.mi.tab;

import au.com.bytecode.opencsv.CSVReader;
import psidev.psi.mi.tab.converter.txt2tab.behaviour.UnparseableLineBehaviour;
import psidev.psi.mi.tab.model.*;
import psidev.psi.mi.tab.model.builder.MitabParsingUtils;
import psidev.psi.mi.tab.model.builder.PsimiTabColumns;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 18/06/2012
 * Time: 14:01
 */
public class PsimiTabReader {

    /**
     * has the input got a header.
     * That is, if true, the first line is skipped.
     */
    private boolean hasHeaderLine = true;

    /**
     * Strategy that defines how the reader behaves when encountering unparseable line.
     */
    private UnparseableLineBehaviour unparseableLineBehaviour;


    public PsimiTabReader(boolean hasHeaderLine) {
        this.hasHeaderLine = hasHeaderLine;
    }

    public boolean isHasHeaderLine() {
        return hasHeaderLine;
    }

    public void setHasHeaderLine(boolean hasHeaderLine) {
        this.hasHeaderLine = hasHeaderLine;
    }

    public UnparseableLineBehaviour getUnparseableLineBehaviour() {
        return unparseableLineBehaviour;
    }

    public void setUnparseableLineBehaviour(UnparseableLineBehaviour unparseableLineBehaviour) {
        this.unparseableLineBehaviour = unparseableLineBehaviour;
    }

    public Collection<BinaryInteraction> read(Reader reader) throws IOException, ConverterException {

        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();

        String[] line;

        int lineIndex = 0;


        if (hasHeaderLine) {
            lineIndex = 1;
        }

        CSVReader csvReader = new CSVReader(reader,'\t','\0',lineIndex);
        while ((line = csvReader.readNext()) != null) {
            // line[] is an array of values from the line
            // Avoid the problem of the size with the different formats

            if (line.length < PsimiTabColumns.MITAB_LENGTH.ordinal()) {
                line = MitabParsingUtils.extendFormat(line, PsimiTabColumns.MITAB_LENGTH.ordinal());
            }
            try {
                interactions.add(MitabParsingUtils.buildBinaryInteraction(line));

            } catch (Throwable e) {
                reader.close();
                throw new ConverterException("Exception parsing line " + lineIndex + ": " + Arrays.toString(line), e);
            }
            lineIndex++;

        }

        reader.close();

        return interactions;
    }


    public Collection<BinaryInteraction> read(String s) throws IOException, ConverterException {
        return read(new ByteArrayInputStream(s.getBytes()));  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<BinaryInteraction> read(InputStream is) throws IOException, ConverterException {
        return read(new InputStreamReader(is));
    }

    public Collection<BinaryInteraction> read(File file) throws IOException, ConverterException {
        return read(new FileReader(file));
    }

    public Collection<BinaryInteraction> read(URL url) throws IOException, ConverterException {
        return read(url.openStream());
    }

    public BinaryInteraction readLine(String str) {
        //TODO Improve
        /**
         * Collection of interactions build.
         */
        Collection<BinaryInteraction> interactions;
        try {
            interactions = read(new ByteArrayInputStream(str.getBytes()));
            if (interactions.iterator().hasNext())
                return interactions.iterator().next();
            else {
                System.err.println("Error");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterator<BinaryInteraction> iterate(Reader r) throws IOException, ConverterException {
        return new PsimiTabIterator(r, hasHeaderLine);
    }

    public Iterator<BinaryInteraction> iterate(String s) throws IOException, ConverterException {
        final ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        return new PsimiTabIterator(new InputStreamReader(is), hasHeaderLine);
    }

    public Iterator<BinaryInteraction> iterate(InputStream is) throws IOException, ConverterException {
        return new PsimiTabIterator(new InputStreamReader(is), hasHeaderLine);
    }

    public Iterator<BinaryInteraction> iterate(File file) throws IOException, ConverterException {
        return new PsimiTabIterator(new FileReader(file), hasHeaderLine);
    }

}