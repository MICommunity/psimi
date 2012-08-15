package psidev.psi.mi.tab;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabParserUtils;

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

    public Collection<BinaryInteraction> read(BufferedReader reader) throws IOException, PsimiTabException {

        Collection<BinaryInteraction> interactions = new ArrayList<BinaryInteraction>();

        String[] line;
        String completeLine;

        int lineIndex = 0;


        while ((completeLine = reader.readLine()) != null) {

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
                handleError("Exception parsing line " + lineIndex + ": " + Arrays.toString(line), e);
            }
            lineIndex++;
        }

        return interactions;
    }


    public Collection<BinaryInteraction> read(String s) throws IOException, PsimiTabException {

        ByteArrayInputStream is = null;
        BufferedReader bufferedReader = null;

        Collection<BinaryInteraction> interactions;

        try {

            is = new ByteArrayInputStream(s.getBytes());
            bufferedReader = new BufferedReader(new InputStreamReader(is));

            interactions = read(bufferedReader);

        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (is != null) {
                is.close();
            }

        }

        return interactions;

    }

    public Collection<BinaryInteraction> read(InputStream is) throws IOException, PsimiTabException {

        BufferedReader bufferedReader = null;

        Collection<BinaryInteraction> interactions;

        try {

            bufferedReader = new BufferedReader(new InputStreamReader(is));

            interactions = read(bufferedReader);

        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        return interactions;
    }

    public Collection<BinaryInteraction> read(File file) throws IOException, PsimiTabException {

        FileReader reader = null;
        BufferedReader bufferedReader = null;

        Collection<BinaryInteraction> interactions;

        try {

            reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);

            interactions = read(bufferedReader);

        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (reader != null) {
                reader.close();
            }

        }

        return interactions;
    }

    public Collection<BinaryInteraction> read(URL url) throws IOException, PsimiTabException {

        InputStream is = null;
        BufferedReader bufferedReader = null;

        Collection<BinaryInteraction> interactions;

        try {

            is = url.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(is));

            interactions = read(bufferedReader);

        } finally {

            // You only need to close the outermost stream class because the close()
            // call is automatically trickled through all the chained classes
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (is != null) {
                is.close();
            }

        }

        return interactions;
    }

    public BinaryInteraction readLine(String str) throws PsimiTabException {

        BinaryInteraction interaction = null;
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
            handleError("Exception parsing line :" + Arrays.toString(line), e);
        }

        return interaction;
    }


    public Iterator<BinaryInteraction> iterate(String s) throws IOException {
        final ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
    }

    public Iterator<BinaryInteraction> iterate(InputStream is) throws IOException {
        final InputStreamReader reader = new InputStreamReader(is);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
    }

    public Iterator<BinaryInteraction> iterate(File file) throws IOException {
        final FileReader reader = new FileReader(file);
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
    }

    public void handleError(String message, Throwable e) throws PsimiTabException {
        throw new PsimiTabException(message, e);
    }

}