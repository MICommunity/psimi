package psidev.psi.mi.jami.crosslink;

/**
 * The different types of crosslink CSV input files
 * - mix: Mix of binary and n-ary interactions which can be distinguished using bait/complex column
 * - single_nary: the CSV file is reporting one single n-ary interaction
 * - binary_only: the CSV file only contains binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/14</pre>
 */

public enum CsvType {

    mix, single_nary, binary_only
}
