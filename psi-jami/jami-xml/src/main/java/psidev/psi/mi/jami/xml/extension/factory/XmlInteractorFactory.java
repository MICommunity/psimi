package psidev.psi.mi.jami.xml.extension.factory;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.factory.InteractorFactory;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Polymer;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.utils.clone.InteractorCloner;
import psidev.psi.mi.jami.xml.extension.*;

import java.util.Collection;

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

    @Override
    public ExtendedPsi25Interactor createInteractorFromInteractorType(CvTerm type, String name) {
        return (ExtendedPsi25Interactor) super.createInteractorFromInteractorType(type, name);
    }

    @Override
    public ExtendedPsi25Interactor createInteractorFromDatabase(CvTerm database, String name) {
        return (ExtendedPsi25Interactor)super.createInteractorFromDatabase(database, name);
    }

    @Override
    public ExtendedPsi25Interactor createInteractorFromIdentityXrefs(Collection<? extends Xref> xrefs, String name) {
        return (ExtendedPsi25Interactor)super.createInteractorFromIdentityXrefs(xrefs, name);
    }

    public Interactor createInteractorFromXmlInteractorInstance(XmlInteractor source){
        ExtendedPsi25Interactor reloadedInteractorDependingOnType = createInteractorFromInteractorType(source.getInteractorType(), source.getShortName());
        if (reloadedInteractorDependingOnType == null){
            reloadedInteractorDependingOnType = createInteractorFromIdentityXrefs(source.getIdentifiers(), source.getShortName());
        }

        if (reloadedInteractorDependingOnType != null){
            InteractorCloner.copyAndOverrideBasicInteractorProperties(source, reloadedInteractorDependingOnType);
            ((FileSourceContext)reloadedInteractorDependingOnType).setSourceLocator((PsiXmLocator)source.getSourceLocator());
            reloadedInteractorDependingOnType.setId(source.getId());
            if (reloadedInteractorDependingOnType instanceof Polymer){
                ((Polymer)reloadedInteractorDependingOnType).setSequence(source.getSequence());
            }

            return reloadedInteractorDependingOnType;
        }
        else{
            return source;
        }
    }
}
