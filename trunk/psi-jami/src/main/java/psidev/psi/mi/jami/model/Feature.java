package psidev.psi.mi.jami.model;

import java.util.Collection;

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
    public Collection<Xref> getIdentifiers();

    /**
     * Collection of cross references which give more information about the feature.
     * The set cannot be null. If the feature does not have any other xrefs, the method should return an empty Collection.
     * Ex: GO xrefs to give information about process or function
     * @return the xrefs
     */
    public Collection<Xref> getXrefs();

    /**
     * Collection of annotations which describe the feature
     * The set cannot be null. If the feature does not have any annotations, the method should return an empty Collection.
     * Ex: observed ptm, cautions, comments, ...
     * @return the annotations
     */
    public Collection<Annotation> getAnnotations();

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
