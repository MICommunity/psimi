package psidev.psi.mi.jami.bridges.ontologymanager;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccessTemplate;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Extension of OntologyAcessTemplate for jami
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/11</pre>
 */

public interface MIOntologyAccess extends OntologyAccessTemplate<MIOntologyTermI> {

    public String getOntologyID();
    public String getDatabaseIdentifier();
    public String getParentFromOtherOntology();
    public Collection<MIOntologyTermI> getRootTerms();
    public Pattern getDatabaseRegexp();
    public OntologyTermFetcher getOntologyTermFetcher();
}
