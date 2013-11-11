package psidev.psi.mi.jami.xml.reference;

import psidev.psi.mi.jami.xml.XmlEntryContext;

/**
 * Abstract implementation for XmlIdReference
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/10/13</pre>
 */

public abstract class AbstractXmlIdReference implements XmlIdReference {
    protected int ref;

    public AbstractXmlIdReference(int ref) {
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
    public String toString() {
        return "Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
    }
}
