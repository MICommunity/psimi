package psidev.psi.mi.jami.model;

/**
 * Alias is a synonym. It is composed of a name and a type.
 *
 * Ex: alias type = 'gene' and name = 'BRCA2'
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Alias {

    public static final String SYNONYM_MI = "MI:1041";
    public static final String SYNONYM = "synonym";
    public static final String GENE_NAME_MI = "MI:0301";
    public static final String GENE_NAME = "gene name";
    public static final String COMPLEX_SYNONYM = "complex-synonym";
    public static final String COMPLEX_SYNONYM_MI = "MI:0673";
    public static final String AUTHOR_ASSIGNED_NAME = "author assigned name";
    public static final String AUTHOR_ASSIGNED_NAME_MI = "MI:0345";
    public static final String GENE_NAME_SYNONYM = "gene name synonym";
    public static final String GENE_NAME_SYNONYM_MI = "MI:0302";
    public static final String ISOFORM_SYNONYM = "gene name synonym";
    public static final String ISOFORM_SYNONYM_MI = "MI:0304";
    public static final String ORF_NAME = "orf name";
    public static final String ORF_NAME_MI = "MI:0306";
    public static final String LOCUS_NAME = "locus name";
    public static final String LOCUS_NAME_MI = "MI:0305";

    /**
     * The alias type is a controlled vocabulary term.
     * The type can be null.
     * Ex : gene name, gene synonym, locus name, ...
     * @return the type of the current alias
     */
    public CvTerm getType();

    /**
     * Alias name cannot be null.
     * Ex: TP53, BRCA2, ..
     * @return the alias name.
     */
    public String getName();
}
