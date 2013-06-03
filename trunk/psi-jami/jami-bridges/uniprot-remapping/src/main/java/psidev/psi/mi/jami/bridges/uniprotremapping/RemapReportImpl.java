package psidev.psi.mi.jami.bridges.uniprotremapping;

import uk.ac.ebi.intact.protein.mapping.actions.ActionName;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/06/13
 * Time: 15:11
 */
public class RemapReportImpl
        implements RemapReport{

    String finalUniprotId = null;


    public String getFinalUniprotId() {
        return finalUniprotId;
    }

    public void setFinalUniprotId(String finalUniprotId) {
        this.finalUniprotId = finalUniprotId;
    }

    public boolean hasUniqueUniprotId() {
        if(finalUniprotId == null) return false;
        else return true;
    }
}
