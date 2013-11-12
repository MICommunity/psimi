package psidev.psi.mi.jami.xml;

/**
 * Index that can assign/retrieve an id for a given MI object.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public interface PsiXml25ObjectIndex {

    public int extractIdFor(Object o);

    public void clear();
}
