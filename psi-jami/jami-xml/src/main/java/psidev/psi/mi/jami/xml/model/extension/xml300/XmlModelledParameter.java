package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ModelledParameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlParameter;
import psidev.psi.mi.jami.xml.model.extension.BibRef;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Xml 3.0 implementation of ModelledParameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlModelledParameter extends AbstractXmlParameter implements ModelledParameter {

    private Publication publication;

    public XmlModelledParameter() {
        super();
    }

    public XmlModelledParameter(CvTerm type, ParameterValue value, BigDecimal uncertainty, CvTerm unit) {
        super(type, value, uncertainty, unit);
    }

    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    @XmlElement(name="bibRef", type = BibRef.class)
    public void setJAXBPublication(Publication publication) {
        setPublication(publication);
    }
}
