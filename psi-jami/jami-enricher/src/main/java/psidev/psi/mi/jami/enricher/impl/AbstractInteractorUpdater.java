package psidev.psi.mi.jami.enricher.impl;

import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.listener.InteractorEnricherListener;
import psidev.psi.mi.jami.enricher.util.EnricherUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;
import psidev.psi.mi.jami.utils.comparator.organism.DefaultOrganismComparator;

/**
 * Abstract class for updater of interactors
 *
 * Minimal update:
 *
 * - enrich shortname of an interactor. If the shortname in the interactor to enrich id different from
 * the shortname loaded from the fetched interactor, it will override the shortname of the interactor to enrich with the shortname of the fetched interactor
 * - enrich fullname of an interactor. If the fullname in the interactor to enrich id different from
 * the fullName loaded from the fetched interactor, it will override the fullname of the interactor to enrich with the fullname of the fetched interactor
 * it will ignore the fullname loaded from the fetched interactor.
 * - enrich identifiers (uniprot, ensembl, etc.) of an interactor. It will use DefaultXrefComparator to compare identifiers and add missing identifiers.
 * It will remove identifiers that are not in the fectched interactor
 * - enrich aliases (gene name, etc.) of an interactor. It will use DefaultAliasComparator to compare aliases and add missing aliases without
 * removing any existing aliases.
 * - enrich interactor type of an interactor if the cv term enricher is not null.  If the interactor type in the interactor to enrich id different from
 * the interactor type loaded from the fetched interactor (see DefaultCvTermComparator), it will override the interactor type of the interactor to enrich with the interactor type of the fetched interactor
 * - enrich organism of an interactor if the organismEnricher is not null.  If the organism in the interactor to enrich id different from
 * the organism loaded from the fetched interactor (see DefaultOrganismComparator), it will override the organism of the interactor to enrich with the organism of the fetched interactor
 *
 * Full update:
 *
 * - enrich xrefs. It will use DefaultXrefComparator to compare xrefs and add missing xrefs without
 * removing any existing xrefs. It will use DefaultXrefComparator to compare identifiers and add missing identifiers.
 * It will remove xrefs that are not in the fectched interactor
 * - enrich checksums, It will use DefaultChecksumComparator to compare checksums and add missing xrefs without
 * removing any existing checksums. It will use DefaultXrefComparator to compare identifiers and add missing identifiers.
 * It will remove checksum that are not in the fectched interactor
 * - enrich annotations It will use DefaultAnnotationComparator to compare annotations and add missing xrefs without
 * removing any existing annotations. It will use DefaultXrefComparator to compare identifiers and add missing identifiers.
 * It will remove annotations that are not in the fectched interactor
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/10/13</pre>
 */

public abstract class AbstractInteractorUpdater<T extends Interactor> extends AbstractInteractorEnricher<T> {

    private AbstractInteractorEnricher<T> interactorEnricher;

    public AbstractInteractorUpdater(AbstractInteractorEnricher<T> interactorEnricher){
        if (interactorEnricher == null){
           throw new IllegalArgumentException("The base interactor enricher is required");
        }
        this.interactorEnricher = interactorEnricher;
    }

