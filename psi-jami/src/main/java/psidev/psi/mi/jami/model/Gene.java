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
     * It is a shortcut which should point to the first ensembl identifier in the collection of identifiers.
     * Ex:ENSG00000172115
     * @return the ENSEMBL accession
     */
    public String getEnsembl();

    /**
     * Sets the Ensembl accession for this gene.
     * It will remove the old ensembl identifier from the collection of identifiers and replace it
     * with the new ensembl identifier. If the new ensembl identifier is null, all the existing ensembl identifiers will be removed from the
     * collection of identifiers
     * @param ac : Ensembl accession
     */
    public void setEnsembl(String ac);

    /**
     * The Ensembl genome accession which identifies the gene.
     * It can be null.
     * It is a shortcut which should point to the first ensembl genomes identifier in the collection of identifiers.
     * Ex: ENSG00000139618
     * @return the Ensembl geneome accessiom
     */
    public String getEnsembleGenome();

    /**
     * Sets the Ensembl genomes identifier
     * It will remove the old ensembl genomes identifier from the collection of identifiers and replace it
     * with the new ensembl genomes identifier. If the new ensembl genomes identifier is null, all the existing ensembl genomes identifiers will be removed from the
     * collection of identifiers
     * @param ac
     */
    public void setEnsemblGenome(String ac);

    /**
     * The Entrez gene id which identifies this gene.
     * It can be null
     * It is a shortcut which should point to the first entrez gene identifier in the collection of identifiers.
     * Ex: 54205
     * @return the Gene id
     */
    public String getEntrezGeneId();

    /**
     * Sets the Entrez gene id.
     * It will remove the old entrez/gene identifier from the collection of identifiers and replace it
     * with the new entrez/gene identifier. If the new entrez/gene identifier is null, all the existing entrez/gene identifiers will be removed from the
     * collection of identifiers
     * @param id: Entrez gene id
     */
    public void setEntrezGeneId(String id);

    /**
     * The refseq accession which identifies the gene.
     * It can be null
     * It is a shortcut which should point to the first refseq identifier in the collection of identifiers.
     * Ex: NM_001071821.1
     * @return the Refseq accession
     */
    public String getRefseq();

    /**
     * Sets the refseq accession.
     * It will remove the old refseq identifier from the collection of identifiers and replace it
     * with the new refseq identifier. If the new refseq identifier is null, all the existing refseq identifiers will be removed from the
     * collection of identifiers
     * @param ac: the refseq accession
     */
    public void setRefseq(String ac);
}
