package psidev.psi.mi.validator.extension.rules;

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

public class MIFileGrammarListenerRule extends AbstractMIRule<MIFileDataSource> implements MitabParserListener, PsiXmlParserListener {
    private List<ValidatorMessage> validatorMessages;

    public MIFileGrammarListenerRule(OntologyManager ontologyManager) {
        super(ontologyManager, MIFileDataSource.class);
        this.validatorMessages = new ArrayList<ValidatorMessage>();
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        // do nothing
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        // do nothing
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        // do nothing
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        // do nothing
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Participant without interactor description. Each participant should describe a valid interactor.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Interaction without any participants. At least one participant is expected.", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid organism taxid: "+taxid+". The organism has been loaded as an unknown organism(-3)", MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
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
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid range position: "+message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid feature range: "+message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Invalid stoichiometry: "+message, MessageLevel.ERROR, new Mi25Context(context.getSourceLocator()),this));
        }
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
        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>(this.validatorMessages);
        this.validatorMessages.clear();
        return messages;
    }

    public String getId() {
        return "R1";
    }

    public void onTextFoundInIdentifier(MitabXref xref) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found MITAB identifier of type db:id(text). In MITAB columns 1,2,3 and 4, we only expect db:id fields", MessageLevel.WARN, new Mi25Context(xref.getSourceLocator()),this));
        }
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found MITAB confidence ot type type:value(text). In MITAB columns 15, we only expect type:value fields", MessageLevel.WARN, new Mi25Context(conf.getSourceLocator()),this));
        }
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("In MITAB complex expansion column, we expect psi-mi cross references of type psi-mi:\"id\"(cv name) but we only found the cv name.", MessageLevel.WARN, new Mi25Context(expansion.getSourceLocator()),this));
        }
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("In MITAB unique identifier columns (1 and 2), we only expect one identifier. However, we found "+ids.size()+" unique identifiers.", MessageLevel.WARN, new Mi25Context(ids.iterator().next().getSourceLocator()),this));
        }
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("MITAB unique identifier columns (1 and 2) should contain at least one identifier.", MessageLevel.WARN, new Mi25Context(new MitabSourceLocator(line, column, mitabColumn)),this));
        }
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Interactors in MITAB should have at least one identifier in columns 1 and 2.", MessageLevel.WARN, new Mi25Context(new MitabSourceLocator(line, column, mitabColumn)),this));
        }
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+organisms.size()+" different organisms associated with the same interactor. Only one organism is expected per interactor.", MessageLevel.WARN, new Mi25Context(organisms.iterator().next().getSourceLocator()),this));
        }
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+stoichiometry.size()+" different stoichiometry values associated with the same participant. Only one stoichiometry value is expected per participant.", MessageLevel.WARN, new Mi25Context(stoichiometry.iterator().next().getSourceLocator()),this));
        }
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+authors.size()+" different first authors associated with the same publication. Only the first 'first author' will be loaded.", MessageLevel.WARN, new Mi25Context(authors.iterator().next().getSourceLocator()),this));
        }
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+sources.size()+" different sources associated with the same publication. Only the first source will be loaded and checked.", MessageLevel.WARN, new Mi25Context(sources.iterator().next().getSourceLocator()),this));
        }
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+dates.size()+" different created dates associated with the same interaction. Only one created date is expected per interaction.", MessageLevel.WARN, new Mi25Context(dates.iterator().next().getSourceLocator()),this));
        }
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+dates.size()+" last update date associated with the same interaction. Only one last update date is expected per interaction.", MessageLevel.WARN, new Mi25Context(dates.iterator().next().getSourceLocator()),this));
        }
    }

    public void onAliasWithoutDbSource(MitabAlias alias) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("In MITAB alias columns 5 and 6, we expect a db source associated with each alias. One of them do not have a db source.", MessageLevel.ERROR, new Mi25Context(alias.getSourceLocator()),this));
        }
    }

    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+terms.size()+" different cv terms where only one cv term will be loaded and checked.", MessageLevel.WARN, new Mi25Context(terms.iterator().next().getSourceLocator()),this));
        }
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+organisms.size()+" host organism associated with the same experiment. Only the first host organism will be loaded and checked.", MessageLevel.WARN, new Mi25Context(organisms.iterator().next().getSourceLocator()),this));
        }
    }

    public void onUnresolvedReference(XmlIdReference ref, String message) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("One PSI-XML reference cannot be resolved. Checks that each id is unique per entry and is not shared between participants, experiments, interactions, features and interactors. ", MessageLevel.WARN, new Mi25Context(ref.getSourceLocator()),this));
        }
    }

    public void onSeveralHostOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+organisms.size()+" host organism associated with the same experiment.", MessageLevel.INFO, new Mi25Context(locator),this));
        }
    }

    public void onSeveralExpressedInOrganismFound(Collection<Organism> organisms, FileSourceLocator locator) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+organisms.size()+" host organism associated with the same participant.", MessageLevel.INFO, new Mi25Context(locator),this));
        }
    }

    public void onSeveralExperimentalRolesFound(Collection<CvTerm> roles, FileSourceLocator locator) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+roles.size()+" experimental roles associated with the same participant.", MessageLevel.INFO, new Mi25Context(locator),this));
        }
    }

    public void onSeveralExperimentsFound(Collection<Experiment> experiments, FileSourceLocator locator) {
        if (this.validatorMessages != null){
            this.validatorMessages.add(new ValidatorMessage("Found "+experiments.size()+" experiments associated with the same interaction.", MessageLevel.INFO, new Mi25Context(locator),this));
        }
    }
}
