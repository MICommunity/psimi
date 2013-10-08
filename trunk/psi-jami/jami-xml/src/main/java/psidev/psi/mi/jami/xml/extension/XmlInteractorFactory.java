package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.factory.InteractorFactory;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Interactor factory for XML interactors
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */

public class XmlInteractorFactory extends InteractorFactory{

    public XmlInteractorFactory() {
        super();
    }

    @Override
    public XmlProtein createProtein(String name, CvTerm type) {
        return new XmlProtein(name, type);
    }

    @Override
    public XmlNucleciAcid createNucleicAcid(String name, CvTerm type) {
        return new XmlNucleciAcid(name, type);
    }

    @Override
    public XmlGene createGene(String name) {
        return new XmlGene(name);
    }

    @Override
    public XmlComplex createComplex(String name, CvTerm type) {
        return new XmlComplex(name, type);
    }

    @Override
    public XmlBioactiveEntity createBioactiveEntity(String name, CvTerm type) {
        return new XmlBioactiveEntity(name, type);
    }

    @Override
    public XmlPolymer createPolymer(String name, CvTerm type) {
        return new XmlPolymer(name, type);
    }

    @Override
    public XmlInteractor createInteractor(String name, CvTerm type) {
        return new XmlInteractor(name, type);
    }

    @Override
    public XmlInteractorSet createInteractorSet(String name, CvTerm type) {
        return new XmlInteractorSet(name, type);
    }
}
