package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.tab.extension.*;

import java.util.Collection;

/**
 * A listener listening to events when parsing a mitab file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface MitabParserListener {

    public void onTextFoundInIdentifier(MitabXref xref, int line, int column, int mitabColumn);

    public void onMissingCvTermName(CvTerm term, int line, int column, int mitabColumn);

    public void onTextFoundInConfidence(MitabConfidence conf, int line, int column, int mitabColumn);

    public void onMissingExpansionId(MitabCvTerm expansion, int line, int column, int mitabColumn);

    public void onInvalidSyntax(int line, int column, int mitabColumn);

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids);

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn);

    public void onEmptyAliases(int line, int column, int mitabColumn);

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn);

    public void onAliasFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn);

    public void onChecksumFoundInAlternativeIds(MitabXref ref, int line, int column, int mitabColumn);

    public void onChecksumFoundInAliases(MitabAlias alias, int line, int column, int mitabColumn);

    public void onSeveralCvTermFound(Collection<MitabCvTerm> terms);

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms);

    public void onParticipantWithoutInteractorDetails(int line, int column, int mitabColumn);

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry);

    public void onInteractionWithoutParticipants(int line);

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors);

    public void onSeveralSourceFound(Collection<MitabSource> sources);

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms);

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates);

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates);
}
