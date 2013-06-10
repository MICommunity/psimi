package psidev.psi.mi.jami.mitab.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultFeature;

/**
 * A MitabFeature is a feature in MITAB with some free text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/06/13</pre>
 */

public class MitabFeature extends DefaultFeature{

    private String text;

    public MitabFeature() {
        super();
    }

    public MitabFeature(CvTerm type) {
        super(type);
    }

    public MitabFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
