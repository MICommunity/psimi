package psidev.psi.mi.jami.xml.model.extension.factory;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.factory.InteractorFactory;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import psidev.psi.mi.jami.utils.clone.InteractorCloner;
import psidev.psi.mi.jami.xml.model.extension.*;

import java.util.Collection;
import java.util.Iterator;

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
    public XmlInteractorPool createInteractorSet(String name, CvTerm type) {
        return new XmlInteractorPool(name, type);
    }

    @Override
    public ExtendedPsiXmlInteractor createInteractorFromInteractorType(CvTerm type, String name) {
        return (ExtendedPsiXmlInteractor) super.createInteractorFromInteractorType(type, name);
    }

    @Override
    public ExtendedPsiXmlInteractor createInteractorFromDatabase(CvTerm database, String name) {
        return (ExtendedPsiXmlInteractor)super.createInteractorFromDatabase(database, name);
    }

    @Override
    public ExtendedPsiXmlInteractor createInteractorFromIdentityXrefs(Collection<? extends Xref> xrefs, String name) {
        return (ExtendedPsiXmlInteractor)super.createInteractorFromIdentityXrefs(xrefs, name);
    }

    public Interactor createInteractorFromXmlInteractorInstance(AbstractXmlInteractor source){
        ExtendedPsiXmlInteractor reloadedInteractorDependingOnType = createInteractorFromInteractorType(source.getInteractorType(), source.getShortName());
        if (reloadedInteractorDependingOnType == null){
            reloadedInteractorDependingOnType = createInteractorFromIdentityXrefs(source.getIdentifiers(), source.getShortName());
        }

        if (reloadedInteractorDependingOnType != null){

            // interactor pool
            if (reloadedInteractorDependingOnType instanceof InteractorPool){
                Collection<Xref> components = XrefUtils.collectAllXrefsHavingQualifier(source.getXrefs(), Xref.INTERACTOR_SET_QUALIFIER_MI,
                        Xref.INTERACTOR_SET_QUALIFIER);

                if (!components.isEmpty()){
                    // remove component xrefs from source
                    source.getXrefs().removeAll(components);
                    // create interactor from component
                    processInteractorPool(components, (InteractorPool)reloadedInteractorDependingOnType, source.getSequence(), source.getOrganism());
                }
            }
            // polymer
            else if (reloadedInteractorDependingOnType instanceof Polymer){
                ((Polymer)reloadedInteractorDependingOnType).setSequence(source.getSequence());
            }

            InteractorCloner.copyAndOverrideBasicInteractorProperties(source, reloadedInteractorDependingOnType);
            ((FileSourceContext)reloadedInteractorDependingOnType).setSourceLocator((PsiXmlLocator)source.getSourceLocator());
            reloadedInteractorDependingOnType.setId(source.getId());

            // we don't have any identifiers, we look back at the xrefs
            if (reloadedInteractorDependingOnType.getIdentifiers().isEmpty() && !reloadedInteractorDependingOnType.getXrefs().isEmpty()){
                Iterator<Xref> refIterator = reloadedInteractorDependingOnType.getXrefs().iterator();
                while (refIterator.hasNext()){
                    Xref ref = refIterator.next();
                    if (CvTermUtils.isCvTerm(ref.getDatabase(), Xref.UNIPROTKB_MI, Xref.UNIPROTKB)
                            || CvTermUtils.isCvTerm(ref.getDatabase(), Xref.REFSEQ_MI, Xref.REFSEQ)
                            || CvTermUtils.isCvTerm(ref.getDatabase(), Xref.ENSEMBL_MI, Xref.ENSEMBL)
                            || CvTermUtils.isCvTerm(ref.getDatabase(), Xref.CHEBI_MI, Xref.CHEBI)
                            || CvTermUtils.isCvTerm(ref.getDatabase(), Xref.ENSEMBL_GENOMES_MI, Xref.ENSEMBL_GENOMES)
                            || CvTermUtils.isCvTerm(ref.getDatabase(), Xref.ENTREZ_GENE_MI, Xref.ENTREZ_GENE)
                            || CvTermUtils.isCvTerm(ref.getDatabase(), Xref.DDBJ_EMBL_GENBANK_MI, Xref.DDBJ_EMBL_GENBANK)){
                        refIterator.remove();
                        reloadedInteractorDependingOnType.getIdentifiers().add(ref);
                    }
                }
            }

            return reloadedInteractorDependingOnType;
        }
        else{
            return source;
        }
    }

    private void processInteractorPool(Collection<Xref> xref, InteractorPool pool, String sequence, Organism organism) {
        for (Xref ref : xref){
            Interactor subInteractor = createInteractorFromDatabase(ref.getDatabase(), ref.getId().toLowerCase());
            if (subInteractor != null){
                subInteractor.getIdentifiers().add(new XmlXref(ref.getDatabase(), ref.getId(), ref.getVersion(), CvTermUtils.createIdentityQualifier()));
                ((XmlInteractor)subInteractor).setSourceLocator(((XmlXref) ref).getSourceLocator());
            }
            // create a default interactor
            else{
                subInteractor = createInteractor(ref.getId().toLowerCase(), CvTermUtils.createUnknownInteractorType());
                subInteractor.getIdentifiers().add(new XmlXref(ref.getDatabase(), ref.getId(), ref.getVersion(), CvTermUtils.createIdentityQualifier()));
                ((XmlInteractor)subInteractor).setSourceLocator(((XmlXref)ref).getSourceLocator());
            }

            // add sequence
            if (subInteractor instanceof Polymer){
                ((Polymer) subInteractor).setSequence(sequence);
            }

            // add organism
            subInteractor.setOrganism(organism);

            // add the component to the interactor pool
            pool.add(subInteractor);
        }
    }
}
