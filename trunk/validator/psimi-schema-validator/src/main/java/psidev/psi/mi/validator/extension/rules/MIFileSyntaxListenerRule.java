package psidev.psi.mi.validator.extension.rules;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;
import psidev.psi.mi.jami.xml.reference.XmlIdReference;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Syntax rule listening to syntax events while parsing MI file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>10/12/13</pre>
 */

public class MIFileSyntaxListenerRule extends AbstractMIRule<MIFileDataSource> implements MitabParserListener, PsiXmlParserListener {
    private List<ValidatorMessage> validatorMessages;

    public MIFileSyntaxListenerRule(OntologyManager ontologyManager) {
        super(ontologyManager, MIFileDataSource.class);
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid syntax : "+ ExceptionUtils.getFullStackTrace(e), MessageLevel.FATAL, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Syntax warning : "+ message, MessageLevel.WARN, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        // do nothing
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        // do nothing
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        // do nothing
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        // do nothing
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        // do nothing
    }

    public void onMissingParameterValue(FileSourceContext context) {
        // do nothing
    }

    public void onMissingParameterType(FileSourceContext context) {
        // do nothing
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        // do nothing
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        // do nothing
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        // do nothing
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        // do nothing
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        // do nothing
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        // do nothing
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        // do nothing
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        // do nothing
    }

    public void onXrefWithoutId(FileSourceContext context) {
        // do nothing
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        // do nothing
    }

    public void onAliasWithoutName(FileSourceContext context) {
        // do nothing
    }

    @Override
    public Collection<ValidatorMessage> check(MIFileDataSource o) throws ValidatorException {
        List<ValidatorMessage> validatorMessages = new ArrayList<ValidatorMessage>();
        this.validatorMessages = validatorMessages;

        o.validateSyntax(this);
        this.validatorMessages = null;
        return validatorMessages;
    }

    public String getId() {
        return "R1";
    }

    public void onTextFoundInIdentifier(MitabXref xref) {
        // do nothing
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        // do nothing
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        // do nothing
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        // do nothing
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        // do nothing
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        // do nothing
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        // do nothing
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        // do nothing
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        // do nothing
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        // do nothing
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        // do nothing
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        // do nothing
    }

    public void onAliasWithoutDbSource(MitabAlias alias) {
        // do nothing
    }

    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message) {
        // do nothing
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context) {
        // do nothing
    }

    public void onUnresolvedReference(XmlIdReference ref, String message) {
        // do nothing
    }

    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        // do nothing
    }

    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        // do nothing
    }

    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator) {
        // do nothing
    }

    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator) {
        // do nothing
    }
}
