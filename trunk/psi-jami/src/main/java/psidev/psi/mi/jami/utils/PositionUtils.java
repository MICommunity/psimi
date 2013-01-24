package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Position;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExternalIdentifier;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Utility class for Positions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class PositionUtils {

    private static CvTerm undetermined;
    private static CvTerm nTerminalRange;
    private static CvTerm cTerminalRange;
    private static CvTerm nTerminal;
    private static CvTerm cTerminal;
    private static CvTerm nTerminalRagged;

    static{
        undetermined = new DefaultCvTerm(Position.UNDETERMINED, new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Position.UNDETERMINED_MI));
        nTerminalRange = new DefaultCvTerm(Position.N_TERMINAL_RANGE, new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Position.N_TERMINAL_RANGE_MI));
        cTerminalRange = new DefaultCvTerm(Position.C_TERMINAL_RANGE, new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Position.C_TERMINAL_RANGE_MI));
        nTerminal = new DefaultCvTerm(Position.N_TERMINAL, new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Position.N_TERMINAL_MI));
        cTerminal = new DefaultCvTerm(Position.C_TERMINAL, new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Position.C_TERMINAL_MI));
        nTerminalRagged = new DefaultCvTerm(Position.RAGGED_N_TERMINAL, new DefaultExternalIdentifier(CvTermFactory.createPsiMiDatabaseNameOnly(), Position.RAGGED_N_TERMINAL_MI));
    }

    public static boolean isUndetermined(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(undetermined, status);
    }

    public static boolean isNTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(nTerminalRange, status);
    }

    public static boolean isCTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(cTerminalRange, status);
    }

    public static boolean isNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(nTerminal, status);
    }

    public static boolean isCTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(cTerminal, status);
    }

    public static boolean isRaggedNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();

        return DefaultCvTermComparator.areEquals(nTerminalRagged, status);
    }
}
