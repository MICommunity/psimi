package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;

import javax.xml.bind.annotation.XmlTransient;

/**
 * A wrapper for confidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */
@XmlTransient
public class XmlConfidenceWrapper extends XmlModelledConfidence{

    private Confidence confidence;

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
}
