package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.Complex;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.NamedInteraction;
import psidev.psi.mi.jami.xml.model.Entry;
import psidev.psi.mi.jami.xml.model.extension.xml300.BindingFeatures;

import java.util.List;

/**
 * Extended interaction for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface ExtendedPsiXmlModelledInteraction extends NamedInteraction<ModelledParticipant>, Complex {

    public Entry getEntry();
    public void setEntry(Entry entry);
    public List<BindingFeatures> getBindingFeatures();
    public int getId();
    public void setId(int id);
    public boolean isIntraMolecular();
    public void setIntraMolecular(boolean intra);
}
