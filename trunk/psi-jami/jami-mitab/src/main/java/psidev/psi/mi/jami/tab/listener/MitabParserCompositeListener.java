package psidev.psi.mi.jami.tab.listener;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.listener.impl.MIFileParserCompositeListener;
import psidev.psi.mi.jami.tab.extension.*;

import java.util.Collection;

/**
 * This class contains several MI file listeners
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/12/13</pre>
 */

public class MitabParserCompositeListener extends MIFileParserCompositeListener<MitabParserListener> implements MitabParserListener {

    public MitabParserCompositeListener(){
        super();
    }


    public void onTextFoundInIdentifier(MitabXref xref) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onTextFoundInIdentifier(xref);
        }
    }

    public void onTextFoundInConfidence(MitabConfidence conf) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onTextFoundInConfidence(conf);
        }
    }

    public void onMissingExpansionId(MitabCvTerm expansion) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onMissingExpansionId(expansion);
        }
    }

    public void onSeveralUniqueIdentifiers(Collection<MitabXref> ids) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralUniqueIdentifiers(ids);
        }
    }

    public void onEmptyUniqueIdentifiers(int line, int column, int mitabColumn) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onEmptyUniqueIdentifiers(line, column, mitabColumn);
        }
    }

    public void onMissingInteractorIdentifierColumns(int line, int column, int mitabColumn) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onMissingInteractorIdentifierColumns(line, column, mitabColumn);
        }
    }

    public void onSeveralOrganismFound(Collection<MitabOrganism> organisms) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralOrganismFound(organisms);
        }
    }

    public void onSeveralStoichiometryFound(Collection<MitabStoichiometry> stoichiometry) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralStoichiometryFound(stoichiometry);
        }
    }

    public void onSeveralFirstAuthorFound(Collection<MitabAuthor> authors) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralFirstAuthorFound(authors);
        }
    }

    public void onSeveralSourceFound(Collection<MitabSource> sources) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralSourceFound(sources);
        }
    }

    public void onSeveralCreatedDateFound(Collection<MitabDate> dates) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralCreatedDateFound(dates);
        }
    }

    public void onSeveralUpdatedDateFound(Collection<MitabDate> dates) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralUpdatedDateFound(dates);
        }
    }

    public void onAliasWithoutDbSource(MitabAlias alias) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onAliasWithoutDbSource(alias);
        }
    }

    public void onSeveralCvTermsFound(Collection<MitabCvTerm> terms, FileSourceContext context, String message) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralCvTermsFound(terms, context, message);
        }
    }

    public void onSeveralHostOrganismFound(Collection<MitabOrganism> organisms, FileSourceContext context) {
        for (MitabParserListener delegate : getDelegates()){
            delegate.onSeveralHostOrganismFound(organisms, context);
        }
    }
}
