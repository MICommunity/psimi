package psidev.psi.mi.jami.enricher.enricherimplementation.protein.event;

import psidev.psi.mi.jami.model.Protein;

/**
 * Details of how the entry has been identified.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 16:12
 */
public class QueryDetails{

    private String queryID = null;
    private String queryIDType = null;
    private String fetcherType = null;
    private final String objectType;
    private Protein protein;

    public QueryDetails(String objectType, String queryID, String queryIDType, String fetcherType, Protein protein) {
        this.objectType = objectType;
        this.queryID = queryID;
        this.queryIDType = queryIDType;
        this.fetcherType = fetcherType;
        this.protein = protein;
    }
}
