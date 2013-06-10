package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.impl.DefaultConfidence;

import java.util.Collection;
import java.util.Collections;

/**
 * A MitabConfidence is a confidence with some text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/06/13</pre>
 */

public class MitabConfidence extends DefaultConfidence implements ModelledConfidence{

    private String text;

    public MitabConfidence(CvTerm type, String value) {
        super(type, value);
    }

    public MitabConfidence(CvTerm type, String value, String text) {
        super(type, value);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Collection<Publication> getPublications() {
        return Collections.EMPTY_LIST;
    }
}
