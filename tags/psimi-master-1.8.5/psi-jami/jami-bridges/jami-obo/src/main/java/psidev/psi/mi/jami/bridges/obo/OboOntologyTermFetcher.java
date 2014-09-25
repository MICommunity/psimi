package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

/**
 * The ontology fetcher based on OBO file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OboOntologyTermFetcher extends OboFetcherTemplate<OntologyTerm> implements OntologyTermFetcher{
    public OboOntologyTermFetcher(CvTerm database, String filePath) {
        super(database, new OntologyOboLoader(database), filePath);
    }

    public OboOntologyTermFetcher(String databaseName, String filePath) {
        super(databaseName, new OntologyOboLoader(databaseName), filePath);
    }
}
