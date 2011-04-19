package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.Mi25ValidatorContext;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorContext;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Rule that allows to check whether the interaction detection method specified matches the interaction type.
 *
 * Rule Id = 10. See http://docs.google.com/Doc?docid=0AXS9Q1JQ2DygZGdzbnZ0Ym5fMHAyNnM3NnRj&hl=en_GB&pli=1
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2InteractionTypeDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2InteractionTypeDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2InteractionTypeDependencyRule.class );

    private InteractionDetectionMethod2InteractionTypeDependencyRule.DependencyMappingInteractionDetectionMethod2InteractionType mapping;

    private static final String superior = ">";
    private static final String inferior = "<";
    private static final String superiorOrEqual = ">=";
    private static final String inferiorOrEqual = "<=";
    private static final String different = "not ";
    private static final String separatorOfConditions = ";";

    public InteractionDetectionMethod2InteractionTypeDependencyRule( OntologyManager ontologyManager ) {
        super( ontologyManager );
        Mi25ValidatorContext validatorContext = Mi25ValidatorContext.getCurrentInstance();

        OntologyAccess mi = ontologyManager.getOntologyAccess( "MI" );
        String fileName = validatorContext.getValidatorConfig().getInteractionDetectionMethod2InteractionType();

        try {

            URL resource = InteractionDetectionMethod2InteractionTypeDependencyRule.class
                    .getResource( fileName );
            mapping = new DependencyMappingInteractionDetectionMethod2InteractionType();
            mapping.buildMappingFromFile( mi, resource);

        } catch (IOException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        } catch (ValidatorException e) {
            throw new ValidatorRuleException("We can't build the map containing the dependencies from the file " + fileName, e);
        }
        // describe the rule.
        setName( "Interaction detection method and interaction type check" );
        setDescription( "Checks that each association interaction detection method - interaction type is valid and respects IMEx curation rules." );
        addTip( "Search the possible terms for interaction detection method and interaction type on http://www.ebi.ac.uk/ontology-lookup/browse.do?ontName=MI" );
        addTip( "Look at the file http://psimi.googlecode.com/svn/trunk/validator/psimi-schema-validator/src/main/resources/InteractionDetectionMethod2InteractionTypes.tsv for the possible dependencies interaction detection method - interaction type" );
    }


    /**
     * For each experiment associated with this interaction, collect all respective participants, host organisms and
     * check if the dependencies are correct.
     *
     * @param interaction an interaction to check on.
     * @return a collection of validator messages.
     *         if we fail to retreive the MI Ontology.
     */
    public Collection<ValidatorMessage> check( Interaction interaction ) throws ValidatorException {

        Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

        // experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();
        // participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();
        // number of participants
        final int numberParticipants = participants.size();
        final Collection<InteractionType> interactionType = interaction.getInteractionTypes();

        for ( ExperimentDescription experiment : experiments ) {

            // build a context in case of error
            Mi25Context context = new Mi25Context();
            context.setInteractionId( interaction.getId() );
            context.setExperimentId( experiment.getId());

            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();
            final Collection<Organism> hostOrganisms = experiment.getHostOrganisms();
            int numberOfBaits = getNumberOfParticipantWithExperimentalRole(participants, RuleUtils.BAIT_MI_REF, "bait", experiment.getId(), messages, context);
            int numberOfPreys = getNumberOfParticipantWithExperimentalRole(participants, RuleUtils.PREY_MI_REF, "prey", experiment.getId(), messages, context);

            for ( Organism host : hostOrganisms ) {
                for (InteractionType type : interactionType){
                    messages.addAll( mapping.check( method, type, host, numberParticipants, numberOfBaits, numberOfPreys, context, this ) );
                }
            }

        } // experiments

        return messages;
    }

    /**
     *
     * @param role
     * @return  true if the role is a role with a psi MI identifier mi or a psi Mi short label shortLabel
     */
    private boolean checkParticipantRole(ExperimentalRole role, String mi, String shortLabel, Collection<ValidatorMessage> messages, Mi25Context context){

        boolean checkName = false;

        Xref ref = role.getXref();
        if (ref != null){
            Collection<DbReference> references = ref.getAllDbReferences();

            Collection<DbReference> psiMiRef = RuleUtils.findByDatabase(references, RuleUtils.PSI_MI, RuleUtils.PSI_MI_REF, messages, context, this);
            if (!psiMiRef.isEmpty()){

                for (DbReference psi : psiMiRef){

                    if (psi.getId().equals(mi)){
                        return true;
                    }
                }
            }
            else {
                checkName = true;
            }
        }
        else {
            checkName = true;
        }

        if (checkName && role.hasNames()){
            if (role.getNames().hasShortLabel()){
                if (role.getNames().getShortLabel().equals(shortLabel)){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     *
     * @param participants
     * @param roleMi
     * @param roleName
     * @return the number of participants with a specific experimental role
     */
    private int getNumberOfParticipantWithExperimentalRole(Collection<Participant> participants, String roleMi, String roleName, int experimentId, Collection<ValidatorMessage> messages, Mi25Context context){

        int num = 0;
        for (Participant p : participants){
            Collection<ExperimentalRole> experimentRoles = p.getExperimentalRoles();
            for (ExperimentalRole role : experimentRoles){
                Collection<ExperimentRef> experimentRefs = role.getExperimentRefs();

                if (experimentRefs.isEmpty()){
                    if (checkParticipantRole(role, roleMi, roleName, messages, context)){
                        num++;
                        break;
                    }
                }
                else {
                    int previousNum = num;
                    for (ExperimentRef ref : experimentRefs){
                        if (ref.getRef() == experimentId){
                            if (checkParticipantRole(role, roleMi, roleName, messages, context)){
                                num++;
                                break;
                            }
                        }
                    }

                    if (previousNum < num){
                        break;
                    }
                }

            } // experimental roles
        }
        return num;
    }

    //////////////////////
    // Inner classes

    /**
     * Contains all the possible conditions of application of a dependency.
     */
    private static class DependencyRequirements {
        /**
         * required host organisms
         */
        Set<String> applicableHostOrganisms = new HashSet<String> ();
        /**
         * required number of participants
         */
        Set<String> applicableNumParticipants = new HashSet<String> ();
        /**
         * required number of baits
         */
        Set<String> applicableNumBaits = new HashSet<String> ();
        /**
         * required number of preys
         */
        Set<String> applicableNumPrey = new HashSet<String> ();

        /**
         *
         * @param hostRequirements
         * @param numParticipantsRequirements
         * @param numBaitsRequirements
         * @param numPreysRequirements
         */
        public DependencyRequirements(String hostRequirements, String numParticipantsRequirements, String numBaitsRequirements, String numPreysRequirements) {

            initialiseHostRequirements(hostRequirements);
            initialiseNumParticipantsRequirements(numParticipantsRequirements);
            initialiseNumBaitsRequirements(numBaitsRequirements);
            initialiseNumPreyRequirements(numPreysRequirements);
        }

        /**
         *
         * @param o
         * @return
         */
        @Override
        public boolean equals( Object o ) {
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;

            DependencyRequirements dependencyRequirements = ( DependencyRequirements ) o;

            if ( !applicableHostOrganisms.equals(dependencyRequirements.getApplicableHostOrganisms()) ) return false;

            if ( applicableNumParticipants.size() != dependencyRequirements.getApplicableNumParticipants().size() ) return false;
            else {
                Iterator<String> it = dependencyRequirements.getApplicableNumParticipants().iterator();
                while (it.hasNext()){
                    if (!applicableNumParticipants.contains(it.next())){
                        return false;
                    }
                }
            }
            if ( applicableNumBaits.size() != dependencyRequirements.getApplicableNumBaits().size() ) return false;
            else {
                Iterator<String> it = dependencyRequirements.getApplicableNumBaits().iterator();
                while (it.hasNext()){
                    if (!applicableNumBaits.contains(it.next())){
                        return false;
                    }
                }
            }
            if ( applicableNumPrey.size() != dependencyRequirements.getApplicableNumPrey().size() ) return false;
            else {
                Iterator<String> it = dependencyRequirements.getApplicableNumPrey().iterator();
                while (it.hasNext()){
                    if (!applicableNumPrey.contains(it.next())){
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         *
         * @return
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((applicableHostOrganisms == null) ? 0 : applicableHostOrganisms.hashCode());
            result = prime * result + ((applicableNumParticipants == null) ? 0 : applicableNumParticipants.hashCode());
            result = prime * result + ((applicableNumBaits == null) ? 0 : applicableNumBaits.hashCode());
            result = prime * result + ((applicableNumPrey == null) ? 0 : applicableNumPrey.hashCode());
            return result;

        }

        /**
         *
         * @param requirements
         * @param listOfRequirements
         */
        private void initialiseRequirements(String requirements, Set<String> listOfRequirements){
            if (listOfRequirements != null){
                if (requirements.trim().length() > 0){
                    if (requirements.contains(separatorOfConditions)){
                        String [] list = requirements.split(separatorOfConditions);

                        for (int i = 0; i < list.length; i++){
                            listOfRequirements.add(list[i]);
                        }
                    }
                    else {
                        listOfRequirements.add(requirements);
                    }
                }
            }
        }

        /**
         * If there are some host organism requirements, stores them in the applicableHostOrganisms map.
         * @param hostRequirements
         */
        private void initialiseHostRequirements(String hostRequirements){
            initialiseRequirements(hostRequirements, this.applicableHostOrganisms);
        }

        /**
         * If there are some requirements about the number of participants, stores them in the applicableNumParticipants map.
         * @param numParticipantsRequirements
         */
        private void initialiseNumParticipantsRequirements(String numParticipantsRequirements){
            initialiseRequirements(numParticipantsRequirements, this.applicableNumParticipants);
        }

        /**
         * If there are some requirements about the number of baits, stores them in the applicableNumBaits map.
         * @param baitRequirements
         */
        private void initialiseNumBaitsRequirements(String baitRequirements){
            initialiseRequirements(baitRequirements, this.applicableNumBaits);
        }

        /**
         * If there are some requirements about the number of preys, stores them in the applicableNumPrey map.
         * @param preyRequirements
         */
        private void initialiseNumPreyRequirements(String preyRequirements){
            initialiseRequirements(preyRequirements, this.applicableNumPrey);
        }

        /**
         *
         * @return the host organism requirements
         */
        public Set<String> getApplicableHostOrganisms() {
            return applicableHostOrganisms;
        }

        /**
         *
         * @param applicableHostOrganisms
         */
        public void setApplicableHostOrganisms(Set<String> applicableHostOrganisms) {
            this.applicableHostOrganisms = applicableHostOrganisms;
        }

        /**
         *
         * @return  the requirements about the number of baits
         */
        public Set<String> getApplicableNumBaits() {
            return applicableNumBaits;
        }

        /**
         *
         * @param applicableNumBaits
         */
        public void setApplicableNumBaits(Set<String> applicableNumBaits) {
            this.applicableNumBaits = applicableNumBaits;
        }

        /**
         *
         * @return the requirements about the number of participants
         */
        public Set<String> getApplicableNumParticipants() {
            return applicableNumParticipants;
        }

        /**
         *
         * @param applicableNumParticipants
         */
        public void setApplicableNumParticipants(Set<String> applicableNumParticipants) {
            this.applicableNumParticipants = applicableNumParticipants;
        }

        /**
         *
         * @return the requirements about the number of preys
         */
        public Set<String> getApplicableNumPrey() {
            return applicableNumPrey;
        }

        /**
         *
         * @param applicableNumPrey
         */
        public void setApplicableNumPrey(Set<String> applicableNumPrey) {
            this.applicableNumPrey = applicableNumPrey;
        }

        public boolean hasHostRequirements(){
            return !this.applicableHostOrganisms.isEmpty();
        }

        public boolean hasNumParticipantsRequirements(){
            return !this.applicableNumParticipants.isEmpty();
        }

        public boolean hasNumBaitsRequirements(){
            return !this.applicableNumBaits.isEmpty();
        }
        public boolean hasNumPreysRequirements(){
            return !this.applicableNumPrey.isEmpty();
        }
    }

    /**
     * A specific AssociatedTerm instance which contains a DependencyRequirements instance.
     */
    private static class InteractionTypeConditions extends AssociatedTerm {

        private DependencyRequirements requirements;

        public InteractionTypeConditions(Term interactionType, DependencyRequirements requirements, String level) {
            super(interactionType, level);
            this.requirements = requirements;
        }

        public InteractionTypeConditions(Term interactionType, DependencyRequirements requirements, DependencyLevel level) {
            super(interactionType, level);
            this.requirements = requirements;
        }

        @Override
        public boolean equals( Object o ) {
            boolean equals = super.equals(o);
            if (equals && o instanceof InteractionTypeConditions){
                InteractionTypeConditions intTypeCond = (InteractionTypeConditions) o;
                if ( !requirements.equals( intTypeCond.getRequirements()) ) return false;
                return true;
            }

            return equals;
        }


        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((requirements == null) ? 0 : requirements.hashCode());
            return result;

        }

        public DependencyRequirements getRequirements() {
            return requirements;
        }

        public void setRequirements(DependencyRequirements requirements) {
            this.requirements = requirements;
        }

        /**
         * To know if the host organism respects the host requirements
         * @param host
         * @return
         */
        private boolean isApplicableHostOrganism(Organism host){

            if (this.requirements.hasHostRequirements()){
                Set<String> validHosts = this.requirements.getApplicableHostOrganisms();
                String taxId = Integer.toString(host.getNcbiTaxId());
                if (validHosts.contains(taxId)){
                    return true;
                }

                for (String h : validHosts){
                    String math = getMathematicalOperator(h);

                    if (math != null){
                        String hostNotAllowed = h.substring(math.length());
                        if (math.toLowerCase().equals(different) && taxId != hostNotAllowed){
                            return true;
                        }
                    }
                }

                return false;
            }
            return true;
        }

        /**
         * To know if there is a mathematical operator at the beginning of the requirement String
         * @param number
         * @return
         */
        public static boolean hasMathematicalOperator(String number){

            if (number.startsWith(superior) || number.startsWith(inferior) || number.toLowerCase().startsWith(different)){
                return true;
            }
            return false;
        }

        /**
         *
         * @param number
         * @return the mathematical operator of the number
         */
        public static String getMathematicalOperator(String number){
            String math = null;

            if (hasMathematicalOperator(number)){
                if (number.startsWith(superiorOrEqual) || number.startsWith(inferiorOrEqual)){
                    return number.substring(0,2);
                }
                else if (number.startsWith(different)){
                    return number.substring(0,4);
                }
                else if (number.startsWith(superior) || number.startsWith(inferior)){
                    return number.substring(0,1);
                }
            }
            return null;
        }

        /**
         * To know if the number of participants, baits or preys respects the requirements
         * @param number
         * @param numberRequirements
         * @return
         */
        private boolean isTheNumberRespectingTheRequirements(int number, Set<String> numberRequirements){

            for (String num : numberRequirements){
                if (num != null){
                    String math = getMathematicalOperator(num);

                    if (math == null){
                        int n = Integer.parseInt(num);
                        if (n == number){
                            return true;
                        }
                    }
                    else{
                        int n = Integer.parseInt(num.substring(math.length()));
                        if (math.equals(superior) && number > n){
                            return true;
                        }
                        else if (math.equals(inferior) && number < n){
                            return true;
                        }
                        else if (math.equals(inferiorOrEqual) && number <= n){
                            return true;
                        }
                        else if (math.equals(superiorOrEqual) && number >= n){
                            return true;
                        }
                        else if (math.equals(different) && number != n){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * To know if there is requirements on the number of participants
         * @return
         */
        private boolean hasRequirementsOnTheNumberOfParticipants(){
            return this.requirements.hasNumParticipantsRequirements();
        }

        /**
         * To know if there is requirements on the number of baits
         * @return
         */
        private boolean hasRequirementsOnTheNumberOfBaits(){
            return this.requirements.hasNumBaitsRequirements();
        }

        /**
         * To know if there is requirements on the number of preys
         * @return
         */
        private boolean hasRequirementsOnTheNumberOfPreys(){
            return this.requirements.hasNumPreysRequirements();
        }

        /**
         * To know if there is requirements on the number of preys
         * @return
         */
        private boolean hasRequirementsOnTheOrganism(){
            return this.requirements.hasHostRequirements();
        }

        /**
         * To know if the number of participants respects the requirements
         * @param numParticipants
         * @return
         */
        private boolean isApplicablewithTheNumberOfParticipants(int numParticipants){

            if (this.requirements.hasNumParticipantsRequirements()){

                Set<String> validNumParticipants = this.requirements.getApplicableNumParticipants();

                return isTheNumberRespectingTheRequirements(numParticipants, validNumParticipants);
            }
            return true;
        }

        /**
         * To know if the number of baits respects the requirements
         * @param numBaits
         * @return
         */
        private boolean isApplicablewithTheNumberOfBaits(int numBaits){

            if (this.requirements.hasNumBaitsRequirements()){

                Set<String> validNumBaits = this.requirements.getApplicableNumBaits();

                return isTheNumberRespectingTheRequirements(numBaits, validNumBaits);
            }
            return true;
        }

        /**
         * To know if the number of preys respects the requirements
         * @param numPreys
         * @return
         */
        private boolean isApplicablewithTheNumberOfPreys(int numPreys){

            if (this.requirements.hasNumPreysRequirements()){

                Set<String> validNumPreys = this.requirements.getApplicableNumPrey();

                return isTheNumberRespectingTheRequirements(numPreys, validNumPreys);
            }
            return true;
        }
    }

    /**
     * Specific dependency mapping for this rule
     */
    private class DependencyMappingInteractionDetectionMethod2InteractionType extends DependencyMapping {

        public DependencyMappingInteractionDetectionMethod2InteractionType() {
            super();
        }

        /**
         * Check if the number is a valid integer
         * @param requirements
         * @return
         */
        private boolean checkPositiveInteger(String requirements){
            if (requirements.length() > 0){
                String number = requirements;
                String math = InteractionTypeConditions.getMathematicalOperator(requirements);

                if (math != null){
                    number = number.substring(math.length());
                }

                try {
                    int i = Integer.parseInt(number);

                    if (i >= 0){
                        return true;
                    }
                }
                catch (NumberFormatException e){
                    throw new ValidatorRuleException(number + " is not a valid integer.", e);
                }
            }
            return true;
        }

        /**
         * Check if the list of numbers that contains the String requirements are valid integer
         * @param requirements
         * @return true if the numbers are a valid integer
         */
        private boolean checkIntegerForAll(String requirements){
            if (requirements.trim().length() > 0){
                if (requirements.contains(separatorOfConditions)){
                    String [] list = requirements.split(separatorOfConditions);

                    for (int i = 0; i < list.length; i++){
                        if (!checkPositiveInteger(list[i])){
                            return false;
                        }
                    }
                }
                else {
                    if (!checkPositiveInteger(requirements)){
                        return false;
                    }
                }
                return true;
            }
            return true;
        }

        /**
         *
         * @param columns
         * @return an InteractionTypeConditions instance
         */
        protected AssociatedTerm createSecondaryTermOfTheDependency(String[] columns){
            Term secondTerm = new Term(columns[3], columns[4]);
            if( columns[5].length() > 0 ) {
                boolean isChildrenIncluded = Boolean.valueOf( columns[5] );

                if (isChildrenIncluded || !isChildrenIncluded){
                    secondTerm.setIncludeChildren(isChildrenIncluded);
                }
                else {
                    throw new ValidatorRuleException("The boolean value for isIncludedChildren should be TRUE or FALSE but not " + isChildrenIncluded);
                }
            }

            if (columns[7] != null){
                if (!checkIntegerForAll(columns[7])){
                    throw new ValidatorRuleException("The column 7 does not contain valid positive numbers of participants.");
                }
            }
            if (columns[8] != null){
                if (!checkIntegerForAll(columns[8])){
                    throw new ValidatorRuleException("The column 8 does not contain valid positive numbers of baits.");
                }
            }
            if (columns[9] != null){
                if (!checkIntegerForAll(columns[9])){
                    throw new ValidatorRuleException("The column 9 does not contain valid positive numbers of preys.");
                }
            }
            // Get the condition of application for the dependencies
            DependencyRequirements depReq = new DependencyRequirements(columns[6], columns[7], columns[8], columns[9]);
            // Get a couple interactionType and the requirements.
            InteractionTypeConditions intConditions = new InteractionTypeConditions(secondTerm, depReq, columns[10] != null && columns[10].length()> 0 ? columns[10] : null);

            return intConditions;
        }

        protected AssociatedTerm createSecondaryTermOfTheDependencyFrom(Term newTerm, AssociatedTerm firstTerm){

            if (firstTerm instanceof InteractionTypeConditions){
                InteractionTypeConditions parent = (InteractionTypeConditions) firstTerm;

                return new InteractionTypeConditions(newTerm, parent.getRequirements(), parent.getLevel());
            }
            else {
                throw new ValidatorRuleException("The parent of " + Term.printTerm(newTerm) + " must be an instance of InteractionTypeCondition and not just an instance of AssociatedTerm.");
            }
        }

        /**
         * Check if the given method and the list of correcponding interaction type(s) are in agreement with the defined dependency mapping.
         *
         * @param method
         * @param interactionType
         * @param host
         * @param numParticipants
         * @param numBaits
         * @param numPreys
         * @param context
         * @param rule
         * @return a list of messages should any error be found.
         */
        private Collection<ValidatorMessage> check( InteractionDetectionMethod method,
                                                    InteractionType interactionType,
                                                    Organism host,
                                                    int numParticipants,
                                                    int numBaits,
                                                    int numPreys,
                                                    Mi25Context context,
                                                    InteractionDetectionMethod2InteractionTypeDependencyRule rule) {

            Collection<ValidatorMessage> messages = new ArrayList<ValidatorMessage>();

            final Map<Term, Set<AssociatedTerm>> dependencies = this.getDependencies();

            final Term methodTerm = Term.buildTerm( method );

            if( methodTerm != null) {

                if( dependencies.containsKey( methodTerm )) {

                    final Set<AssociatedTerm> interactionTypeCondition = dependencies.get( methodTerm );

                    final Term brTerm = Term.buildTerm( interactionType );
                    if( interactionType == null ) {

                        Set<AssociatedTerm> required = getRequiredDependenciesFor(methodTerm);
                        if (!required.isEmpty()){
                            String msg = "An interaction type is required when the interaction detection method is " + Term.printTerm(methodTerm) + "." +
                                    " In this case, the possible interaction types are : ";
                            final StringBuffer sb = new StringBuffer( 1024 );
                            sb.append( msg );

                            for (AssociatedTerm r : required){
                                sb.append(Term.printTerm(methodTerm) + " : " + Term.printTerm(r.getSecondTermOfTheDependency()) + ValidatorContext.getCurrentInstance().getValidatorConfig().getLineSeparator());
                            }
                            messages.add( new ValidatorMessage( sb.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                            return messages;
                        }
                        else {

                            Set<AssociatedTerm> recommended = getRecommendedDependenciesFor(methodTerm);
                            if (!recommended.isEmpty()){
                                final StringBuffer msg = new StringBuffer( 1024 );
                                msg.append("An interaction type is usually given when the interaction detection method is "+Term.printTerm(methodTerm)+"." +
                                        " In this case, the recommended interaction types are : ");

                                for (AssociatedTerm r : recommended){

                                    msg.append(Term.printTerm(r.getSecondTermOfTheDependency()) + " or ");
                                }
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

                        for (AssociatedTerm pair : interactionTypeCondition){
                            DependencyLevel level = pair.getLevel();

                            if (pair instanceof InteractionTypeConditions){
                                InteractionTypeConditions cond = (InteractionTypeConditions) pair;
                                boolean isApplicableHostHorganism = cond.isApplicableHostOrganism(host);
                                boolean isApplicableNumberParticipant = cond.isApplicablewithTheNumberOfParticipants(numParticipants);
                                boolean isApplicableNumberBait = cond.isApplicablewithTheNumberOfBaits(numBaits);
                                boolean isApplicableNumberPrey = cond.isApplicablewithTheNumberOfPreys(numPreys);

                                if (level.equals(DependencyLevel.REQUIRED) && isApplicableHostHorganism && isApplicableNumberParticipant && isApplicableNumberBait && isApplicableNumberPrey){
                                    isAValueRequired = true;
                                }
                                else if (level.equals(DependencyLevel.SHOULD) && isApplicableHostHorganism && isApplicableNumberParticipant && isApplicableNumberBait && isApplicableNumberPrey){
                                    isARecommendedValue = true;
                                }

                                if (brTerm.equals(pair.getSecondTermOfTheDependency())){
                                    hasFoundDependency = true;

                                    if (level.equals(DependencyLevel.ERROR)){

                                        if (cond.hasRequirementsOnTheOrganism() && cond.isApplicableHostOrganism(host)){
                                            final StringBuffer msg = new StringBuffer( 1024 );
                                            msg.append("The interaction detection method "+Term.printTerm(methodTerm)+" " +
                                                    "is not normally associated with the interaction type "+Term.printTerm(brTerm)+" when the host organism is " + host.getNcbiTaxId() + "." +
                                                    " In this case, the possible interaction types are ");

                                            writePossibleDependenciesFor(interactionTypeCondition, msg);

                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.forName( level.toString() ), context.copy(), rule ) );
                                        }
                                        if (cond.hasRequirementsOnTheNumberOfParticipants() && cond.isApplicablewithTheNumberOfParticipants(numParticipants)){
                                            final StringBuffer msg = new StringBuffer( 1024 );
                                            msg.append("The interaction detection method "+Term.printTerm(methodTerm)+" " +
                                                    "is not normally associated with the interaction type "+Term.printTerm(brTerm)+" when the number of participants is " + numParticipants + "." +
                                                    " In this case, the possible interaction types are ");

                                            writePossibleDependenciesFor(interactionTypeCondition, msg);

                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.forName( level.toString() ), context.copy(), rule ) );
                                        }
                                        if (cond.hasRequirementsOnTheNumberOfBaits() && cond.isApplicablewithTheNumberOfBaits(numBaits)){
                                            final StringBuffer msg = new StringBuffer( 1024 );
                                            msg.append("The interaction detection method "+Term.printTerm(methodTerm)+" " +
                                                    "is not normally associated with the interaction type "+Term.printTerm(brTerm)+" when the number of baits is " + numBaits + "." +
                                                    " In this case, the possible interaction types are ");

                                            writePossibleDependenciesFor(interactionTypeCondition, msg);

                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.forName( level.toString() ), context.copy(), rule ) );
                                        }
                                        if (cond.hasRequirementsOnTheNumberOfPreys() && cond.isApplicablewithTheNumberOfPreys(numPreys)){
                                            final StringBuffer msg = new StringBuffer( 1024 );
                                            msg.append("The interaction detection method "+Term.printTerm(methodTerm)+" " +
                                                    "is not normally associated with the interaction type "+Term.printTerm(brTerm)+" when the number of preys is " + numPreys + "." +                                                    "In this case, the possible interaction types are ");

                                            writePossibleDependenciesFor(interactionTypeCondition, msg);

                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.forName( level.toString() ), context.copy(), rule ) );
                                        }
                                    }
                                    else {

                                        if (!cond.isApplicableHostOrganism(host)){
                                            final StringBuffer msg = new StringBuffer( 1024 );

                                            msg.append("The organism " + host.getNcbiTaxId() + " is unusual. When the interaction detection method is "+ Term.printTerm(methodTerm) +" and " +
                                                    "the interaction type is " + Term.printTerm(brTerm) + " the host organism should be : ") ;
                                            for (String validHost : cond.getRequirements().getApplicableHostOrganisms()){
                                                msg.append(validHost + ValidatorContext.getCurrentInstance().getValidatorConfig().getLineSeparator());
                                            }
                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                                        }
                                        if (!cond.isApplicablewithTheNumberOfParticipants(numParticipants)){
                                            final StringBuffer msg = new StringBuffer( 1024 );

                                            msg.append(numParticipants +" is an unusual number of participants . When the interaction detection method is "+ Term.printTerm(methodTerm) +" and " +
                                                    "the interaction type is " + Term.printTerm(brTerm) + " the number of participants should be : ") ;
                                            for (String validNum : cond.getRequirements().getApplicableNumParticipants()){
                                                msg.append(validNum + ValidatorContext.getCurrentInstance().getValidatorConfig().getLineSeparator());
                                            }
                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                                        }
                                        if (!cond.isApplicablewithTheNumberOfBaits(numBaits)){
                                            final StringBuffer msg = new StringBuffer( 1024 );
                                            msg.append(numBaits +" is an unusual number of baits . When the interaction detection method is "+ Term.printTerm(methodTerm) +" and " +
                                                    "the interaction type is " + Term.printTerm(brTerm) + " the number of baits should be : ") ;
                                            for (String validNum : cond.getRequirements().getApplicableNumBaits()){
                                                msg.append(validNum + ValidatorContext.getCurrentInstance().getValidatorConfig().getLineSeparator());
                                            }
                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                                        }
                                        if (!cond.isApplicablewithTheNumberOfPreys(numPreys)){
                                            final StringBuffer msg = new StringBuffer( 1024 );
                                            msg.append(numPreys +" is an unusual number of preys . When the interaction detection method is "+ Term.printTerm(methodTerm) +" and " +
                                                    "the interaction type is " + Term.printTerm(brTerm) + " the number of preys should be : ") ;
                                            for (String validNum : cond.getRequirements().getApplicableNumPrey()){
                                                msg.append(validNum + ValidatorContext.getCurrentInstance().getValidatorConfig().getLineSeparator());
                                            }
                                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                                        }
                                    }
                                }
                            }
                            else {
                                log.error("The dependencyMapping does not contains any InteractionTypeCondition instance and it is required.");
                            }
                        }

                        if (!hasFoundDependency && isAValueRequired){

                            Set<AssociatedTerm> req = getRequiredDependenciesFor(methodTerm);
                            final StringBuffer msg = new StringBuffer( 1024 );
                            msg.append("There is an unusual combination of interaction detection method "+Term.printTerm(methodTerm)+" " +
                                    "and interaction type "+Term.printTerm(brTerm)+"." +
                                    " In this case, the possible interaction types are  ");
                            writePossibleDependenciesFor(req, msg);

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                        }
                        else if (!hasFoundDependency && isARecommendedValue){
                            Set<AssociatedTerm> req = getRecommendedDependenciesFor(methodTerm);
                            final StringBuffer msg = new StringBuffer( 1024 );
                            msg.append("Are you sure of the combination of interaction detection method "+Term.printTerm(methodTerm)+" " +
                                    "and interaction type "+Term.printTerm(brTerm)+" ?" +
                                    " In this case, the recommended interaction types are  ");
                            writePossibleDependenciesFor(req, msg);

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                        }

                    }

                }
            } else {
                throw new ValidatorRuleException("The rule of type interaction detection method - interaction type can be applied as the interaction detection method is null.");
            }

            // warning if CVs do not have MIs

            return messages;
        }

        protected void writePossibleDependenciesFor(Set<AssociatedTerm> associatedTerms, StringBuffer msg, Organism host,
                                                    int numParticipants,
                                                    int numBaits,
                                                    int numPreys){
            for (AssociatedTerm r : associatedTerms){
                InteractionTypeConditions it = (InteractionTypeConditions) r;

                if (it.isApplicableHostOrganism(host) && it.isApplicablewithTheNumberOfBaits(numBaits) && it.isApplicablewithTheNumberOfParticipants(numParticipants) && it.isApplicablewithTheNumberOfPreys(numPreys)){
                    msg.append(Term.printTerm(it.getSecondTermOfTheDependency()) + ", ");
                }
            }

            if (msg.toString().endsWith(", ")){
                msg.delete(msg.lastIndexOf(","), msg.length());
            }
        }

    }
}
