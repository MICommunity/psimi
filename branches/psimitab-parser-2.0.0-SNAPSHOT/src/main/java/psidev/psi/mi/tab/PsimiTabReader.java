package psidev.psi.mi.tab;

import psidev.psi.mi.tab.listeners.MitabParserListener;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabParserUtils;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 18/06/2012
 * Time: 14:01
 */
public class PsimiTabReader implements psidev.psi.mi.tab.io.PsimiTabReader {

    protected EventListenerList listenerList = new EventListenerList();

    protected Collection<BinaryInteraction> read(BufferedReader reader) throws IOException {

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

            interactions.add(MitabParserUtils.buildBinaryInteraction(line, lineIndex, getListeners(MitabParserListener.class)));

            lineIndex++;
        }

        return interactions;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<BinaryInteraction> read(Reader reader) throws IOException {

        BufferedReader bufferedReader = null;

        Collection<BinaryInteraction> interactions;

        try {

            bufferedReader = new BufferedReader(reader);

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

    /**
     * {@inheritDoc}
     */
    public Collection<BinaryInteraction> read(String s) throws IOException {

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

    /**
     * {@inheritDoc}
     */
    public Collection<BinaryInteraction> read(InputStream is) throws IOException {

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

    /**
     * {@inheritDoc}
     */
    public Collection<BinaryInteraction> read(File file) throws IOException {

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

    /**
     * {@inheritDoc}
     */
    public Collection<BinaryInteraction> read(URL url) throws IOException {

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

    public BinaryInteraction readLine(String str)  {

        String[] line;

        line = MitabParserUtils.quoteAwareSplit(str, new char[]{'\t'}, false);

        if (line.length > 0 && line[0].startsWith("#")) {
            //This line is a comment, we skip the line
            return null;
        }
        // line[] is an array of values from the line
        // Avoid the problem of the size with the different formats

        return MitabParserUtils.buildBinaryInteraction(line, 0, getListeners(MitabParserListener.class));
    }

    public BinaryInteraction readLine(String str, int lineIndex) {

        BinaryInteraction interaction = null;
        String[] line;

        line = MitabParserUtils.quoteAwareSplit(str, new char[]{'\t'}, false);

        if (line.length > 0 && line[0].startsWith("#")) {
            //This line is a comment, we skip the line
            return null;
        }
        // line[] is an array of values from the line
        // Avoid the problem of the size with the different formats

        return MitabParserUtils.buildBinaryInteraction(line, lineIndex, getListeners(MitabParserListener.class));
    }


    public Iterator<BinaryInteraction> iterate(Reader reader) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(reader);
        return new PsimiTabIterator(bufferedReader);
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

    public void addMitabParserListener(MitabParserListener l) {
        listenerList.add(MitabParserListener.class, l);
    }

    public void removeMitabParserListener(MitabParserListener l) {
        listenerList.remove(MitabParserListener.class, l);
    }

    protected <T> List<T> getListeners(Class<T> listenerClass) {
        List list = new ArrayList();

        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == MitabParserListener.class) {
                if (listenerClass.isAssignableFrom(listeners[i+1].getClass())) {
                    list.add(listeners[i+1]);
                }
            }
        }
        return list;
    }
}