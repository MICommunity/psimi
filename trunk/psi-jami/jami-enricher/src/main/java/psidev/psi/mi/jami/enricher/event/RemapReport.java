package psidev.psi.mi.jami.enricher.event;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 13:36
 */
public class RemapReport {
    private String msg;

    public RemapReport(String msg){
        this.msg = msg;
    }

    public String getMsg(){return msg;}

}
