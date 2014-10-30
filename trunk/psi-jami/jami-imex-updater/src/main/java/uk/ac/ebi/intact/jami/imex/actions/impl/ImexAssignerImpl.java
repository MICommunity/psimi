package uk.ac.ebi.intact.jami.imex.actions.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.XrefUtils;
import uk.ac.ebi.intact.jami.bridges.imex.ImexCentralClient;
import uk.ac.ebi.intact.jami.imex.actions.ImexAssigner;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class will assign an IMEx id to a publication using imex central webservice and update experiments and interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/03/12</pre>
 */
public class ImexAssignerImpl implements ImexAssigner {

    private static final Log log = LogFactory.getLog(ImexAssignerImpl.class);

    private ImexCentralClient imexCentral;
    private int currentIndex=1;
    private Pattern interaction_imex_regexp = Pattern.compile("(IM-[1-9][0-9]*)-([1-9][0-9]*)");

    public void assignImexIdentifier(Publication publication, Publication imexPublication) throws BridgeFailedException {

        String pubId = publication.getPubmedId() != null ? publication.getPubmedId() : publication.getDoi();
        String source = publication.getPubmedId() != null ? Xref.PUBMED : Xref.DOI;
        if (pubId == null && !publication.getIdentifiers().isEmpty()) {
            Xref id = publication.getXrefs().iterator().next();
            source = id.getDatabase().getShortName();
            pubId = id.getId();
        }

        imexPublication = imexCentral.fetchPublicationImexAccession(pubId, source, true);

        // set imex id
        if (imexPublication.getImexId() != null) {
            publication.assignImexId(imexPublication.getImexId());
        }
    }

