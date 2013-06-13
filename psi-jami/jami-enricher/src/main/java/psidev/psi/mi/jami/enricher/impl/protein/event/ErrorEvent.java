package psidev.psi.mi.jami.enricher.enricherimplementation.protein.event;

import psidev.psi.mi.jami.model.Protein;

/**
 * An ErrorEvent is fired when an unusual case takes place
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 11:06
 */
public class ErrorEvent {
    private String errorType;
    private String field;
    private String errorMessage;

    public ErrorEvent(String errorType, String field, String errorMessage){
        this.errorType = errorType;
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static final String ERROR_CONFLICT = "Conflict";

}
