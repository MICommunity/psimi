package psidev.psi.mi.jami.bridges.mapper;


import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 05/06/13
 */
public interface ProteinMapperListener {

    /**
     * A stacked report listing the exit case first, followed by any errors, the approaches used and the strategy.
     * @param p
     * @param report
     */
    public void onSuccessfulMapping(Protein p, Collection<String> report);

    /**
     *
     * @param p
     * @param report
     */
    public void onFailedMapping(Protein p, Collection<String> report);

}
