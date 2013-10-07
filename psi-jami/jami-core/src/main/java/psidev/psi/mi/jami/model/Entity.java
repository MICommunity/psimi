package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * An entity is an interactor having several features/annotations/xrefs in a special context/environment (example an interactor within an interactor set or interaction or complex)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/10/13</pre>
 */

public interface Entity<F extends Feature> {

    /**
     * The molecule/complex of molecules which interacts.
     * It cannot be null.
     * @return the interactor
     */
    public Interactor getInteractor();

    /**
     * Sets the interactor
     * @param interactor : interactor
     * @throws IllegalArgumentException when interactor is null
     */
    public void setInteractor(Interactor interactor);

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
     * The causal relationship of this participant on another participant of the interaction.
     * It can be null if the participant does not have any causal relationship or it is not relevant
     * Ex: increasing, decreasing, disrupting, etc.
     * @return the effect of this participant on the interaction
     */
    public CausalRelationship getCausalRelationship();

    /**
     * Sets the interaction effect for this participant on another participant of the interaction.
     * @param relationship : the relationship between this participant and another participant of another interaction
     */
    public void setCausalRelationship(CausalRelationship relationship);

    /**
     * Collection of cross references which give more information about the participant.
     * The set of xrefs cannot be null. If the participant does not have any xrefs, the method should return an empty Collection.
     * Ex: author identifiers, ...
     * @return the xrefs
     */
    public <X extends Xref> Collection<X> getXrefs();

    /**
     * Collection of annotations describing the participant.
     * The set cannot be null. If the participant does not have any annotations, the method should return an empty Collection.
     * @return the annotations
     */
    public <A extends Annotation> Collection<A> getAnnotations();

    /**
     * Collection of aliases which give more information about the participant.
     * The set of aliases cannot be null. If the participant does not have any aliases, the method should return an empty Collection.
     * Ex: author assigned name, ...
     * @return the xrefs
     */
    public <A extends Alias> Collection<A> getAliases();

    /**
     * The stoichiometry for this participant.
     * If the stoichiometry for this participant is unknown, the method should return null.
     * @return the stoichiometry
     */
    public Stoichiometry getStoichiometry();

    /**
     * Sets the mean stoichiometry for this participant.
     * @param stoichiometry : mean stoichiometry value
     */
    public void setStoichiometry(Integer stoichiometry);

    /**
     * Sets the stoichiometry for this participant.
     * @param stoichiometry : the stoichiometry
     */
    public void setStoichiometry(Stoichiometry stoichiometry);

    /**
     * Properties for this participant.
     * The collection cannot be null. If the participant does not have any features, the method should return an empty collection.
     * @return the features
     */
    public <F2 extends F> Collection<F2> getFeatures();
}
