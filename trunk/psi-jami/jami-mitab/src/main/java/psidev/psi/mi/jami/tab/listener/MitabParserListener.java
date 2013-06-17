package psidev.psi.mi.jami.tab.listener;

/**
 * A listener listening to events when parsing a mitab file
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/06/13</pre>
 */

public interface MitabParserListener {

    public void onSeveralUniqueIdentifiers(int line, int column, int mitabColumn);

    public void onTextFoundInIdentifier(int line, int column, int mitabColumn);

    public void onMissingCvTermName(int line, int column, int mitabColumn);

    public void onSeveralCvTermFound(int line, int column, int mitabColumn);

    public void onSeveralFirstAuthorFound(int line, int column, int mitabColumn);

    public void onTextFoundInConfidence(int line, int column, int mitabColumn);

    public void onMissingExpansionId(int line, int column, int mitabColumn);

    public void onInvalidXref(int line, int column, int mitabColumn);

    public void onInvalidAlias(int line, int column, int mitabColumn);

    public void onInvalidCvTerm(int line, int column, int mitabColumn);

    public void onInvalidFirstAuthor(int line, int column, int mitabColumn);

    public void onInvalidOrganismSyntax(int line, int column, int mitabColumn);

    public void onInvalidSource(int line, int column, int mitabColumn);

    public void onInvalidConfidence(int line, int column, int mitabColumn);

    public void onInvalidAnnotation(int line, int column, int mitabColumn);

    public void onInvalidParameter(int line, int column, int mitabColumn);

    public void onInvalidDate(int line, int column, int mitabColumn);

    public void onInvalidChecksum(int line, int column, int mitabColumn);

    public void onInvalidNegative(int line, int column, int mitabColumn);

    public void onInvalidRange(int line, int column, int mitabColumn);

    public void onInvalidFeature(int line, int column, int mitabColumn);

    public void onInvalidStoichiometry(int line, int column, int mitabColumn);
}
