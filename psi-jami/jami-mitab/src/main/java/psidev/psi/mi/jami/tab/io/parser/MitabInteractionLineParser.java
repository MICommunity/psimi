package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
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

public class MitabInteractionLineParser extends MitabLineParser {

    private MitabParserListener listener;
    private MitabInteractorFactory interactorFactory;

    public MitabInteractionLineParser(InputStream stream) {
        super(stream);
    }

    public MitabInteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public MitabInteractionLineParser(Reader stream) {
        super(stream);
    }

    public MitabInteractionLineParser(MitabLineParserTokenManager tm) {
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
            int newMitabColumn = mitabColumn - 1;

            if (newMitabColumn == MitabColumnName.ID_INTERACTOR_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ID_INTERACTOR_B.ordinal() ||
                    newMitabColumn == MitabColumnName.ALTID_INTERACTOR_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ALTID_INTERACTOR_B.ordinal() ||
                    newMitabColumn == MitabColumnName.PUB_ID.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTION_ID.ordinal() ||
                    newMitabColumn == MitabColumnName.XREFS_A.ordinal() ||
                    newMitabColumn == MitabColumnName.XREFS_B.ordinal() ||
                    newMitabColumn == MitabColumnName.XREFS_I.ordinal()){
                this.listener.onInvalidXref(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.ALIAS_INTERACTOR_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ALIAS_INTERACTOR_B.ordinal()){
                this.listener.onInvalidAlias(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.INT_DET_METHOD.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTION_TYPE.ordinal() ||
                    newMitabColumn == MitabColumnName.COMPLEX_EXPANSION.ordinal() ||
                    newMitabColumn == MitabColumnName.BIOROLE_A.ordinal() ||
                    newMitabColumn == MitabColumnName.BIOROLE_B.ordinal() ||
                    newMitabColumn == MitabColumnName.EXPROLE_A.ordinal() ||
                    newMitabColumn == MitabColumnName.EXPROLE_B.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTOR_TYPE_A.ordinal() ||
                    newMitabColumn == MitabColumnName.INTERACTOR_TYPE_B.ordinal() ||
                    newMitabColumn == MitabColumnName.PARTICIPANT_IDENT_MED_A.ordinal() ||
                    newMitabColumn == MitabColumnName.PARTICIPANT_IDENT_MED_B.ordinal()){
                this.listener.onInvalidCvTerm(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.PUB_AUTH.ordinal()){
                this.listener.onInvalidFirstAuthor(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.TAXID_A.ordinal() ||
                    newMitabColumn == MitabColumnName.TAXID_B.ordinal() ||
                    newMitabColumn == MitabColumnName.HOST_ORGANISM.ordinal()){
                this.listener.onInvalidOrganismSyntax(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.SOURCE.ordinal()){
                this.listener.onInvalidSource(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.CONFIDENCE.ordinal()){
                this.listener.onInvalidConfidence(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.ANNOTATIONS_A.ordinal() ||
                    newMitabColumn == MitabColumnName.ANNOTATIONS_B.ordinal() ||
                    newMitabColumn == MitabColumnName.ANNOTATIONS_I.ordinal()){
                this.listener.onInvalidAnnotation(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.PARAMETERS_I.ordinal()){
                this.listener.onInvalidParameter(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.CHECKSUM_A.ordinal() ||
                    newMitabColumn == MitabColumnName.CHECKSUM_B.ordinal() ||
                    newMitabColumn == MitabColumnName.CHECKSUM_I.ordinal()){
                this.listener.onInvalidChecksum(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.CREATION_DATE.ordinal() ||
                    newMitabColumn == MitabColumnName.UPDATE_DATE.ordinal()){
                this.listener.onInvalidDate(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.NEGATIVE.ordinal()){
                this.listener.onInvalidNegative(numberLine, numberColumn, mitabColumn);
            }
            else if (newMitabColumn == MitabColumnName.FEATURES_A.ordinal() ||
                    newMitabColumn == MitabColumnName.FEATURES_B.ordinal()){
                if (!isRange){
                    this.listener.onInvalidFeature(numberLine, numberColumn, mitabColumn);
                }
                else{
                    this.listener.onInvalidRange(numberLine, numberColumn, mitabColumn);
                }
            }
            else if (newMitabColumn == MitabColumnName.STOICHIOMETRY_A.ordinal() ||
                    newMitabColumn == MitabColumnName.STOICHIOMETRY_B.ordinal()){
                this.listener.onInvalidStoichiometry(numberLine, numberColumn, mitabColumn);
            }
            else{
                this.listener.onInvalidLine(numberLine, numberColumn, mitabColumn);
            }
        }
    }

    @Override
    MitabParticipantEvidence finishParticipant(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabOrganism> taxid, Collection<MitabCvTerm> bioRole, Collection<MitabCvTerm> expRole, Collection<MitabCvTerm> type, Collection<MitabXref> xref, Collection<MitabAnnotation> annot, Collection<MitabChecksum> checksum, Collection<MitabFeature> feature, Collection<MitabStoichiometry> stc, Collection<MitabCvTerm> detMethod) {
        // first identify interactor

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    MitabInteractionEvidence finishInteraction(Participant A, Participant B, Collection<MitabCvTerm> detMethod, Collection<MitabAuthor> firstAuthor, Collection<MitabXref> pubId, Collection<MitabCvTerm> interactionType, Collection<MitabSource> source, Collection<MitabXref> interactionId, Collection<MitabConfidence> conf, Collection<MitabCvTerm> expansion, Collection<MitabXref> xrefI, Collection<MitabAnnotation> annotI, Collection<MitabOrganism> host, Collection<MitabParameter> params, Collection<MitabDate> created, Collection<MitabDate> update, Collection<MitabChecksum> checksumI, boolean isNegative) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
        }
        interactor.getIdentifiers().addAll(altid);
        interactor.getAliases().addAll(aliases);


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

    protected Collection<MitabAlias> moveAliasesAndChecksumsFromAlternativeIdentifiers(Collection<MitabXref> altid, Collection<MitabAlias> aliases, Collection<MitabChecksum> checksums){

        Iterator<MitabXref> refsIterator = altid.iterator();
        while (refsIterator.hasNext()){
            MitabXref ref = refsIterator.next();

            // gene name is alias
            if (XrefUtils.doesXrefHaveQualifier(ref, Alias.GENE_NAME_MI, Alias.GENE_NAME)){
                aliases = createdAliasFromXref(aliases, refsIterator, ref);
            }
            // gene name synonym is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, Alias.GENE_NAME_SYNONYM_MI, Alias.GENE_NAME)){
                aliases = createdAliasFromXref(aliases, refsIterator, ref);
            }
            // short label is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, null, MitabUtils.SHORTLABEL)){
                aliases = createdAliasFromXref(aliases, refsIterator, ref);
            }
            // display short is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, null, MitabUtils.DISPLAY_SHORT)){
                aliases = createdAliasFromXref(aliases, refsIterator, ref);
            }
            // display long is alias
            else if (XrefUtils.doesXrefHaveQualifier(ref, null, MitabUtils.DISPLAY_LONG)){
                aliases = createdAliasFromXref(aliases, refsIterator, ref);
            }
        }

        return aliases;
    }

    protected Collection<MitabAlias> createdAliasFromXref(Collection<MitabAlias> aliases, Iterator<MitabXref> refsIterator, MitabXref ref) {
        if (aliases.isEmpty()){
            aliases = new ArrayList<MitabAlias>();
        }
        // create alias from xref
        MitabAlias alias = new MitabAlias(ref.getDatabase().getShortName(), ref.getQualifier(), ref.getId(), ref.getSourceLocator());
        aliases.add(alias);
        refsIterator.remove();
        return aliases;
    }

    protected Collection<MitabChecksum> createdChecksumFromXref(Collection<MitabChecksum> checksums, Iterator<MitabXref> refsIterator, MitabXref ref) {
        if (checksums.isEmpty()){
            checksums = new ArrayList<MitabChecksum>();
        }
        // create checksum from xref
        MitabChecksum checksum = new MitabChecksum(ref.getDatabase(), ref.getId(), ref.getSourceLocator());
        checksums.add(checksum);
        refsIterator.remove();
        return checksums;
    }
}
