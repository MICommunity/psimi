package psidev.psi.mi.jami.model;

/**
 * Interactor for genetic interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Gene extends Interactor{

    public static String GENE = "gene";
    public static String GENE_ID = "MI:0250";

    /**
     * The unique Ensembl accession which identifies the gene.
     * It can be null
     * Ex:ENSG00000172115
     * @return the ENSEMBL accession
     */
    public String getEnsembl();

    /**
     * Sets the Ensembl accession for this gene
     * @param ac : Ensembl accession
     */
    public void setEnsembl(String ac);

    /**
     * The Ensembl genome accession which identifies the gene.
     * It can be null.
     * Ex: ENSG00000139618
     * @return the Ensembl geneome accessiom
     */
    public String getEnsembleGenome();
    public void setEnsemblGenome(String ac);

    /**
     * The Entrez gene id which identifies this gene.
     * It can be null
     * Ex: 54205
     * @return the Gene id
     */
    public String getEntrezGeneId();

    /**
     * Sets the Entrez gene id
     * @param id: Entrez gene id
     */
    public void setEntrezGeneId(String id);

    /**
     * The refseq accession which identifies the gene.
     * It can be null
     * Ex: NM_001071821.1
     * @return the Refseq accession
     */
    public String getRefseq();

    /**
     * Sets the refseq accession
     * @param ac: the refseq accession
     */
    public void setRefseq(String ac);
}
