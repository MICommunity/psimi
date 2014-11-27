package psidev.psi.mi.jami.json;

/**
 * Type of json export
 *
 * binary_only: the JSON writer will always write binary interactions and break n-ary interactions into binary interactions
 * n_ary_only: the JSON writer will always write the full interaction even if it is n-ary
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/14</pre>
 */

public enum MIJsonType {
    binary_only, n_ary_only
}
