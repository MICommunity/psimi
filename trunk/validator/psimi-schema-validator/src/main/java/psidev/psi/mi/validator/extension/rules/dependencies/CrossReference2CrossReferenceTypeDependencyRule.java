package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Rule that allows to check whether the cross reference specified matches the cross-reference type.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: CrossReference2CrossReferenceTypeDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class CrossReference2CrossReferenceTypeDependencyRule extends ObjectRule<XrefContainer> {

    private static final Log log = LogFactory.getLog( CrossReference2CrossReferenceTypeDependencyRule.class );

    /**
     * The dependency mapping object for this rule
     */
    private static DependencyMappingCrossReference2CrossReferenceType mapping;

    //////////////////
    // Constructors

    /**
     *
     * @param ontologyManager
     */
    public CrossReference2CrossReferenceTypeDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        try {
            // TODO : the resource should be a final private static or should be put as argument of the constructor
            String resource = CrossReference2CrossReferenceTypeDependencyRule.class
                    .getResource( "/CrossReference2Location2CrossRefType.tsv" ).getFile();

            // Create a new instance of dependency mapping
            mapping = new DependencyMappingCrossReference2CrossReferenceType();
            // Build the dependency mapping from the file
            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ValidatorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // describe the rule.
        setName( "Cross reference and cross reference type" );
//        addTip( "" );
    }

    ///////////////////////
    // ObjectRule

    /**
     *
     * @param o
     * @return true if o is an instance of XrefContainer.
     */
    public boolean canCheck(Object o) {
        if (o instanceof XrefContainer){
            return true;
        }
        return false;
    }


    /**
     * For each cross reference of this XrefContainer, collects all respective cross reference type(s) and
     * check if the dependencies are correct.
     *
     * @param container to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( XrefContainer container) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // build a context in case of error
        Mi25Context context = new Mi25Context();

        // Get the xRef
        Xref xRef = container.getXref();

        if (xRef != null){
            // Collect the db references
            Collection<DbReference> databaseReferences = xRef.getAllDbReferences();

            for ( DbReference reference : databaseReferences) {

                context.setId( reference.getId());
                messages.addAll( mapping.check( reference, container, context, this ) );
            }

        }

        if (container instanceof ExperimentDescription){
            ExperimentDescription exp = (ExperimentDescription) container;

            if (exp.getBibref() != null){
                if (exp.getBibref().getXref() != null){
                    // Collect the db references
                    Collection<DbReference> databaseReferences = exp.getBibref().getXref().getAllDbReferences();

                    for ( DbReference reference : databaseReferences) {

                        context.setId( reference.getId());
                        messages.addAll( mapping.check( reference, container, context, this ) );
                    }
                }

            }
        }

        return messages;
    }

    //////////////////////
    // Inner classes

    public static final class CrossReferenceType extends AssociatedTerm {

        private String location;

        public CrossReferenceType( Term referenceType, String location, String message ) {
            super( referenceType, message );

            this.location = location;
        }

        public CrossReferenceType( Term referenceType) {

            super(referenceType);
        }

        public String getLocation() {
            return location;
        }

        public boolean isReferenceTypeRuleApplicableTo(XrefContainer container){

            if (this.location != null){

                if (container instanceof ExperimentDescription && this.location.toLowerCase().equals("experiment")){
                    return true;
                }
                else if (container instanceof Interaction && this.location.toLowerCase().equals("interaction")){
                    return true;
                }
                else if (container instanceof Interactor && this.location.toLowerCase().equals("interactor")){
                    return true;
                }
                else if (container instanceof Participant && this.location.toLowerCase().equals("participant")){
                    return true;
                }
                else if (container instanceof Feature && this.location.toLowerCase().equals("feature")){
                    return true;
                }
                return false;
            }

            return true;
        }

        @Override
        public boolean equals( Object o ) {
            boolean equals = super.equals(o);
            if (!equals && o instanceof CrossReferenceType){
                CrossReferenceType c = (CrossReferenceType) o;
                if ( !location.equals( c.getLocation()) ) return false;
                return true;
            }

            return equals;

        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((location == null) ? 0 : location.hashCode());
            return result;
        }
    }

    private static class DependencyMappingCrossReference2CrossReferenceType extends DependencyMapping {

        public DependencyMappingCrossReference2CrossReferenceType() {
            super();
        }

        protected Term createFirstTermOfTheDependency(String[] columns){

            Term term = new psidev.psi.mi.validator.extension.rules.dependencies.Term( columns[0], columns[1] );

            return term;
        }

        protected AssociatedTerm createSecondaryTermOfTheDependency(String[] columns){
            Term secondTerm = new Term(columns[3], columns[4]);

            // Get a couple interactionType and the message level.
            CrossReferenceType crossRef = new CrossReferenceType(secondTerm, columns[2].length()> 0 && !columns[2].equals("NONE") ? columns[2] : null, columns[5].length()> 0 && !columns[5].equals("NONE") ? columns[5] : null);

            return crossRef;
        }

        /**
         * Check if the given cross reference and the list of corresponding cross reference type are in agreement with the defined dependency mapping.
         *
         * @param reference
         * @param container
         * @param context
         * @param rule
         * @return a list of messages should any error be found.
         */
        private Collection<ValidatorMessage> check( DbReference reference,
                                                    XrefContainer container,
                                                    Mi25Context context,
                                                    CrossReference2CrossReferenceTypeDependencyRule rule) {

            Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

            String dbAC = null;
            String dbName = reference.getDb();

            if (reference.hasDbAc()){
                dbAC = reference.getDbAc();
            }

            Term database = new Term(dbAC, dbName);

            if( database != null) {

                if( dependencies.containsKey( database )) {
                    final Set<AssociatedTerm> types = dependencies.get( database );

                    String refAC = null;
                    String refName = null;

                    if (reference.hasRefTypeAc()){
                        refAC = reference.getRefTypeAc();
                    }
                    if (reference.hasRefType()){
                        refName = reference.getRefType();
                    }

                    Term type = new Term(refAC, refName);

                    if( type == null ) {

                        // TODO here we could report eror given that no MI accession number was given for that reference type.

                    } else {
                        boolean hasFoundDependency = false;
                        boolean isAValueRequired = false;
                        // To check if the associated term of a dependency has a warning message. (at least one dependency with a level required
                        // should be respected)
                        boolean isARecommendedValue = false;

                        for (AssociatedTerm t : types){
                            String level = t.getLevel();

                            if (level != null){
                                if (level.toLowerCase().equals("required")){
                                    isAValueRequired = true;
                                }
                                else if (level.toLowerCase().equals("should")){
                                    isARecommendedValue = true;
                                }
                            }

                            if (type.equals(t.getSecondTermOfTheDependency())){

                                if (t instanceof CrossReferenceType){
                                    CrossReferenceType crossType = (CrossReferenceType) t;

                                    if (crossType.isReferenceTypeRuleApplicableTo(container)){
                                        if (level != null){
                                            if (level.toLowerCase().equals("required") || level.toLowerCase().equals("should")){
                                                hasFoundDependency = true;
                                            }
                                            else {
                                                final String msg = "Are you sure of the combination of " + reference.getClass().getSimpleName() + " ["+Term.printTerm(database)+"] " +
                                                        "and " + container.getClass().getSimpleName() + "["+Term.printTerm(type)+"] ?";
                                                messages.add( new ValidatorMessage( msg,  MessageLevel.forName( level ), context.copy(), rule ) );
                                            }
                                        }
                                    }
                                }
                                else{
                                    log.error("The dependencyMapping does not contains any CrossReferenceType instance and it is required.");
                                }
                            }
                        }

                        if (!hasFoundDependency && isAValueRequired){
                            Set<AssociatedTerm> req = getRequiredDependenciesFor(database);
                            final StringBuffer msg = new StringBuffer("There is an unusual combination of " + reference.getClass().getSimpleName() + " ["+Term.printTerm(database)+"] " +
                                    "and " + container.getClass().getSimpleName() + " ["+Term.printTerm(type)+"] ?" +
                                    " The possible dependencies are : \n");

                            for (AssociatedTerm r : req){
                                CrossReferenceType ct = (CrossReferenceType) r;
                                msg.append(Term.printTerm(database) + " : " + Term.printTerm(ct.getSecondTermOfTheDependency()) + ", Location : " + ct.getLocation() + " \n");
                            }

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                        }
                        else if (!hasFoundDependency && isARecommendedValue){
                            Set<AssociatedTerm> rec = getRecommendedDependenciesFor(database);
                            final StringBuffer msg = new StringBuffer("Are you sure of the combination of " + reference.getClass().getSimpleName() + " ["+Term.printTerm(database)+"] " +
                                    "and " + container.getClass().getSimpleName() + " ["+Term.printTerm(type)+"]." +
                                    " The usual dependencies are : \n");

                            for (AssociatedTerm r : rec){
                                CrossReferenceType ct = (CrossReferenceType) r;

                                msg.append(Term.printTerm(database) + " : " + Term.printTerm(ct.getSecondTermOfTheDependency()) + ", Location : " + ct.getLocation() + " \n");
                            }

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                        }

                    }

                } // there are rule for the reference

            } else {
                // TODO here we could report eror given that no MI accession number was given for that reference.
            }

            // warning if CVs do not have MIs

            return messages;
        }
    }
}
