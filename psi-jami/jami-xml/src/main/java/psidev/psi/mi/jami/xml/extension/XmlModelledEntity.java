package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of Xml modelled entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */
@XmlTransient
public class XmlModelledEntity extends AbstractXmlEntity<ModelledFeature> implements ModelledEntity{

    public XmlModelledEntity() {
    }

    public XmlModelledEntity(Interactor interactor) {
        super(interactor);
    }

    public XmlModelledEntity(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public XmlModelledEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public XmlModelledEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }
}
