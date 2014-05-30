package psidev.psi.mi.jami.xml.model.extension.xml300;

import java.util.List;

/**
 * Extended interaction evidence for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsiXmlInteractionEvidence extends psidev.psi.mi.jami.xml.model.extension.ExtendedPsiXmlInteractionEvidence{

    public List<ExtendedPsiXmlCausalRelationship> getCausalRelationships();
}
