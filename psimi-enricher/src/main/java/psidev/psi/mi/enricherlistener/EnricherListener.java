package psidev.psi.mi.enricherlistener;


import psidev.psi.mi.enricherlistener.event.EnricherEvent;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 13/05/13
 * Time: 11:11
 */
public interface EnricherListener {

    public void enricherEvent(EnricherEvent e);


}
