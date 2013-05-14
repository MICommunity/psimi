package psidev.psi.mi.enricher.cvtermenricher.enricherlistener.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:36
 */
public class ObsoleteTermEvent extends EnricherEvent {

    public ObsoleteTermEvent(EnricherEvent e){
        super(e);
    }

    public ObsoleteTermEvent(String queryID, String queryIDType){
        super(queryID, queryIDType);
    }
}
