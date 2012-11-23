package psidev.psi.mi.jami.model;

/**
 * Molecules showing activity in a living system
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface BioactiveEntity extends Interactor {

    public String getChebi();
    public void setChebi(String id);

    public String getSmile();
    public void setSmile(String smile);

    public String getStandardInchiKey();
    public void setStandardInchiKey(String key);

    public String getStandardInchi();
    public void setStandardInchi(String inchi);
}
