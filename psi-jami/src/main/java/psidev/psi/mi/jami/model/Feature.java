package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Set;

/**
 * 	Property of a participant that may interfere with the binding of a molecule.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Feature<T extends Feature, P extends Participant> {

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
     * External identifier which describes this feature.
     * It can be null if the feature is not described in an external database.
     * Ex: interpro:IPR003121
     * @return the identifier
     */
    public ExternalIdentifier getIdentifier();

    /**
     * Sets the external identifier
     * @param identifier: identifier
     */
    public void setIdentifier(ExternalIdentifier identifier);

    /**
     * Set of cross references which give more information about the feature.
     * The set cannot be null. If the feature does not have any other xrefs, the method should return an empty set.
     * Ex: GO xrefs to give information about process or function
     * @return the xrefs
     */
    public Set<Xref> getXrefs();

    /**
     * Set of annotations which describe the feature
     * The set cannot be null. If the feature does not have any annotations, the method should return an empty set.
     * Ex: observed ptm, cautions, comments, ...
     * @return the annotations
     */
    public Set<Annotation> getAnnotations();

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
    public Collection<Range> getRanges();

    /**
     * The other features that can bind to this feature.
     * The collection cannot be null. If the feature does not bind with any other features, the method should return an empty collection
     * @return the binding features
     */
    public Collection<T> getBindingFeatures();

    /**
     * The participant to which the feature is attached.
     * It cannot be null.
     * @return the participant
     */
    public P getParticipant();

    /**
     * Sets the participant.
     * @param participant : participant
     * @throws IllegalArgumentException when participant is null
     */
    public void setParticipant(P participant);
}
