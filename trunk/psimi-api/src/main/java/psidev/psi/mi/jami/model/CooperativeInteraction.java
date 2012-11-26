package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/12</pre>
 */

public interface CooperativeInteraction extends Complex {

    public CvTerm getCooperativeMechanism();
    public void setMechanism(CvTerm mechanism);

    public Collection<Complex> getAffectedInteractions();

    public CvTerm getEffectOutCome();
    public void setEffectOutCome(CvTerm effect);

    public CvTerm getResponse();
    public void setResponse(CvTerm response);
}
