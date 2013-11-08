package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.InteractionSource;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract class for an InteractionSource coming from a MITAB file.
 *
 * This datasource only provides interaction iterator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public abstract class AbstractMitabSource<T extends Interaction, P extends Participant, F extends Feature> implements MIFileDataSource, InteractionSource<T> {
    private AbstractMitabStreamSource<T,P,F> delegatedSource;
    private Collection<T> loadedInteractions;

    public AbstractMitabSource(AbstractMitabStreamSource<T,P,F> delegatedSource){
        if (delegatedSource == null){
            throw new IllegalArgumentException("The delegated MITAB stream source is required.");
        }
        this.delegatedSource = delegatedSource;
    }

    public Collection<T> getInteractions() throws MIIOException {
        if (this.loadedInteractions == null){
            initialiseInteractionCollection();
        }
        return this.loadedInteractions;
    }

    public long getNumberOfInteractions() {
        if (this.loadedInteractions == null){
            initialiseInteractionCollection();
        }
        return this.loadedInteractions.size();
    }

    public Iterator<T> getInteractionsIterator() throws MIIOException {
        if (this.loadedInteractions == null){
            initialiseInteractionCollection();
        }
        return this.loadedInteractions.iterator();
    }

    public MIFileParserListener getFileParserListener() {
        return this.delegatedSource.getFileParserListener();
    }

    public boolean validateSyntax() throws MIIOException {
        return this.delegatedSource.validateSyntax();
    }

    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException {
        return this.delegatedSource.validateSyntax(listener);
    }

    public void initialiseContext(Map<String, Object> options) {
        this.delegatedSource.initialiseContext(options);
    }

    public void close() throws MIIOException {
        this.delegatedSource.close();
        this.loadedInteractions = null;
    }

    public void reset() throws MIIOException {
        this.delegatedSource.reset();
        this.loadedInteractions = null;
    }

    private void initialiseInteractionCollection(){
        Iterator<T> interactionIterator = this.delegatedSource.getInteractionsIterator();
        this.loadedInteractions = new ArrayList<T>();
        while(interactionIterator.hasNext()){
            this.loadedInteractions.add(interactionIterator.next());
        }
    }
}
