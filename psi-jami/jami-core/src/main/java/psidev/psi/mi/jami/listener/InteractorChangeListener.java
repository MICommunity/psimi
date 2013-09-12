package psidev.psi.mi.jami.listener;

import psidev.psi.mi.jami.model.Interactor;


/**
 * A generic listener for an interactor.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 08/08/13
 */
public interface InteractorChangeListener<T extends Interactor>
        extends AliasesChangeListener<T>, XrefsChangeListener<T>, AnnotationsChangeListener<T>, IdentifiersChangeListener<T>,
                ChecksumsChangeListener<T>{

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
}
