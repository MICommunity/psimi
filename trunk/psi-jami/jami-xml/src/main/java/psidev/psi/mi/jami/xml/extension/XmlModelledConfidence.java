package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml implementation of ModelledConfidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledConfidence extends XmlConfidence implements ModelledConfidence{

    Collection<Publication> publications;

    public XmlModelledConfidence() {
        super();

    }

    public XmlModelledConfidence(XmlOpenCvTerm type, String value) {
        super(type, value);
    }

    public Collection<Publication> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<Publication>(getExperiments().size());
            for (Experiment exp : getExperiments()){
                if (exp.getPublication() != null){
                    publications.add(exp.getPublication());
                }
            }
        }
        return this.publications;
    }
}
