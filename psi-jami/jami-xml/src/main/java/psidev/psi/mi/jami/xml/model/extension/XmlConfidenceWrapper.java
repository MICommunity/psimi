package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.bind.annotation.XmlTransient;

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
    private Publication publication;

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
    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    @Override
    public String toString() {
        return this.confidence.toString();
    }
}
