package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.listener.MIFileParserListener;
import psidev.psi.mi.jami.tab.extension.*;

import java.util.Collection;

/**
 * A listener listening to events when parsing a mitab file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface MitabParserListener extends MIFileParserListener{

    public void onTextFoundInIdentifier(MitabXref xref);

    public void onTextFoundInConfidence(MitabConfidence conf);

    public void onMissingExpansionId(MitabCvTerm expansion);

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids);

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn);

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn);

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms);

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry);

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors);

    public void onSeveralSourceFound(Collection<MitabSource> sources);

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates);

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates);
}
