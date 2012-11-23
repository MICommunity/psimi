package psidev.psi.mi.jami.model;

/**
 * Xref is an extension of ExternalIdentifier and is giving information about the type of the cross reference.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Xref extends ExternalIdentifier {

    public CvTerm getQualifier();
    public void setQualifier(CvTerm refType);
}
