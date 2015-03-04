package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
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

    public void onAliasWithoutDbSource(MitabAlias alias);

    /**
     * Listen to an event where several CvTerms were found and only one was expected.
     * Can happen when reading a clustered interaction evidence for instance
     * @param terms
     * @param context
     * @param message
     */
    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message);

    /**
     * Listen to an event where several host organisms were found in a single experiment and only one was expected.
     * Can happen when reading a clustered interaction evidence for instance
     * @param organisms
     * @param context
     */
    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context);
}
