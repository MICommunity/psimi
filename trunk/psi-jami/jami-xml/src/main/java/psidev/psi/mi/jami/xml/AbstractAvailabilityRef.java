package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.xml.extension.Availability;

/**
 * Abstract class for Availability ref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractAvailabilityRef extends Availability implements XmlIdReference{
    protected int ref;

    public AbstractAvailabilityRef(int ref) {
        this.ref = ref;
    }

    public void registerForResolution() {
        XmlEntryContext.getInstance().getReferences().add(this);
    }
}
