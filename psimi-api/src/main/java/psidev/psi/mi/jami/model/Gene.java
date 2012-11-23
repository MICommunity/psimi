package psidev.psi.mi.jami.model;

/**
 * Interactor for genetic interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface Gene extends Interactor{

    public String getEnsembl();
    public void setEnsembl(String ac);

    public String getEnsembleGenome();
    public void setEnsemblGenome(String ac);

    public String getEntrezGeneId();
    public void setEntrezGeneId(String id);

    public String getRefseqAc();
    public void setRefseqAc(String ac);
}
