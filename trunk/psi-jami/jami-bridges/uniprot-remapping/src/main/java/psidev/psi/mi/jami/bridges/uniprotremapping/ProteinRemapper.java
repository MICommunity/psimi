package psidev.psi.mi.jami.bridges.uniprotremapping;

import psidev.psi.mi.jami.bridges.uniprotremapping.listener.RemapListener;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 * Time: 10:08
 */
public interface ProteinRemapper {

    //public ProteinRemapper(RemapperBridge bridge);

    public void addRemapListener(RemapListener listener);
    public void removeRemapListener(RemapListener listener);

    /**
     * Will remap the protein using the settings provided.
     * A remapReport will be fired to all RemapListeners at the end.
     */
    public void remapProtein();

    /**
     * Sets the protein. Also cleans the fetcher.
     * @param p
     */
    public void setProtein(Protein p);

    /**
     * Informs whether identifier checking is enabled.
     *
     * Identifier checking will compare each identifier
     * to confirm that they have matching accessions.
     * @return
     */
    public boolean isCheckingEnabled() ;

    /**
     * Sets the status of identifier checking.
     * Will also reinitialise the remap report.
     * @param checkingEnabled
     */
    public void setCheckingEnabled(boolean checkingEnabled) ;


    public boolean isUseIdentifiers();

    /**
     * Sets the priority of identifiers to sequences
     * Will also reinitialise the remap report.
     * @param useIdentifiers
     */
    public void setUseIdentifiers(boolean useIdentifiers);

    public boolean isUseSequence();

    /**
     * Sets the priority of sequences to identifiers.
     * Will also reinitialise the remap report.
     * @param useSequence
     */
    public void setUseSequence(boolean useSequence);

    /**
     * Sets the protein to null and cleans the fetchers.
     */
    public void clean();
}
