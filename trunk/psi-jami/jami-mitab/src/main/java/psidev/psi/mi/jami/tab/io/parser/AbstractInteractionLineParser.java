package psidev.psi.mi.jami.tab.io.parser;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;

/**
 * Abstract mitab line parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public abstract class AbstractInteractionLineParser<T extends Interaction, P extends Participant> extends MitabLineParser<T,P> {

    private MitabParserListener listener;
    private MitabInteractorFactory interactorFactory;
    private boolean hasFinished = false;

    public AbstractInteractionLineParser(InputStream stream) {
        super(stream);
    }

    public AbstractInteractionLineParser(InputStream stream, String encoding) {
        super(stream, encoding);
    }

    public AbstractInteractionLineParser(Reader stream) {
        super(stream);
    }

    public AbstractInteractionLineParser(MitabLineParserTokenManager tm) {
        super(tm);
    }

    @Override
    public MitabParserListener getParserListener() {
        return listener;
    }

    @Override
    public void setParserListener(MitabParserListener listener) {
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
    void fireOnInvalidSyntax(int numberLine, int numberColumn, int mitabColumn, Exception e) {
        if (this.listener != null){
            this.listener.onInvalidSyntax(new DefaultFileSourceContext(new MitabSourceLocator(numberLine, numberColumn, mitabColumn)), e);
        }
    }

    @Override
    void reachEndOfFile() {
        this.hasFinished = true;
    }

    @Override
    public void ReInit(InputStream stream) {
        hasFinished = false;
        super.ReInit(stream);
    }

    @Override
    public void ReInit(InputStream stream, String encoding) {
        hasFinished = false;
        super.ReInit(stream, encoding);
    }

    @Override
    public void ReInit(Reader stream) {
        hasFinished = false;
        super.ReInit(stream);
    }

    @Override
    public void ReInit(MitabLineParserTokenManager tm) {
        hasFinished = false;
        super.ReInit(tm);
    }

    public boolean hasFinished() {
        return hasFinished;
    }

    protected void initialiseInteractionIdentifiers(Collection<MitabXref> interactionIds, T interaction){

        Iterator<MitabXref> refsIterator = interactionIds.iterator();
        while (refsIterator.hasNext()){
            MitabXref ref = refsIterator.next();

            if (XrefUtils.isXrefFromDatabase(ref, Xref.IMEX_MI, Xref.IMEX) && XrefUtils.doesXrefHaveQualifier(ref, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)){
                interaction.getXrefs().add(ref);
            }
            else if (XrefUtils.isXrefFromDatabase(ref, null, Checksum.RIGID) || XrefUtils.isXrefFromDatabase(ref, null, Checksum.IRIGID)){
                createChecksumFromId(interaction, ref);
            }
            else{
                interaction.getIdentifiers().add(ref);
            }
        }
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
            if (this.listener != null){
                listener.onMissingInteractorIdentifierColumns(line, column, mitabColumn);
            }
            shortName = MitabUtils.UNKNOWN_NAME;
        }
        else{
            // first retrieve what will be the name of the interactor
            shortName = findInteractorShortNameFrom(uniqueId, altid, aliases, line, column, mitabColumn);
        }

        // fire event if several uniqueIds
        if (uniqueId.size() > 1 && listener != null){
            listener.onSeveralUniqueIdentifiers(uniqueId);
        }

        // find interactor type
        interactor = getInteractorFactory().createInteractorFromInteractorTypes(type, shortName);
        // we don't have an interactor type, use identifiers
        if (interactor == null && hasId){
            interactor = getInteractorFactory().createInteractorFromIdentityXrefs(!uniqueId.isEmpty() ? uniqueId : altid, shortName);

            // we still don't know which interactor it is
            if (interactor == null){
                interactor = getInteractorFactory().createInteractor(shortName, null);
            }
        }
        // we don't have an interactor type, and we don't have identifiers, create an unknown participant
        else if (interactor == null){
            interactor = getInteractorFactory().createInteractor(shortName, null);
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
        if (type.size() > 1 && listener != null){
            listener.onSeveralCvTermsFound(type, type.iterator().next(), type.size() + " interactor types were found and only the first one will be loaded.");
        }

        // set source locator
        ((FileSourceContext)interactor).setSourceLocator(new MitabSourceLocator(line, column, mitabColumn));

        if (!hasAlias){
            listener.onMissingInteractorName(interactor, (FileSourceContext)interactor);
        }

        return interactor;
    }

    protected String findInteractorShortNameFrom(Collection<MitabXref> uniqueId, Collection<MitabXref> altid, Collection<MitabAlias> aliases, int line, int column, int mitabColumn){

        MitabAlias shortName = MitabUtils.findBestShortNameFromAliases(aliases);
        if (shortName != null){
            return shortName.getName();
        }

        MitabXref shortNameFromAltid = MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid);
        if (shortNameFromAltid != null){
            return shortNameFromAltid.getId();
        }
        else if (!uniqueId.isEmpty()){
            return uniqueId.iterator().next().getId();
        }
        else if (!altid.isEmpty()){
            if (this.listener != null){
                listener.onEmptyUniqueIdentifiers(line, column, mitabColumn);
            }

            return altid.iterator().next().getId();
        }
        else if (this.listener != null){
            listener.onEmptyUniqueIdentifiers(line, column, mitabColumn);
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
            if (listener != null){
                listener.onSeveralOrganismFound(organisms);
            }
            interactor.setOrganism(organisms.iterator().next());
        }
        else if (!organisms.isEmpty()){
            interactor.setOrganism(organisms.iterator().next());
        }
    }

    protected void createChecksumFromId(Interaction interaction, MitabXref ref) {
        // create checksum from xref
        MitabChecksum checksum = new MitabChecksum(ref.getDatabase(), ref.getId(), ref.getSourceLocator());
        interaction.getChecksums().add(checksum);
        if (listener != null){
            listener.onSyntaxWarning(ref, "Found a Checksum in the interaction identifiers column. Will load it as a checksum.");
        }
    }

    protected void createChecksumFromAltId(Interactor interactor, MitabXref ref) {
        // create checksum from xref
        MitabChecksum checksum = new MitabChecksum(ref.getDatabase(), ref.getId(), ref.getSourceLocator());
        interactor.getChecksums().add(checksum);
        if (listener != null){
            listener.onSyntaxWarning(ref, "Found a Checksum in the alternative identifiers column. Will load it as a checksum.");
        }
    }

    protected void createAliasFromAltId(Interactor interactor, MitabXref ref) {
        // create alias from xref
        MitabAlias alias = new MitabAlias(ref.getDatabase().getShortName(), ref.getQualifier(), ref.getId(), ref.getSourceLocator());
        interactor.getAliases().add(alias);
        if (listener != null){
            listener.onSyntaxWarning(ref, "Found an Alias in the alternative identifiers column. Will load it as a checksum.");
        }
    }

    protected void createChecksumFromAlias(Interactor interactor, MitabAlias alias) {
        // create checksum from alias
        MitabChecksum checksum = new MitabChecksum(alias.getType(), alias.getName(), alias.getSourceLocator());
        interactor.getChecksums().add(checksum);
        if (listener != null){
            listener.onSyntaxWarning(alias, "Found a Checksum in the aliases column. Will load it as a checksum.");
        }
    }
}