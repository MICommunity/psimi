package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Date;

/**
 * Interaction involving one to several molecules.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Interaction<T extends Participant> {

    /**
     * The short name of the interaction.
     * It can be null.
     * Ex: foxc1-flna, ...
     * @return short name of the interaction
     */
    public String getShortName();

    /**
     * Sets the short name of the interaction
     * @param name : short name
     */
    public void setShortName(String name);

    /**
     * The checksum computed from the rogids from all the proteins involved in the interaction.
     * It is only relevant for protein-protein interactions.
     * This is a shortcut to the first rigid in the list of checksums
     * @return the rigid
     */
    public String getRigid();

    /**
     * Sets the rigid of this interaction.
     * It will remove the previous rigid from the list of checksum and add the new one.
     * If rigid is null, it will remove all the rigid in the list of checksum
     * @param rigid : the rigid
     */
    public void setRigid(String rigid);

    /**
     * The identifiers for an interaction.
     * The Collection cannot be null. If the interaction does not have any identifiers (IMEx is not among the identifiers), the method should return an emtpy Collection.
     * Ex: original interaction database accession, ...
     * @return the xrefs
     */
    public <X extends Xref> Collection<X> getIdentifiers();

    /**
     * The external cross references for an interaction.
     * The Collection cannot be null. If the interaction does not have any xrefs, the method should return an emtpy Collection.
     * Ex: GO process xrefs, GO component xrefs, database accession that can identify the interaction, ...
     * @return the xrefs
     */
    public <X extends Xref> Collection<X> getXrefs();

    /**
     * Set of checksums computed for this interaction..
     * The Collection cannot be null so when an interaction does not have a checksum, the method should return an empty Collection
     * Ex: rigid:u1FCes02jPb3CGRj1aDkzpbSiuI9606, ...
     * @return the set of checksums
     */
    public <C extends Checksum> Collection<C> getChecksums();

    /**
     * The Collection of annotations describing the interaction.
     * The Collection cannot be null. If the interaction does not have any annotations, the method should return an empty Collection.
     * Ex: figure-legend annotations, comments, cautions, ...
     * @return the annotations
     */
    public <A extends Annotation> Collection<A> getAnnotations();

    /**
     * The last update date.
     * It can be null if not relevant or not known.
     * @return the last update date
     */
    public Date getUpdatedDate();

    /**
     * Sets the last update date
     * @param updated : last update date
     */
    public void setUpdatedDate(Date updated);

    /**
     * The creation date.
     * It can be null if not relevant or not known.
     * @return the last update date
     */
    public Date getCreatedDate();

    /**
     * Sets the created date
     * @param created : created date
     */
    public void setCreatedDate(Date created);

    /**
     * The interaction type is a controlled vocabulary term.
     * It can be null.
     * Ex: direct interaction, association, ...
     * @return the interaction type
     */
    public CvTerm getInteractionType();

    /**
     * Sets the interaction type.
     * @param term : interaction type
     */
    public void setInteractionType(CvTerm term);

    /**
     * The collection of participants involved in this interaction.
     * The collection cannot be null. If the interaction does not involve any participants, the method should return an empty set.
     * @return the particiants
     */
    public <T2 extends T> Collection<T2> getParticipants();

    /**
     * This method will add the participant and set the interaction of the new participant to this current interaction
     * @param part : participant to add
     * @return true if participant is added to the list of participants
     */
    public boolean addParticipant(T part);

    /**
     * This method will remove the participant and set the interaction of the new participant to null
     * @param part : participant to remove
     * @return true if participant is removed from the list of participants
     */
    public boolean removeParticipant(T part);

    /**
     * This method will add all the participant and set the interaction of the new participant to this current interaction
     * @param participants : participants to add
     * @return true if participant are added to the list of participants
     */
    public boolean  addAllParticipants(Collection<? extends T> participants);

    /**
     * This method will remove the participant and set the interaction of the removed participant to null.
     * @param participants : participants to remove
     * @return true if participant are removed from the list of participants
     */
    public boolean removeAllParticipants(Collection<? extends T> participants);
}
