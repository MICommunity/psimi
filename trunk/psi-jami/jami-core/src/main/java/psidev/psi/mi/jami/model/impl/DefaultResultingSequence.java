package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.ResultingSequence;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.comparator.range.ResultingSequenceComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ResultingSequence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/05/13</pre>
 */

public class DefaultResultingSequence implements ResultingSequence {

    private String originalSequence;
    private String newSequence;
    private Collection<Xref> xrefs;

    public DefaultResultingSequence(){
        this.originalSequence = null;
        this.newSequence = null;
    }

    public DefaultResultingSequence(String oldSequence, String newSequence){
        this.originalSequence = oldSequence;
        this.newSequence = newSequence;
    }

    protected void initialiseXrefs(){
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseXrefsWith(Collection<Xref> xrefs){
        if (xrefs == null){
            this.xrefs = Collections.EMPTY_LIST;
        }
        else {
            this.xrefs = xrefs;
        }
    }

    public String getNewSequence() {
        return newSequence;
    }

    public String getOriginalSequence() {
        return originalSequence;
    }

    public Collection<Xref> getXrefs() {
        if (xrefs == null){
           initialiseXrefs();
        }
        return xrefs;
    }

    public void setNewSequence(String sequence) {
        this.newSequence = sequence;
    }

    public void steOriginalSequence(String sequence) {
        this.originalSequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ResultingSequence)){
            return false;
        }

        return ResultingSequenceComparator.areEquals(this, (ResultingSequence) o);
    }

    @Override
    public int hashCode() {
        return ResultingSequenceComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return (originalSequence != null ? "original sequence: "+originalSequence : "") +
                (newSequence != null ? "new sequence: "+newSequence : "");
    }
}
