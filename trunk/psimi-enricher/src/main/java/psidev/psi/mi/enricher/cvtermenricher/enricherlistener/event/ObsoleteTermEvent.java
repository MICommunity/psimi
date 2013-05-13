package psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:36
 */
public class ObsoleteTermEvent extends EnricherEvent {
    private String identity;


    public ObsoleteTermEvent(String identity){
        this.identity = identity;

    }
}
