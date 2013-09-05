package psidev.psi.mi.jami.bridges.mapper;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.model.Protein;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  07/06/13
 */
public interface ProteinMapper {

    public void setListener(ProteinMapperListener listener);
    public ProteinMapperListener getListener();

    /**
     * Will remap the protein using the settings provided.
     * A remapReport will be fired to all RemapListeners at the end.
     */
    public void mapProtein(Protein p) throws BridgeFailedException;
}
