package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Utility class for CvTerms
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class CvTermUtils {

    private static CvTerm gene;
    private static CvTerm allosteryMechanism;

    static{
        gene = CvTermFactory.createGeneNameAliasType();
        allosteryMechanism = CvTermFactory.createAllosteryCooperativeMechanism();
    }

    public static CvTerm getGene() {
        return gene;
    }

    public static CvTerm getAllosteryMechanism() {
        return allosteryMechanism;
    }
}
