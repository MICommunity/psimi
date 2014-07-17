package psidev.psi.mi.jami.factory;

import psidev.psi.mi.jami.model.*;
import java.util.*;

/**
 * Interface for factories allowing creation of a proper interactor depending on the type
 * and xrefs
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public interface InteractorFactory {


    /**
     * Return the proper instance of the interactor if the type is recognized and not null. It returns null otherwise.
     * @param type
     * @param name
     * @return the proper instance of the interactor if the type is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromInteractorType(CvTerm type, String name);

    /**
     * Return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     * @param database
     * @param name
     * @return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromDatabase(CvTerm database, String name);

    /**
     * Return the proper instance of the interactor if the database is recognized (the interactor will be returned on the first database which is recognized). It returns null otherwise.
     * @param xrefs
     * @param name
     * @return the proper instance of the interactor if the database is recognized (the interactor will be returned on the first database which is recognized). It returns null otherwise.
     */
    public Interactor createInteractorFromIdentityXrefs(Collection<? extends Xref> xrefs, String name);

    /**
     * Creates a new Protein with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Protein createProtein(String name, CvTerm type);

    /**
     * Creates a new NucleicAcid with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public NucleicAcid createNucleicAcid(String name, CvTerm type);

    /**
     * Creates a new Gene with the name
     * @param name
     * @return
     */
    public Gene createGene(String name);

    /**
     * Creates a new Complex with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Complex createComplex(String name, CvTerm type);

    /**
     * Creates a new BioactiveEntity with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public BioactiveEntity createBioactiveEntity(String name, CvTerm type);

    /**
     * Creates a new Polymer with the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Polymer createPolymer(String name, CvTerm type);

    /**
     * Creates a default interactor from the name and interactor type
     * @param name
     * @param type
     * @return
     */
    public Interactor createInteractor(String name, CvTerm type);

    public InteractorPool createInteractorSet(String name, CvTerm type);
}
