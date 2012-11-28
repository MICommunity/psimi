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
     * @return the bibref
     */
    public ExternalIdentifier getBibRef();

    /**
     * Set the bibliographical reference
     * @param ref : publication reference
     */
    public void setBibRef(ExternalIdentifier ref);
}
