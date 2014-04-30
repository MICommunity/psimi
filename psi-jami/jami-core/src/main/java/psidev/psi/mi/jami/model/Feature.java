package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * 	Property of a participant that may interfere with the binding of a molecule.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Feature<P extends Participant, F extends Feature> {

    public static final String BIOLOGICAL_FEATURE = "biological feature";
    public static final String BIOLOGICAL_FEATURE_MI ="MI:0252";
    public static final String EXPERIMENTAL_FEATURE = "experimental feature";
    public static final String EXPERIMENTAL_FEATURE_MI ="MI:0505";
    public static final String MUTATION = "mutation";
    public static final String MUTATION_MI ="MI:0118";
    public static final String ALLOSTERIC_PTM = "allosteric post-translational modification";
    public static final String ALLOSTERIC_PTM_MI ="MI:1175";
    public static final String VARIANT = "variant";
    public static final String VARIANT_MI ="MI:1241";
    public static final String SUFFICIENT_BINDING = "sufficient binding region";
    public static final String SUFFICIENT_BINDING_MI ="MI:0442";
    public static final String DIRECT_BINDING = "direct binding region";
    public static final String DIRECT_BINDING_MI ="MI:1125";
    public static final String PREREQUISITE_PTM = "prerequisite-ptm";
    public static final String PREREQUISITE_PTM_MI ="MI:0638";
    public static final String DECREASING_PTM = "decreasing-ptm";
    public static final String DECREASING_PTM_MI ="MI:1223";
    public static final String DISRUPTING_PTM = "disrupting-ptm";
    public static final String DISRUPTING_PTM_MI ="MI:1225";
    public static final String INCREASING_PTM = "increasing-ptm";
    public static final String INCREASING_PTM_MI ="MI:1224";
    public static final String RESULTING_PTM = "resulting-ptm";
    public static final String RESULTING_PTM_MI ="MI:0639";
    public static final String RESULTING_CLEAVAGE = "resulting-cleavage";
    public static final String RESULTING_CLEAVAGE_MI ="MI:1233";
    public static final String BINDING_SITE = "binding-associated region";
    public static final String BINDING_SITE_MI ="MI:0117";
    public static final String OBSERVED_PTM = "observed ptm";
    public static final String OBSERVED_PTM_MI ="MI:0925";

    /**
     * The short name of a feature.
     * It can be null
     * Ex: region, SH3 domains
     * @return the short name
     */
    public String getShortName();

    /**
     * Sets the short name of the feature
     * @param name : short name
     */
    public void setShortName(String name);

    /**
     * The full name that describes the feature.
     * It can be null.
     * Ex: SWIB/MDM2 domain (IPR003121)
     * @return the full name
     */
    public String getFullName();

    /**
     * Sets the full name that describes the molecule
     * @param name : full name
     */
    public void setFullName(String name);

    /**
     * The interpro identifier if it exists, null otherwise.
     * It is a shortcut to the first interpro identifier in the list of identifiers.
     * @return the interpro identifier if it exists.
     */
    public String getInterpro();

    /**
     * Sets the interpro identifier.
     * It will remove the old interpro identifier and add a new interpro identity Xref in the list
     * of identifiers. If interpro is null, it will remove all existing interpro Xref in the list of identifiers.
     * @param interpro : the new interpro identifier
     */
    public void setInterpro(String interpro);


    /**
     * Collection of External identifiers which describes this feature.
     * The Collection cannot be null and if the feature is not described in an external databases, the method should return an empty Collection.
     * Ex: interpro:IPR003121
     * @return the identifier
     */
    public <X extends Xref> Collection<X> getIdentifiers();

    /**
     * Collection of cross references which give more information about the feature.
     * The set cannot be null. If the feature does not have any other xrefs, the method should return an empty Collection.
     * Ex: GO xrefs to give information about process or function
     * @return the xrefs
     */
    public <X extends Xref> Collection<X> getXrefs();

    /**
     * Collection of annotations which describe the feature
     * The set cannot be null. If the feature does not have any annotations, the method should return an empty Collection.
     * Ex: observed ptm, cautions, comments, ...
     * @return the annotations
     */
    public <A extends Annotation> Collection<A> getAnnotations();

    /**
     * The type for this feature. It is a controlled vocabulary term and can be null.
     * Ex: fluorophore, tag, mutation, ...
     * @return The feature type
     */
    public CvTerm getType();

    /**
     * Sets the feature type.
     * @param type : feature type
     */
    public void setType(CvTerm type);

    /**
     * The ranges which locate the feature in the interactor sequence/structure.
     * The collection cannot be null. If the feature does not have any ranges, the method should return an empty collection
     * @return a collection of ranges
     */
    public <R extends Range> Collection<R> getRanges();

    /**
     * The effect of this feature on the interaction where the feature has been reported or
     * the interaction dependency between the interaction reporting this feature and this feature.
     * It can be null if the feature does not have any effects on the interaction or it is not relevant
     * Ex: increasing interaction, decreasing interaction, disrupting interaction, etc.
     * Ex: resulting-ptm,prerequisite-ptm ...
     * @return the effect of this feature on the interaction
     */
    public CvTerm getRole();

    /**
     * Sets the interaction effect/role for this feature.
     * @param role : the feature role
     */
    public void setRole(CvTerm role);

    /**
     * The participant to which the feature is attached.
     * It can be null if the feature is not attached to any participants.
     * @return the participant
     */
    public P getParticipant();

    /**
     * Sets the participant.
     * @param participant : participant
     */
    public void setParticipant(P participant);

    /**
     * Sets the participant and add this feature to its list of features.
     * If participant is null, it will remove this feature from the previous participant attached to this feature
     * @param participant : participant
     */
    public void setParticipantAndAddFeature(P participant);

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public <T extends F> Collection<T> getLinkedFeatures();

    /**
     * Collection of aliases for a feature.
     * The Collection cannot be null and if the experiment does not have any aliases, the method should return an empty Collection.
     * @return the aliases
     */
    public <A extends Alias> Collection<A> getAliases();
}
