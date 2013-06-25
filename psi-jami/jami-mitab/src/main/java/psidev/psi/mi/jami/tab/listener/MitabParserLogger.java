package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.model.CvTerm;
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
    private static final Logger cvChangeLogger = Logger.getLogger("MitabParserLogger");

    public void onTextFoundInIdentifier(MitabXref xref, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "Some text has been found in one of the identifier ("+xref.getSourceLocator().toString()+"). In unique identifiers and alternative identifiers columns, we expect db:id and not db:id(text).");
    }

    public void onMissingCvTermName(CvTerm term, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "A cv term does not have a name (line: "+line+", column: "+column+", mitabColumn: "+mitabColumn+"). In the cv term columns, we expect db:id(name) and not just db:id.");
    }

    public void onTextFoundInConfidence(MitabConfidence conf, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "Some text has been found in one interaction confidence ("+conf.getSourceLocator().toString()+"). In the interaction confidences column, we expect confidence_type:value and not just confidence_type:value(text).");
    }

    public void onMissingExpansionId(MitabCvTerm expansion, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "The complex expansion does not have a MI identifier. ("+expansion.getSourceLocator().toString()+"). In the complex expansion column, we expect to find db:id(name) and not just a name. It is taken into account for backward compatibility");
    }

    public void onInvalidSyntax(int line, int column, int mitabColumn, Exception e) {
        cvChangeLogger.log(Level.SEVERE, "Invalid syntax (line: "+line+", column: "+column+", mitab column: "+mitabColumn+"): ", e);
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        cvChangeLogger.log(Level.WARNING, ids.size() + " unique identifiers have been found in ("+ids.iterator().next().getSourceLocator().toString()+"). Only one unique identifier is expected.");
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "No unique identifiers have been found in (line: "+line+", column: "+column+", mitab column: "+mitabColumn+"). An unique identifier is expected.");
    }

    public void onEmptyAliases(int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "No aliases have been found in (line: "+line+", column: "+column+", mitab column: "+mitabColumn+"). At least one alias is expected.");
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.SEVERE, "No interactor identifiers have been found in (line: "+line+", column: "+column+", mitab column: "+mitabColumn+"). At least one unique identifier and one alias is expected. The interactor will be loaded as unknown interactor.");
    }

    public void onAliasFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "Found an alias in alternative identifiers ("+ref.getSourceLocator().toString()+"). The names such as gene names and synonyms should be moved from the alternative identifiers column to the aliases column.");
    }

    public void onChecksumFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "Found a checksum in alternative identifiers ("+ref.getSourceLocator().toString()+"). The checksum such as rogid and irogid should be moved from the alternative identifiers column to the checksum column.");
    }

    public void onChecksumFoundInAliases(MitabAlias alias, int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.WARNING, "Found a checksum in aliases("+alias.getSourceLocator().toString()+"). The checksum such as smiles and inchi key should be moved from the aliases column to the checksum column.");
    }

    public void onSeveralCvTermFound(Collection<MitabCvTerm> terms) {
        cvChangeLogger.log(Level.WARNING, terms.size() + " cv terms have been found in ("+terms.iterator().next().getSourceLocator().toString()+"). Only one cv term is expected so only the first term will be loaded.");
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        cvChangeLogger.log(Level.WARNING, organisms.size() + " organisms have been found in ("+organisms.iterator().next().getSourceLocator().toString()+"). Only one organism is expected per interactor so only the first organism will be loaded.");
    }

    public void onParticipantWithoutInteractorDetails(int line, int column, int mitabColumn) {
        cvChangeLogger.log(Level.SEVERE, "No interactor details have been found in participant (line: "+line+", column: "+column+", mitab column: "+mitabColumn+"). At least one unique identifier and one alias is expected to describe the interactor. The interactor will be loaded as unknown interactor.");
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        cvChangeLogger.log(Level.WARNING, stoichiometry.size() + " stoichiometry values have been found in ("+stoichiometry.iterator().next().getSourceLocator().toString()+"). Only one stoichiometry is expected per participant so only the first stoichiometry will be loaded.");
    }

    public void onInteractionWithoutParticipants(int line) {
        cvChangeLogger.log(Level.SEVERE, "No participant and interactor details have been found in interaction (line: "+line+"). At least one participant is expected per interaction. The interaction will be empty.");
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        cvChangeLogger.log(Level.WARNING, authors.size() + " authors have been found in ("+authors.iterator().next().getSourceLocator().toString()+"). Only one first author is expected per interaction so only the first author will be loaded.");
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        cvChangeLogger.log(Level.WARNING, sources.size() + " sources have been found in ("+sources.iterator().next().getSourceLocator().toString()+"). Only one source is expected per interaction so only the first source will be loaded.");
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms) {
        cvChangeLogger.log(Level.WARNING, organisms.size() + " host organisms have been found in ("+organisms.iterator().next().getSourceLocator().toString()+"). Only one host organism is expected per interaction so only the first host organism will be loaded.");
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        cvChangeLogger.log(Level.WARNING, dates.size() + " created dates have been found in ("+dates.iterator().next().getSourceLocator().toString()+"). Only one created date is expected per interaction so only the first created date will be loaded.");
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        cvChangeLogger.log(Level.WARNING, dates.size() + " update dates have been found in ("+dates.iterator().next().getSourceLocator().toString()+"). Only one created date is expected per interaction so only the first update date will be loaded.");
    }

    public void onEndOfFile() {
        cvChangeLogger.log(Level.INFO, "Finished to read the file.");
    }
}
