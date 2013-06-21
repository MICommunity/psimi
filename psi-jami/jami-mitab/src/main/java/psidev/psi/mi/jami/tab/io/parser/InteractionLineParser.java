package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.utils.InteractorUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;

/**
 * An extension of MitabLineParser that returns simple binary interactions only.
 *
 * It ignore properties of InteractionEvidence and ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class InteractionLineParser extends AbstractInteractionLineParser<BinaryInteraction, Participant> {

    public InteractionLineParser(InputStream stream) {
        super(stream);
    }

    public InteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public InteractionLineParser(Reader stream) {
        super(stream);
    }

    public InteractionLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    MitabParticipant finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<MitabFeature> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod, int line, int column, int mitabColumn) {
        boolean hasParticipantFields = !bioRole.isEmpty() || !annot.isEmpty() || !feature.isEmpty() || !stc.isEmpty();
        // first identify interactor
        Interactor interactor = createInteractorFrom(uniqueId, altid, aliases, taxid, type, xref, checksum, line, column, mitabColumn);
        MitabParticipant participant = null;

        if (interactor == null && !hasParticipantFields){
            return participant;
        }
        else if (interactor == null){
            interactor = InteractorUtils.createUnknownBasicInteractor();
            if (getParserListener() != null){
                getParserListener().onParticipantWithoutInteractorDetails(line, column, mitabColumn);
            }
        }

        if (hasParticipantFields){
            MitabCvTerm bioRoleTerm = null;
            // set biorole
            if (bioRole.size() > 1){
                if (getParserListener() != null){
                    getParserListener().onSeveralCvTermFound(bioRole);
                }
                bioRoleTerm = bioRole.iterator().next();
            }
            else if (bioRole.isEmpty()){
                bioRoleTerm = bioRole.iterator().next();
            }

            participant = new MitabParticipant(interactor, bioRoleTerm);

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
            else if (bioRole.isEmpty()){
                participant.setStoichiometry(stc.iterator().next());
            }
            // add source locator
            participant.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        }
        else {
            participant = new MitabParticipant(interactor);
            // add source locator
            participant.setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));
        }

        return participant;
    }

    @Override
    MitabBinaryInteraction finishInteraction(Participant A, Participant B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative, int line) {
        MitabBinaryInteraction interaction = null;
        boolean hasInteractionFields = !interactionType.isEmpty() || !source.isEmpty() || !interactionId.isEmpty() || !expansion.isEmpty()
                || !xrefI.isEmpty() || !annotI.isEmpty() || !checksumI.isEmpty() || !created.isEmpty() || !update.isEmpty();

        if (A == null && B == null){
            if (getParserListener() != null){
                getParserListener().onInteractionWithoutParticipants(line);
            }
            if (!hasInteractionFields){
                return interaction;
            }
        }

        // create interaction with participants
        interaction = new MitabBinaryInteraction(A, B);

        // set interaction type
        if (interactionType.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermFound(interactionType);
            }
            interaction.setInteractionType(interactionType.iterator().next());
        }
        else if (interactionType.isEmpty()){
            interaction.setInteractionType(interactionType.iterator().next());
        }
        // set identifiers
        initialiseInteractionIdentifiers(interactionId, interaction);
        // set expansion method
        if (expansion.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCvTermFound(expansion);
            }
            interaction.setComplexExpansion(expansion.iterator().next());
        }
        else if (expansion.isEmpty()){
            interaction.setComplexExpansion(expansion.iterator().next());
        }
        // add xrefs
        interaction.getXrefs().addAll(xrefI);
        // initialise annotations
        interaction.getAnnotations().addAll(annotI);
        // created
        if (created.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralCreatedDateFound(created);
            }
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        else if (created.isEmpty()){
            interaction.setCreatedDate(created.iterator().next().getDate());
        }
        // update
        if (update.size() > 1){
            if (getParserListener() != null){
                getParserListener().onSeveralUpdatedDateFound(update);
            }
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        else if (update.isEmpty()){
            interaction.setUpdatedDate(update.iterator().next().getDate());
        }
        // checksum
        interaction.getChecksums().addAll(checksumI);

        return interaction;
    }
}