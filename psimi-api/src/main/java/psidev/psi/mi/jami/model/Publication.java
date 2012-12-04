package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * A scientific publication which has been curated by an interaction database.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Publication {

    /**
     * The publication identifier. Usually a pubmed id or DOI number, it could also refers to a
     * database internal identifier if the publication is not published yet.
     * The identifier could be null if the publications is not published or will not be published in a journal
     * Ex: pubmed:14681455
     * @return the publication identifier
     */
    public ExternalIdentifier getIdentifier();

    /**
     * Set the publication identifier
     * @param identifier : publication identifier
     */
    public void setIdentifier(ExternalIdentifier identifier);

    /**
     * IMEx identifier of the publication if it has been registered in IMEx central as a publication curated following IMEx curation rules.
     * It can be null if the publication is not registered in IMEx central or does not follow the IMEx curation rules.
     * Ex: IM-123
     * @return the IMEx identifier
     */
    public String getImexId();

    /**
     * Assign an IMEx id to a publication.
     * @param identifier : the IMEx id from IMEx central
     * @throws IllegalArgumentException if
     * - the identifier is null, empty or not a valid IMEx identifier
     * - the publication already has an IMEx identifier
     */
    public void assignImexId(String identifier);

    /**
     * The publication title. It can be null.
     * @return the title
     */
    public String getTitle();

    /**
     * Set the publication title
     * @param title : publication title
     */
    public void setTitle(String title);

    /**
     * The journal where the publication has been published.
     * It can be null if not published.
     * @return the journal
     */
    public String getJournal();

    /**
     * Set the journal where the publication has been published
     * @param journal : the journal
     */
    public void setJournal(String journal);

    /**
     * The date of publication.
     * It can be null if not published
     * @return the publication date
     */
    public Date getPublicationDate();

    /**
     * Set the date of publication.
     * @param date : publication date
     */
    public void setPublicationDate(Date date);

    /**
     * The List of authors with the same order as it appears in the publication.
     * It cannot be null. If the publication does not have any authors, the list should be empty.
     * @return list of authors
     */
    public List<String> getAuthors();

    /**
     * Other cross references which give more information about the publication.
     * It cannot be null. If the publication does not have any xrefs, the method should return an empty set.
     * Ex: other primary references such as DOI : 10.1023/A:1005823620291
     * @return the xrefs
     */
    public Set<Xref> getXrefs();

    /**
     * Other publication annotations which can give more information about the curated publication.
     * It cannot be null. If the publication does not have any other annotations, the method should return an empty Set.
     * Ex: topic = dataset value = Cyanobacteria - Interaction dataset based on Cyanobacteria proteins and related species
     * @return the annotations
     */
    public Set<Annotation> getAnnotations();

    /**
     * The curated experiments which have been described in the publication.
     * It cannot be null. If no experiments have been curated in this publication, the method should return an empty collection.
     * @return the collection of experiments
     */
    public Collection<Experiment> getExperiments();

    /**
     * The curation depth for this publication.
     * If the curation depth is undefined, the method should not return null but
     * CurationDepth.undefined.
     * Ex: IMEx, MIMIx, undefined
     * @return the curation depth
     */
    public CurationDepth getCurationDepth();

    /**
     * Set the curation depth of the publication.
     * If the curation depth is null, it should set the cuuration depth to CurationDepth.undefined
     * @param curationDepth : the curation depth
     */
    public void setCurationDepth(CurationDepth curationDepth);

    /**
     * Publication released date by the interaction database or resource which curated the publication.
     * It can be null if the publication is not released.
     * @return the released date
     */
    public Date getReleasedDate();

    /**
     * Set the released date of the curated publication
     * @param released : the released date
     */
    public void setReleasedDate(Date released);
}
