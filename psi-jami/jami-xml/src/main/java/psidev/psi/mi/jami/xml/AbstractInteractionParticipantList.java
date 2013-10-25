package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.extension.AbstractXmlInteraction;

/**
 * Abstract class for participant list of an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/10/13</pre>
 */

public abstract class AbstractInteractionParticipantList<P extends Participant, T extends AbstractXmlInteraction<P>> extends AbstractJAXBList<T, P> {

    public AbstractInteractionParticipantList() {
        super();
    }

    @Override
    protected boolean addToSpecificIndex(int index, P element) {
        super.add(index, element);
        getParent().processAddedParticipant(element);
        return true;
    }

    @Override
    protected boolean addElement(P element) {
        if (super.add(element)){
            getParent().processAddedParticipant(element);
            return true;
        }
        return false;
    }
}
