package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.xml.model.CvType;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.AbstractRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

        // TODO : it is better if we don't write the column number directly in the code but we deduce it from the column names
        Term term = new Term( columns[0], columns[1] );
        if( columns[2].length() > 0 ) {
            term.setIncludeChildren( Boolean.valueOf( columns[2] ) );
        }

        return term;
    }

    /**
     * From the different colums, this method creates the second term of the dependency
     * @param columns
     * @return  the associated term of the dependency
     */
    protected AssociatedTerm createSecondaryTermOfTheDependency(String[] columns){

        // TODO : it is better if we don't write the column number directly in the code but we deduce it from the column names
        Term secondTerm = new Term(columns[3], columns[4]);
        if( columns[5].length() > 0 ) {
            secondTerm.setIncludeChildren(Boolean.valueOf( columns[5] ));
        }

        // Get a couple secondTerm and message level.
        AssociatedTerm associatedTerm = new AssociatedTerm(secondTerm, columns[6].length()> 0 && !columns[6].equals("NONE") ? columns[6] : null);

        return associatedTerm;
    }

    /**
     * Builds the dependencies from a file (tab file).
     * @param mi
     * @param name
     * @throws IOException
     * @throws ValidatorException
     */
    public void buildMappingFromFile( OntologyAccess mi, String name) throws IOException, ValidatorException {

        BufferedReader in = new BufferedReader( new FileReader( new File(name) ) );
        String str;
        int lineCount = 0;
        while ( ( str = in.readLine() ) != null ) {
            // we skip empty lines and those starting with the symbol '#'. We remove the " if they it appears at the beginning or the end of the line

            if (str.startsWith( "\"" )){
                str = str.substring(1);
            }
            if (str.endsWith("\"")){
                str = str.substring(0, str.length() - 1);
            }

            lineCount++;

            if( str.startsWith( "#" ) || str.trim().length() == 0) {
                continue; // skip the commentary
            }

            // 0. first term MI
            // 1. first term NAME
            // 2. INCLUDE CHILDREN
            // 3. second term MI
            // 4. second term NAME
            // 5. message level

            if (str.contains("\t")){
                final String[] columns = str.split( "\t" );

                // Remove the possible " characters we can find after editing a tab file using excel.
                for (int i = 0; i < columns.length; i++){
                    String col = columns[i];
                    if (col != null){
                        if (col.startsWith( "\"" )){
                            columns[i] = columns[i].substring(1);
                        }
                        if (col.endsWith("\"")){
                            columns[i] = columns[i].substring(0, columns[i].length() - 1);
                        }
                    }
                }

                // Creates the first term of the dependency
                Term firstTerm = createFirstTermOfTheDependency(columns);
                // creates the associated term
                AssociatedTerm associatedTerm = createSecondaryTermOfTheDependency(columns);

                // Create a dependency for these terms
                loadDependencies(firstTerm, associatedTerm, mi);

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
                            loadDependencies(termChild, associatedTerm, mi);
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
                            loadDependencies(firstTerm, childTerm2, mi);

                            if (!firstTermChildren.isEmpty()){
                                // duplicate the dependencies for the children of the first term too
                                for (Term first : firstTermChildren){
                                    // Create a dependency with the children
                                    loadDependencies(first, childTerm2, mi);
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
     * @param mi
     */
    protected void loadDependencies(Term term, AssociatedTerm secondTerm, OntologyAccess mi){
        Set<AssociatedTerm> associatedTermSet = new HashSet<AssociatedTerm> ();

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

                        if (t != null && t2 != null){
                            // There is an associated term in the dependency map with the same id/name as 'secondTerm.getSecondTermOfTheDependency()'
                            if (t.equals(t2)){
                                if (t.isDeducedFromItsParent()){
                                    // If the new associated term is deduced from its parent, keep the associated term with the younger parent.
                                    if (t2.isDeducedFromItsParent()){
                                        final Collection<OntologyTermI> children = mi.getValidTerms( t.getParent().getId(), true, false );
                                        if (!children.isEmpty()){
                                            if (children.contains(t2.getParent())){
                                                associatedTermToReplace.add(associatedTerm);
                                            }
                                        }
                                    }
                                    //If the new associated term isn't deduced from its parent, replace the old dependency.
                                    else{
                                        associatedTermToReplace.add(associatedTerm);
                                    }
                                }
                                // If the existing first term in the dependency map is deduced from a parent term.
                                else if (oldTerm.isDeducedFromItsParent()){
                                    // If the new first term is deduced from its parent, keep the term with the younger parent.
                                    if (term.isDeducedFromItsParent()){
                                        final Collection<OntologyTermI> children = mi.getValidTerms( oldTerm.getParent().getId(), true, false );
                                        if (!children.isEmpty()){
                                            if (children.contains(term.getParent())){
                                                associatedTermToReplace.add(associatedTerm);
                                            }
                                        }
                                    }
                                    //If the new first term isn't deduced from its parent, replace the old dependency.
                                    else{
                                        oldTerm = term;
                                        associatedTermToReplace.add(associatedTerm);
                                    }
                                }
                                else {
                                    log.warn( "The dependency term " + Term.printTerm(term) + " - secondTerm " + Term.printTerm(secondTerm.getSecondTermOfTheDependency()) +
                                            "can't be added in the dependency map as it is already included. Check the file containing the dependencies.'" );
                                }
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
                if (at.getLevel() != null){
                    if (at.getLevel().toLowerCase().equals("required")){
                        requiredTerms.add(at);
                    }
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
                if (at.getLevel() != null){
                    if (at.getLevel().toLowerCase().equals("should")){
                        recommendedTerms.add(at);
                    }
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
            String level = associatedTerm.getLevel();

            if (level != null){
                if (level.toLowerCase().equals("required")){
                    isAValueRequired = true;
                }
                else if (level.toLowerCase().equals("should")){
                    isARecommendedValue = true;
                }
            }

            if (secondTermDependency.equals(associatedTerm.getSecondTermOfTheDependency())){

                if (level != null){
                    if (level.toLowerCase().equals("required") || level.toLowerCase().equals("should")){
                        hasFoundADependency = true;
                    }
                    else {
                        final String msg = "Are you sure of the combination of " + term1.getClass().getSimpleName() + " ["+Term.printTerm(firstTermOfDependency)+"] " +
                                "and " + term2.getClass().getSimpleName() + " ["+Term.printTerm(secondTermDependency)+"] ?";
                        messages.add( new ValidatorMessage( msg,  MessageLevel.forName( level ), context.copy(), rule ) );
                    }
                }
            }
        }

        if (!hasFoundADependency && isAValueRequired){
            Set<AssociatedTerm> req = getRequiredDependenciesFor(firstTermOfDependency);
            final StringBuffer msg = new StringBuffer("There is an unusual combination of " + term1.getClass().getSimpleName() + " ["+Term.printTerm(firstTermOfDependency)+"] " +
                    "and " + term2.getClass().getSimpleName() + " ["+Term.printTerm(secondTermDependency)+"]." +
                    " The possible dependencies are : \n");

            for (AssociatedTerm r : req){
                msg.append(Term.printTerm(firstTermOfDependency) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + " \n");
            }

            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
        }
        else if (!hasFoundADependency && isARecommendedValue){
            Set<AssociatedTerm> rec = getRecommendedDependenciesFor(firstTermOfDependency);
            final StringBuffer msg = new StringBuffer("Are you sure of the combination of " + term1.getClass().getSimpleName() + " ["+Term.printTerm(firstTermOfDependency)+"] " +
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

        if( firstTermOfDependency != null) {

            if( dependencies.containsKey( firstTermOfDependency )) {

                final Set<AssociatedTerm> associatedTermDependency = dependencies.get( firstTermOfDependency );

                final Term secondTermDependency = Term.buildTerm(term2);
                if( secondTermDependency == null ) {
                    Set<AssociatedTerm> required = getRequiredDependenciesFor(firstTermOfDependency);
                    if (!required.isEmpty()){
                        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage> ();

                        final StringBuffer msg = new StringBuffer("A second term is required when the " + term1.getClass().getSimpleName() + " is ["+Term.printTerm(firstTermOfDependency)+"]." +
                                " The possible dependencies are : \n");

                        for (AssociatedTerm r : required){
                            msg.append(Term.printTerm(firstTermOfDependency) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + " \n");
                        }
                        messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                        return messages;
                    }
                    else {

                        Set<AssociatedTerm> recommended = getRecommendedDependenciesFor(firstTermOfDependency);
                        if (!recommended.isEmpty()){
                            
                            Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage> ();

                            final StringBuffer msg = new StringBuffer("When the " + term1.getClass().getSimpleName() + " is ["+Term.printTerm(firstTermOfDependency)+"]." +
                                    " The recommended dependencies are : \n");

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

            } else {
                // TODO here we could report eror given that no MI accession number was given for that method.
            }

            // warning if CVs do not have MIs
        }

        return new ArrayList<ValidatorMessage>();
    }
}
