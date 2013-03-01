package psidev.psi.mi.tab.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.DataSourceError;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.StreamingInteractionSource;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.tab.PsimiTabIterator;
import psidev.psi.mi.tab.PsimiTabReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * A simple Mitab datasource.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class SimpleMitabDataSource implements StreamingInteractionSource{

    private PsimiTabReader reader;
    private Iterator<BinaryInteraction> mitabIterator;
    private File file;
    private InputStream stream;
    private Map<DataSourceError, List<FileSourceContext>> errors;

    private Log log = LogFactory.getLog(SimpleMitabDataSource.class);

    public SimpleMitabDataSource(File file){
        this.reader = new PsimiTabReader();
        this.file = file;
        if (file == null){
           throw new IllegalArgumentException("File is mandatory for a MITAb datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public SimpleMitabDataSource(InputStream stream){
        this.reader = new PsimiTabReader();
        this.stream = stream;
        if (stream == null){
            throw new IllegalArgumentException("InputStream is mandatory for a MITAb datasource");
        }
        errors = new HashMap<DataSourceError, List<FileSourceContext>>();
    }

    public Iterator<? extends InteractionEvidence> getExperimentalInteractionsIterator() {
        return mitabIterator;
    }

    public Iterator<? extends InteractionEvidence> getInteractionEvidencesIterator() {
        return null;
    }

    public Iterator<? extends ModelledInteraction> getModelledInteractionsIterator() {
        return null;
    }

    public Iterator<? extends CooperativeInteraction> getCooperativeInteractionsIterator() {
        return null;
    }

    public Iterator<? extends AllostericInteraction> getAllostericInteractionsIterator() {
        return null;
    }

    public Iterator<? extends Interaction> getInteractionsIterator() {
        return getInteractionEvidencesIterator();
    }

    public void initialiseContext(Map<String, Object> stringObjectMap) {
        // do nothing
    }

    public Map<DataSourceError, List<FileSourceContext>> getDataSourceErrors() {
        return errors;
    }

    public void open() {
        if (file != null){
            try {
                this.mitabIterator = this.reader.iterate(file);
            } catch (IOException e) {
                log.error("Impossible to parse current file");
                this.mitabIterator = null;
                this.errors.put(new DataSourceError(e.getCause().toString(), e.getMessage()), Arrays.<FileSourceContext>asList(new MitabFileSource()));
            }
        }
        else {
            try {
                this.mitabIterator = this.reader.iterate(stream);
            } catch (IOException e) {
                log.error("Impossible to parse current InputStream");
                this.mitabIterator = null;
                this.errors.put(new DataSourceError(e.getCause().toString(), e.getMessage()), Arrays.<FileSourceContext>asList(new MitabFileSource()));
            }
        }
    }

    public void close() {
        if (mitabIterator != null){
            ((PsimiTabIterator) mitabIterator).closeStreamReader();
            mitabIterator = null;
        }
    }
}
