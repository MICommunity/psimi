package psidev.psi.mi.jami.model;

/**
 * Reciprocal energetic coupling between two binding events at distinct sites on the same molecule.
 * The first binding event alters the binding or catalytic properties of the molecule for the second binding event
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26/11/12</pre>
 */

public interface AllostericInteraction extends CooperativeInteraction {

    /**
     * The process that mediates the allosteric
     * response of a molecule upon allosteric post-translational modification or binding of an allosteric effector.
     * It is a controlled vocabulary term and cannot be null.
     * Ex: allosteric change in dynamics, allosteric change in structure, ...
     * @return the allosteric mechanism
     */
    public CvTerm getAllostericMechanism();

    /**
     * Sets the allosteric mechanism.
     * @param mechanism : allosteric mechanism
     * @throws IllegalArgumentException when mechanism is null
     */
    public void setAllostericMechanism(CvTerm mechanism);

    /**
     * The allosteric type indicates the chemical relationship between the two ligands whose binding is allosterically coupled.
     * It is a controlled vocabulary term and cannot be null.
     * Ex: heterotropic allostery, homotropic allostery
     * @return the allostery stype
     */
    public CvTerm getAllosteryType();

    /**
     * Sets the allostery type.
     * @param type : allostery type
     * @throws IllegalArgumentException when the type is null
     */
    public void setAllosteryType(CvTerm type);

    /**
     * 	A molecule whose binding or catalytic properties at one site are altered by allosteric post-translational
     * 	modification or binding of an allosteric effector at a distinct site.
     * 	It cannot be null.
     * @return the allosteric molecule
     */
    public Component getAllostericMolecule();

    /**
     * Sets the allosteric molecule.
     * @param participant : allosteric molecule
     * @throws IllegalArgumentException when participant is null
     */
    public void setAllostericMolecule(Component participant);

    /**
     * A ligand that elicits an allosteric response upon binding to a target molecule.
     * It can be null
     * @return the allosteric effector
     */
    public Component getAllostericEffector();

    /**
     * Sets the allosteric effector.
     * @param effector: allosteric effector
     */
    public void setAllostericEffector(Component effector);

    /**
     * A post-translational modification that elicits an allosteric response upon addition to a target molecule.
     * It can be null.
     * @return the allosteric PTM
     */
    public BiologicalFeature getAllostericPtm();

    /**
     * Sets the allosteric PTM.
     * @param feature : allosteric PTM
     */
    public void setAllostericPtm(BiologicalFeature feature);
}
