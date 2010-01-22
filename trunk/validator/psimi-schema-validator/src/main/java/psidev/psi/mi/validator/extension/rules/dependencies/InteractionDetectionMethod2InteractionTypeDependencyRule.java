package psidev.psi.mi.validator.extension.rules.dependencies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.validator.extension.Mi25Context;
import psidev.psi.mi.validator.extension.Mi25InteractionRule;
import psidev.psi.mi.validator.extension.rules.RuleUtils;
import psidev.psi.mi.xml.model.*;
import psidev.psi.tools.ontology_manager.OntologyManager;
import psidev.psi.tools.ontology_manager.interfaces.OntologyAccess;
import psidev.psi.tools.validator.MessageLevel;
import psidev.psi.tools.validator.ValidatorException;
import psidev.psi.tools.validator.ValidatorMessage;

import java.io.IOException;
import java.util.*;

/**
 * Rule that allows to check whether the interaction detection method specified matches the interaction type.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: InteractionDetectionMethod2InteractionTypeDependencyRule.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class InteractionDetectionMethod2InteractionTypeDependencyRule extends Mi25InteractionRule {

    private static final Log log = LogFactory.getLog( InteractionDetectionMethod2InteractionTypeDependencyRule.class );

    private static InteractionDetectionMethod2InteractionTypeDependencyRule.DependencyMappingInteractionDetectionMethod2InteractionType mapping;

    public InteractionDetectionMethod2InteractionTypeDependencyRule( OntologyManager ontologyMaganer ) {
        super( ontologyMaganer );

        OntologyAccess mi = ontologyMaganer.getOntologyAccess( "MI" );
        try {
            // TODO : the resource should be a final private static or should be put as argument of the constructor

            String resource = InteractionDetectionMethod2InteractionTypeDependencyRule.class
                    .getResource( "/InteractionDetection2InteractionTypes.tsv" ).getFile();

            mapping = new DependencyMappingInteractionDetectionMethod2InteractionType();
            mapping.buildMappingFromFile( mi, resource);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ValidatorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        // describe the rule.
        setName( "Dependency between interaction detection method and interaction type" );
//        addTip( "" );
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

        // build a context in case of error
        Mi25Context context = new Mi25Context();

        context.setInteractionId( interaction.getId() );
        // experiments for detecting the interaction
        final Collection<ExperimentDescription> experiments = interaction.getExperiments();
        // participants of the interaction
        final Collection<Participant> participants = interaction.getParticipants();
        // number of participants
        final int numberParticipants = participants.size();
        final Collection<InteractionType> interactionType = interaction.getInteractionTypes();


        for ( ExperimentDescription experiment : experiments ) {

            context.setExperimentId( experiment.getId());
            final InteractionDetectionMethod method = experiment.getInteractionDetectionMethod();
            final Collection<Organism> hostOrganisms = experiment.getHostOrganisms();
            final int numberOfBaits = getNumberOfBaits(participants);
            final int numberOfPreys = getNumberOfPreys(participants);

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
     * @return  true if the role is a bait role
     */
    private boolean isABaitRole(ExperimentalRole role){

        boolean checkName = false;

        Xref ref = role.getXref();
        if (ref != null){
            Collection<DbReference> references = ref.getAllDbReferences();

            Collection<DbReference> psiMiRef = RuleUtils.findByDatabase(references, "psi-mi", "MI:0488");
            if (!psiMiRef.isEmpty()){

                for (DbReference psi : psiMiRef){

                    if (psi.getId().equals("MI:0496")){
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
                if (role.getNames().getShortLabel().equals("bait")){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     *
     * @param role
     * @return true if the role is a prey role
     */
    private boolean isAPreyRole(ExperimentalRole role){

        boolean checkName = false;

        Xref ref = role.getXref();
        if (ref != null){
            Collection<DbReference> references = ref.getAllDbReferences();

            Collection<DbReference> psiMiRef = RuleUtils.findByDatabase(references, "psi-mi", "MI:0488");
            if (!psiMiRef.isEmpty()){

                for (DbReference psi : psiMiRef){

                    if (psi.getId().equals("MI:0498")){
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
                if (role.getNames().getShortLabel().equals("prey")){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     *
     * @param participants
     * @return the number of baits within the participants
     */
    private int getNumberOfBaits(Collection<Participant> participants){

        int numBaits = 0;
        for (Participant p : participants){
            Collection<ExperimentalRole> experimentRoles = p.getExperimentalRoles();
            for (ExperimentalRole role : experimentRoles){
                if (isABaitRole(role)){
                    numBaits++;
                    break;
                }

            } // experimental roles
        }
        return numBaits;
    }

    /**
     *
     * @param participants
     * @return the number of preys within the participants
     */
    private int getNumberOfPreys(Collection<Participant> participants){

        int numPreys = 0;
        for (Participant p : participants){
            Collection<ExperimentalRole> experimentRoles = p.getExperimentalRoles();
            for (ExperimentalRole role : experimentRoles){
                if (isAPreyRole(role)){
                    numPreys++;
                    break;
                }
            } // experimental roles
        }
        return numPreys;
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
            if ( !applicableNumParticipants.equals(dependencyRequirements.getApplicableNumParticipants()) ) return false;
            if ( !applicableNumBaits.equals(dependencyRequirements.getApplicableNumBaits()) ) return false;
            if ( !applicableNumPrey.equals(dependencyRequirements.getApplicableNumPrey()) ) return false;

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
         * If there are some host organism requirements, stores them in the applicableHostOrganisms map.
         * @param hostRequirements
         */
        private void initialiseHostRequirements(String hostRequirements){
            if (hostRequirements != null){
                if (hostRequirements.trim().length() > 0 && !hostRequirements.equals("NONE")){
                    if (hostRequirements.contains(";")){
                        String [] listHosts = hostRequirements.split(";");

                        for (int i = 0; i < listHosts.length; i++){
                            this.applicableHostOrganisms.add(listHosts[i]);
                        }
                    }
                    else {
                        this.applicableHostOrganisms.add(hostRequirements);
                    }
                }
            }
        }

        /**
         * If there are some requirements about the number of participants, stores them in the applicableNumParticipants map.
         * @param numParticipantsRequirements
         */
        private void initialiseNumParticipantsRequirements(String numParticipantsRequirements){
            if (numParticipantsRequirements != null){
                if (numParticipantsRequirements.trim().length() > 0 && !numParticipantsRequirements.equals("NONE")){
                    if (numParticipantsRequirements.contains(";")){
                        String [] listNumParticipants = numParticipantsRequirements.split(";");

                        for (int i = 0; i < listNumParticipants.length; i++){
                            this.applicableNumParticipants.add(listNumParticipants[i]);
                        }
                    }
                    else {
                        this.applicableNumParticipants.add(numParticipantsRequirements);
                    }
                }
            }
        }

        /**
         * If there are some requirements about the number of baits, stores them in the applicableNumBaits map.
         * @param baitRequirements
         */
        private void initialiseNumBaitsRequirements(String baitRequirements){
            if (baitRequirements != null){
                if (baitRequirements.trim().length() > 0 && !baitRequirements.equals("NONE")){
                    if (baitRequirements.contains(";")){
                        String [] listBaits = baitRequirements.split(";");

                        for (int i = 0; i < listBaits.length; i++){
                            this.applicableNumBaits.add(listBaits[i]);
                        }
                    }
                    else {
                        this.applicableNumBaits.add(baitRequirements);
                    }
                }
            }
        }

        /**
         * If there are some requirements about the number of preys, stores them in the applicableNumPrey map.
         * @param preyRequirements
         */
        private void initialiseNumPreyRequirements(String preyRequirements){
            if (preyRequirements != null){
                if (preyRequirements.trim().length() > 0 && !preyRequirements.equals("NONE")){
                    if (preyRequirements.contains(";")){
                        String [] listNumPreys = preyRequirements.split(";");

                        for (int i = 0; i < listNumPreys.length; i++){
                            this.applicableNumPrey.add(listNumPreys[i]);
                        }
                    }
                    else {
                        this.applicableNumPrey.add(preyRequirements);
                    }
                }
            }
        }

        /**
         *
         * @return the host organism requirements
         */
        public Collection<String> getApplicableHostOrganisms() {
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
        public Collection<String> getApplicableNumPrey() {
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

            if (!this.applicableHostOrganisms.isEmpty()){
                return true;
            }
            return false;
        }

        public boolean hasNumParticipantsRequirements(){

            if (!this.applicableNumParticipants.isEmpty()){
                return true;
            }
            return false;
        }

        public boolean hasNumBaitsRequirements(){

            if (!this.applicableNumBaits.isEmpty()){
                return true;
            }
            return false;
        }

        public boolean hasNumPreysRequirements(){

            if (!this.applicableNumPrey.isEmpty()){
                return true;
            }
            return false;
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

        @Override
        public boolean equals( Object o ) {
            boolean equals = super.equals(o);
            if (!equals && o instanceof InteractionTypeConditions){
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
         * To know if the dependency respects the requirements.
         * @param host
         * @param numParticipants
         * @param numBaits
         * @param numPreys
         * @return
         */
        public boolean isInteractionTypeDepencyApplicableTo(Organism host, int numParticipants, int numBaits, int numPreys){

            if (isApplicableHostOrganism(host)){
                if (isApplicablewithTheNumberOfParticipants(numParticipants)){
                    if (isApplicablewithTheNumberOfBaits(numBaits)){
                        if(isApplicablewithTheNumberOfPreys(numPreys)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * To know if the host organism respects the host requirements
         * @param host
         * @return
         */
        private boolean isApplicableHostOrganism(Organism host){

            if (this.requirements.hasHostRequirements()){
                Collection<String> validHosts = this.requirements.getApplicableHostOrganisms();
                if (validHosts.contains(Integer.toString(host.getNcbiTaxId()))){
                    return true;
                }
                else if (host.hasNames()){
                    if (host.getNames().hasFullName()){
                        if (validHosts.contains(host.getNames().getFullName())){
                            return true;
                        }
                    }
                }
                else if (host.hasCellType()){
                    CellType cell = host.getCellType();
                    if (cell.hasNames()){
                        Names cellName = cell.getNames();

                        if (cellName.hasFullName()){
                            if (validHosts.contains(cellName.getFullName())){
                                return true;
                            }
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
        private boolean hasMathematicalOperator(String number){

            if (number.startsWith(">") || number.startsWith("<")){
                return true;
            }
            return false;
        }

        /**
         *
         * @param number
         * @return the mathematical operator of the number
         */
        private String getMathematicalOperator(String number){
            String math = null;

            if (hasMathematicalOperator(number)){
                if (number.startsWith("<=") || number.startsWith(">=") ){
                    return number.substring(0,2);
                }
                if (number.startsWith(">") || number.startsWith("<")){
                    return number.substring(0,1);
                }
            }
            return null;
        }

        /**
         * To know if the number of participants respects the requirements
         * @param numParticipants
         * @return
         */
        private boolean isApplicablewithTheNumberOfParticipants(int numParticipants){

            if (this.requirements.hasNumParticipantsRequirements()){

                Collection<String> validNumParticipants = this.requirements.getApplicableNumParticipants();

                for (String num : validNumParticipants){
                    if (num != null){
                        String math = getMathematicalOperator(num);

                        if (math == null){
                            try{
                                int number = Integer.parseInt(num);
                                if (number == numParticipants){
                                    return true;
                                }
                            }catch (NumberFormatException e){
                                //TODO message
                            }
                        }
                        else{
                            try{
                                int number = Integer.parseInt(num.substring(math.length()));
                                if (math.equals(">") && number > numParticipants){
                                    return true;
                                }
                                else if (math.equals("<") && number < numParticipants){
                                    return true;
                                }
                                else if (math.equals("<=") && number <= numParticipants){
                                    return true;
                                }
                                else if (math.equals(">=") && number >= numParticipants){
                                    return true;
                                }
                            }catch (NumberFormatException e){
                                //TODO message
                            }
                        }
                    }
                }
                return false;
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

                Collection<String> validNumBaits = this.requirements.getApplicableNumBaits();

                for (String num : validNumBaits){
                    if (num != null){
                        String math = getMathematicalOperator(num);

                        if (math == null){
                            try{
                                int number = Integer.parseInt(num);
                                if (number == numBaits){
                                    return true;
                                }
                            }catch (NumberFormatException e){
                                //TODO message
                            }
                        }
                        else{
                            try{
                                int number = Integer.parseInt(num.substring(math.length()));
                                if (math.equals(">") && number > numBaits){
                                    return true;
                                }
                                else if (math.equals("<") && number < numBaits){
                                    return true;
                                }
                                else if (math.equals("<=") && number <= numBaits){
                                    return true;
                                }
                                else if (math.equals(">=") && number >= numBaits){
                                    return true;
                                }
                            }catch (NumberFormatException e){
                                //TODO message
                            }
                        }
                    }
                }
                return false;
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

                Collection<String> validNumPreys = this.requirements.getApplicableNumPrey();

                for (String num : validNumPreys){
                    if (num != null){
                        String math = getMathematicalOperator(num);

                        if (math == null){
                            try{
                                int number = Integer.parseInt(num);
                                if (number == numPreys){
                                    return true;
                                }
                            }catch (NumberFormatException e){
                                //TODO message
                            }
                        }
                        else{
                            try{
                                int number = Integer.parseInt(num.substring(math.length()));
                                if (math.equals(">") && number > numPreys){
                                    return true;
                                }
                                else if (math.equals("<") && number < numPreys){
                                    return true;
                                }
                                else if (math.equals("<=") && number <= numPreys){
                                    return true;
                                }
                                else if (math.equals(">=") && number >= numPreys){
                                    return true;
                                }
                            }catch (NumberFormatException e){
                                //TODO message
                            }
                        }
                    }
                }
                return false;
            }
            return true;
        }
    }

    /**
     * Specific dependency mapping for this rule
     */
    private static class DependencyMappingInteractionDetectionMethod2InteractionType extends DependencyMapping {

        public DependencyMappingInteractionDetectionMethod2InteractionType() {
            super();
        }

        /**
         *
         * @param columns
         * @return an InteractionTypeConditions instance
         */
        protected AssociatedTerm createSecondaryTermOfTheDependency(String[] columns){
            Term secondTerm = new Term(columns[3], columns[4]);
            if( columns[5].length() > 0 ) {
                secondTerm.setIncludeChildren(Boolean.valueOf( columns[5] ));
            }

            // Get the condition of application for the dependencies
            DependencyRequirements depReq = new DependencyRequirements(columns[6], columns[7], columns[8], columns[9]);
            // Get a couple interactionType and the requirements.
            InteractionTypeConditions intConditions = new InteractionTypeConditions(secondTerm, depReq, columns[10].length()> 0 && !columns[10].equals("NONE") ? columns[10] : null);

            return intConditions;
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

            final Map<Term, Set<AssociatedTerm>> dependencies = mapping.getDependencies();

            final Term methodTerm = Term.buildTerm( method );

            if( methodTerm != null) {

                if( dependencies.containsKey( methodTerm )) {

                    final Set<AssociatedTerm> interactionTypeCondition = dependencies.get( methodTerm );

                    final Term brTerm = Term.buildTerm( interactionType );
                    if( brTerm == null ) {

                        // TODO here we could report eror given that no MI accession number was given for that interaction type.

                    } else {
                        boolean hasFoundDependency = false;
                        boolean isAValueRequired = false;
                        // To check if the associated term of a dependency has a warning message. (at least one dependency with a level required
                        // should be respected)
                        boolean isARecommendedValue = false;

                        for (AssociatedTerm pair : interactionTypeCondition){
                            String level = pair.getLevel();

                            if (level != null){
                                if (level.toLowerCase().equals("required")){
                                    isAValueRequired = true;
                                }
                                else if (level.toLowerCase().equals("should")){
                                    isARecommendedValue = true;
                                }
                            }

                            if (brTerm.equals(pair.getSecondTermOfTheDependency())){
                                if (pair instanceof InteractionTypeConditions){
                                    InteractionTypeConditions cond = (InteractionTypeConditions) pair;

                                    if (cond.isInteractionTypeDepencyApplicableTo(host, numParticipants, numBaits, numPreys)){
                                        if (level != null){
                                            if (level.toLowerCase().equals("required") || level.toLowerCase().equals("should")){
                                                hasFoundDependency = true;
                                            }
                                            else {
                                                final String msg = "Are you sure of the combination of " + method.getClass().getSimpleName() + " ["+Term.printTerm(methodTerm)+"] " +
                                                        "and " + interactionType.getClass().getSimpleName() + " ["+Term.printTerm(brTerm)+"] with the following conditions : "+
                                                        " host organism = " + host + ", number of participants = " + numParticipants + ", number of baits = " + numBaits + ", number of preys = " + numPreys + "?";
                                                messages.add( new ValidatorMessage( msg,  MessageLevel.forName( level ), context.copy(), rule ) );
                                            }
                                        }
                                    }
                                }
                                else {
                                    log.error("The dependencyMapping does not contains any InteractionTypeCondition instance and it is required.");
                                }
                            }
                        }

                        if (!hasFoundDependency && isAValueRequired){
                            Set<AssociatedTerm> req = getRequiredDependenciesFor(methodTerm);
                            final StringBuffer msg = new StringBuffer("There is an unusual combination of " + method.getClass().getSimpleName() + " ["+Term.printTerm(methodTerm)+"] " +
                                    "and " + interactionType.getClass().getSimpleName() + " ["+Term.printTerm(brTerm)+"] ?" +
                                    " The possible dependencies are : \n");

                            for (AssociatedTerm r : req){
                                InteractionTypeConditions it = (InteractionTypeConditions) r;
                                msg.append(Term.printTerm(methodTerm) + " : " + Term.printTerm(it.getSecondTermOfTheDependency()) + ", Hosts : " + it.getRequirements().applicableHostOrganisms + ", Number participants : " + it.getRequirements().applicableNumParticipants +
                                        ", Number of baits : " + it.getRequirements().applicableNumBaits+ ", Number of preys : " + it.getRequirements().applicableNumPrey + " \n");
                            }

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.ERROR, context.copy(), rule ) );
                        }
                        else if (!hasFoundDependency && isARecommendedValue){
                            Set<AssociatedTerm> req = getRecommendedDependenciesFor(methodTerm);
                            final StringBuffer msg = new StringBuffer("Are you sure of the combination of " + method.getClass().getSimpleName() + " ["+Term.printTerm(methodTerm)+"] " +
                                    "and " + interactionType.getClass().getSimpleName() + " ["+Term.printTerm(brTerm)+"] ?" +
                                    " The recommended dependencies are : \n");

                            for (AssociatedTerm r : req){
                                InteractionTypeConditions it = (InteractionTypeConditions) r;
                                msg.append(Term.printTerm(methodTerm) + " : " + Term.printTerm(it.getSecondTermOfTheDependency()) + ", Hosts : " + it.getRequirements().applicableHostOrganisms + ", Number participants : " + it.getRequirements().applicableNumParticipants +
                                        ", Number of baits : " + it.getRequirements().applicableNumBaits+ ", Number of preys : " + it.getRequirements().applicableNumPrey + " \n");
                            }

                            messages.add( new ValidatorMessage( msg.toString(),  MessageLevel.WARN, context.copy(), rule ) );
                        }

                    }

                }
            } else {
                // TODO here we could report eror given that no MI accession number was given for that method.
            }

            // warning if CVs do not have MIs

            return messages;
        }
    }
}
