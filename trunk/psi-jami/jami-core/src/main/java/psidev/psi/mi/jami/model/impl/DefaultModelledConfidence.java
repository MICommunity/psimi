package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;

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

    private Publication publication;

    public DefaultModelledConfidence(CvTerm type, String value) {
        super(type, value);
    }

    public DefaultModelledConfidence(CvTerm type, String value, Publication publication) {
        super(type, value);
        this.publication = publication;
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
