package psidev.psi.mi.jami.model;

/**
 * Identifier from an external database or resource which allows to identify an object without ambiguity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/11/12</pre>
 */
public interface ExternalIdentifier extends Xref{

    /**
     * It should return identity (MI:0356) qualifier
     * @return
     */
    public CvTerm getQualifier();
}
