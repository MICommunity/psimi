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
     * @param type : interactor type
     * @param name : short name
     * @return the proper instance of the interactor if the type is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromInteractorType(CvTerm type, String name);

    /**
     * Return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     * @param database : database
     * @param name :name
     * @return the proper instance of the interactor if the database is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromDatabase(CvTerm database, String name);

    /**
     * Return the proper instance of the interactor if the database is recognized (the interactor will be returned on the first database which is recognized). It returns null otherwise.
     * @param xrefs : identifiers
     * @param name : name
     * @return the proper instance of the interactor if the database is recognized (the interactor will be returned on the first database which is recognized). It returns null otherwise.
     */
    public Interactor createInteractorFromIdentityXrefs(Collection<? extends Xref> xrefs, String name);

    /**
     * Creates a new Protein with the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return created protein
     */
    public Protein createProtein(String name, CvTerm type);

    /**
     * Creates a new NucleicAcid with the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return created nucleic acid
     */
    public NucleicAcid createNucleicAcid(String name, CvTerm type);

    /**
     * Creates a new Gene with the name
     * @param name : name
     * @return created gene
     */
    public Gene createGene(String name);

    /**
     * Creates a new Complex with the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return  created complex
     */
    public Complex createComplex(String name, CvTerm type);

    /**
     * Creates a new BioactiveEntity with the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return created bioactive entity
     */
    public BioactiveEntity createBioactiveEntity(String name, CvTerm type);

    /**
     * Creates a new Polymer with the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return created polymer
     */
    public Polymer createPolymer(String name, CvTerm type);

    /**
     * Creates a default interactor from the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return created default interactor
     */
    public Interactor createInteractor(String name, CvTerm type);

    /**
     * Creates an interactor pool from the name and interactor type
     * @param name : short name
     * @param type : interactor type
     * @return created interactor pool
     */
    public InteractorPool createInteractorSet(String name, CvTerm type);
}
