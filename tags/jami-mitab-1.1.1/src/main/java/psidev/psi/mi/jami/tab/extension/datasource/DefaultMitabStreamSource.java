package psidev.psi.mi.jami.tab.extension.datasource;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.extension.MitabSource;
import psidev.psi.mi.jami.tab.extension.factory.MitabDataSourceFactory;
import psidev.psi.mi.jami.tab.extension.factory.options.MitabDataSourceOptions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Generic class for Mitab streaming datasource.
 *
 * This datasource streams the interactions from a MITAB file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class DefaultMitabStreamSource implements MitabStreamSource{

    private MitabStreamSource delegate;

    /**
     * Empty constructor for the factory
     */
    public DefaultMitabStreamSource(){
    }

    public Iterator<Interaction> getInteractionsIterator() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return this.delegate.getInteractionsIterator();
    }

    public MIFileParserListener getFileParserListener() {
        return this.delegate != null ? this.delegate.getFileParserListener() : null;
    }

    public void setFileParserListener(MIFileParserListener listener) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.setFileParserListener(listener);
    }

    public boolean validateSyntax() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return this.delegate.validateSyntax();
    }

    public boolean validateSyntax(MIFileParserListener listener) throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        return this.delegate.validateSyntax(listener);
    }

    public void initialiseContext(Map<String, Object> options) {
        MitabDataSourceFactory factory = MitabDataSourceFactory.getInstance();
        InteractionCategory category = InteractionCategory.evidence;
        ComplexType type = ComplexType.n_ary;

        if (options == null || !options.containsKey(MitabDataSourceOptions.INPUT_OPTION_KEY)){
            throw new IllegalArgumentException("The options for the Mitab interaction datasource should contain at least "+
                    MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }

        if (options.containsKey(MitabDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY)){
            Object value = options.get(MitabDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY);
            if (value instanceof InteractionCategory){
                category = (InteractionCategory)value;
            }
        }
        if (options.containsKey(MitabDataSourceOptions.COMPLEX_TYPE_OPTION_KEY)){
            Object value = options.get(MitabDataSourceOptions.COMPLEX_TYPE_OPTION_KEY);
            if (value instanceof ComplexType){
                type = (ComplexType)value;
            }
        }

        initialiseDelegate(options, factory, category, type);
    }

    protected void initialiseDelegate(Map<String, Object> options, MitabDataSourceFactory factory, InteractionCategory category, ComplexType type) {
        this.delegate = factory.createMitabDataSource(category, type, true);
        this.delegate.initialiseContext(options);
    }

    public void close() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.close();
    }

    public void reset() throws MIIOException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.reset();
    }

    public void onTextFoundInIdentifier(MitabXref xref) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onTextFoundInIdentifier(xref);
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onTextFoundInConfidence(conf);
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingExpansionId(expansion);
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralUniqueIdentifiers(ids);
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onEmptyUniqueIdentifiers(line, column, mitabColumn);
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingInteractorIdentifierColumns(line, column, mitabColumn);
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralOrganismFound(organisms);
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralStoichiometryFound(stoichiometry);
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralFirstAuthorFound(authors);
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralSourceFound(sources);
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralCreatedDateFound(dates);
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralUpdatedDateFound(dates);
    }

    public void onAliasWithoutDbSource(MitabAlias alias) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onAliasWithoutDbSource(alias);
    }

    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralCvTermsFound(terms, context, message);
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralHostOrganismFound(organisms, context);
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onInvalidSyntax(context, e);
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSyntaxWarning(context, message);
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingCvTermName(term, context, message);
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingInteractorName(interactor, context);
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onParticipantWithoutInteractor(participant, context);
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onInteractionWithoutParticipants(interaction, context);
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onInvalidOrganismTaxid(taxid, context);
    }

    public void onMissingParameterValue(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingParameterValue(context);
    }

    public void onMissingParameterType(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingParameterType(context);
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingConfidenceValue(context);
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingConfidenceType(context);
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingChecksumValue(context);
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onMissingChecksumMethod(context);
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onInvalidPosition(message, context);
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onInvalidRange(message, context);
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onInvalidStoichiometry(message, context);
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onXrefWithoutDatabase(context);
    }

    public void onXrefWithoutId(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onXrefWithoutId(context);
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onAnnotationWithoutTopic(context);
    }

    public void onAliasWithoutName(FileSourceContext context) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onAliasWithoutName(context);
    }

    protected MitabStreamSource getDelegate() {
        return delegate;
    }

    protected void setDelegate(MitabStreamSource delegate) {
        this.delegate = delegate;
    }
}
