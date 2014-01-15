package psidev.psi.mi.jami.tab.extension;

import psidev.psi.mi.jami.factory.InteractorFactory;
import psidev.psi.mi.jami.model.*;

import java.util.Collection;
import java.util.Iterator;

/**
 * The MITAB extension of the InteractorFactory
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class MitabInteractorFactory extends InteractorFactory {

    public MitabInteractorFactory() {
        super();
    }

    @Override
    public MitabProtein createProtein(String name, CvTerm type) {
        return new MitabProtein(name, type);
    }

    @Override
    public MitabNucleicAcid createNucleicAcid(String name, CvTerm type) {
        return new MitabNucleicAcid(name, type);
    }

    @Override
    public MitabGene createGene(String name) {
        return new MitabGene(name);
    }

    @Override
    public MitabComplex createComplex(String name, CvTerm type) {
        return new MitabComplex(name, type);
    }

    @Override
    public MitabBioactiveEntity createBioactiveEntity(String name, CvTerm type) {
        return new MitabBioactiveEntity(name, type);
    }

    @Override
    public MitabPolymer createPolymer(String name, CvTerm type) {
        return new MitabPolymer(name, type);
    }

    @Override
    public MitabInteractor createInteractor(String name, CvTerm type) {
        return new MitabInteractor(name, type);
    }

    @Override
    public MitabInteractorPool createInteractorSet(String name, CvTerm type) {
        return new MitabInteractorPool(name, type);
    }

    /**
     * Return the proper instance of the interactor if at least one type is recognized (always take the first recognized type). It returns null otherwise.
     * @param types
     * @param name
     * @return the proper instance of the interactor if at least one type (always take the first recognized type) is recognized. It returns null otherwise.
     */
    public Interactor createInteractorFromInteractorTypes(Collection<MitabCvTerm> types, String name){

        Interactor interactor = null;
        Iterator<MitabCvTerm> typesIterator = types.iterator();
        while (interactor == null && typesIterator.hasNext()){
            interactor = createInteractorFromInteractorType(typesIterator.next(), name);
        }

        return interactor;
    }
}
