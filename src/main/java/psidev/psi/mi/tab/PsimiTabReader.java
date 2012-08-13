package psidev.psi.mi.tab;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabParserUtils;
import psidev.psi.mi.xml.converter.ConverterException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 18/06/2012
 * Time: 14:01
 */
public class PsimiTabReader implements psidev.psi.mi.tab.io.PsimiTabReader {

    public Collection<BinaryInteraction> read(Reader reader) throws IOException, ConverterException {

        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();

        String[] line;
        String completeLine;

        int lineIndex = 0;

        BufferedReader input = new BufferedReader(reader);

        while ((completeLine = input.readLine()) != null) {

            line = MitabParserUtils.quoteAwareSplit(completeLine, new char[]{'\t'}, false);

            if (line != null && line.length > 0 && line[0].startsWith("#")) {
                //This line is a comment, we skip the line
                lineIndex++;
                continue;
            }
            // line[] is an array of values from the line
            // Avoid the problem of the size with the different formats


            try {
                interactions.add(MitabParserUtils.buildBinaryInteraction(line));

            } catch (Throwable e) {
                throw new ConverterException("Exception parsing line " + lineIndex + ": " + Arrays.toString(line), e);

            }
            lineIndex++;
        }

        return interactions;
    }


    public Collection<BinaryInteraction> read(String s) throws IOException, ConverterException {
        final ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        Collection<BinaryInteraction> interactions = read(bufferedReader);
        bufferedReader.close();
        reader.close();
        is.close();
        return interactions;
    }

    public Collection<BinaryInteraction> read(InputStream is) throws IOException, ConverterException {
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        Collection<BinaryInteraction> interactions = read(bufferedReader);
        bufferedReader.close();
        reader.close();
        return interactions;
    }

    public Collection<BinaryInteraction> read(File file) throws IOException, ConverterException {
        final FileReader reader = new FileReader(file);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        Collection<BinaryInteraction> interactions = read(bufferedReader);
        bufferedReader.close();
        reader.close();
        return interactions;
    }

    public Collection<BinaryInteraction> read(URL url) throws IOException, ConverterException {
        final InputStream is = url.openStream();
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        Collection<BinaryInteraction> interactions = read(bufferedReader);
        bufferedReader.close();
        reader.close();
        is.close();
        return interactions;
    }

    public BinaryInteraction readLine(String str) throws ConverterException {

        BinaryInteraction interaction;
        String[] line;

        line = MitabParserUtils.quoteAwareSplit(str, new char[]{'\t'}, false);

        if (line.length > 0 && line[0].startsWith("#")) {
            //This line is a comment, we skip the line
            return null;
        }
        // line[] is an array of values from the line
        // Avoid the problem of the size with the different formats

        try {
            interaction = MitabParserUtils.buildBinaryInteraction(line);

        } catch (Throwable e) {
            throw new ConverterException("Exception parsing line :" + Arrays.toString(line), e);
        }

        return interaction;
    }

    /**
     * The iterate function will close the reader in the last line.
     *
     * @param r Reader
     * @return a Iterator of binary interactions
     * @throws IOException
     * @throws ConverterException
     */
    public Iterator<BinaryInteraction> iterate(Reader r) throws IOException, ConverterException {
        return new PsimiTabIterator(r);
    }

    public Iterator<BinaryInteraction> iterate(String s) throws IOException, ConverterException {
        final ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
    }

    public Iterator<BinaryInteraction> iterate(InputStream is) throws IOException, ConverterException {
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
    }

    public Iterator<BinaryInteraction> iterate(File file) throws IOException, ConverterException {
        final FileReader reader = new FileReader(file);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
    }

}