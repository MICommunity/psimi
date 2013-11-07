package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger for MitabParser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/13</pre>
 */

public class MitabParserLogger implements MitabParserListener{
    private static final Logger logger = Logger.getLogger("MitabParserLogger");

    public void onTextFoundInIdentifier(MitabXref xref) {
        logger.log(Level.WARNING, "Some text has been found in one of the identifier (" + xref.toString() + "). In unique identifiers and alternative identifiers columns, we expect db:id and not db:id(text).");
    }

    public void onMissingCvTermName(CvTerm term, FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + context.toString() + ")");
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        logger.log(Level.WARNING, "Some text has been found in one interaction confidence (" + conf.toString() + "). In the interaction confidences column, we expect confidence_type:value and not confidence_type:value(text).");
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        logger.log(Level.WARNING, "The complex expansion does not have a MI identifier. (" + expansion.toString() + "). In the complex expansion column, we expect to find db:id(name) and not just a name. It is however taken into account for backward compatibility");
    }

    public void onInvalidSyntax(FileSourceContext context, Exception e) {
        logger.log(Level.SEVERE, "Invalid syntax (" + context.toString() + "): ", e);
    }

    public void onSyntaxWarning(FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + context.toString() + ")");
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        logger.log(Level.WARNING, ids.size() + " unique identifiers have been found in (" + ids.iterator().next().toString() + "). Only one unique identifier is expected.");
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        logger.log(Level.WARNING, "No unique identifier have been found in (line: " + line + ", column: " + column + ", mitab column: " + mitabColumn + "). An unique identifier is expected.");
    }

    public void onMissingInteractorName(Interactor interactor, FileSourceContext context) {
        logger.log(Level.WARNING, "No aliases have been found in (" + context.toString() + "). At least one alias is expected for each interactor.");
    }

    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message) {
        logger.log(Level.WARNING, message + "(" + context.toString() + ")");
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        logger.log(Level.SEVERE, "No interactor identifiers have been found in (line: " + line + ", column: " + column + ", mitab column: " + mitabColumn + "). At least one unique identifier and one alias is expected. The interactor will be loaded as unknown interactor.");
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        logger.log(Level.WARNING, organisms.size() + " organisms have been found in (" + organisms.iterator().next().toString() + "). Only one organism is expected per interactor so only the first organism will be loaded.");
    }

    public void onParticipantWithoutInteractor(Participant participant, FileSourceContext context) {
        logger.log(Level.SEVERE, "No interactor details have been found in participant (" + context.toString() + "). At least one unique identifier and one alias is expected to describe the interactor. The interactor will be loaded as unknown interactor.");
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        logger.log(Level.WARNING, stoichiometry.size() + " stoichiometry values have been found in (" + stoichiometry.iterator().next().toString() + "). Only one stoichiometry is expected per participant so only the first stoichiometry will be loaded.");
    }

    public void onInteractionWithoutParticipants(Interaction interaction, FileSourceContext context) {
        logger.log(Level.SEVERE, "No participant and interactor details have been found in interaction (" + context.toString() + "). At least one participant is expected per interaction. The interaction will be empty.");
    }

    public void onInvalidOrganismTaxid(String taxid, FileSourceContext context) {
        logger.log(Level.WARNING, "The organism taxid "+taxid+" ("+context.toString()+") is not valid. Only positive integers, -1, -2, -3, -4 and -5 are recognized. The organism will be loaded as -3(unknown)");
    }

    public void onMissingParameterValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The parameter is not valid as it does not have a value ("+context.toString()+"). The parameter will be loaded with a value of 0.");
    }

    public void onMissingParameterType(FileSourceContext context) {
        logger.log(Level.WARNING, "The parameter is not valid as it does not have a parameter type ("+context.toString()+"). The parameter will be loaded with a type 'unknown'.");
    }

    public void onMissingConfidenceValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The confidence is not valid as it does not have a value ("+context.toString()+"). The confidence will be loaded with a value 'unknown'.");
    }

    public void onMissingConfidenceType(FileSourceContext context) {
        logger.log(Level.WARNING, "The confidence is not valid as it does not have a confidence type ("+context.toString()+"). The confidence will be loaded with a type 'unknown'.");
    }

    public void onMissingChecksumValue(FileSourceContext context) {
        logger.log(Level.WARNING, "The checksum is not valid as it does not have a value ("+context.toString()+"). The checksum will be loaded with a value 'unknown'.");
    }

    public void onMissingChecksumMethod(FileSourceContext context) {
        logger.log(Level.WARNING, "The checksum is not valid as it does not have a checksum method ("+context.toString()+"). The checksum will be loaded with a method 'unknown'.");
    }

    public void onInvalidPosition(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The position is not valid ("+context.toString()+"): "+message+". The position will be loaded as undetermined position.");
    }

    public void onInvalidRange(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The range is not valid ("+context.toString()+"): "+ message+". The range will be loaded as undetermined range.");
    }

    public void onInvalidStoichiometry(String message, FileSourceContext context) {
        logger.log(Level.WARNING, "The stoichiometry is not valid ("+context.toString()+"): "+message+". The stoichiometry will be loaded with a value of 0.");
    }

    public void onXrefWithoutDatabase(FileSourceContext context) {
        logger.log(Level.WARNING, "The xref is not valid as it does not have a database ("+context.toString()+"). The xref will be loaded with a database 'unknown'.");
    }

    public void onXrefWithoutId(FileSourceContext context) {
        logger.log(Level.WARNING, "The xref is not valid as it does not have an id ("+context.toString()+"). The xref will be loaded with an id 'unknown'.");
    }

    public void onAnnotationWithoutTopic(FileSourceContext context) {
        logger.log(Level.WARNING, "The annotation is not valid as it does not have a topic ("+context.toString()+"). The annotation will be loaded with a topic 'unknown'.");
    }

    public void onAliasWithoutName(FileSourceContext context) {
        logger.log(Level.WARNING, "The alias is not valid as it does not have a name ("+context.toString()+"). The alias will be loaded with a name 'unspecified'.");
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        logger.log(Level.WARNING, authors.size() + " authors have been found in (" + authors.iterator().next().toString() + "). Only one first author is expected per interaction so only the first author will be loaded.");
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        logger.log(Level.WARNING, sources.size() + " sources have been found in (" + sources.iterator().next().toString() + "). Only one source is expected per interaction so only the first source will be loaded.");
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context) {
        logger.log(Level.WARNING, organisms.size() + " host organisms have been found in (" + context.toString() + "). Only one host organism is expected per interaction so only the first host organism will be loaded.");
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        logger.log(Level.WARNING, dates.size() + " created dates have been found in (" + dates.iterator().next().toString() + "). Only one created date is expected per interaction so only the first created date will be loaded.");
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        logger.log(Level.WARNING, dates.size() + " update dates have been found in (" + dates.iterator().next().toString() + "). Only one created date is expected per interaction so only the first update date will be loaded.");
    }

    public void onAliasWithoutDbSource(MitabAlias alias) {
        logger.log(Level.WARNING, "The alias is not valid in MITAB as it does not have a db source. The alias will be loaded with a db source 'unknown'.");
    }

}