    @Override
    protected void processShortLabel(T bioactiveEntityToEnrich, T fetched) throws EnricherException{
        if(!fetched.getShortName().equalsIgnoreCase(bioactiveEntityToEnrich.getShortName())){
            String oldValue = bioactiveEntityToEnrich.getShortName();
            bioactiveEntityToEnrich.setShortName(fetched.getShortName());
            if(getListener() != null)
                getListener().onShortNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    public void processAliases(T bioactiveEntityToEnrich, T fetched) throws EnricherException{
        EnricherUtils.mergeAliases(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getAliases(), fetched.getAliases(), true,
                getListener());
    }

    @Override
    protected void processIdentifiers(T bioactiveEntityToEnrich, T fetched) throws EnricherException{
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getIdentifiers(), fetched.getIdentifiers(), true, true,
                getListener(), getListener());
    }

    @Override
    public void processFullName(T bioactiveEntityToEnrich, T fetched) throws EnricherException{
        if((fetched.getFullName() != null && !fetched.getFullName().equalsIgnoreCase(bioactiveEntityToEnrich.getFullName())
                || (fetched.getFullName() == null && bioactiveEntityToEnrich.getFullName() != null))){
            String oldValue = bioactiveEntityToEnrich.getFullName();
            bioactiveEntityToEnrich.setFullName(fetched.getFullName());
            if(getListener() != null)
                getListener().onFullNameUpdate(bioactiveEntityToEnrich , oldValue);
        }    }

    @Override
    public void processInteractorType(T entityToEnrich, T fetched) throws EnricherException {
        if (!DefaultCvTermComparator.areEquals(entityToEnrich.getInteractorType(), fetched.getInteractorType())){
            CvTerm old = entityToEnrich.getInteractorType();
            entityToEnrich.setInteractorType(fetched.getInteractorType());
            if (getListener() != null){
                getListener().onInteractorTypeUpdate(entityToEnrich, old);
            }
        }
        else if (getCvTermEnricher() != null
                && entityToEnrich.getInteractorType() != fetched.getInteractorType()){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType(), fetched.getInteractorType());
        }

        if (getCvTermEnricher() != null){
            getCvTermEnricher().enrich(entityToEnrich.getInteractorType());
        }
    }

    @Override
    public void processOrganism(T entityToEnrich, T fetched) throws EnricherException {
        if (!DefaultOrganismComparator.areEquals(entityToEnrich.getOrganism(), fetched.getOrganism())){
            Organism old = entityToEnrich.getOrganism();
            entityToEnrich.setOrganism(fetched.getOrganism());
            if (getListener() != null){
                getListener().onOrganismUpdate(entityToEnrich, old);
            }
        }
        else if (getOrganismEnricher() != null
                && entityToEnrich.getOrganism() != fetched.getOrganism()){
            getOrganismEnricher().enrich(entityToEnrich.getOrganism(), fetched.getOrganism());
        }

        if (getOrganismEnricher() != null && entityToEnrich.getOrganism() != null){
            getOrganismEnricher().enrich(entityToEnrich.getOrganism());
        }
    }

    @Override
    protected void processChecksums(T bioactiveEntityToEnrich, T fetched) throws EnricherException {
        EnricherUtils.mergeChecksums(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getChecksums(), fetched.getChecksums(), true,
                getListener());
    }

    @Override
    protected void processAnnotations(T objectToEnrich, T fetchedObject)throws EnricherException{
        EnricherUtils.mergeAnnotations(objectToEnrich, objectToEnrich.getAnnotations(), fetchedObject.getAnnotations(), true,
                getListener());
    }

    @Override
    protected void processXrefs(T bioactiveEntityToEnrich, T fetched) throws EnricherException{
        EnricherUtils.mergeXrefs(bioactiveEntityToEnrich, bioactiveEntityToEnrich.getXrefs(), fetched.getXrefs(), true, false,
                getListener(), getListener());
    }

    @Override
    public InteractorEnricherListener<T> getListener() {
        return interactorEnricher.getListener();
    }

    @Override
    public void setListener(InteractorEnricherListener<T> listener) {
        this.interactorEnricher.setListener(listener);
    }

    @Override
    public T find(T objectToEnrich) throws EnricherException {
        return interactorEnricher.find(objectToEnrich);
    }

    @Override
    protected void onEnrichedVersionNotFound(T objectToEnrich) throws EnricherException {
        interactorEnricher.onEnrichedVersionNotFound(objectToEnrich);
    }

    @Override
    protected boolean isFullEnrichment() {
        return false;
    }

    @Override
    protected void onCompletedEnrichment(T objectToEnrich) {
        interactorEnricher.onCompletedEnrichment(objectToEnrich);
    }

    @Override
    protected void onInteractorCheckFailure(T objectToEnrich, T fetchedObject) throws EnricherException {
        interactorEnricher.onInteractorCheckFailure(objectToEnrich, fetchedObject);
    }

    @Override
    protected boolean canEnrichInteractor(T entityToEnrich, T fetchedEntity) throws EnricherException{
        return interactorEnricher.canEnrichInteractor(entityToEnrich, fetchedEntity);
    }

    protected AbstractInteractorEnricher<T> getInteractorEnricher() {
        return interactorEnricher;
    }
}
