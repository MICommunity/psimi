package psidev.psi.mi.jami.enricher.enricherimplementation.protein.listener;


import psidev.psi.mi.jami.enricher.enricherimplementation.protein.event.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 10/06/13
 * Time: 10:56
 */
public interface ProteinEnricherListener {

    public void onAdditionReportEvent(AddedEvent e);
    public void onErrorEvent(ErrorEvent e);
    public void onConflictEvent(ConflictEvent e);
    public void onUpdateEventReport(UpdatedEvent e);
    public void onDeadProteinEvent(DeadProteinEvent e);
}
