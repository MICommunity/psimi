package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.listener.impl.MIFileParserLogger;
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

public class MitabParserLogger extends MIFileParserLogger implements MitabParserListener{
    private static final Logger logger = Logger.getLogger("MitabParserLogger");

    public void onTextFoundInIdentifier(MitabXref xref) {
        logger.log(Level.WARNING, "Some text has been found in one of the identifier (" + xref.toString() + "). In unique identifiers and alternative identifiers columns, we expect db:id and not db:id(text).");
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        logger.log(Level.WARNING, "Some text has been found in one interaction confidence (" + conf.toString() + "). In the interaction confidences column, we expect confidence_type:value and not confidence_type:value(text).");
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        logger.log(Level.WARNING, "The complex expansion does not have a MI identifier. (" + expansion.toString() + "). In the complex expansion column, we expect to find db:id(name) and not just a name. It is however taken into account for backward compatibility");
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        logger.log(Level.WARNING, ids.size() + " unique identifiers have been found in (" + ids.iterator().next().toString() + "). Only one unique identifier is expected.");
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        logger.log(Level.WARNING, "No unique identifier have been found in (line: " + line + ", column: " + column + ", mitab column: " + mitabColumn + "). An unique identifier is expected.");
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

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        logger.log(Level.WARNING, stoichiometry.size() + " stoichiometry values have been found in (" + stoichiometry.iterator().next().toString() + "). Only one stoichiometry is expected per participant so only the first stoichiometry will be loaded.");
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
