package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.Entry;

import java.util.List;

/**
 * Extended interaction for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsi25Interaction<T extends Participant> extends NamedInteraction<T> {

    public List<CvTerm> getInteractionTypes();
    public Entry getEntry();
    public void setEntry(Entry entry);
    public List<InferredInteraction> getInferredInteractions();
    public int getId();
    public void setId(int id);
    public boolean isIntraMolecular();
    public void setIntraMolecular(boolean intra);
}
