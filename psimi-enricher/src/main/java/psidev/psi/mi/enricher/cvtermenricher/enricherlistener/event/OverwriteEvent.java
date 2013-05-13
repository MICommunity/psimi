package psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:40
 */
public class OverwriteEvent {

    private String identity;
    private String identityType;
    private String field;
    private String oldValue;
    private String newValue;

    public OverwriteEvent(String identity, String identityType, String field, String oldValue, String newValue){
        this.identity = identity;
        this.identityType = identityType;
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