    public boolean updateImexIdentifierForExperiment(Experiment experiment, String imexId) throws EnricherException {

        if (imexId != null) {

            Collection<Xref> imexPrimaryRefs = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(experiment.getXrefs(),
                    Xref.IMEX_MI,
                    Xref.IMEX,
                    Xref.IMEX_PRIMARY_MI,
                    Xref.IMEX_PRIMARY);

            if (imexPrimaryRefs.isEmpty()) {
                experiment.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX,
                        Xref.IMEX_MI,
                        imexId,
                        Xref.IMEX_PRIMARY,
                        Xref.IMEX_PRIMARY_MI));

            } else if (imexPrimaryRefs.size() == 1) {
                if (!imexPrimaryRefs.iterator().next().getId().equals(imexId)) {
                    throw new EnricherException("The experiment already has an IMEx identifier : " + imexPrimaryRefs.iterator().next().getId()
                            + " and it cannot be updated to " + imexId);
                }
                return false;
            }
            // we may have conflict of imex-primary
            else {
                // collect all imex references having same id
                Collection<Xref> imexRefs = XrefUtils.collectAllXrefsHavingDatabaseAndId(imexPrimaryRefs, Xref.IMEX_MI, Xref.IMEX, imexId);

                // remove duplicated references
                if (imexRefs.size() == imexPrimaryRefs.size()) {
                    Iterator<Xref> refIterator = imexRefs.iterator();
                    // keep first
                    Xref mainRef = refIterator.next();
                    while (refIterator.hasNext()) {
                        experiment.getXrefs().remove(refIterator.next());
                    }

                    return true;
                }
                // we have imex conflicting xrefs
                else {
                    throw new EnricherException("The experiment has several IMEx identifiers and cannot be updated");
                }
            }
        }

        return false;
    }

    public boolean updateImexIdentifierForInteraction(InteractionEvidence interaction, String imexId) throws EnricherException{

        if (imexId != null){
            if (!isEntitledToImex(interaction)){
                return false;
            }
            else{
                if (currentIndex == 0){
                    initialiseCurrentIndex(interaction);
                }

                Collection<Xref> imexPrimaryRefs = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(interaction.getXrefs(),
                        Xref.IMEX_MI,
                        Xref.IMEX,
                        Xref.IMEX_PRIMARY_MI,
                        Xref.IMEX_PRIMARY);

                if (imexPrimaryRefs.isEmpty()) {
                    interaction.getXrefs().add(XrefUtils.createXrefWithQualifier(Xref.IMEX,
                            Xref.IMEX_MI,
                            imexId+"-"+currentIndex,
                            Xref.IMEX_PRIMARY,
                            Xref.IMEX_PRIMARY_MI));
                    currentIndex++;
                    return true;

                } else if (imexPrimaryRefs.size() == 1) {
                    if (!imexPrimaryRefs.iterator().next().getId().startsWith(imexId+"-")) {
                        throw new EnricherException("The interaction already has an IMEx identifier : " + imexPrimaryRefs.iterator().next().getId()
                                + " and it cannot be updated to " + imexId);
                    }
                    return false;
                }
                // we may have conflict of imex-primary
                else {
                    // collect all imex references having same id
                    Collection<Xref> imexRefs = XrefUtils.collectAllXrefsHavingDatabaseAndId(imexPrimaryRefs, Xref.IMEX_MI, Xref.IMEX, imexId+"-"+currentIndex);

                    // remove duplicated references
                    if (imexRefs.size() == imexPrimaryRefs.size()) {
                        Iterator<Xref> refIterator = imexRefs.iterator();
                        // keep first
                        Xref mainRef = refIterator.next();
                        while (refIterator.hasNext()) {
                            interaction.getXrefs().remove(refIterator.next());
                        }

                        return true;
                    }
                    // we have imex conflicting xrefs
                    else {
                        throw new EnricherException("The experiment has several IMEx identifiers and cannot be updated");
                    }
                }
            }
        }
        else {
            return false;
        }
    }

    private void initialiseCurrentIndex(InteractionEvidence interaction) {
        if (interaction.getExperiment() != null){
            if (interaction.getExperiment().getPublication() != null){
                currentIndex = getNextImexChunkNumberAndFilterValidImexIdsFrom(interaction.getExperiment().getPublication());
            }
            else {
                currentIndex = 1;
            }
        }
        else{
            currentIndex = 1;
        }
    }

    public ImexCentralClient getImexCentralClient() {
        return imexCentral;
    }

    public void setImexCentralClient(ImexCentralClient imexCentral) {
        this.imexCentral = imexCentral;
    }

    /**
     *
     * @param interaction
     * @return true if it is a PPI interaction (at least one proteins or peptides) and is not negative, false otherwise
     */
    public boolean isEntitledToImex( InteractionEvidence interaction ) {
        if (interaction.isNegative()){
            return false;
        }

        int numberOfProteinsPeptides = 0;

        for ( ParticipantEvidence component : interaction.getParticipants() ) {
            if (InteractorUtils.doesInteractorHaveType(component.getInteractor(), Protein.PROTEIN_MI, Protein.PROTEIN)
                    || InteractorUtils.doesInteractorHaveType(component.getInteractor(), Protein.PEPTIDE_MI, Protein.PEPTIDE)){
                numberOfProteinsPeptides++;
            }
        }

        return numberOfProteinsPeptides > 0 && (numberOfProteinsPeptides == interaction.getParticipants().size());
    }

    public void clearInteractionImexContext() {
        currentIndex = 0;
    }

    public int getNextImexChunkNumberAndFilterValidImexIdsFrom(Publication publication){
        int number = 0;
        for (Experiment experiment: publication.getExperiments()){
            for (InteractionEvidence interaction : experiment.getInteractionEvidences()){

                if (interaction.getImexId() != null){
                    Matcher matcher = interaction_imex_regexp.matcher(interaction.getImexId());

                    if (matcher.find()){
                        String pubImex = matcher.group(1);

                        // valid imex id in sync with publication
                        if (publication.getImexId() != null && publication.getImexId().equals(pubImex)){
                            int index = Integer.parseInt(matcher.group(2));
                            if (number < index){
                                number = index;
                            }
                        }
                    }
                }
            }
        }

        return number+1;
    }
}
