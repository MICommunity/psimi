package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract class for Interaction evidence parsers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/07/13</pre>
 */

public abstract class AbstractInteractionEvidenceLineParser<T extends InteractionEvidence> extends AbstractInteractionLineParser<T, ParticipantEvidence, FeatureEvidence> {

    public AbstractInteractionEvidenceLineParser(InputStream stream) {
        super(stream);
    }

    public AbstractInteractionEvidenceLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public AbstractInteractionEvidenceLineParser(Reader stream) {
        super(stream);
    }

    public AbstractInteractionEvidenceLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    MitabFeatureEvidence createFeature(String type, Collection<Range> ranges, String text, int line, int column, int mitabColumn){
        MitabFeatureEvidence feature = new MitabFeatureEvidence(new DefaultCvTerm(type));
        feature.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        feature.getRanges().addAll(ranges);
        processTextFor(feature, text);

        return feature;
    }

    @Override
    MitabParticipantEvidence finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<FeatureEvidence> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod, int line, int column, int mitabColumn) {
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
            ((FileSourceContext)interactor).setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
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
            participant.addAllFeatures(feature);
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
    T finishInteraction(ParticipantEvidence A, ParticipantEvidence B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative, int line) {
        T interaction = null;
        boolean hasInteractionFields = !detMethod.isEmpty() || !firstAuthor.isEmpty() || !pubId.isEmpty() || !interactionType.isEmpty() || !source.isEmpty() || !interactionId.isEmpty() || !conf.isEmpty() || !expansion.isEmpty()
                || !xrefI.isEmpty() || !annotI.isEmpty() || !checksumI.isEmpty() || !params.isEmpty() || !host.isEmpty() || !created.isEmpty() || !update.isEmpty() || isNegative;

        if (A == null && B == null && !hasInteractionFields){
            return interaction;
        }

        // create interaction with participants
        interaction = createInteraction();

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
        initialiseExpansionMethod(expansion, interaction);
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
            getParserListener().onInteractionWithoutParticipants(interaction, (FileSourceContext)interaction);
        }

        if (A != null){
            interaction.addParticipant(A);
        }
        if (B != null){
            interaction.addParticipant(B);
        }

        ((FileSourceContext)interaction).setSourceLocator(new MitabSourceLocator(line, 0, 0));

        return interaction;
    }

    protected void initialiseInteractionAnnotations(Collection<MitabAnnotation> annots, T interaction){

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
        initialiseHostOrganism(host, experiment);

        return experiment;
    }

    protected void initialiseHostOrganism(Collection<MitabOrganism> organisms, Experiment exp){

        if (organisms.size() > 1){
            Iterator<MitabOrganism> organismsIterator = organisms.iterator();
            int taxid=0;
            String commonName=null;
            int commonNameLength = 0;
            int fullNameLength = 0;
            MitabOrganism currentOrganism=null;
            boolean hasSeveralOrganisms = false;
            do {

                MitabOrganism organism = organismsIterator.next();
                if (currentOrganism == null){
                    currentOrganism = organism;
                    commonName = organism.getCommonName();
                    commonNameLength = commonName.length();
                    fullNameLength = commonName.length();
                    taxid = organism.getTaxId();
                }
                // we have same organism
                else if (organism.getTaxId() == taxid){
                    // we have a new common name
                    if (organism.getCommonName() != null && organism.getCommonName().length() < commonNameLength){
                        if (currentOrganism.getScientificName() == null){
                            currentOrganism.setScientificName(currentOrganism.getCommonName());
                        }
                        // we have a synonym for the organism
                        else {
                            currentOrganism.getAliases().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, currentOrganism.getCommonName()));
                        }
                        currentOrganism.setCommonName(organism.getCommonName());
                        commonNameLength = organism.getCommonName().length();
                    }
                    // we have a full name
                    else if (currentOrganism.getScientificName() == null){
                        currentOrganism.setScientificName(organism.getCommonName());
                        fullNameLength = organism.getCommonName().length();
                    }
                    // we have a new fullname
                    else if (organism.getCommonName().length() < fullNameLength) {
                        currentOrganism.getAliases().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, currentOrganism.getScientificName()));
                        currentOrganism.setScientificName(organism.getCommonName());
                        fullNameLength = organism.getCommonName().length();
                    }
                    // we have a synonym for the organism
                    else {
                        currentOrganism.getAliases().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, organism.getCommonName()));
                    }
                }
                else{
                    hasSeveralOrganisms = true;
                }

            } while(organismsIterator.hasNext());

            if (getParserListener() != null && hasSeveralOrganisms){
                getParserListener().onSeveralHostOrganismFound(organisms, organisms.iterator().next());
            }

            exp.setHostOrganism(currentOrganism);
        }
        else if (!organisms.isEmpty()){
            exp.setHostOrganism(organisms.iterator().next());
        }
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
