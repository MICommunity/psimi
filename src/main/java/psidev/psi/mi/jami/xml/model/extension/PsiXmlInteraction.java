package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.NamedInteraction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.model.Entry;

import java.util.Collection;

/**
 * Interaction for PSI-XML which contains the entry
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public interface PsiXmlInteraction<T extends Participant> extends NamedInteraction<T>, FileSourceContext {

    public Entry getEntry();
    public void setEntry(Entry entry);
    public int getId();
    public void setId(int id);
    public boolean isIntraMolecular();
    public void setIntraMolecular(boolean intra);
    public Collection<Alias> getAliases();
}
