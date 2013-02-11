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
    private static CvTerm psimi;
    private static CvTerm psimod;
    private static CvTerm identity;
    private static CvTerm undetermined;
    private static CvTerm nTerminalRange;
    private static CvTerm cTerminalRange;
    private static CvTerm nTerminal;
    private static CvTerm cTerminal;
    private static CvTerm nTerminalRagged;
    private static CvTerm greaterThan;
    private static CvTerm lessThan;

    public static CvTerm getGene() {
        if (gene == null){
            gene = CvTermFactory.createGeneNameAliasType();
        }
        return gene;
    }

    public static CvTerm getAllosteryMechanism() {
        if (allosteryMechanism == null){
            allosteryMechanism = CvTermFactory.createAllosteryCooperativeMechanism();
        }
        return allosteryMechanism;
    }

    public static CvTerm getPsimi() {
        if (psimi == null){
            psimi = CvTermFactory.createPsiMiDatabaseNameOnly();
        }
        return psimi;
    }

    public static CvTerm getPsimod() {
        if (psimod == null){
            psimod = CvTermFactory.createPsiModDatabase();
        }
        return psimod;
    }

    public static CvTerm getIdentity() {
        if (identity == null){
            identity = CvTermFactory.createIdentityQualifierNameOnly();
        }
        return identity;
    }

    public static CvTerm getUndetermined() {
        if (undetermined == null){
            undetermined = CvTermFactory.createUndeterminedStatus();
        }
        return undetermined;
    }

    public static CvTerm getNTerminalRange() {
        if (nTerminalRange == null){
            nTerminalRange = CvTermFactory.createNTerminalRangeStatus();
        }
        return nTerminalRange;
    }

    public static CvTerm getCTerminalRange() {
        if (cTerminalRange == null){
            cTerminalRange = CvTermFactory.createCTerminalRangeStatus();
        }
        return cTerminalRange;
    }

    public static CvTerm getNTerminal() {
        if (nTerminal == null){
            nTerminal = CvTermFactory.createNTerminalStatus();
        }
        return nTerminal;
    }

    public static CvTerm getCTerminal() {
        if (cTerminal == null){
            cTerminal = CvTermFactory.createCTerminalStatus();
        }
        return cTerminal;
    }

    public static CvTerm getNTerminalRagged() {
        if (nTerminalRagged == null){
            nTerminalRagged = CvTermFactory.createRaggedNTerminalStatus();
        }
        return nTerminalRagged;
    }

    public static CvTerm getGreaterThan() {
        if (greaterThan == null){
            greaterThan = CvTermFactory.createGreaterThanRangeStatus();
        }
        return greaterThan;
    }

    public static CvTerm getLessThan() {
        if (lessThan == null){
            lessThan = CvTermFactory.createLessThanRangeStatus();
        }
        return lessThan;
    }
}
