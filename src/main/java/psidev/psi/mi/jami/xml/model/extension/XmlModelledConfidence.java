package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Xml implementation of ModelledConfidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledConfidence extends XmlConfidence implements ModelledConfidence {

    Publication publication;

    public XmlModelledConfidence() {
        super();

    }

    public XmlModelledConfidence(XmlOpenCvTerm type, String value) {
        super(type, value);
    }

    public Publication getPublication() {
        if (publication == null){
            for (Experiment exp : getExperiments()){
                if (exp.getPublication() != null){
                    this.publication = exp.getPublication();
                    break;
                }
            }
        }
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}
