package psidev.psi.mi.jami.utils;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Position;

/**
 * Utility class for Positions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/12/12</pre>
 */

public class PositionUtils {

    public static boolean isUndetermined(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();
        ExternalIdentifier statusId = status.getOntologyIdentifier();

        // status id
        if (statusId != null){
            ExternalIdentifier databaseId = statusId.getDatabase().getOntologyIdentifier();

            // database has psi-mi identifier
            if (databaseId != null && CvTerm.PSI_MI.equalsIgnoreCase(databaseId.getDatabase().getShortName()) && CvTerm.PSI_MI_ID.equals(databaseId.getId())){
                return Position.UNDETERMINED_MI.equals(statusId.getId());
            }
            // database has psi-mi shortlabel
            else if (databaseId == null && CvTerm.PSI_MI.equalsIgnoreCase(statusId.getDatabase().getShortName())){
                return Position.UNDETERMINED_MI.equals(statusId.getId());
            }

            return false;
        }
        else {
            return (Position.UNDETERMINED.equalsIgnoreCase(status.getShortName()) || Position.UNDETERMINED_FULL.equals(status.getShortName()));
        }
    }

    public static boolean isNTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();
        ExternalIdentifier statusId = status.getOntologyIdentifier();

        // status id
        if (statusId != null){
            ExternalIdentifier databaseId = statusId.getDatabase().getOntologyIdentifier();

            // database has psi-mi identifier
            if (databaseId != null && CvTerm.PSI_MI.equalsIgnoreCase(databaseId.getDatabase().getShortName()) && CvTerm.PSI_MI_ID.equals(databaseId.getId())){
                return Position.N_TERMINAL_RANGE_MI.equals(statusId.getId());
            }
            // database has psi-mi shortlabel
            else if (databaseId == null && CvTerm.PSI_MI.equalsIgnoreCase(statusId.getDatabase().getShortName())){
                return Position.N_TERMINAL_RANGE_MI.equals(statusId.getId());
            }

            return false;
        }
        else {
            return (Position.N_TERMINAL_RANGE.equalsIgnoreCase(status.getShortName()) || Position.N_TERMINAL_RANGE_FULL.equals(status.getShortName()));
        }
    }

    public static boolean isCTerminalRange(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();
        ExternalIdentifier statusId = status.getOntologyIdentifier();

        // status id
        if (statusId != null){
            ExternalIdentifier databaseId = statusId.getDatabase().getOntologyIdentifier();

            // database has psi-mi identifier
            if (databaseId != null && CvTerm.PSI_MI.equalsIgnoreCase(databaseId.getDatabase().getShortName()) && CvTerm.PSI_MI_ID.equals(databaseId.getId())){
                return Position.C_TERMINAL_RANGE_MI.equals(statusId.getId());
            }
            // database has psi-mi shortlabel
            else if (databaseId == null && CvTerm.PSI_MI.equalsIgnoreCase(statusId.getDatabase().getShortName())){
                return Position.C_TERMINAL_RANGE_MI.equals(statusId.getId());
            }

            return false;
        }
        else {
            return (Position.C_TERMINAL_RANGE.equalsIgnoreCase(status.getShortName()) || Position.C_TERMINAL_RANGE_FULL.equals(status.getShortName()));
        }
    }

    public static boolean isNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();
        ExternalIdentifier statusId = status.getOntologyIdentifier();

        // status id
        if (statusId != null){
            ExternalIdentifier databaseId = statusId.getDatabase().getOntologyIdentifier();

            // database has psi-mi identifier
            if (databaseId != null && CvTerm.PSI_MI.equalsIgnoreCase(databaseId.getDatabase().getShortName()) && CvTerm.PSI_MI_ID.equals(databaseId.getId())){
                return Position.N_TERMINAL_MI.equals(statusId.getId());
            }
            // database has psi-mi shortlabel
            else if (databaseId == null && CvTerm.PSI_MI.equalsIgnoreCase(statusId.getDatabase().getShortName())){
                return Position.N_TERMINAL_MI.equals(statusId.getId());
            }

            return false;
        }
        else {
            return (Position.N_TERMINAL.equalsIgnoreCase(status.getShortName()) || Position.N_TERMINAL_FULL.equals(status.getShortName()));
        }
    }

    public static boolean isCTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();
        ExternalIdentifier statusId = status.getOntologyIdentifier();

        // status id
        if (statusId != null){
            ExternalIdentifier databaseId = statusId.getDatabase().getOntologyIdentifier();

            // database has psi-mi identifier
            if (databaseId != null && CvTerm.PSI_MI.equalsIgnoreCase(databaseId.getDatabase().getShortName()) && CvTerm.PSI_MI_ID.equals(databaseId.getId())){
                return Position.C_TERMINAL_MI.equals(statusId.getId());
            }
            // database has psi-mi shortlabel
            else if (databaseId == null && CvTerm.PSI_MI.equalsIgnoreCase(statusId.getDatabase().getShortName())){
                return Position.C_TERMINAL_MI.equals(statusId.getId());
            }

            return false;
        }
        else {
            return (Position.C_TERMINAL.equalsIgnoreCase(status.getShortName()) || Position.C_TERMINAL_FULL.equals(status.getShortName()));
        }
    }

    public static boolean isRaggedNTerminal(Position position){
        if (position == null){
            return false;
        }

        CvTerm status = position.getStatus();
        ExternalIdentifier statusId = status.getOntologyIdentifier();

        // status id
        if (statusId != null){
            ExternalIdentifier databaseId = statusId.getDatabase().getOntologyIdentifier();

            // database has psi-mi identifier
            if (databaseId != null && CvTerm.PSI_MI.equalsIgnoreCase(databaseId.getDatabase().getShortName()) && CvTerm.PSI_MI_ID.equals(databaseId.getId())){
                return Position.RAGGED_N_TERMINAL_MI.equals(statusId.getId());
            }
            // database has psi-mi shortlabel
            else if (databaseId == null && CvTerm.PSI_MI.equalsIgnoreCase(statusId.getDatabase().getShortName())){
                return Position.RAGGED_N_TERMINAL_MI.equals(statusId.getId());
            }

            return false;
        }
        else {
            return Position.RAGGED_N_TERMINAL.equalsIgnoreCase(status.getShortName());
        }
    }
}
