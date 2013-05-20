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

public interface Interaction {

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
     * @param rigid: the rigid
     */
    public void setRigid(String rigid);

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
     * Set of checksums computed for this interaction..
     * The Collection cannot be null so when an interaction does not have a checksum, the method should return an empty Collection
     * Ex: rigid:u1FCes02jPb3CGRj1aDkzpbSiuI9606, ...
     * @return the set of checksums
     */
    public Collection<Checksum> getChecksums();

    /**
     * The Collection of annotations describing the interaction.
     * The Collection cannot be null. If the interaction does not have any annotations, the method should return an empty Collection.
     * Ex: figure-legend annotations, comments, cautions, ...
     * @return the annotations
     */
    public Collection<Annotation> getAnnotations();

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
    public CvTerm getType();

    /**
     * Sets the interaction type.
     * @param term : interaction type
     */
    public void setType(CvTerm term);
}
