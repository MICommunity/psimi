package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

/**
 * An extension of MitabLineParser that returns binary interactions evidences only.
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class InteractionEvidenceLineParser extends AbstractInteractionLineParser<InteractionEvidence, ParticipantEvidence> {

    public InteractionEvidenceLineParser(InputStream stream) {
        super(stream);
    }

    public InteractionEvidenceLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public InteractionEvidenceLineParser(Reader stream) {
        super(stream);
    }

    public InteractionEvidenceLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    MitabParticipantEvidence finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<MitabFeature> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod, int line, int column, int mitabColumn) {
        boolean hasParticipantFields = !bioRole.isEmpty() || !expRole.isEmpty() || !annot.isEmpty() || !feature.isEmpty() || !stc.isEmpty() || !detMethod.isEmpty();
        // first identify interactor
        Interactor interactor = createInteractorFrom(uniqueId, altid, aliases, taxid, type, xref, checksum, line, column, mitabColumn);
        MitabParticipantEvidence participant = null;
        boolean hasInteractorDetails = true;

        if (interactor == null && !hasParticipantFields){
            return participant;
        }
        else if (interactor == null){
            interactor = getInteractorFactory().createInteractor(MitabUtils.UNKNOWN_NAME, null);
            hasInteractorDetails = false;
        }

        if (hasParticipantFields){
            MitabCvTerm bioRoleTerm = null;
            // set biorole
            if (bioRole.size() > 1){
                if (getParserListener() != null){
                    getParserListener().onSeveralCvTermsFound(bioRole, bioRole.iterator().next(), bioRole.size() + " biological roles found in one participant. Only the first one will be loaded");
                }
                bioRoleTerm = bioRole.iterator().next();
            }
            else if (!bioRole.isEmpty()){
                bioRoleTerm = bioRole.iterator().next();
            }

            MitabCvTerm expRoleTerm = null;
            // set expRole
            if (expRole.size() > 1){
                if (getParserListener() != null){
                    getParserListener().onSeveralCvTermsFound(expRole, expRole.iterator().next(), expRole.size() + " experimental roles found in one participant. Only the first one will be loaded");
                }
                expRoleTerm = expRole.iterator().next();
            }
            else if (!expRole.isEmpty()){
                expRoleTerm = expRole.iterator().next();
            }

            participant = new MitabParticipantEvidence(interactor, bioRoleTerm, expRoleTerm, null);

            // add annotations
            participant.getAnnotations().addAll(annot);
            // add features
            participant.getFeatures().addAll(feature);
            // add stc
            if (stc.size() > 1){
                if (getParserListener() != null){
                    getParserListener().onSeveralStoichiometryFound(stc);
                }
                participant.setStoichiometry(stc.iterator().next());
            }
            else if (!stc.isEmpty()){
                participant.setStoichiometry(stc.iterator().next());
            }
            // add detection methods
            participant.getIdentificationMethods().addAll(detMethod);
            // add source locator
            participant.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        }
        else {
            participant = new MitabParticipantEvidence(interactor);
            // add source locator
            participant.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        }

        if (!hasInteractorDetails && getParserListener() != null){
            getParserListener().onParticipantWithoutInteractor(participant, participant);
        }

        return participant;
    }

    @Override
    MitabInteractionEvidence finishInteraction(ParticipantEvidence A, ParticipantEvidence B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative, int line) {
        MitabInteractionEvidence interaction = null;
        boolean hasInteractionFields = !detMethod.isEmpty() || !firstAuthor.isEmpty() || !pubId.isEmpty() || !interactionType.isEmpty() || !source.isEmpty() || !interactionId.isEmpty() || !conf.isEmpty() || !expansion.isEmpty()
                || !xrefI.isEmpty() || !annotI.isEmpty() || !checksumI.isEmpty() || !params.isEmpty() || !host.isEmpty() || !created.isEmpty() || !update.isEmpty() || isNegative;

        if (A == null && B == null && !hasInteractionFields){
            return interaction;
        }

        // create interaction with participants
        interaction = new MitabInteractionEvidence();

        // create publication
        MitabPublication publication = createPublicationFrom(firstAuthor, pubId, source);
        // create experiment
        interaction.setExperimentAndAddInteractionEvidence(createExperimentFrom(publication, detMethod, host));
        // set interaction type
        if (interactionType.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermsFound(interactionType, interactionType.iterator().next(), interactionType.size()+" interaction types found. Only the first one will be loaded.");
            }
            interaction.setInteractionType(interactionType.iterator().next());
        }
        else if (!interactionType.isEmpty()){
            interaction.setInteractionType(interactionType.iterator().next());
        }
        // set identifiers
        initialiseInteractionIdentifiers(interactionId, interaction);
        // add confidences
        interaction.getConfidences().addAll(conf);
        // set expansion method
        if (expansion.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermsFound(expansion, expansion.iterator().next(), interactionType.size()+" interaction types found. Only the first one will be loaded.");
            }
            interaction.getAnnotations().add(new MitabAnnotation(expansion.iterator().next()));
        }
        else if (!expansion.isEmpty()){
            interaction.getAnnotations().add(new MitabAnnotation(expansion.iterator().next()));
        }
        // add xrefs
        interaction.getXrefs().addAll(xrefI);
        // initialise annotations
        initialiseInteractionAnnotations(annotI, interaction);
        // add params
        interaction.getParameters().addAll(params);
        // created
        if (created.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCreatedDateFound(created);
            }
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        else if (!created.isEmpty()){
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        // update
        if (update.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralUpdatedDateFound(update);
            }
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        else if (!update.isEmpty()){
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        // checksum
        interaction.getChecksums().addAll(checksumI);
        // negative
        interaction.setNegative(isNegative);

        if (A == null && B == null && getParserListener() != null){
            getParserListener().onInteractionWithoutParticipants(interaction, interaction);
        }

        if (A != null){
            interaction.addParticipantEvidence(A);
        }
        if (B != null){
            interaction.addParticipantEvidence(B);
        }

        interaction.setSourceLocator(new MitabSourceLocator(line, 0, 0));

        return interaction;
    }

    protected void initialiseInteractionAnnotations(Collection<MitabAnnotation> annots, InteractionEvidence interaction){

        Iterator<MitabAnnotation> annotsIterator = annots.iterator();
        while (annotsIterator.hasNext()){
            MitabAnnotation annot = annotsIterator.next();

            // add curation depth
            if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.IMEX_CURATION_MI, Annotation.IMEX_CURATION)){
                interaction.getExperiment().getPublication().setCurationDepth(CurationDepth.IMEx);
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.MIMIX_CURATION_MI, Annotation.MIMIX_CURATION)){
                interaction.getExperiment().getPublication().setCurationDepth(CurationDepth.MIMIx);
            }
            else if (AnnotationUtils.doesAnnotationHaveTopic(annot, Annotation.RAPID_CURATION_MI, Annotation.RAPID_CURATION)){
                interaction.getExperiment().getPublication().setCurationDepth(CurationDepth.rapid_curation);
            }
            else{
                interaction.getAnnotations().add(annot);
            }
        }
    }

    protected MitabExperiment createExperimentFrom(MitabPublication publication, Collection<MitabCvTerm> detMethod, Collection<MitabOrganism> host){

        // first get the interaction detection method
        MitabCvTerm detectionMethod = null;
        if (detMethod.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermsFound(detMethod, detMethod.iterator().next(), detMethod.size()+" interaction detection methods found. Only the first one will be loaded.");
            }
            detectionMethod = detMethod.iterator().next();
        }
        else if (!detMethod.isEmpty()){
            detectionMethod = detMethod.iterator().next();
        }

        MitabExperiment experiment = new MitabExperiment(publication, detectionMethod);
        publication.getExperiments().add(experiment);

        // then get the host organism
        if (host.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralHostOrganismFound(host, host.iterator().next());
            }
            experiment.setHostOrganism(host.iterator().next());
        }
        else if (!host.isEmpty()){
            experiment.setHostOrganism(host.iterator().next());
        }

        return experiment;
    }

    protected MitabPublication createPublicationFrom(Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabSource> source){
        MitabPublication publication = new MitabPublication();
        boolean hasSetLocator = false;

        // first initialise authors
        if (firstAuthor.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralFirstAuthorFound(firstAuthor);
            }
            MitabAuthor author = firstAuthor.iterator().next();
            initialiseAuthorAndPublicationDate(publication, author);
            publication.setSourceLocator(author.getSourceLocator());
            hasSetLocator = true;
        }
        else if (!firstAuthor.isEmpty()){
            MitabAuthor author = firstAuthor.iterator().next();
            initialiseAuthorAndPublicationDate(publication, author);
            publication.setSourceLocator(author.getSourceLocator());
            hasSetLocator = true;
        }

        // then initialise pubids
        hasSetLocator = initialisePublicationIdentifiers(pubId, publication, hasSetLocator);

        // then initialise source
        if (source.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralSourceFound(source);
            }
            MitabSource firstSource = source.iterator().next();
            publication.setSource(firstSource);
            if (!hasSetLocator){
                publication.setSourceLocator(firstSource.getSourceLocator());
            }
        }
        else if (!source.isEmpty()){
            MitabSource firstSource = source.iterator().next();
            publication.setSource(firstSource);
            if (!hasSetLocator){
                publication.setSourceLocator(firstSource.getSourceLocator());
            }
        }

        return publication;
    }

    protected boolean initialisePublicationIdentifiers(Collection<MitabXref> pubId, MitabPublication publication, boolean hasInitialisedLocator){

        Iterator<MitabXref> refsIterator = pubId.iterator();
        while (refsIterator.hasNext()){
            MitabXref ref = refsIterator.next();

            if (!hasInitialisedLocator){
                publication.setSourceLocator(ref.getSourceLocator());
                hasInitialisedLocator = true;
            }

            if (XrefUtils.isXrefFromDatabase(ref, Xref.IMEX_MI, Xref.IMEX) && XrefUtils.doesXrefHaveQualifier(ref, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                 publication.getXrefs().add(ref);
            }
            else{
                publication.getIdentifiers().add(ref);
            }
        }

        return hasInitialisedLocator;
    }

    protected void initialiseAuthorAndPublicationDate(MitabPublication publication, MitabAuthor author) {
        if (author.getFirstAuthor() != null){
            publication.getAuthors().add(author.getFirstAuthor());
        }
        if (author.getPublicationDate() != null){
            publication.setPublicationDate(author.getPublicationDate());
        }
    }
}
