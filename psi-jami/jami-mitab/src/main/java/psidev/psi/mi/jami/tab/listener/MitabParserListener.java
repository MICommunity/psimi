package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * A listener listening to events when parsing a mitab file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface MitabParserListener {

    public void onTextFoundInIdentifier(int line, int column, int mitabColumn);

    public void onMissingCvTermName(int line, int column, int mitabColumn);

    public void onTextFoundInConfidence(int line, int column, int mitabColumn);

    public void onMissingExpansionId(int line, int column, int mitabColumn);

    public void onInvalidSyntax(int line, int column, int mitabColumn);

    public void onSeveralUniqueIdentifiers(Collection<? extends Xref> ids);

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn);

    public void onEmptyAliases(int line, int column, int mitabColumn);

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn);

    public void onAliasFoundInAlternativeIds(Xref ref, int line, int column, int mitabColumn);

    public void onChecksumFoundInAlternativeIds(Xref ref, int line, int column, int mitabColumn);

    public void onChecksumFoundInAliases(Alias alias, int line, int column, int mitabColumn);

    public void onSeveralCvTermFound(Collection<? extends CvTerm> terms);

    public void onSeveralFirstAuthorFound(int line, int column, int mitabColumn);

    public void onSeveralOrganismFound(Collection<? extends Organism> organisms);

    public void onParticipantWithoutInteractorDetails(int line, int column, int mitabColumn);

    public void onSeveralStoichiometryFound(Collection<? extends Stoichiometry> stoichiometry);

    public void onInteractionWithoutParticipants(int line);
}
