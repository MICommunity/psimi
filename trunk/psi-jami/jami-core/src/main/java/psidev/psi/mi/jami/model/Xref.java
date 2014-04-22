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

    public static String ENSEMBL = "ensembl";
    public static String ENSEMBL_MI = "MI:0476";
    public static String ENSEMBL_GENOMES = "ensemblgenomes";
    public static String ENSEMBL_GENOMES_MI = "MI:1013";
    public static String ENTREZ_GENE = "entrezgene/locuslink";
    public static String ENTREZ_GENE_MI = "MI:0477";
    public static String REFSEQ = "refseq";
    public static String REFSEQ_MI = "MI:0481";
    public static String CHEBI = "chebi";
    public static String CHEBI_MI = "MI:0474";
    public static String DDBJ_EMBL_GENBANK = "ddbj/embl/genbank";
    public static String DDBJ_EMBL_GENBANK_MI = "MI:0475";
    public static String UNIPROTKB = "uniprotkb";
    public static String UNIPROTKB_MI = "MI:0486";
    public static String IMEX = "imex";
    public static String IMEX_MI = "MI:0670";
    public static String PUBMED = "pubmed";
    public static String PUBMED_MI = "MI:0446";
    public static String DOI = "doi";
    public static String DOI_MI = "MI:0574";
    public static String INTERPRO = "interpro";
    public static String INTERPRO_MI = "MI:0449";

    public static String IMEX_PRIMARY = "imex-primary";
    public static String IMEX_PRIMARY_MI = "MI:0662";
    public static String IDENTITY = "identity";
    public static String IDENTITY_MI = "MI:0356";
    public static String SECONDARY = "secondary-ac";
    public static String SECONDARY_MI = "MI:0360";
    public static String PRIMARY = "primary-reference";
    public static String PRIMARY_MI = "MI:0358";
    public static String SEE_ALSO = "see-also";
    public static String SEE_ALSO_MI = "MI:0361";
    public static String GO = "go";
    public static String GO_MI = "MI:0448";
    public static String METHOD_REFERENCE = "method reference";
    public static String METHOD_REFERENCE_MI = "MI:0357";
    public static String RESID = "resid";
    public static String RESID_MI = "MI:0248";
    public static String SO = "so";
    public static String SO_MI = "MI:0601";
    public static final String CHAIN_PARENT_MI = "MI:0951";
    public static final String CHAIN_PARENT = "chain-parent";
    public static final String ISOFORM_PARENT_MI = "MI:0243";
    public static final String ISOFORM_PARENT = "isoform-parent";

    public static final String INTERACTOR_SET_QUALIFIER="interactor set component";
    public static final String INTERACTOR_SET_QUALIFIER_MI="MI:xxxx";

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
    public String getVersion();

    /**
     * The qualifier of the xref is the reference type and is a controlled vocabulary term.
     * It can be null. If null, the Xref is giving unqualified information which is not an identifier (different from ExternalIdentifier)
     * Ex: primary-reference, see-also, identity, method reference, ...
     * @return the qualifier of the xref
     */
    public CvTerm getQualifier();
}
