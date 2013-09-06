package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Simple fetcher based on OBO files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OboCvTermFetcher extends OboFetcherTemplate<CvTerm> implements CvTermFetcher<CvTerm>{

    public OboCvTermFetcher(CvTerm database, String filePath) {
        super(database, new BasicOboLoader(database), filePath);
    }

    public OboCvTermFetcher(String databaseName, String filePath) {
        super(databaseName, new BasicOboLoader(databaseName), filePath);
    }
}
