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
        registerForResolution();
    }

    public void registerForResolution() {
        XmlEntryContext.getInstance().registerReference(this);
    }

    public int getRef() {
        return ref;
    }

    @Override
    public boolean isComplexReference() {
        return false;
    }

    @Override
    public String toString() {
        return "Availability Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
