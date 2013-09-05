package psidev.psi.mi.jami.bridges.mapper.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapper;
import psidev.psi.mi.jami.bridges.mapper.ProteinMapperListener;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 23/07/13
 */
public class MockProteinMapper implements ProteinMapper {

    private ProteinMapperListener listener = null;
    private boolean checking;
    private boolean priorityIdentifiers;
    private boolean prioritySequence;

    private Map<String,String> localRemap;

    public MockProteinMapper(){
        localRemap = new HashMap<String, String>();
    }

    public void addMappingResult(String oldKey, String newIdentifier){
        if(oldKey == null || newIdentifier == null) return;
        this.localRemap.put(oldKey , newIdentifier);
    }

    public void setListener(ProteinMapperListener listener) {
        this.listener = listener;
    }

    public ProteinMapperListener getListener() {
        return listener;
    }

    public void mapProtein(Protein p) throws BridgeFailedException {
        String newID = null;

        if(p.getSequence() != null) {
            newID = localRemap.get(p.getSequence());
            if(newID != null){
                p.setUniprotkb(newID);
                if( listener != null )
                    listener.onSuccessfulMapping(p, Collections.<String>emptyList());
            }
            return;
        }
        if(! p.getXrefs().isEmpty()) {
            newID = localRemap.get(p.getXrefs().iterator().next().getId());
            if(newID != null){
                p.setUniprotkb(newID);
                if( listener != null )
                    listener.onSuccessfulMapping(p, Collections.<String>emptyList());
            }
            return;
        }

        if( listener != null )
            listener.onFailedMapping(p, Collections.<String>emptyList());

    }

    public boolean isCheckingEnabled() {
        return checking;
    }

    public void setCheckingEnabled(boolean checkingEnabled) {
        this.checking = checkingEnabled;
    }

    public boolean isPriorityIdentifiers() {
        return priorityIdentifiers;
    }

    public void setPriorityIdentifiers(boolean priorityIdentifiers) {
        this.priorityIdentifiers = priorityIdentifiers;
    }

    public boolean isPrioritySequence() {
        return prioritySequence;
    }

    public void setPrioritySequence(boolean prioritySequence) {
        this.prioritySequence = prioritySequence;
    }
}
