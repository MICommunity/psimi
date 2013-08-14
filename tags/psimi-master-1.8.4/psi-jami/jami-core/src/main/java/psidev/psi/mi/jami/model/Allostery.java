package psidev.psi.mi.jami.model;

/**
 * The allostery cooperative Effect.
 *
 * Reciprocal energetic coupling between two binding events at distinct sites on the same molecule.
 * The first binding event alters the binding or catalytic properties of the molecule for the second binding event
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface Allostery<T extends AllostericEffector> extends CooperativeEffect {

    /**
     * The process that mediates the allosteric
     * response of a molecule upon allosteric post-translational modification or binding of an allosteric effector.
     * It is a controlled vocabulary term and can be null if not known.
     * Ex: allosteric change in dynamics, allosteric change in structure, ...
     * @return the allosteric mechanism
     */
    public CvTerm getAllostericMechanism();

    /**
     * Sets the allosteric mechanism.
     * @param mechanism : allosteric mechanism
     */
    public void setAllostericMechanism(CvTerm mechanism);

    /**
     * The allosteric type indicates the chemical relationship between the two ligands whose binding is allosterically coupled.
     * It is a controlled vocabulary term and can be null if not known.
     * Ex: heterotropic allostery, homotropic allostery
     * @return the allostery stype
     */
    public CvTerm getAllosteryType();

    /**
     * Sets the allostery type.
     * @param type : allostery type
     */
    public void setAllosteryType(CvTerm type);

    /**
     * 	A molecule whose binding or catalytic properties at one site are altered by allosteric post-translational
     * 	modification or binding of an allosteric effector at a distinct site.
     * 	It cannot be null.
     * @return the allosteric molecule
     */
    public ModelledParticipant getAllostericMolecule();

    /**
     * Sets the allosteric molecule.
     * @param participant : allosteric molecule
     * @throws IllegalArgumentException when participant is null
     */
    public void setAllostericMolecule(ModelledParticipant participant);

    /**
     * The effector that elicits an allosteric response.
     * It cannot be null.
     * @return The effector that elicits an allosteric response (participant or feature).
     */
    public T getAllostericEffector();

    /**
     * Sets the effector that elicits an allosteric response.
     * @param effector
     * @throws IllegalArgumentException when effector is null
     */
    public void setAllostericEffector(T effector);
}
