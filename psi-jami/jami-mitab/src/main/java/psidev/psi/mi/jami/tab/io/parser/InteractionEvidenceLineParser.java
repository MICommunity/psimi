package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

/**
 * An extension of MitabLineParser that returns simple interactions only.
 *
 * It ignore properties of InteractionEvidence and ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/06/13</pre>
 */

public class InteractionEvidenceLineParser extends MitabLineParser {

    private MitabParserListener listener;
    private MitabInteractorFactory interactorFactory;

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
    MitabParserListener getParserListener() {
        return listener;
    }

    @Override
    void setParserListener(MitabParserListener listener) {
        this.listener = listener;
    }

    public MitabInteractorFactory getInteractorFactory() {
        if (interactorFactory == null){
            interactorFactory = new MitabInteractorFactory();
        }
        return interactorFactory;
    }

    public void setInteractorFactory(MitabInteractorFactory interactorFactory) {
        this.interactorFactory = interactorFactory;
    }

    @Override
    void fireOnInvalidSyntax(int numberLine, int numberColumn, int mitabColumn, boolean isRange) {
        if (this.listener != null){
            this.listener.onInvalidSyntax(numberLine, numberColumn, mitabColumn);
        }
    }

    @Override
    MitabParticipantEvidence finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<MitabFeature> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod, int line, int column, int mitabColumn) {
        boolean hasParticipantFields = !bioRole.isEmpty() || !expRole.isEmpty() || !annot.isEmpty() || !feature.isEmpty() || !stc.isEmpty() || !detMethod.isEmpty();
        // first identify interactor
        Interactor interactor = createInteractorFrom(uniqueId, altid, aliases, taxid, type, xref, checksum, line, column, mitabColumn);
        MitabParticipantEvidence participant = null;

        if (interactor == null && !hasParticipantFields){
            return participant;
        }
        else if (interactor == null){
            interactor = InteractorUtils.createUnknownBasicInteractor();
            listener.onParticipantWithoutInteractorDetails(line, column, mitabColumn);
        }

        if (hasParticipantFields){
            MitabCvTerm bioRoleTerm = null;
            // set biorole
            if (bioRole.size() > 1){
                listener.onSeveralCvTermFound(bioRole);
                bioRoleTerm = bioRole.iterator().next();
            }
            else if (bioRole.isEmpty()){
                bioRoleTerm = bioRole.iterator().next();
            }

            MitabCvTerm expRoleTerm = null;
            // set expRole
            if (expRole.size() > 1){
                listener.onSeveralCvTermFound(expRole);
                expRoleTerm = expRole.iterator().next();
            }
            else if (bioRole.isEmpty()){
                expRoleTerm = expRole.iterator().next();
            }

            participant = new MitabParticipantEvidence(interactor, bioRoleTerm, expRoleTerm, null);

            // add annotations
            participant.getAnnotations().addAll(annot);
            // add features
            participant.getFeatures().addAll(feature);
            // add stc
            if (stc.size() > 1){
                listener.onSeveralStoichiometryFound(stc);
                participant.setStoichiometry(stc.iterator().next());
            }
            else if (bioRole.isEmpty()){
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

        return participant;
    }

    @Override
    MitabBinaryInteractionEvidence finishInteraction(Participant A, Participant B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative, int line) {
        MitabBinaryInteractionEvidence interaction = null;
        boolean hasInteractionFields = !detMethod.isEmpty() || !firstAuthor.isEmpty() || !pubId.isEmpty() || !interactionType.isEmpty() || !source.isEmpty() || !interactionId.isEmpty() || !conf.isEmpty() || !expansion.isEmpty()
                || !xrefI.isEmpty() || !annotI.isEmpty() || !checksumI.isEmpty() || !params.isEmpty() || !host.isEmpty() || !created.isEmpty() || !update.isEmpty() || isNegative;

        if (A == null && B == null && !hasInteractionFields){
            listener.onInteractionWithoutParticipants(line);
            return interaction;
        }

        // create interaction with participants
        interaction = new MitabBinaryInteractionEvidence((MitabParticipantEvidence) A, (MitabParticipantEvidence)B);

        // create publication
        MitabPublication publication = createPublicationFrom(firstAuthor, pubId, source);
        // create experiment
        interaction.setExperimentAndAddInteractionEvidence(createExperimentFrom(publication, detMethod, host));
        // set interaction type
        if (interactionType.size() > 1){
            listener.onSeveralCvTermFound(interactionType);
            interaction.setInteractionType(interactionType.iterator().next());
        }
        else if (interactionType.isEmpty()){
            interaction.setInteractionType(interactionType.iterator().next());
        }
        // set identifiers
        initialiseInteractionIdentifiers(interactionId, interaction);
        // add confidences
        interaction.getConfidences().addAll(conf);
        // set expansion method
        if (expansion.size() > 1){
            listener.onSeveralCvTermFound(expansion);
            interaction.setComplexExpansion(expansion.iterator().next());
        }
        else if (expansion.isEmpty()){
            interaction.setComplexExpansion(expansion.iterator().next());
        }
        // add xrefs
        interaction.getXrefs().addAll(xrefI);
        // initialise annotations
        initialiseInteractionAnnotations(annotI, interaction);
        // add params
        interaction.getParameters().addAll(params);
        // created
        if (created.size() > 1){
            listener.onSeveralCreatedDateFound(created);
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        else if (created.isEmpty()){
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        // update
        if (update.size() > 1){
            listener.onSeveralUpdatedDateFound(update);
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        else if (update.isEmpty()){
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        // checksum
        interaction.getChecksums().addAll(checksumI);
        // negative
        interaction.setNegative(isNegative);

        return interaction;
    }

    protected void initialiseInteractionAnnotations(Collection<MitabAnnotation> annots, BinaryInteractionEvidence interaction){

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

    protected void initialiseInteractionIdentifiers(Collection<MitabXref> interactionIds, BinaryInteractionEvidence interaction){

        Iterator<MitabXref> refsIterator = interactionIds.iterator();
        while (refsIterator.hasNext()){
            MitabXref ref = refsIterator.next();

            if (XrefUtils.isXrefFromDatabase(ref, Xref.IMEX_MI, Xref.IMEX) && XrefUtils.doesXrefHaveQualifier(ref, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                interaction.getXrefs().add(ref);
            }
            else{
                interaction.getIdentifiers().add(ref);
            }
        }
    }

    protected MitabExperiment createExperimentFrom(MitabPublication publication, Collection<MitabCvTerm> detMethod, Collection<MitabOrganism> host){

        // first get the interaction detection method
        MitabCvTerm detectionMethod = null;
        if (detMethod.size() > 1){
            listener.onSeveralCvTermFound(detMethod);
            detectionMethod = detMethod.iterator().next();
        }
        else if (detMethod.isEmpty()){
            detectionMethod = detMethod.iterator().next();
        }

        MitabExperiment experiment = new MitabExperiment(publication, detectionMethod);
        publication.getExperiments().add(experiment);

        // then get the host organism
        if (host.size() > 1){
            listener.onSeveralHostOrganismFound(host);
            experiment.setHostOrganism(host.iterator().next());
        }
        else if (detMethod.isEmpty()){
            experiment.setHostOrganism(host.iterator().next());
        }

        return experiment;
    }

    protected MitabPublication createPublicationFrom(Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabSource> source){
        MitabPublication publication = new MitabPublication();
        boolean hasSetLocator = false;

        // first initialise authors
        if (firstAuthor.size() > 1){
            listener.onSeveralFirstAuthorFound(firstAuthor);
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
            listener.onSeveralSourceFound(source);
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

    protected Interactor createInteractorFrom(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabChecksum> checksum, int line, int column, int mitabColumn){
        boolean hasId = !uniqueId.isEmpty() || !altid.isEmpty();
        boolean hasAlias = !aliases.isEmpty();
        boolean hasOtherFields = !taxid.isEmpty() || !checksum.isEmpty() || !type.isEmpty() || !xref.isEmpty();
        Interactor interactor = null;
        String shortName;

        // find shortName first
        // the interactor is empty
        if (!hasId && !hasAlias && !hasOtherFields){
            return null;
        }
        // the interactor name will be unknown but needs to be created
        else if (!hasId && !hasAlias){
            listener.onMissingInteractorIdentifierColumns(line, column, mitabColumn);
            shortName = "unknown name";
        }
        else{
            // first retrieve what will be the name of the interactor
            shortName = findInteractorShortNameFrom(uniqueId, altid, aliases, line, column, mitabColumn);
        }

        // fire event if several uniqueIds
        if (uniqueId.size() > 1){
            listener.onSeveralUniqueIdentifiers(uniqueId);
        }

        // find interactor type
        interactor = interactorFactory.createInteractorFromInteractorTypes(type, shortName);
        // we don't have an interactor type, use identifiers
        if (interactor == null && hasId){
            interactor = interactorFactory.createInteractorFromIdentityXrefs(!uniqueId.isEmpty() ? uniqueId : altid, shortName);

            // we still don't know which interactor it is
            if (interactor == null){
                interactor = interactorFactory.createInteractor(shortName, null);
            }
        }
        // we don't have an interactor type, and we don't have identifiers, create an unknown participant
        else if (interactor == null){
            interactor = interactorFactory.createInteractor(shortName, null);
        }

        if (hasId){
            // add unique ids first
            interactor.getIdentifiers().addAll(uniqueId);

            // add alternative identifiers
            fillInteractorWithAlternativeIdentifiers(altid, interactor);
        }
        if (hasAlias){
            fillInteractorWithAliases(aliases, interactor);
        }

        // add checksum
        interactor.getChecksums().addAll(checksum);
        // add xref
        interactor.getXrefs().addAll(xref);
        // set organism
        initialiseOrganism(taxid, interactor);
        // if several types fire event
        if (type.size() > 1){
            listener.onSeveralCvTermFound(type);
        }

        // set source locator
        ((FileSourceContext)interactor).setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));

        return interactor;
    }

    protected String findInteractorShortNameFrom(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, int line, int column, int mitabColumn){

        MitabAlias shortName = MitabUtils.findBestShortNameFromAliases(aliases);
        if (shortName != null){
            return shortName.getName();
        }
        else{
            listener.onEmptyAliases(line, column, mitabColumn);
        }

        MitabXref shortNameFromAltid = MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid);
        if (shortNameFromAltid != null){
            return shortNameFromAltid.getId();
        }
        else if (!uniqueId.isEmpty()){
            return uniqueId.iterator().next().getId();
        }
        else if (!altid.isEmpty()){
            listener.onEmptyUniqueIdentifiers(line, column, mitabColumn);

            return altid.iterator().next().getId();
        }

        return null;
    }

    protected void fillInteractorWithAlternativeIdentifiers(Collection<MitabXref> altid, Interactor interactor){

        Iterator<MitabXref> refsIterator = altid.iterator();
        while (refsIterator.hasNext()){
            MitabXref ref = refsIterator.next();

            // gene name is alias
            if (XrefUtils.doesXrefHaveQualifier(ref, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
                createAliasFromAltId(interactor, ref);
            }
            // gene name synonym is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME)){
                createAliasFromAltId(interactor, ref);
            }
            // short label is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, null, MitabUtils.SHORTLABEL)){
                createAliasFromAltId(interactor, ref);
            }
            // display short is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, null, MitabUtils.DISPLAY_SHORT)){
                createAliasFromAltId(interactor, ref);
            }
            // display long is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, null, MitabUtils.DISPLAY_LONG)){
                createAliasFromAltId(interactor, ref);
            }
            // database is rogid so we have a checksum
            else if (XrefUtils.isXrefFromDatabase(ref, null, Checksum.ROGID)){
                createChecksumFromAltId(interactor, ref);

            }
            // database is irogid so we have a checksum
            else if (XrefUtils.isXrefFromDatabase(ref, null, Checksum.IROGID)){
                createChecksumFromAltId(interactor, ref);
            }
            // we have a simple xref
            else {
                interactor.getIdentifiers().add(ref);
            }
        }
    }

    protected void fillInteractorWithAliases(Collection<MitabAlias> aliases, Interactor interactor){

        Iterator<MitabAlias> aliasIterator = aliases.iterator();
        while (aliasIterator.hasNext()){
            MitabAlias alias = aliasIterator.next();

            // we have a smile
            if (AliasUtils.doesAliasHaveType(alias, Checksum.SMILE_MI, Checksum.SMILE) ||
                    AliasUtils.doesAliasHaveType(alias, Checksum.SMILE_MI, Checksum.SMILE_SHORT)){
                createChecksumFromAlias(interactor, alias);

            }
            // we have inchi key
            else if (AliasUtils.doesAliasHaveType(alias, Checksum.INCHI_KEY_MI, Checksum.INCHI_KEY)){
                createChecksumFromAlias(interactor, alias);
            }
            // we have standard inchi
            else if (AliasUtils.doesAliasHaveType(alias, Checksum.STANDARD_INCHI_KEY_MI, Checksum.STANDARD_INCHI_KEY)){
                createChecksumFromAlias(interactor, alias);
            }
            // we have inchi
            else if (AliasUtils.doesAliasHaveType(alias, Checksum.INCHI_MI, Checksum.INCHI) ||
                    AliasUtils.doesAliasHaveType(alias, Checksum.INCHI_MI, Checksum.INCHI_SHORT)){
                createChecksumFromAlias(interactor, alias);
            }
            // we have rogid
            else if (AliasUtils.doesAliasHaveType(alias, null, Checksum.ROGID) ||
                    AliasUtils.doesAliasHaveType(alias, null, Checksum.IROGID)){
                createChecksumFromAlias(interactor, alias);
            }
            // we have a simple alias
            else {
                interactor.getAliases().add(alias);
            }
        }
    }

    protected void initialiseOrganism(Collection<MitabOrganism> organisms, Interactor interactor){

        if (organisms.size() > 1){
            listener.onSeveralOrganismFound(organisms);
        }
        else if (!organisms.isEmpty()){
            interactor.setOrganism(organisms.iterator().next());
        }
    }

    protected void createChecksumFromAltId(Interactor interactor, MitabXref ref) {
        // create checksum from xref
        MitabChecksum checksum = new MitabChecksum(ref.getDatabase(), ref.getId(), ref.getSourceLocator());
        interactor.getChecksums().add(checksum);
        listener.onChecksumFoundInAlternativeIds(ref, ref.getSourceLocator().getLineNumber(), ref.getSourceLocator().getCharNumber(), ((MitabSourceLocator)ref.getSourceLocator()).getColumnNumber());
    }

    protected void createAliasFromAltId(Interactor interactor, MitabXref ref) {
        // create alias from xref
        MitabAlias alias = new MitabAlias(ref.getDatabase().getShortName(), ref.getQualifier(), ref.getId(), ref.getSourceLocator());
        interactor.getAliases().add(alias);
        listener.onAliasFoundInAlternativeIds(ref, ref.getSourceLocator().getLineNumber(), ref.getSourceLocator().getCharNumber(), ((MitabSourceLocator)ref.getSourceLocator()).getColumnNumber());
    }

    protected void createChecksumFromAlias(Interactor interactor, MitabAlias alias) {
        // create checksum from alias
        MitabChecksum checksum = new MitabChecksum(alias.getType(), alias.getName(), alias.getSourceLocator());
        interactor.getChecksums().add(checksum);
        listener.onChecksumFoundInAliases(alias, alias.getSourceLocator().getLineNumber(), alias.getSourceLocator().getCharNumber(), ((MitabSourceLocator)alias.getSourceLocator()).getColumnNumber());
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
