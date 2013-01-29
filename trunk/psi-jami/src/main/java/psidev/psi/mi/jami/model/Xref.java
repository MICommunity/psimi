package psidev.psi.mi.jami.model;

/**
 * Cross reference to an external database/resource which can give more information about an object.
 * Ex:
 * - GO cross references for an interactor to give information about its biological role(s) or location.
 * - publication primary references
 * - identifier of an object (use ExternalIdentifier)
 * - imex primary references
 * - secondary references to an external database
 * - ...
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Xref {

    public static String IDENTITY = "identity";
    public static String IDENTITY_MI = "MI:0356";
    public static String ENSEMBL = "ensembl";
    public static String ENSEMBL_ID = "MI:0476";
    public static String ENSEMBL_GENOMES = "ensemblgenomes";
    public static String ENSEMBL_GENOMES_ID = "MI:1013";
    public static String ENTREZ_GENE = "entrezgene/locuslink";
    public static String ENTREZ_GENE_ID = "MI:0477";
    public static String REFSEQ = "refseq";
    public static String REFSEQ_ID = "MI:0481";
    public static String CHEBI = "chebi";
    public static String CHEBI_ID = "MI:0474";

    /**
     * The database is a controlled vocabulary term. It cannot be null.
     * @return the database
     */
    public CvTerm getDatabase();

    /**
     * The identifier in the external database/resource. It cannot be null or empty.
     * @return the database identifier
     */
    public String getId();

    /**
     * The version of the identifier in the database/resource if relevant.
     * It can be null if no versions have been specified
     * @return the version
     */
    public Integer getVersion();

    /**
     * The qualifier of the xref is the reference type and is a controlled vocabulary term.
     * It can be null. If null, the Xref is giving unqualified information which is not an identifier (different from ExternalIdentifier)
     * Ex: primary-reference, see-also, identity, method reference, ...
     * @return the qualifier of the xref
     */
    public CvTerm getQualifier();
}
