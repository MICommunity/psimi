package psidev.psi.mi.jami.bridges.remapper.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapper;
import psidev.psi.mi.jami.bridges.remapper.ProteinRemapperListener;
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
public class MockProteinRemapper implements ProteinRemapper {

    private ProteinRemapperListener listener = null;
    private boolean checking;
    private boolean priorityIdentifiers;
    private boolean prioritySequence;



    private Map<String,String> localRemap;

    public MockProteinRemapper(){
        localRemap = new HashMap<String, String>();
    }

    public void addRemap(String oldKey , String newIdentifier){
        if(oldKey == null || newIdentifier == null) return;
        this.localRemap.put(oldKey , newIdentifier);
    }



    public void setRemapListener(ProteinRemapperListener listener) {
        this.listener = listener;
    }

    public ProteinRemapperListener getRemapListener() {
        return listener;
    }


    public void remapProtein(Protein p) throws BridgeFailedException {
        String newID = null;

        if(p.getSequence() != null) {
            newID = localRemap.get(p.getSequence());
            if(newID != null){
                p.setUniprotkb(newID);
                if( listener != null )
                    listener.onRemappingSuccessful(p, Collections.<String>emptyList());
            }
            return;
        }
        if(! p.getXrefs().isEmpty()) {
            newID = localRemap.get(p.getXrefs().iterator().next().getId());
            if(newID != null){
                p.setUniprotkb(newID);
                if( listener != null )
                    listener.onRemappingSuccessful(p, Collections.<String>emptyList());
            }
            return;
        }

        if( listener != null )
            listener.onRemappingFailed(p, Collections.<String>emptyList());

    }

    public boolean isCheckingEnabled() {
        return checking;
    }

    public void setCheckingEnabled(boolean checkingEnabled) {
        this.checking = checkingEnabled;
    }

    public boolean isPriorityIdentifiers() {
        return priorityIdentifiers;  //To change body of implemented methods use File | Settings | File Templates.
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
