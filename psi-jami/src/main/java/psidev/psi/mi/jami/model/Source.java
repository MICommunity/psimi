package psidev.psi.mi.jami.model;

/**
 * Source of the data. Can be an institution, organisation, database, etc.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Source extends CvTerm{

    /**
     * Public URL of the data source.
     * It can be null
     * Ex: www.ebi.ac.uk/intact
     * @return the url
     */
    public String getUrl();

    /**
     * Set the URL
     * @param url
     */
    public void setUrl(String url);

    /**
     * Postal address of the data source.
     * It can be null
     * Ex: European Bioinformatics Institute; Wellcome Trust Genome Campus; Hinxton, Cambridge; CB10 1SD; United Kingdom
     * @return the url
     */
    public String getPostalAddress();

    /**
     * Set the postal address
     * @param address
     */
    public void setPostalAddress(String address);

    /**
     * Bibliographical reference of the data source.
     * It can be null.
     * Ex: 14681455 is the pubmed primary reference for the IntAct database
     * @return the bibref
     */
    public Publication getPublication();

    /**
     * Set the bibliographical reference
     * @param ref : publication reference
     */
    public void setPublication(Publication ref);
}
