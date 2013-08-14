package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.InteractionEvidenceSource;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.FeatureEvidence;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.tab.io.iterator.MitabInteractionEvidenceIterator;

import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * A mitab datasource that loads interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/13</pre>
 */

public class MitabEvidenceDataSource extends AbstractMitabDataSource<InteractionEvidence, ParticipantEvidence, FeatureEvidence> implements InteractionEvidenceSource{

    public MitabEvidenceDataSource() {
        super();
    }

    public MitabEvidenceDataSource(File file) throws IOException {
        super(file);
    }

    public MitabEvidenceDataSource(InputStream input) {
        super(input);
    }

    public MitabEvidenceDataSource(Reader reader) {
        super(reader);
    }

    @Override
    protected void initialiseMitabLineParser(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        setOriginalReader(reader);
        setLineParser(new InteractionEvidenceLineParser(reader));
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
        setLineParser(new InteractionEvidenceLineParser(input));
    }

    @Override
    protected Iterator<InteractionEvidence> createMitabIterator() throws MIIOException {
        return new MitabInteractionEvidenceIterator(getLineParser());
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
}
