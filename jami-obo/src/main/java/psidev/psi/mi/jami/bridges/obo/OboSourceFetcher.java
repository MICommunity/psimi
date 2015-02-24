package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.fetcher.SourceFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;

/**
 * Simple fetcher based on OBO files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OboSourceFetcher extends OboFetcherTemplate<Source> implements SourceFetcher{

    public OboSourceFetcher(CvTerm database, String filePath) {
        super(database, new SourceOboLoader(database), filePath);
    }

    public OboSourceFetcher(String databaseName, String filePath) {
        super(databaseName, new SourceOboLoader(databaseName), filePath);
    }
}
