package psidev.psi.mi.jami.bridges.uniprotremapping.listener;

import psidev.psi.mi.jami.bridges.uniprotremapping.RemapReport;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 05/06/13
 * Time: 15:47
 */
public interface RemapListener {
    public void fireRemapReport(RemapReport report);
}
