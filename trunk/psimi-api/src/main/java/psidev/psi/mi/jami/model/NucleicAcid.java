package psidev.psi.mi.jami.model;

/**
 * Polymers of nucleotides
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>23/11/12</pre>
 */

public interface NucleicAcid extends Interactor {

    public String getDdbjEmblGenbank();
    public void setDdbjEmblGenbank(String id);

    public String getSequence();
    public void setSequence(String sequence);
}
