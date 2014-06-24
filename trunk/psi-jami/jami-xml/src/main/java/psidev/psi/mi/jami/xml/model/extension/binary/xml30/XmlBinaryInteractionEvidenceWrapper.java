package psidev.psi.mi.jami.xml.model.extension.binary.xml30;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlCausalRelationship;
import psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlInteractionEvidence;
import psidev.psi.mi.jami.xml.model.extension.xml300.XmlInteractionEvidence;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 * Xml implementation of BinaryInteractionWrapper with a source locator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>16/10/13</pre>
 */
@XmlTransient
public class XmlBinaryInteractionEvidenceWrapper extends psidev.psi.mi.jami.xml.model.extension.binary.xml25.XmlBinaryInteractionEvidenceWrapper
        implements psidev.psi.mi.jami.xml.model.extension.xml300.ExtendedPsiXmlInteractionEvidence, Serializable {

    public XmlBinaryInteractionEvidenceWrapper(ExtendedPsiXmlInteractionEvidence interaction){
        super(interaction);
    }


    public XmlBinaryInteractionEvidenceWrapper(ExtendedPsiXmlInteractionEvidence interaction, CvTerm complexExpansion){
        super(interaction, complexExpansion);
    }

    @Override
    public List<ExtendedPsiXmlCausalRelationship> getCausalRelationships() {
        return ((XmlInteractionEvidence)getWrappedInteraction()).getCausalRelationships();
    }
}
