package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.EventListener;

/**
 * CvTerm change listener
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public interface CvTermChangeListener extends EventListener {

    /**
     * Listen to the event where the shortName of a cv term has been changed.
     * @param cv
     * @param oldShortName
     */
    public void onShortNameUpdate(CvTerm cv, String oldShortName);

    /**
     * Listen to the event where the fullName of a cv term has been changed.
     * If oldFullName is null, it means that the fullName of the cv term has been initialised.
     * If the current fullName of the cv term is null, it means that the fullName has been reset
     * @param cv
     * @param oldFullName
     */
    public void onFullNameUpdate(CvTerm cv, String oldFullName);

    /**
     * Listen to the event where the MI identifier of a cv term has been changed.
     * If oldMI is null, it means that a MI identifier has been added to the cv term.
     * If the MI of the cv term is null, it means that the MI identifier of the cv term has been removed
     * @param cv
     * @param oldMI
     */
    public void onMIIdentifierUpdate(CvTerm cv, String oldMI);

    /**
     * Listen to the event where the MOD identifier of a cv term has been changed.
     * If oldMOD is null, it means that a MOD identifier has been added to the cv term.
     * If the MOD of the cv term is null, it means that the MOD identifier of the cv term has been removed
     * @param cv
     * @param oldMOD
     */
    public void onMODIdentifierUpdate(CvTerm cv, String oldMOD);

    /**
     * Listen to the event where the PAR identifier of a cv term has been changed.
     * If oldPAR is null, it means that a PAR identifier has been added to the cv term.
     * If the PAR of the cv term is null, it means that the PAR identifier of the cv term has been removed
     * @param cv
     * @param oldPAR
     */
    public void onPARIdentifierUpdate(CvTerm cv, String oldPAR);

    /**
     * Listen to the event where an identifier has been added to the cv term identifiers.
     * @param cv
     * @param added
     */
    public void onAddedIdentifier(CvTerm cv, Xref added);

    /**
     * Listen to the event where an identifier has been removed from the cv term identifiers.
     * @param cv
     * @param removed
     */
    public void onRemovedIdentifier(CvTerm cv, Xref removed);

    /**
     * Listen to the event where a xref has been added to the cv term xrefs.
     * @param cv
     * @param added
     */
    public void onAddedXref(CvTerm cv, Xref added);

    /**
     * Listen to the event where a xref has been removed from the cv term xrefs.
     * @param cv
     * @param removed
     */
    public void onRemovedXref(CvTerm cv, Xref removed);

    /**
     * Listen to the event where a synonym has been added to the cv term synonyms.
     * @param cv
     * @param added
     */
    public void onAddedSynonym(CvTerm cv, Alias added);

    /**
     * Listen to the event where a synonym has been removed from the cv term synonyms.
     * @param cv
     * @param removed
     */
    public void onRemovedSynonym(CvTerm cv, Alias removed);
}
