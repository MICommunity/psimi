package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25Ontology;
import psidev.psi.mi.xml.model.CvType;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.AbstractRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Represents the dependencies between one term and a set of associated terms.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: DependencyMapping.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class DependencyMapping {

    /**
     * contains all the dependencies.
     */
    protected Map<Term, Set<AssociatedTerm>> dependencies;
    /**
     * The file containing the dependencies should have the columns separated by a tab
     */
    private static final String separator = "\t";
    /**
     * The comments in the file
     */
    private static final String comment = "#";
    /**
     * The quote to remove in the file
     */
    private static final String quote = "\"";

    private static final Log log = LogFactory.getLog( DependencyMapping.class );

    public DependencyMapping() {
        this.dependencies = new HashMap<Term, Set<AssociatedTerm>>( );
    }

    /**
     *
     * @return the dependencies
     */
    public Map<Term, Set<AssociatedTerm>> getDependencies() {
        return dependencies;
    }

    /**
     *
     * @param ontology
     * @return
     */
    public synchronized DependencyMapping getInstance( OntologyAccess ontology ) {
        return this;
    }

    /**
     * From the different colums, this method creates the first term of the dependency
     * @param columns
     * @return  the first term of the dependency
     */
    protected Term createFirstTermOfTheDependency(String[] columns){

        Term term = new Term( columns[0], columns[1] );
        if( columns[2] != null && columns[2].length() > 0 ) {
            boolean isChildrenIncluded = Boolean.valueOf( columns[2] );

            if (isChildrenIncluded || !isChildrenIncluded){
                term.setIncludeChildren(isChildrenIncluded);
            }
            else {
                throw new ValidatorRuleException("The boolean value for isIncludedChildren should be TRUE or FALSE but not " + isChildrenIncluded);
            }
        }

        return term;
    }

    /**
     * From the different colums, this method creates the second term of the dependency
     * @param columns
     * @return  the associated term of the dependency
     */
    protected AssociatedTerm createSecondaryTermOfTheDependency(String[] columns){

        Term secondTerm = new Term(columns[3], columns[4]);
        if( columns[5] != null && columns[5].length() > 0 ) {
            boolean isChildrenIncluded = Boolean.valueOf( columns[5] );

            if (isChildrenIncluded || !isChildrenIncluded){
                secondTerm.setIncludeChildren(isChildrenIncluded);
            }
            else {
                throw new ValidatorRuleException("The boolean value for isIncludedChildren should be TRUE or FALSE but not " + isChildrenIncluded);
            }
        }

        // Get a couple secondTerm and message level.
        AssociatedTerm associatedTerm = new AssociatedTerm(secondTerm, columns[6] != null && columns[6].length()> 0 ? columns[6] : null);

        return associatedTerm;
    }

    /**
     * Remove the quote at the beginning and/or the end of a column value
     * @param columnWithQuote : value of a column
     * @return the value of the column without any quotes
     */
    private String extractValueOf(String columnWithQuote){
        String value = columnWithQuote;

        if (columnWithQuote.startsWith( quote )){
            value = value.substring(1);
        }
        if (columnWithQuote.endsWith(quote)){
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    /**
     * Builds the dependencies from a file (tab file).
     * @param ontology
     * @param mi
     * @param url
     * @throws IOException
     * @throws ValidatorException
     */
    public void buildMappingFromFile( Mi25Ontology ontology, OntologyAccess mi, URL url) throws IOException, ValidatorException {

        InputStream is = url.openStream();
        BufferedReader in = new BufferedReader( new InputStreamReader(is) );

        String str;
        int lineCount = 0;
        while ( ( str = in.readLine() ) != null ) {
            try {
                lineCount++;
                str = extractValueOf(str);

                if( str.startsWith( comment ) || str.trim().length() == 0) {
                    continue; // skip the commentary
                }

                // 0. first term MI
                // 1. first term NAME
                // 2. INCLUDE CHILDREN
                // 3. second term MI
                // 4. second term NAME
                // 5. message level

                if (str.contains(separator)){
                    final String[] columns = StringUtils.splitPreserveAllTokens(str,separator);

                    // we skip empty lines and those starting with the symbol '#'. We remove the " if they it appears at the beginning or the end of the line
                    for (int i = 0; i < columns.length; i++){
                        String col = columns[i];
                        if (col != null){
                            columns[i] = extractValueOf(col);
                        }
                    }

                    // Creates the first term of the dependency
                    Term firstTerm = createFirstTermOfTheDependency(columns);

                    // creates the associated term
                    AssociatedTerm associatedTerm = createSecondaryTermOfTheDependency(columns);

                    // Create a dependency for these terms
                    loadDependencies(firstTerm, associatedTerm, ontology, lineCount);

                    // contains all the possible children of the first term of the dependency.
                    Set<Term> firstTermChildren = new HashSet<Term>();

                    // If the first term is including its children, we duplicate the dependency for all the children
                    if(firstTerm.isIncludeChildren()) {
                        // fetch all children and append them to the local map
                        final Collection<OntologyTermI> children = mi.getValidTerms( firstTerm.getId(), true, false );
                        if( ! children.isEmpty() ) {

                            for ( OntologyTermI child : children ) {
                                final Term termChild = new Term( child.getTermAccession(), child.getPreferredName() );
                                termChild.setParent(firstTerm);

                                // add the child in the set of the children of the first term.
                                firstTermChildren.add(termChild);

                                // Create a dependency with the children
                                loadDependencies(termChild, associatedTerm, ontology, lineCount);
                            }
                        }
                    }
                    // If the second term is including its children, we duplicate the dependency for all the children
                    if (associatedTerm.getSecondTermOfTheDependency().isIncludeChildren()){
                        // fetch all children and append them to the local map
                        final Collection<OntologyTermI> children2 = mi.getValidTerms( associatedTerm.getSecondTermOfTheDependency().getId(), true, false );
                        if( ! children2.isEmpty() ) {

                            for ( OntologyTermI child2 : children2 ) {
                                final Term termChild2 = new Term( child2.getTermAccession(), child2.getPreferredName() );
                                AssociatedTerm childTerm2 = new AssociatedTerm(termChild2, associatedTerm.getLevel());
                                childTerm2.getSecondTermOfTheDependency().setParent(associatedTerm.getSecondTermOfTheDependency());

                                // Create a dependency with the children
                                loadDependencies(firstTerm, childTerm2, ontology, lineCount);

                                if (!firstTermChildren.isEmpty()){
                                    // duplicate the dependencies for the children of the first term too
                                    for (Term first : firstTermChildren){
                                        // Create a dependency with the children
                                        loadDependencies(first, childTerm2, ontology, lineCount);
                                    }
                                }
                            }
                        }
                    }
                }
                else{
                    log.error( "The dependency mapping file is not valid. It should be a tab file : first term MI  first term name  includeChildren  second term MI  second term name  includeChildren  message level" );
                }

                if ( log.isInfoEnabled() ) {
                    log.info( "Completed reading " + this.dependencies.size() + " dependencies from mapping file" );
                }
            }
            catch (ValidatorRuleException e) {
                throw new ValidatorRuleException("Check the file " + url.getFile() + " at the line "+ lineCount +".", e);
            }
        }
        in.close();
    }

    /**
     * Add the new term 'term' and the associated term 'secondTerm' in the dependency map.
     * If a term with the same id/name as 'term' is already included in the dependency map with the
     * same associated term (same id and/or same name), check if it is a dependency deduced from the parents.
     *  If no, log a warning message.
     *  If yes, check if the new term 'term' is also deduced from its parents.
     *      If no, replace the old dependency with a new dependency 'term' - 'secondTerm'.
     *      If yes, check which term between the parent of the old term and the one of the new term is the child of the other,
     *      then keep only in the dependency map the term with a 'younger' parent.
     * @param term
     * @param secondTerm
     * @param ontology
     */
    protected void loadDependencies(Term term, AssociatedTerm secondTerm, Mi25Ontology ontology, int lineCount){
        Set<AssociatedTerm> associatedTermSet = new HashSet<AssociatedTerm> ();
        if (term == null || secondTerm == null){
            log.error("The first term and the second term of a dependency can't be null : first term = " + term + "and second term = " + secondTerm);
        }
        // Add the new term in the dependency map.
        if( ! this.dependencies.containsKey( term ) ) {
            this.dependencies.put( term, associatedTermSet );
            associatedTermSet.add( secondTerm ); // will only add if not already in.
        }
        else {
            // contains all the existing associated term we want to remove.
            Set<AssociatedTerm> associatedTermToReplace = new HashSet<AssociatedTerm> ();

            for (Term oldTerm : this.dependencies.keySet()){
                // There is already a term in the dependency map with the same id/name as 'term'.
                if (oldTerm.equals(term)){
                    associatedTermSet = this.dependencies.get( oldTerm );

                    for (AssociatedTerm associatedTerm : associatedTermSet){
                        Term t = associatedTerm.getSecondTermOfTheDependency();
                        Term t2 = secondTerm.getSecondTermOfTheDependency();

                        // There is an associated term in the dependency map with the same id/name as 'secondTerm.getSecondTermOfTheDependency()'
                        if (t.equals(t2)){
                            log.warn("The dependency " + oldTerm + " - " + t + " at the line "+ lineCount +" has already been loaded.");
                            OntologyTermI termT = new TermBridge(t.getParent());
                            OntologyTermI termT2 = new TermBridge(t2.getParent());
                            OntologyTermI firstTerm = new TermBridge(term);
                            OntologyTermI oldFirstTerm = new TermBridge(oldTerm);
                            boolean conflictResolved = false;

                            if (t.isDeducedFromItsParent() && t2.isDeducedFromItsParent()){
                                if (ontology.isChildOf(termT, termT2)){
                                    log.warn("The existing term " + t + " deduced from its parent "+ t.getParent() + " has been replaced by the new term " + t2 + " deduced from its parent "+ t2.getParent() + " (line "+lineCount+")");
                                    associatedTermToReplace.add(associatedTerm);
                                    conflictResolved = true;
                                }
                                else if (ontology.isChildOf(termT2, termT)){
                                    log.warn("The existing term " + t + " deduced from its parent "+ t.getParent() + " has not been replaced by the new term " + t2 + " deduced from its parent "+ t2.getParent() + " (line "+lineCount+")");
                                    conflictResolved = true;
                                }
                                else {
                                    log.error("The new term " + t2 + "deduced from its parent "+ t2.getParent() + " has not been loaded. There is a conflict in the dependency file. (line "+lineCount+")");
                                }
                            }
                            else if (t.isDeducedFromItsParent() && !t2.isDeducedFromItsParent()){
                                log.warn("The existing term " + t + " deduced from its parent "+ t.getParent() + " has been replaced by the new term " + t2 + " (line "+lineCount+")");
                                associatedTermToReplace.add(associatedTerm);
                                conflictResolved = true;
                            }
                            else if (!t.isDeducedFromItsParent() && t2.isDeducedFromItsParent()){
                                log.warn("The existing term " + t + " has not been replaced by the new term " + t2 + "deduced from its parent "+ t2.getParent() + " (line "+lineCount+")");
                                conflictResolved = true;
                            }

                            // If the existing first term in the dependency map is deduced from a parent term.
                            if (oldTerm.isDeducedFromItsParent() && term.isDeducedFromItsParent()){
                                if (ontology.isChildOf(oldFirstTerm, firstTerm)){
                                    log.warn("The existing term " + oldTerm + " deduced from its parent "+ oldTerm.getParent() + " has been replaced by the new term " + term + " deduced from its parent "+ term.getParent() + " (line "+lineCount+")");
                                    associatedTermToReplace.add(associatedTerm);
                                    oldTerm = term;
                                    conflictResolved = true;
                                }
                                else if (ontology.isChildOf(firstTerm, oldFirstTerm)){
                                    log.warn("The existing term " + oldTerm + " deduced from its parent "+ oldTerm.getParent() + " has not been replaced by the new term " + term + " deduced from its parent "+ term.getParent() + " (line "+lineCount+")");
                                    conflictResolved = true;
                                }
                                else {
                                    log.error("The new term " + term + "deduced from its parent "+ term.getParent() + " has not been loaded. There is a conflict in the dependency file. (line "+lineCount+")");
                                }
                            }
                            else if (oldTerm.isDeducedFromItsParent() && !term.isDeducedFromItsParent()) {
                                log.warn("The existing term " + oldTerm + " deduced from its parent "+ oldTerm.getParent() + " has been replaced by the new term " + term + " (line "+lineCount+")");
                                associatedTermToReplace.add(associatedTerm);
                                oldTerm = term;
                                conflictResolved = true;
                            }
                            else if (!oldTerm.isDeducedFromItsParent() && term.isDeducedFromItsParent()) {
                                log.warn("The existing term " + oldTerm + " has not been replaced by the new term " + term + " deduced from its parent "+ term.getParent() + " (line "+lineCount+")");
                                conflictResolved = true;
                            }

                            // If the conflict has not been resolved and the associated term are equal, an error is thrown
                            if (!conflictResolved && associatedTerm.equals(secondTerm)){
                                throw new ValidatorRuleException("A conflict exists in the file containing the dependencies at the line " + lineCount + " and can't be resolved. We can't take into account this dependency.");
                            }
                        }
                    }

                }

            }

            // remove the old associated terms to replace
            associatedTermSet.removeAll(associatedTermToReplace);
            // add the new associated term.
            associatedTermSet.add( secondTerm ); // will only add if not already in.
        }
    }

    /**
     *
     * @param term
     * @return the associated term in the dependency map with a message level 'required'
     */
    protected Set<AssociatedTerm> getRequiredDependenciesFor(Term term){
        Set<AssociatedTerm> requiredTerms = new HashSet<AssociatedTerm>();

        if (this.dependencies.containsKey(term)){
            Set<AssociatedTerm> associatedTerms = this.dependencies.get(term);

            for (AssociatedTerm at : associatedTerms){
                if (at.getLevel().equals(DependencyLevel.REQUIRED)){
                    requiredTerms.add(at);
                }
            }
        }
        return requiredTerms;
    }

    /**
     *
     * @param term
     * @return the associated term in the dependency map with a message level 'should'
     */
    protected Set<AssociatedTerm> getRecommendedDependenciesFor(Term term){
        Set<AssociatedTerm> recommendedTerms = new HashSet<AssociatedTerm>();

        if (this.dependencies.containsKey(term)){
            Set<AssociatedTerm> associatedTerms = this.dependencies.get(term);

            for (AssociatedTerm at : associatedTerms){
                if (at.getLevel().equals(DependencyLevel.SHOULD)){
                    recommendedTerms.add(at);
                }
            }
        }
        return recommendedTerms;
    }

    /**
     * Write the validatorMessage for a dependency depending on its level.
     * @param term1
     * @param term2
     * @param firstTermOfDependency
     * @param secondTermDependency
     * @param associatedTermDependency
     * @param context
     * @param rule
     * @return a list of messages
     */
    protected Collection<ValidatorMessage> writeValidatorMessages(CvType term1, CvType term2, Term firstTermOfDependency, Term secondTermDependency,
                                                                  Collection<AssociatedTerm> associatedTermDependency,
                                                                  Mi25Context context, AbstractRule rule) {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // To check if the dependency firstTermOfDependency - associatedTerm matches a dependency in the file
        boolean hasFoundADependency = false;
        // To check if the associated term of a dependency has a required message. (at least one dependency with a level required
        // must be respected)
        boolean isAValueRequired = false;
        // To check if the associated term of a dependency has a warning message. (at least one dependency with a level required
        // should be respected)
        boolean isARecommendedValue = false;

        for (AssociatedTerm associatedTerm : associatedTermDependency){
            // Get the message level of the associated term
            DependencyLevel level = associatedTerm.getLevel();

            if (level.equals(DependencyLevel.REQUIRED)){
                isAValueRequired = true;
            }
            else if (level.equals(DependencyLevel.SHOULD)){
                isARecommendedValue = true;
            }

            if (secondTermDependency.equals(associatedTerm.getSecondTermOfTheDependency())){

                if (level.equals(DependencyLevel.REQUIRED) || level.equals(DependencyLevel.SHOULD)){
                    hasFoundADependency = true;
                }
                else if (level.equals(DependencyLevel.ERROR)) {
                    final String msg = "Are you sure of the combination of " + term1.getClass().getSimpleName() + " ["+Term.printTerm(firstTermOfDependency)+"] " +
                            "and " + term2.getClass().getSimpleName() + " ["+Term.printTerm(secondTermDependency)+"] ?";
                    messages.add( new ValidatorMessage( msg,  MessageLevel.forName( level.toString() ), context.copy(), rule ) );
                }
            }
        }

        if (!hasFoundADependency && isAValueRequired){
            Set<AssociatedTerm> req = getRequiredDependenciesFor(firstTermOfDependency);
            final StringBuffer msg = new StringBuffer( 1024 );
            msg.append("There is an unusual combination of " + term1.getClass().getSimpleName() + " ["+Term.printTerm(firstTermOfDependency)+"] " +
                    "and " + term2.getClass().getSimpleName() + " ["+Term.printTerm(secondTermDependency)+"]." +
                    " The possible dependencies are : \n");

            for (AssociatedTerm r : req){
                msg.append(Term.printTerm(firstTermOfDependency) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + " \n");
            }

            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
        }
        else if (!hasFoundADependency && isARecommendedValue){
            Set<AssociatedTerm> rec = getRecommendedDependenciesFor(firstTermOfDependency);
            final StringBuffer msg = new StringBuffer( 1024 );
            msg.append("Are you sure of the combination of " + term1.getClass().getSimpleName() + " ["+Term.printTerm(firstTermOfDependency)+"] " +
                    "and " + term2.getClass().getSimpleName() + " ["+Term.printTerm(secondTermDependency)+"]." +
                    " The usual dependencies are : \n");

            for (AssociatedTerm r : rec){
                msg.append(Term.printTerm(firstTermOfDependency) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + " \n");
            }

            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
        }

        return messages;

    }

    /**
     * Check if the given first term and the list of associated terms are in agreement with the defined dependency mapping.
     *
     * @param term1
     * @param term2
     * @param context
     * @param rule
     * @return a list of messages should any error be found.
     */
    public Collection<ValidatorMessage> check( CvType term1,
                                               CvType term2,
                                               Mi25Context context,
                                               AbstractRule rule) {

        final Term firstTermOfDependency = Term.buildTerm(term1);

        if( term1 != null) {

            if( dependencies.containsKey( firstTermOfDependency )) {

                final Set<AssociatedTerm> associatedTermDependency = dependencies.get( firstTermOfDependency );

                final Term secondTermDependency = Term.buildTerm(term2);
                if( term2 == null ) {
                    Set<AssociatedTerm> required = getRequiredDependenciesFor(firstTermOfDependency);
                    if (!required.isEmpty()){
                        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage> ();

                        String msg = "Dependencies of type "+ rule.getClass().getSimpleName() +": A second term is required when the " + term1.getClass().getSimpleName() + " is [" + Term.printTerm(firstTermOfDependency) + "]." +
                                " The possible dependencies are : \n";
                        final StringBuffer sb = new StringBuffer( 1024 );
                        sb.append( msg );

                        for (AssociatedTerm r : required){
                            sb.append(Term.printTerm(firstTermOfDependency) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + " \n");
                        }
                        messages.add( new ValidatorMessage( sb.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                        return messages;
                    }
                    else {

                        Set<AssociatedTerm> recommended = getRecommendedDependenciesFor(firstTermOfDependency);
                        if (!recommended.isEmpty()){

                            Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage> ();
                            final StringBuffer msg = new StringBuffer( 1024 );
                            msg.append("Dependencies of type "+ rule.getClass().getSimpleName() +": When the " + term1.getClass().getSimpleName() + " is ["+Term.printTerm(firstTermOfDependency)+"]," +
                                    " the recommended dependencies are : \n");

                            for (AssociatedTerm r : recommended){
                                msg.append(Term.printTerm(firstTermOfDependency) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + " \n");
                            }
                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );

                            return messages;
                        }
                    }
                }else {

                    Collection<ValidatorMessage> messages = writeValidatorMessages(term1, term2, firstTermOfDependency, secondTermDependency, associatedTermDependency, context, rule);

                    return messages;

                } // there are rule for the method

            }
        }
        else {
            Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();
            messages.add(new ValidatorMessage("We can't check the dependencies of type " + rule.getClass().getSimpleName() + " as the first term of the dependency is null.",  MessageLevel.WARN, context.copy(), rule));

            return messages;
        }
        return new ArrayList<ValidatorMessage>();
    }
}
