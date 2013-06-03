package psidev.psi.mi.jami.bridges.uniprotremapping;

import uk.ac.ebi.intact.protein.mapping.actions.ActionName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/06/13
 * Time: 15:15
 */
public interface RemapReport {

    public String getFinalUniprotId();
    public void  setFinalUniprotId(String finalUniprotId);
    public boolean hasUniqueUniprotId();

}
