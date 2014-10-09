package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.fetcher.OntologyTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * The ontology fetcher based on OBO file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OboOntologyTermFetcher extends OboFetcherTemplate<OntologyTerm> implements OntologyTermFetcher{
    Collection<OntologyTerm> rootTerms=null;

    public OboOntologyTermFetcher(CvTerm database, String filePath) {
        super(database, new OntologyOboLoader(database), filePath);
    }

    public OboOntologyTermFetcher(String databaseName, String filePath) {
        super(databaseName, new OntologyOboLoader(databaseName), filePath);
    }

    public Collection<OntologyTerm> getRoots() {

        if ( rootTerms != null ) {
            return rootTerms;
        }

        // it wasn't precalculated, then do it here...
        rootTerms = new HashSet<OntologyTerm>();

        for ( Iterator<OntologyTerm> iterator = getId2Term().values().iterator(); iterator.hasNext(); ) {
            OntologyTerm ontologyTerm = iterator.next();

            if ( ontologyTerm.getParents().isEmpty() ) {
                rootTerms.add( ontologyTerm );
            }
        }

        if ( rootTerms.isEmpty() ) {
            return Collections.EMPTY_LIST;
        }

        return rootTerms;
    }
}
