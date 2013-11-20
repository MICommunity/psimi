package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml implementation of ModelledParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledParameter extends XmlParameter implements ModelledParameter{
    private Collection<Publication> publications;

    public XmlModelledParameter() {
        super();
    }

    public XmlModelledParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
        super(type, value, uncertainty, unit);
    }

    public Collection<Publication> getPublications() {
        if (publications == null){
            this.publications = new ArrayList<Publication>(1);
            if (getExperiment() != null){
                Publication pub = getExperiment().getPublication();
                if (pub != null){
                   this.publications.add(pub);
                }
            }
        }
        return this.publications;
    }
}
