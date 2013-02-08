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

public interface Interaction<P extends Participant> {

    /**
     * The short name of the interaction.
     * It can be null.
     * Ex: foxc1-flna, ...
     * @return
     */
    public String getShortName();

    /**
     * Sets the short name of the interaction
     * @param name : short name
     */
    public void setShortName(String name);

    /**
     * The identifiers for an interaction.
     * The Collection cannot be null. If the interaction does not have any identifiers (IMEx is not among the identifiers), the method should return an emtpy Collection.
     * Ex: original interaction database accession, ...
     * @return the xrefs
     */
    public Collection<Xref> getIdentifiers();

    /**
     * The external cross references for an interaction.
     * The Collection cannot be null. If the interaction does not have any xrefs, the method should return an emtpy Collection.
     * Ex: GO process xrefs, GO component xrefs, database accession that can identify the interaction, ...
     * @return the xrefs
     */
    public Collection<Xref> getXrefs();

    /**
     * The Collection of annotations describing the interaction.
     * The Collection cannot be null. If the interaction does not have any annotations, the method should return an empty Collection.
     * Ex: figure-legend annotations, comments, cautions, ...
     * @return the annotations
     */
    public Collection<Annotation> getAnnotations();

    /**
     * The collection of participants involved in this interaction.
     * The collection cannot be null. If the interaction does not involve any participants, the method should return an empty set.
     * @return the particiants
     */
    public Collection<P> getParticipants();

    /**
     * The source which curated this interaction. It can be an organization, institute, ...
     * It can be null if the source is unknown or not relevant.
     * Ex: IntAct, MINT, DIP, ...
     * @return the source
     */
    public Source getSource();

    /**
     * Sets the source of the interaction.
     * @param source: source for this interaction
     */
    public void setSource(Source source);

    /**
     * Boolean value to know if an interaction is negative.
     * It is false by default (positive interaction by default)
     * @return true if the interaction is negative
     */
    public boolean isNegative();

    /**
     * Sets the negative boolean value.
     * @param negative: negative value
     */
    public void setNegative(boolean negative);

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
     * The confidences in this interaction.
     * The Collection cannot be null. If the interaction does not have any confidences, the method should return an empty Collection.
     * Ex: author based scores, statistical confidences, ...
     * @return the confidences
     */
    public Collection<Confidence> getConfidences();

    /**
     * The interaction type is a controlled vocabulary term.
     * It can be null.
     * Ex: direct interaction, association, ...
     * @return the interaction type
     */
    public CvTerm getType();

    /**
     * Sets the interaction type.
     * @param term : interaction type
     */
    public void setType(CvTerm term);
}
