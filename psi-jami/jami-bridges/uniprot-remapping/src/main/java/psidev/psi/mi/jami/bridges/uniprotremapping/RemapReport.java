package psidev.psi.mi.jami.bridges.uniprotremapping;

import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 15:55
 */
public class RemapReport {

    private boolean remapped = false;
    private boolean checkingEnabled = true;
    private String conflictMessage = null;
    private boolean identifierFromSequence = false;
    private boolean identifierFromIdentifiers = false;

    private ArrayList<IdentificationResults> identificationResults = new ArrayList<IdentificationResults>();

    public RemapReport(boolean checkingEnabled){
        this.checkingEnabled = checkingEnabled;
    }

    public boolean isCheckingEnabled(){
        return checkingEnabled;
    }

    public boolean isRemapped() {
        return remapped;
    }

    public void setRemapped(boolean remapped) {
        this.remapped = remapped;
    }

    public String getConflictMessage() {
        return conflictMessage;
    }

    public void setConflictMessage(String conflictMessage) {
        this.conflictMessage = conflictMessage;
    }

    public boolean isIdentifierFromSequence() {
        return identifierFromSequence;
    }

    public void setIdentifierFromSequence(boolean identifierFromSequence) {
        this.identifierFromSequence = identifierFromSequence;
    }

    public boolean isIdentifierFromIdentifiers() {
        return identifierFromIdentifiers;
    }

    public void setIdentifierFromIdentifiers(boolean identifierFromIdentifiers) {
        this.identifierFromIdentifiers = identifierFromIdentifiers;
    }

    public ArrayList<IdentificationResults> getIdentificationResults() {
        return identificationResults;
    }

    public void setIdentificationResults(ArrayList<IdentificationResults> identificationResults) {
        this.identificationResults = identificationResults;
    }

}
