package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * Participant identified in an experimental interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Participant<I, T extends Interactor, F extends Feature> {

    /**
     * The interaction in which the participant is involved.
     * It cannot be null.
     * @return the interaction
     */
    public I getInteraction();

    /**
     * Sets the interaction.
     * @param interaction : experimental interaction
     * @throws IllegalArgumentException when interaction is null.
     */
    public void setInteraction(I interaction);

    /**
     * The molecule/complex of molecules which interacts.
     * It cannot be null.
     * @return the interactor
     */
    public T getInteractor();

    /**
     * Sets the interactor
     * @param interactor : interactor
     * @throws IllegalArgumentException when interactor is null
     */
    public void setInteractor(T interactor);

    /**
     * The biological role of the participant.
     * It is a controlled vocabulary term and cannot be null.
     * It the biological role of a participant is not known or not relevant, the method should return
     * unspecified role (MI:0499)
     * Ex: enzyme, enzyme target, ...
     * @return the biological role
     */
    public CvTerm getBiologicalRole();

    /**
     * Sets the biological role.
     * If the bioRole is null, should create a bioRole
     * @param bioRole : biological role unspecified role (MI:0499)
     */
    public void setBiologicalRole(CvTerm bioRole);

    /**
     * Set of cross references which give more information about the participant.
     * The set of xrefs cannot be null. If the participant does not have any xrefs, the method should return an empty set.
     * Ex: author identifiers, ...
     * @return the xrefs
     */
    public Set<Xref> getXrefs();

    /**
     * Set of annotations describing the participant.
     * The set cannot be null. If the participant does not have any annotations, the method should return an empty set.
     * @return the annotations
     */
    public Set<Annotation> getAnnotations();

    /**
     * Set of aliases which give more information about the participant.
     * The set of aliases cannot be null. If the participant does not have any aliases, the method should return an empty set.
     * Ex: author assigned name, ...
     * @return the xrefs
     */
    public Set<Alias> getAliases();

    /**
     * Properties for this participant which are supported by experimental evidences.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public Collection<F> getFeatures();

    /**
     * The stoichiometry for this participant.
     * If the stoichiometry for this participant is unknown, the method should return null.
     * @return the stoichiometry
     */
    public Integer getStoichiometry();

    /**
     * Sets the stoichiometry for this participant.
     * @param stoichiometry : stoichiometry
     */
    public void setStoichiometry(Integer stoichiometry);
}
