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

    public static String SYNONYM_MI = "MI:1041";
    public static String SYNONYM = "synonym";

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
