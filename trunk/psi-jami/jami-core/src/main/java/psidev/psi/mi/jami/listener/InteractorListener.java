package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;

import java.util.EventListener;


/**
 * A generic listener for an interactor.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface InteractorListener<T extends Interactor>
        extends EventListener {

    /**
     * Listens for the event where the shortName has been changed.
     * @param interactor        The interactor which has changed.
     * @param oldShortName      The old shortName.
     */
    public void onShortNameUpdate(T interactor , String oldShortName);

    /**
     * Listens for the event where the fullName has been changed.
     * @param interactor        The interactor which has changed.
     * @param oldFullName       The old fullName.
     */
    public void onFullNameUpdate(T interactor , String oldFullName);

    /**
     * Listen to the event where the organism of a interactor has been initialised.
     * This event happens when a interactor does not have any organisms
     * @param interactor        The interactor which has changed.
     */
    public void onAddedOrganism(T interactor);

    /**
     * Listen to the event where the interactor type has been initialised.
     * This event happens when a interactor does not have any interactor types
     * @param interactor        The interactor which has changed.
     */
    public void onAddedInteractorType(T interactor);

    /**
     * Listen to the event where an identifier has been added to the protein identifiers.
     * @param interactor        The interactor which has changed.
     * @param added             The added identifier
     */
    public void onAddedIdentifier(T interactor , Xref added);

    /**
     * Listen to the event where an identifier has been removed from the protein identifiers.
     * @param interactor        The interactor which has changed.
     * @param removed           The removed identifier.
     */
    public void onRemovedIdentifier(T interactor , Xref removed);

    /**
     * Listen to the event where a xref has been added to the protein xrefs.
     * @param interactor        The interactor which has changed.
     * @param added             The added Xref.
     */
    public void onAddedXref(T interactor , Xref added);

    /**
     * Listen to the event where a xref has been removed from the interactor xrefs.
     * @param interactor        The interactor which has changed.
     * @param removed           The removed Xref.
     */
    public void onRemovedXref(T interactor , Xref removed);

    /**
     * Listen to the event where an alias has been added to the interactor aliases.
     * @param interactor        The interactor which has changed.
     * @param added             The added alias.
     */
    public void onAddedAlias(T interactor , Alias added);

    /**
     * Listen to the event where an alias has been removed from the interactor aliases.
     * @param interactor        The interactor which has changed.
     * @param removed           The removed alias.
     */
    public void onRemovedAlias(T interactor , Alias removed);


    /**
     * Listen to the event where a checksum has been added to the interactor checksums.
     * @param interactor        The interactor which has changed.
     * @param added             The added checksum.
     */
    public void onAddedChecksum(T interactor , Checksum added);

    /**
     * Listen to the event where a checksum has been removed from the interactor checksums.
     * @param interactor        The interactor which has changed.
     * @param removed           The removed checksum.
     */
    public void onRemovedChecksum(T interactor , Checksum removed);


     // ANNOTATIONS do not have any listeners currently


}
