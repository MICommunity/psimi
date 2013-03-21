package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;
import psidev.psi.tools.validator.rules.codedrule.ObjectRule;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Rule that allows to check whether the cross reference specified matches the cross-reference type.
 *
 * Rule Id = 15. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: CrossReference2CrossReferenceTypeDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class CrossReference2CrossReferenceTypeDependencyRule extends ObjectRule<XrefContainer> {

    private static final Log log = LogFactory.getLog( CrossReference2CrossReferenceTypeDependencyRule.class );

    /**
     * The dependency mapping object for this rule
     */
    private DependencyMappingCrossReference2CrossReferenceType mapping;

    //////////////////
    // Constructors

    /**
     *
     * @param ontologyManager
     */
    public CrossReference2CrossReferenceTypeDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getCrossReference2CrossReferenceType();

        try {

            URL resource = CrossReference2CrossReferenceTypeDependencyRule.class
                    .getResource( fileName );

            // Create a new instance of dependency mapping
            mapping = new DependencyMappingCrossReference2CrossReferenceType();
            // Build the dependency mapping from the file
            mapping.buildMappingFromFile( mi, resource );

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Dependency Check : Cross reference database and cross reference qualifier" );
        setDescription( "Checks that each association database - qualifier respects IMEx curetion rules. For example, for each feature, all the interpro" +
                " cross references should have a qualifier 'identity'.");
        addTip( "Search the possible terms for database cross reference and reference type on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/crossReference2Location2CrossRefType.tsv for the possible dependencies cross reference database - qualifier" );
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

        // Get the xRef
        Xref xRef = container.getXref();

        Mi25Context context = new Mi25Context();
        if (container instanceof FileSourceContext){
            context.extractFileContextFrom((FileSourceContext) container);
        }
        else if (container instanceof HasId){
            context.setId(((HasId)container).getId());
        }

        if (xRef != null){
            // Collect the db references
            Collection<DbReference> databaseReferences = xRef.getAllDbReferences();

            for ( DbReference reference : databaseReferences) {
                // build a context in case of error
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
                        messages.addAll( mapping.check( reference, container, context, this ) );
                    }
                }

            }
        }

        return messages;
    }

    public String getId() {
        return "R43";
    }

    //////////////////////
    // Inner classes

    public static final class CrossReferenceType extends AssociatedTerm {

        private Locations location;

        public CrossReferenceType( Term referenceType, String location, String message ) {
            super( referenceType, message );

            if ("experiment".equalsIgnoreCase(location)){
                this.location = Locations.experiment;
            }
            else if ("interaction".equalsIgnoreCase(location)){
                this.location = Locations.interaction;
            }
            else if ("interactor".equalsIgnoreCase(location)){
                this.location = Locations.interactor;
            }
            else if ("participant".equalsIgnoreCase(location)){
                this.location = Locations.participant;
            }
            else if ("feature".equalsIgnoreCase(location)){
                this.location = Locations.feature;
            }
            else {
                throw new ValidatorRuleException("The location " + location + " is not valid. It can be either experiment, interaction, interactor, participant or feature.");
            }
        }

        public CrossReferenceType( Term referenceType, Locations location, String message ) {
            super( referenceType, message );

            this.location = location;
        }

        public CrossReferenceType( Term referenceType, String location, DependencyLevel message ) {
            super( referenceType, message );

            if ("experiment".equalsIgnoreCase(location)){
                this.location = Locations.experiment;
            }
            else if ("interaction".equalsIgnoreCase(location)){
                this.location = Locations.interaction;
            }
            else if ("interactor".equalsIgnoreCase(location)){
                this.location = Locations.interactor;
            }
            else if ("participant".equalsIgnoreCase(location)){
                this.location = Locations.participant;
            }
            else if ("feature".equalsIgnoreCase(location)){
                this.location = Locations.feature;
            }
            else {
                throw new ValidatorRuleException("The location " + location + " is not valid. It can be either experiment, interaction, interactor, participant or feature.");
            }
        }

        public CrossReferenceType( Term referenceType, Locations location, DependencyLevel message ) {
            super( referenceType, message );

            this.location = location;
        }

        public CrossReferenceType( Term referenceType) {

            super(referenceType);
        }

        public Locations getLocation() {
            return location;
        }

        public boolean isReferenceTypeRuleApplicableTo(XrefContainer container){

            if (this.location != null){

                if (container instanceof ExperimentDescription && this.location.equals(Locations.experiment)){
                    return true;
                }
                else if (container instanceof Interaction && this.location.equals(Locations.interaction)){
                    return true;
                }
                else if (container instanceof Interactor && this.location.equals(Locations.interactor)){
                    return true;
                }
                else if (container instanceof Participant && this.location.equals(Locations.participant)){
                    return true;
                }
                else if (container instanceof Feature && this.location.equals(Locations.feature)){
                    return true;
                }
                return false;
            }

            return true;
        }

        @Override
        public boolean equals( Object o ) {
            boolean equals = super.equals(o);
            if (equals && o instanceof CrossReferenceType){
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

            Term term = new Term( columns[0], columns[1] );

            return term;
        }

        protected AssociatedTerm createSecondaryTermOfTheDependency(String[] columns){
            Term secondTerm = new Term(columns[3], columns[4]);

            // Get a couple interactionType and the message level.
            CrossReferenceType crossRef = new CrossReferenceType(secondTerm, columns[2] != null && columns[2].length()> 0 ? columns[2] : null, columns[5] != null && columns[5].length()> 0 ? columns[5] : null);

            return crossRef;
        }

        protected AssociatedTerm createSecondaryTermOfTheDependencyFrom(Term newTerm, AssociatedTerm firstTerm){

            if (firstTerm instanceof CrossReferenceType){
                CrossReferenceType parent = (CrossReferenceType) firstTerm;

                return new CrossReferenceType(newTerm, parent.getLocation(), parent.getLevel());
            }
            else {
                throw new ValidatorRuleException("The parent of " + Term.printTerm(newTerm) + " must be an instance of CrossReferenceType and not just an instance of AssociatedTerm.");
            }
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

            if (reference == null){
                throw new ValidatorRuleException("The database reference cannot be null.");
            }

            String dbAC = null;
            String dbName = reference.getDb();

            if (reference.hasDbAc()){
                dbAC = reference.getDbAc();
            }

            Term database = new Term(dbAC, dbName);

            if( dbAC != null || dbName != null) {

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

                    if( refAC == null && refName == null ) {

                        Set<AssociatedTerm> required = getRequiredDependenciesFor(database, container);
                        if (!required.isEmpty()){
                            String msg = "At the level of the "+container.getClass().getSimpleName()+", each " + Term.printTerm(database) + " cross reference must be associated with a reference type." +
                                    " In this case, the possible reference types are " ;
                            final StringBuffer sb = new StringBuffer( 1024 );
                            sb.append( msg );

                            writePossibleDependenciesFor(required, sb, container);

                            messages.add( new ValidatorMessage( sb.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                            return messages;
                        }
                        else {

                            Set<AssociatedTerm> recommended = getRecommendedDependenciesFor(database, container);
                            if (!recommended.isEmpty()){

                                final StringBuffer msg = new StringBuffer( 1024 );
                                msg.append("At the level of the "+container.getClass().getSimpleName()+", each " + Term.printTerm(database) + " cross reference should be associated with a reference type." +
                                        " In this case, the recommended reference types are ");
                                writePossibleDependenciesFor(recommended, msg, container);

                                messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );

                                return messages;
                            }
                        }

                    } else {
                        boolean hasFoundDependency = false;
                        boolean isAValueRequired = false;
                        // To check if the associated term of a dependency has a warning message. (at least one dependency with a level required
                        // should be respected)
                        boolean isARecommendedValue = false;

                        for (AssociatedTerm t : types){
                            DependencyLevel level = t.getLevel();
                            if (t instanceof CrossReferenceType){
                                CrossReferenceType crossType = (CrossReferenceType) t;
                                boolean isRuleApplicable = crossType.isReferenceTypeRuleApplicableTo(container);

                                if (level.equals(DependencyLevel.REQUIRED) && isRuleApplicable){
                                    isAValueRequired = true;
                                }
                                else if (level.equals(DependencyLevel.SHOULD) && isRuleApplicable){
                                    isARecommendedValue = true;
                                }

                                if (type.equals(t.getSecondTermOfTheDependency())){

                                    if (isRuleApplicable){
                                        hasFoundDependency = true;

                                        if (level != null){
                                            if (level.equals(DependencyLevel.ERROR)){
                                                final String msg = "At the level of the "+container.getClass().getSimpleName()+", one "+Term.printTerm(database)+" cross reference " +
                                                        "cannot be associated with the reference qualifier " + Term.printTerm(type)+" .";
                                                messages.add( new ValidatorMessage( msg,  MessageLevel.forName( level.toString() ), context.copy(), rule ) );
                                            }
                                        }
                                    }
                                }
                            }
                            else{
                                log.error("The dependencyMapping does not contains any CrossReferenceType instance and it is required.");
                            }
                        }

                        if (!hasFoundDependency && isAValueRequired){
                            Set<AssociatedTerm> req = getRequiredDependenciesFor(database);
                            final StringBuffer msg = new StringBuffer( 1024 );
                            msg.append("At the level of the " + container.getClass().getSimpleName() + " one "+Term.printTerm(database)+" cross reference " +
                                    "cannot be associated with the reference qualifier " +Term.printTerm(type)+
                                    ". In this case, the possible reference qualifiers are : ");
                            writePossibleDependenciesFor(req, msg, container);

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                        }
                        else if (!hasFoundDependency && isARecommendedValue){
                            Set<AssociatedTerm> rec = getRecommendedDependenciesFor(database);
                            final StringBuffer msg = new StringBuffer( 1024 );
                            msg.append("At the level of the "+container.getClass().getSimpleName()+ ", one "+Term.printTerm(database)+" cross reference " +
                                    "should not be associated with  "+Term.printTerm(type)+
                                    " In this case, the usual reference qualifiers are : ");

                            writePossibleDependenciesFor(rec, msg, container);

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                        }
                    }
                }

            } // there are rule for the reference

            else {
                throw new ValidatorRuleException( "The rule of type cross reference - cross reference type cannot be executed as the cross reference does not have a database name.");
            }
            return messages;
        }

        protected void writePossibleDependenciesFor(Set<AssociatedTerm> associatedTerms, StringBuffer msg, XrefContainer container){
            for (AssociatedTerm r : associatedTerms){
                if (r instanceof CrossReferenceType){
                    CrossReferenceType ct = (CrossReferenceType) r;

                    if (ct.isReferenceTypeRuleApplicableTo(container)){
                        msg.append(Term.printTerm(ct.getSecondTermOfTheDependency()) + ", ");
                    }
                }
            }

            if (msg.toString().endsWith(", ")){
                msg.delete(msg.lastIndexOf(","), msg.length());
            }
        }

        /**
         *
         * @param term
         * @return the associated term in the dependency map with a message level 'required'
         */
        protected Set<AssociatedTerm> getRequiredDependenciesFor(Term term, XrefContainer container){
            Set<AssociatedTerm> requiredTerms = new HashSet<AssociatedTerm>();

            if (this.dependencies.containsKey(term)){
                Set<AssociatedTerm> associatedTerms = this.dependencies.get(term);

                for (AssociatedTerm at : associatedTerms){

                    if (at.getLevel().equals(DependencyLevel.REQUIRED)){
                        if (at instanceof CrossReferenceType){
                            CrossReferenceType ct = (CrossReferenceType) at;

                            if (ct.isReferenceTypeRuleApplicableTo(container)){
                                requiredTerms.add(at);
                            }
                        }
                    }
                }
            }
            return requiredTerms;
        }

        /**
         *
         * @param term
         * @return the associated term in the dependency map with a message level 'required'
         */
        protected Set<AssociatedTerm> getRecommendedDependenciesFor(Term term, XrefContainer container){
            Set<AssociatedTerm> requiredTerms = new HashSet<AssociatedTerm>();

            if (this.dependencies.containsKey(term)){
                Set<AssociatedTerm> associatedTerms = this.dependencies.get(term);

                for (AssociatedTerm at : associatedTerms){

                    if (at.getLevel().equals(DependencyLevel.SHOULD)){
                        if (at instanceof CrossReferenceType){
                            CrossReferenceType ct = (CrossReferenceType) at;

                            if (ct.isReferenceTypeRuleApplicableTo(container)){
                                requiredTerms.add(at);
                            }
                        }
                    }
                }
            }
            return requiredTerms;
        }
    }
}
