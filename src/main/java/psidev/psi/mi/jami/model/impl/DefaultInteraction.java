package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;

/**
 * Default implementation for interaction
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the interaction object is a complex object.
 * To compare interaction objects, you can use some comparators provided by default:
 * - DefaultInteractionBaseComparator
 * - UnambiguousInteractionBaseComparator
 * - DefaultCuratedInteractionBaseComparator
 * - UnambiguousCuratedInteractionBaseComparator
 * - DefaultInteractionComparator
 * - UnambiguousInteractionComparator
 * - DefaultCuratedInteractionComparator
 * - UnambiguousCuratedInteractionComparator
 * - AbstractInteractionBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultInteraction extends AbstractInteraction<Participant> {
    public DefaultInteraction() {
        super();
    }

    public DefaultInteraction(String shortName) {
        super(shortName);
    }

    public DefaultInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }
}
