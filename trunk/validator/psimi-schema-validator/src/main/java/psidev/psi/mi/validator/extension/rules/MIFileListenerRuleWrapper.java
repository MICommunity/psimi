package psidev.psi.mi.validator.extension.rules;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.util.Collection;

/**
 * This rule contains all rules that can check Alias objects
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>03/04/13</pre>
 */

public class MIFileListenerRuleWrapper extends AbstractRuleWrapper<MIFileDataSource> implements MitabParserListener, PsiXmlParserListener{

    public MIFileListenerRuleWrapper(OntologyManager ontologyManager) {
        super(ontologyManager, MIFileDataSource.class);
    }

    public String getId() {
        return "RmiFile";
    }

    public void onTextFoundInIdentifier(MitabXref xref) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onTextFoundInIdentifier(xref);
        }
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onTextFoundInConfidence(conf);
        }
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onMissingExpansionId(expansion);
        }
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralUniqueIdentifiers(ids);
        }
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onEmptyUniqueIdentifiers(line, column, mitabColumn);
        }
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onMissingInteractorIdentifierColumns(line, column, mitabColumn);
        }
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralOrganismFound(organisms);
        }
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralStoichiometryFound(stoichiometry);
        }
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralFirstAuthorFound(authors);
        }
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralSourceFound(sources);
        }
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralCreatedDateFound(dates);
        }
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralUpdatedDateFound(dates);
        }
    }

    public void onAliasWithoutDbSource(MitabAlias alias) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onAliasWithoutDbSource(alias);
        }
    }

    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralCvTermsFound(terms, context, message);
        }
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MitabParserListener)rule).onSeveralHostOrganismFound(organisms, context);
        }
    }

    public void onUnresolvedReference(XmlIdReference ref, String message) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((PsiXmlParserListener)rule).onUnresolvedReference(ref, message);
        }
    }

    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((PsiXmlParserListener)rule).onSeveralHostOrganismFound(organisms, locator);
        }
    }

    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((PsiXmlParserListener)rule).onSeveralExpressedInOrganismFound(organisms, locator);
        }
    }

    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((PsiXmlParserListener)rule).onSeveralExperimentalRolesFound(roles, locator);
        }
    }

    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((PsiXmlParserListener)rule).onSeveralExperimentsFound(experiments, locator);
        }
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onInvalidSyntax(context, e);
        }
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onSyntaxWarning(context, message);
        }
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingCvTermName(term, context, message);
        }
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingInteractorName(interactor, context);
        }
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onParticipantWithoutInteractor(participant, context);
        }
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onInteractionWithoutParticipants(interaction, context);
        }
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onInvalidOrganismTaxid(taxid, context);
        }
    }

    public void onMissingParameterValue(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingParameterValue(context);
        }
    }

    public void onMissingParameterType(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingParameterType(context);
        }
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingConfidenceValue(context);
        }
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingConfidenceType(context);
        }
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingChecksumValue(context);
        }
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onMissingChecksumMethod(context);
        }
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onInvalidPosition(message, context);
        }
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onInvalidRange(message, context);
        }
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onInvalidStoichiometry(message, context);
        }
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onXrefWithoutDatabase(context);
        }
    }

    public void onXrefWithoutId(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onXrefWithoutId(context);
        }
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onAnnotationWithoutTopic(context);
        }
    }

    public void onAliasWithoutName(FileSourceContext context) {
        for (ObjectRule<MIFileDataSource> rule : getRules()){
            ((MIFileParserListener)rule).onAliasWithoutName(context);
        }
    }
}