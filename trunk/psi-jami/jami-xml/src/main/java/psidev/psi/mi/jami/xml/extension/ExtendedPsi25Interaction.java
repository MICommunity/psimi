package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.XmlEntry;

import java.util.List;

/**
 * Extended interaction for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsi25Interaction<T extends Participant> extends Interaction<T>{

    public String getFullName();
    public void setFullName(String name);
    public List<Alias> getAliases();
    public List<CvTerm> getInteractionTypes();
    public XmlEntry getEntry();
    public void setEntry(XmlEntry entry);
    public List<InferredInteraction> getInferredInteractions();
    public int getId();
    public void setId(int id);
}
