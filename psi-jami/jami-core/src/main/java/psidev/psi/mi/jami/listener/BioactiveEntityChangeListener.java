package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.*;


/**
 * A listener for changes to a bioactiveEntity.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface BioactiveEntityChangeListener
        extends InteractorListener<BioactiveEntity>{

    /**
     * Listens for the event where the Chebi identifier has been updated.
     * @param bioactiveEntity   The bioactive entity which has been changed.
     * @param oldId             The old Chebi Id.
     */
    public void onChebiUpdate(BioactiveEntity bioactiveEntity, String oldId);

    /**
     * Listens for the event where the Smile has been updated.
     * @param bioactiveEntity   The bioactive entity which has been changed.
     * @param oldSmile          The old Smile.
     */
    public void onSmileUpdate(BioactiveEntity bioactiveEntity, String oldSmile);

    /**
     * Listens for the event where the Inchi key has been updated.
     * @param bioactiveEntity   The bioactive entity which has been changed.
     * @param oldKey            The old Inchi key.
     */
    public void onStandardInchiKeyUpdate(BioactiveEntity bioactiveEntity, String oldKey);

    /**
     * Listens for the event where the Inchi code has been updated.
     * @param bioactiveEntity   The bioactive entity which has been changed.
     * @param oldInchi          The old Inchi code.
     */
    public void onStandardInchiUpdate(BioactiveEntity bioactiveEntity, String oldInchi);


}
