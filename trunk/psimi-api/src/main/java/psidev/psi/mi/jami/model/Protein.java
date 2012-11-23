package psidev.psi.mi.jami.model;

/**
 * Polymer of amino acids.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Protein extends Interactor {

    public String getUniprotkb();
    public void setUniprotkb(String ac);

    public String getRefseq();
    public void setRefseq(String ac);

    public String getGeneName();
    public void setGeneName(String name);

    public String getRogid();
    public void setRogid(String rogid);

    public String getSequence();
    public void setSequence(String sequence);
}
