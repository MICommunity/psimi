package psidev.psi.mi.jami.bridges.remapper;

import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 07/06/13
 */
public interface ProteinRemapper {

    //public ProteinRemapper(RemapperBridge bridge);

    public void setRemapListener(ProteinRemapperListener listener);
    public ProteinRemapperListener getRemapListener();

    /**
     * Will remap the protein using the settings provided.
     * A remapReport will be fired to all RemapListeners at the end.
     */
    public void remapProtein(Protein p);

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


    public boolean isPriorityIdentifiers();

    /**
     * Sets the priority of identifiers to sequences
     * Will also reinitialise the remap report.
     * @param priorityIdentifiers
     */
    public void setPriorityIdentifiers(boolean priorityIdentifiers);

    public boolean isPrioritySequence();

    /**
     * Sets the priority of sequences to identifiers.
     * Will also reinitialise the remap report.
     * @param prioritySequence
     */
    public void setPrioritySequence(boolean prioritySequence);

}
