package psidev.psi.mi.jami.model;

/**
 * Source of the data. Normally an organisation
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Source extends CvTerm{

    public String getUrl();
    public void setUrl(String url);

    public ExternalIdentifier getBibRef();
    public void setBibRef(ExternalIdentifier ref);
}
