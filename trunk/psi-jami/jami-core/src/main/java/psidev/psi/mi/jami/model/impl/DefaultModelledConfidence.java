package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ModelledInteraction
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with UnambiguousConfidenceComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public class DefaultModelledConfidence extends DefaultConfidence implements ModelledConfidence {

    private Collection<Publication> publications;

    public DefaultModelledConfidence(CvTerm type, String value) {
        super(type, value);
    }

    protected void initialisePublications(){
        publications = new ArrayList<Publication>();
    }

    protected void initialisePublicationsWith(Collection<Publication> publications){
        if (publications == null){
            this.publications = Collections.EMPTY_LIST;
        }
        else {
            this.publications = publications;
        }
    }

    public Collection<Publication> getPublications() {
        if (this.publications == null){
            initialisePublications();
        }
        return publications;
    }
}
