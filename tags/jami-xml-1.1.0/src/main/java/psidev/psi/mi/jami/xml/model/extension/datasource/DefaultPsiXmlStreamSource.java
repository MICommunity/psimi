package psidev.psi.mi.jami.xml.model.extension.datasource;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.factory.options.MIFileDataSourceOptions;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.model.extension.factory.PsiXmlDataSourceFactory;
import psidev.psi.mi.jami.xml.model.extension.factory.options.PsiXmlDataSourceOptions;
import psidev.psi.mi.jami.xml.model.reference.XmlIdReference;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Generic class for PSI-XML streaming datasource.
 *
 * This datasource streams the interactions from a PSI-XML file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/06/13</pre>
 */

public class DefaultPsiXmlStreamSource implements PsiXmlStreamSource {

    private PsiXmlStreamSource delegate;

    /**
     * Empty constructor for the factory
     */
    public DefaultPsiXmlStreamSource(){
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
        PsiXmlDataSourceFactory factory = PsiXmlDataSourceFactory.getInstance();
        InteractionCategory category = null;
        ComplexType type = ComplexType.n_ary;

        if (options == null || !options.containsKey(PsiXmlDataSourceOptions.INPUT_OPTION_KEY)){
            throw new IllegalArgumentException("The options for the PSI-XML interaction datasource should contain at least "+
                    MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }

        if (options.containsKey(PsiXmlDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY)){
            Object value = options.get(PsiXmlDataSourceOptions.INTERACTION_CATEGORY_OPTION_KEY);
            if (value instanceof InteractionCategory){
                category = (InteractionCategory)value;
            }
        }
        if (options.containsKey(PsiXmlDataSourceOptions.COMPLEX_TYPE_OPTION_KEY)){
            Object value = options.get(PsiXmlDataSourceOptions.COMPLEX_TYPE_OPTION_KEY);
            if (value instanceof ComplexType){
                type = (ComplexType)value;
            }
        }

        initialiseDelegate(options, factory, category, type);
    }

    protected void initialiseDelegate(Map<String, Object> options, PsiXmlDataSourceFactory factory, InteractionCategory category, ComplexType type) {
        this.delegate = factory.createPsiXmlDataSource(category, type, true);
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

    protected PsiXmlStreamSource getDelegate() {
        return delegate;
    }

    protected void setDelegate(PsiXmlStreamSource delegate) {
        this.delegate = delegate;
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.warning(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.fatalError(e);
    }

    @Override
    public void onUnresolvedReference(XmlIdReference ref, String message) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onUnresolvedReference(ref, message);
    }

    @Override
    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralHostOrganismFound(organisms, locator);
    }

    @Override
    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralExpressedInOrganismFound(organisms, locator);
    }

    @Override
    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralExperimentalRolesFound(roles, locator);
    }

    @Override
    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator) {
        if (this.delegate == null){
            throw new IllegalStateException("The Mitab interaction datasource has not been initialised. The options for the Mitab interaction datasource " +
                    "should contain at least "+ MIFileDataSourceOptions.INPUT_OPTION_KEY + " to know where to read the interactions from.");
        }
        this.delegate.onSeveralExperimentsFound(experiments, locator);
    }
}
