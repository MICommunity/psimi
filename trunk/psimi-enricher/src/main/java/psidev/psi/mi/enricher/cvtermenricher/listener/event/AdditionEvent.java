package psidev.psi.mi.enricher.cvtermenricher.listener.event;

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
    private String newValue;

    public AdditionEvent(String field, String identity, String newValue){
        this.field = field;
        this.identity = identity;
        this.newValue = newValue;
    }
}
