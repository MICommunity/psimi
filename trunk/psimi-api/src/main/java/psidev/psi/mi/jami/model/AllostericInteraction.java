package psidev.psi.mi.jami.model;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/12</pre>
 */

public interface AllostericInteraction extends CooperativeInteraction {

    public CvTerm getAllostericMechanism();
    public void setAllostericMechanism(CvTerm mechanism);

    public CvTerm getAllosteryType();
    public void setAllosteryType(CvTerm type);

    public ComplexParticipant getAllostericMolecule();
    public void setAllostericMolecule(ComplexParticipant participant);

    public ComplexParticipant getAllostericEffector();
    public void setAllostericEffector(ComplexParticipant effector);

    public BiologicalFeature getAllostericPtm();
    public void setAllostericPtm(BiologicalFeature feature);
}
