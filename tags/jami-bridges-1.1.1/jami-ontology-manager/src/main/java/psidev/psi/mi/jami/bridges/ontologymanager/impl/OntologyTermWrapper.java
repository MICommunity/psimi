package psidev.psi.mi.jami.bridges.ontologymanager.impl;

import psidev.psi.mi.jami.bridges.ontologymanager.MIOntologyTermI;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.utils.AnnotationUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ontology term for PSI-MI JAMI
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/11/11</pre>
 */

public class OntologyTermWrapper implements MIOntologyTermI {

    private OntologyTerm delegate;
    /**
     * Obsolete message
     */
    private String obsoleteMessage;

    private String remappedTerm;
    private Set<String> possibleTermsToRemapTo = new HashSet<String>();
    private Collection<String> synonyms = new ArrayList<String>();
    private static final String LINE_BREAK = "\n";
    private static final String OBSOLETE_DEF = "OBSOLETE";
    private static final String REMAP = "REMAP TO";
    private static final String MAP = "MAP TO";
    private static final String REPLACE = "REPLACE BY";
    public final static Pattern MOD_REGEXP = Pattern.compile("MOD:[0-9]{5}+");
    public final static Pattern MI_REGEXP = Pattern.compile("MI:[0-9]{4}+");
    public final static Pattern ECO_REGEXP = Pattern.compile("ECO:[0-9]+");

    public OntologyTermWrapper(OntologyTerm cv){
        if (cv == null){
            throw new IllegalArgumentException("The cv term cannot be null");
        }
        this.delegate = cv;

        // initialise synonyms
        for (Alias alias : cv.getSynonyms()){
            synonyms.add(alias.getName());
        }

        // initialise obsolete annotation
        Annotation def = AnnotationUtils.collectFirstAnnotationWithTopic(cv.getAnnotations(), null, "definition");
        if (def != null && def.getValue() != null){
           processDefinition(def.getValue());
        }
    }

    public String getTermAccession() {
        if (this.delegate.getMIIdentifier() != null){
            return this.delegate.getMIIdentifier();
        }
        else if (this.delegate.getMODIdentifier() != null){
            return this.delegate.getMODIdentifier();
        }
        else if (this.delegate.getPARIdentifier() != null){
            return this.delegate.getPARIdentifier();
        }
        else if (!this.delegate.getIdentifiers().isEmpty()){
            return this.delegate.getIdentifiers().iterator().next().getId();
        }
        return null;
    }

    public String getPreferredName() {
        return this.delegate.getShortName();
    }

    public void setTermAccession(String accession) {
         throw new UnsupportedOperationException("The OntologyTerm Wrapper is readonly and cannot be modified");
    }

    public void setPreferredName(String preferredName) {
        throw new UnsupportedOperationException("The OntologyTerm Wrapper is readonly and cannot be modified");
    }

    public Collection<String> getNameSynonyms() {
        List<String> synonyms = new ArrayList<String>(this.delegate.getSynonyms().size());
        for (Alias alias : this.delegate.getSynonyms()){
            synonyms.add(alias.getName());
        }
        return synonyms;
    }

    public void setNameSynonyms(Collection<String> nameSynonyms) {
        throw new UnsupportedOperationException("The OntologyTerm Wrapper is readonly and cannot be modified");
    }

    public String getObsoleteMessage() {
        return this.obsoleteMessage;
    }

    public String getRemappedTerm() {
        return this.remappedTerm;
    }

    public Set<String> getPossibleTermsToRemapTo() {
        return this.possibleTermsToRemapTo;
    }

    public OntologyTerm getDelegate() {
        return this.delegate;
    }

    public void setRemappedTerm(String remappedTerm) {
        this.remappedTerm = remappedTerm;
    }

    public void setObsoleteMessage(String obsoleteMessage) {
        this.obsoleteMessage = obsoleteMessage;
    }


    /**
     * Process the definition String
     * @param definition
     * @return
     */
    private void processDefinition(String definition) {
        if ( definition.contains( LINE_BREAK ) ) {
            String[] defArray = definition.split( LINE_BREAK );

            String otherInfoString = null;

            if ( defArray.length == 2 ) {
                otherInfoString = defArray[1];
                processInfoInDescription(otherInfoString);
            } else if ( defArray.length > 2 ) {
                for (int i = 1; i < defArray.length; i++){
                    otherInfoString = defArray[i];
                    processInfoInDescription(otherInfoString);
                }
            }
        }
        else if (definition.contains( OBSOLETE_DEF )){
            String[] defArray = definition.split( OBSOLETE_DEF );

            if ( defArray.length == 2 ) {
                this.obsoleteMessage = OBSOLETE_DEF + defArray[1];

                if (obsoleteMessage != null){
                    processObsoleteMessage();
                }
            } else if ( defArray.length > 2 ) {
                this.obsoleteMessage = definition.substring(defArray[0].length());

                if (obsoleteMessage != null){
                    processObsoleteMessage();
                }
            }
        }
        else {
            processInfoInDescription(definition);
        }
    }

    /**
     * Process the other information in the description
     * @param otherInfoString
     * @return true if an obsolete annotation has been added
     */
    private void processInfoInDescription(String otherInfoString) {

        // obsolete message
        if ( otherInfoString.startsWith( OBSOLETE_DEF )) {

            this.obsoleteMessage = otherInfoString;

            if (otherInfoString != null){
                processObsoleteMessage();
            }
        }
    }

    private void processObsoleteMessage() {
        String upperObsoleteMessage = this.obsoleteMessage.toUpperCase();
        String remappingString = null;

        if (upperObsoleteMessage.contains(MAP)){
            remappingString = upperObsoleteMessage.substring(upperObsoleteMessage.indexOf(MAP) + MAP.length());
        }
        else if (upperObsoleteMessage.contains(REMAP)){
            remappingString = upperObsoleteMessage.substring(upperObsoleteMessage.indexOf(REMAP) + REMAP.length());
        }
        else if (upperObsoleteMessage.contains(REPLACE)){
            remappingString = upperObsoleteMessage.substring(upperObsoleteMessage.indexOf(REPLACE) + REPLACE.length());
        }

        if (remappingString != null){
            Matcher miMatcher = MI_REGEXP.matcher(remappingString);
            Matcher modMatcher = MOD_REGEXP.matcher(remappingString);

            while (miMatcher.find()){
                this.possibleTermsToRemapTo.add(miMatcher.group());
            }

            while (modMatcher.find()){
                this.possibleTermsToRemapTo.add(modMatcher.group());
            }

            if (this.possibleTermsToRemapTo.size() == 1){
                this.remappedTerm = this.possibleTermsToRemapTo.iterator().next();

                // we do not need the remapped term to be kept twice
                this.possibleTermsToRemapTo.clear();
            }
        }
        else {
            Matcher miMatcher = MI_REGEXP.matcher(upperObsoleteMessage);
            Matcher modMatcher = MOD_REGEXP.matcher(upperObsoleteMessage);

            while (miMatcher.find()){
                this.possibleTermsToRemapTo.add(miMatcher.group());
            }

            while (modMatcher.find()){
                this.possibleTermsToRemapTo.add(modMatcher.group());
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OntologyTermWrapper){
            return getDelegate().equals(((OntologyTermWrapper)obj).getDelegate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getDelegate().hashCode();
    }
}
