package psidev.psi.mi.jami.bridges.ontologymanager;

import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;

import java.util.Set;

/**
 * Extension of OntologyTermI for jami
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/11/11</pre>
 */

public interface MIOntologyTermI extends OntologyTermI {

    public String getObsoleteMessage();
    public String getRemappedTerm();
    public Set<String> getPossibleTermsToRemapTo();
    public OntologyTerm getDelegate();
}
