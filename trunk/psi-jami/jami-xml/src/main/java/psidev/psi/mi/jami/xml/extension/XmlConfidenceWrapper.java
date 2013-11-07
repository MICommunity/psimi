package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A wrapper for confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlConfidenceWrapper implements ModelledConfidence{

    private Confidence confidence;
    private Collection<Publication> publications;

    public XmlConfidenceWrapper(Confidence conf){
        if (conf == null){
           throw new IllegalArgumentException("A confidence wrapper needs a non null Confidence");
        }
        this.confidence = conf;
    }

    @Override
    public CvTerm getType() {
        return this.confidence.getType();
    }

    @Override
    public String getValue() {
        return this.confidence.getValue();
    }

    @Override
    public Collection<Publication> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<Publication>();
        }
        return this.publications;
    }

    @Override
    public String toString() {
        return this.confidence.toString();
    }
}
