package psidev.psi.mi.enricher.cvtermenricher.listener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:40
 */
public class OverwriteEvent {
    private String field;
    private String identity;
    private String oldValue;
    private String newValue;

    public OverwriteEvent(String field, String identity, String oldValue, String newValue){
        this.field = field;
        this.identity = identity;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
}
