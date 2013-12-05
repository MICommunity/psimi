package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.xml.Xml25EntryContext;
import psidev.psi.mi.jami.xml.extension.AbstractAvailability;

/**
 * Abstract class for Availability ref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractAvailabilityRef extends AbstractAvailability implements XmlIdReference{
    protected int ref;

    public AbstractAvailabilityRef(int ref) {
        this.ref = ref;
        registerForResolution();
    }

    public void registerForResolution() {
        Xml25EntryContext.getInstance().registerReference(this);
    }

    public int getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return "Availability Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
