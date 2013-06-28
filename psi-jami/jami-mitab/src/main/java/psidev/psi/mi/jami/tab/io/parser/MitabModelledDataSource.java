package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.io.iterator.MitabModelledInteractionIterator;

import java.io.*;
import java.util.Iterator;

/**
 * A mitab datasource that loads modelled interactions and ignore experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class MitabModelledDataSource extends AbstractMitabDataSource<ModelledInteraction, ModelledParticipant>{

    public MitabModelledDataSource() {
        super();
    }

    public MitabModelledDataSource(File file) throws IOException {
        super(file);
    }

    public MitabModelledDataSource(InputStream input) {
        super(input);
    }

    public MitabModelledDataSource(Reader reader) {
        super(reader);
    }

    @Override
    protected void initialiseMitabLineParser(Reader reader) {
        if (reader == null){
            throw new IllegalArgumentException("The reader cannot be null.");
        }
        setOriginalReader(reader);
        setLineParser(new ModelledInteractionLineParser(reader));
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
            throw new RuntimeException("Impossible to open the file " + file.getName());
        }
    }

    @Override
    protected void initialiseMitabLineParser(InputStream input) {
        setOriginalStream(input);
        setLineParser(new ModelledInteractionLineParser(input));
    }

    @Override
    protected Iterator<ModelledInteraction> createMitabIterator() {
        return new MitabModelledInteractionIterator(getLineParser());
    }
}

