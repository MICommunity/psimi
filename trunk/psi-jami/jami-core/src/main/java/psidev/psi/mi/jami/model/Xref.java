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

    public static final String ENSEMBL = "ensembl";
    public static final String ENSEMBL_MI = "MI:0476";
    public static final String ENSEMBL_GENOMES = "ensemblgenomes";
    public static final String ENSEMBL_GENOMES_MI = "MI:1013";
    public static final String ENTREZ_GENE = "entrezgene/locuslink";
    public static final String ENTREZ_GENE_MI = "MI:0477";
    public static final String REFSEQ = "refseq";
    public static final String REFSEQ_MI = "MI:0481";
    public static final String CHEBI = "chebi";
    public static final String CHEBI_MI = "MI:0474";
    public static final String DDBJ_EMBL_GENBANK = "ddbj/embl/genbank";
    public static final String DDBJ_EMBL_GENBANK_MI = "MI:0475";
    public static final String UNIPROTKB = "uniprotkb";
    public static final String UNIPROTKB_MI = "MI:0486";
    public static final String IMEX = "imex";
    public static final String IMEX_MI = "MI:0670";
    public static final String PUBMED = "pubmed";
    public static final String PUBMED_MI = "MI:0446";
    public static final String DOI = "doi";
    public static final String DOI_MI = "MI:0574";
    public static final String INTERPRO = "interpro";
    public static final String INTERPRO_MI = "MI:0449";

    public static final String IMEX_PRIMARY = "imex-primary";
    public static final String IMEX_PRIMARY_MI = "MI:0662";
    public static final String IDENTITY = "identity";
    public static final String IDENTITY_MI = "MI:0356";
    public static final String SECONDARY = "secondary-ac";
    public static final String SECONDARY_MI = "MI:0360";
    public static final String PRIMARY = "primary-reference";
    public static final String PRIMARY_MI = "MI:0358";
    public static final String SEE_ALSO = "see-also";
    public static final String SEE_ALSO_MI = "MI:0361";
    public static final String GO = "go";
    public static final String GO_MI = "MI:0448";
    public static final String METHOD_REFERENCE = "method reference";
    public static final String METHOD_REFERENCE_MI = "MI:0357";
    public static final String RESID = "resid";
    public static final String RESID_MI = "MI:0248";
    public static final String SO = "so";
    public static final String SO_MI = "MI:0601";
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
