package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Stoichiometry;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation for entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlTransient
public class XmlEntity extends AbstractXmlEntity<Feature>{
    public XmlEntity() {
        super();
    }

    public XmlEntity(Interactor interactor) {
        super(interactor);
    }

    public XmlEntity(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public XmlEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public XmlEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }
}
