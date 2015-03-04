package psidev.psi.mi.jami.tab.extension.datasource;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.iterator.MitabInteractionIterator;
import psidev.psi.mi.jami.tab.io.parser.InteractionLineParser;

import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * A mitab datasource that loads very basic interactions and ignore experimental details, source, confidence and experimental details
 * It will only provide an iterator of interactions.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class LightMitabStreamSource extends AbstractMitabStreamSource<Interaction, Participant, Feature> {

    public LightMitabStreamSource() {
        super();
    }

    public LightMitabStreamSource(File file) throws IOException {
        super(file);
    }

    public LightMitabStreamSource(InputStream input) {
        super(input);
    }

    public LightMitabStreamSource(Reader reader) {
        super(reader);
    }

    @Override
    protected void initialiseMitabLineParser(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        setOriginalReader(reader);
        setLineParser(new InteractionLineParser(reader));
    }

    @Override
    protected void initialiseMitabLineParser(File file) {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canRead()){
            throw new IllegalArgumentException("Does not have the permissions to read the file "+file.getAbsolutePath());
        }
        setOriginalFile(file);
        try {
            InputStream stream = new BufferedInputStream(new FileInputStream(file));
            initialiseMitabLineParser(stream);
        } catch (FileNotFoundException e) {
            throw new MIIOException("Impossible to open the file " + file.getName());
        }
    }

    @Override
    protected void initialiseMitabLineParser(InputStream input) {
        setOriginalStream(input);
        setLineParser(new InteractionLineParser(input));
    }

    @Override
    protected void initialiseMitabLineParser(URL url) {
        if (url == null){
            throw new IllegalArgumentException("The url cannot be null.");
        }
        setOriginalURL(url);
        try {
            InputStream stream = new BufferedInputStream(url.openStream());
            initialiseMitabLineParser(stream);
        } catch (IOException e) {
            throw new MIIOException("Impossible to open the url " + url.toExternalForm());
        }
    }

    @Override
    protected Iterator<Interaction> createMitabIterator() throws MIIOException{
        return new MitabInteractionIterator(getLineParser());
    }
}