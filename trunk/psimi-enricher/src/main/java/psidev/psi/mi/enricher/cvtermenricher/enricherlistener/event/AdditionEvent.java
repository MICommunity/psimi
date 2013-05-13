package psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 14:30
 */
public class AdditionEvent {
    private String field;
    private String identity;
    private String identityType;
    private String newValue;

    public AdditionEvent(String identity, String identityType, String field,  String newValue){
        this.field = field;
        this.identity = identity;
        this.identityType = identityType;
        this.newValue = newValue;
    }
}
