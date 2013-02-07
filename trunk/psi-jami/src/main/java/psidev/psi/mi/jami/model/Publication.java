package psidev.psi.mi.jami.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * A scientific publication which has been curated by an interaction database.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Publication {

    /**
     * The pubmed identifier which identifies the publication.
     * It is a shortcut for the first pubmed identifier in the collection of identifiers.
     * It will be null if the collection of identifiers does not contain any pubmed identifiers.
     * @return the pubmed identifier
     */
    public String getPubmedId();

    /**
     * Sets the pubmed identifier.
     * It will remove the previous pubmed identifier from the collection of identifiers, and add the new one in the collection of identifiers
     * with qualifier identity. If pubmedId is null, it will remove all the pubmed identifiers from the
     * collection of identifiers.
     * @param pubmedId
     */
    public void setPubmedId(String pubmedId);

    /**
     * The doi number which identifies the publication.
     * It is a shortcut for the first doi in the collection of identifiers.
     * It will be null if the collection of identifiers does not contain any doi.
     * @return the doi number
     */
    public String getDoi();

    /**
     * Sets the doi.
     * It will remove the previous doi from the collection of identifiers, and add the new one in the collection of identifiers
     * with qualifier identity. If doi is null, it will remove all the doi from the
     * collection of identifiers.
     * @param doi
     */
    public void setDoi(String doi);

    /**
     * The publication identifiers. Usually a publication only has one pubmed id or DOI number, but it could also have a
     * database internal identifier if the publication is not published yet.
     * The Collection cannot be null. If the publication does not have any identifiers, the method should return an empty Collection
     * Ex: pubmed:14681455
     * @return the publication identifier
     */
    public Collection<Xref> getIdentifiers();

    /**
     * IMEx identifier of the publication if it has been registered in IMEx central as a publication curated following IMEx curation rules.
     * It can be null if the publication is not registered in IMEx central or does not follow the IMEx curation rules.
     * It is a shortcut to the first IMEx imex-primary reference in the list of xrefs.
     * Ex: IM-123
     * @return the IMEx identifier
     */
    public String getImexId();

    /**
     * Assign an IMEx id to a publication.
     * It will add a Xref imex with qualifier imex-primary to the list of xrefs.
     * @param identifier : the IMEx id from IMEx central
     * @throws IllegalArgumentException if
     * - the identifier is null or empty
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
     * It cannot be null. If the publication does not have any authors, the collection should be empty.
     * @return collection of authors
     */
    public List<String> getAuthors();

    /**
     * Other cross references which give more information about the publication.
     * It cannot be null. If the publication does not have any xrefs, the method should return an empty Collection.
     * Ex: other primary references such as DOI : 10.1023/A:1005823620291
     * @return the xrefs
     */
    public Collection<Xref> getXrefs();

    /**
     * Other publication annotations which can give more information about the curated publication.
     * It cannot be null. If the publication does not have any other annotations, the method should return an empty Collection.
     * Ex: topic = dataset value = Cyanobacteria - Interaction dataset based on Cyanobacteria proteins and related species
     * @return the annotations
     */
    public Collection<Annotation> getAnnotations();

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
