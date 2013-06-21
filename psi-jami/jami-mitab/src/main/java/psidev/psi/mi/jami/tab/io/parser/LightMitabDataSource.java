package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.io.iterator.MitabBinaryIterator;

import java.io.*;
import java.util.Iterator;

/**
 * A mitab datasource that loads very basic interactions and ignore experimental details, source, confidence and experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class LightMitabDataSource extends AbstractMitabDataSource<Interaction, BinaryInteraction, Participant>{

    public LightMitabDataSource() {
        super();
    }

    public LightMitabDataSource(File file) throws IOException {
        super(file);
    }

    public LightMitabDataSource(InputStream input) {
        super(input);
    }

    public LightMitabDataSource(Reader reader) {
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
            throw new RuntimeException("Impossible to open the file " + file.getName());
        }
    }

    @Override
    protected void initialiseMitabLineParser(InputStream input) {
        setOriginalStream(input);
        setLineParser(new InteractionLineParser(input));
    }

    @Override
    protected Iterator<Interaction> createMitabIterator() {
        return new MitabBinaryIterator(getLineParser());
    }
}