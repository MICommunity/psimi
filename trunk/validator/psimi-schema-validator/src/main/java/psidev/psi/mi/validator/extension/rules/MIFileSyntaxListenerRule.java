package psidev.psi.mi.validator.extension.rules;

import org.apache.commons.lang.exception.ExceptionUtils;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Interactor;
import psidev.psi.mi.jami.model.Participant;
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

public class MIFileSyntaxListenerRule extends AbstractMIRule<MIFileDataSource> implements MIFileParserListener {
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
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Cv without name: "+ message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Interactor without name. At least a shortLabel is expected.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Participant without interactor. A participant must always have an interactor.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Interaction without participants. An interaction must always have at least one participant.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("The organism taxid "+taxid+" is not a valid taxid.", MessageLevel.WARN, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingParameterValue(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Parameter without a value. Parameters must always have a value and a parameter type.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingParameterType(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Parameter without a parameter type. Parameters must always have a value and a parameter type.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Confidence without a value. Confidences must always have a value and a confidence type.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Confidence without a confidence type. Confidences must always have a value and a confidence type.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Checksum without a value. Checksum must always have a value and a method.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Checksum without a value. Checksum must always have a value and a method.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid range position: "+message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid range: "+message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid stoichiometry: "+message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Database xref without a database. A database xref must always have a database.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onXrefWithoutId(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Database xref without an id. A database xref must always have an id.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Annotation without a topic. An annotation must always have a topic.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onAliasWithoutName(FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Alias without a name. An alias must always have a name.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
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
}
