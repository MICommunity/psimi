package psidev.psi.mi.jami.bridges.uniprotremapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.bridges.uniprotremapping.exception.ResultConflictException;
import psidev.psi.mi.jami.bridges.uniprotremapping.listener.RemapListener;
import psidev.psi.mi.jami.model.Protein;
import psidev.psi.mi.jami.model.Xref;
import uk.ac.ebi.intact.protein.mapping.results.IdentificationResults;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 03/06/13
 * Time: 13:50
 */
public class DefaultProteinRemap {

    public static final Log log = LogFactory.getLog(DefaultProteinRemap.class);
    private ProteinRemapFetcher remapFetcher = new ProteinRemapFetcher();



    private Collection<RemapListener> listeners = new ArrayList<RemapListener>();

    private boolean checkingEnabled = true;
    private boolean useIdentifiers = true;
    private boolean useSequence = true;
    private RemapReport remapReport = new RemapReport(checkingEnabled);

    private Protein p;

    public DefaultProteinRemap(){

    }

    public void addRemapListener(RemapListener listener){
        listeners.add(listener);
    }
    public void removeRemapListener(RemapListener listener){
        listeners.remove(listener);
    }
    public void fireRemapReport(){
        for(RemapListener r: listeners){
            r.fireRemapReport(remapReport);
        }
    }

    public void remapProtein(){
        try {
            String identifier  = getIdentifier();
            p.setUniprotkb(identifier);
            remapReport.setRemapped(true);
        } catch (ResultConflictException e) {
            remapReport.setRemapped(false);
            remapReport.setConflictMessage(e.getMessage());
        }
    }


    /**
     * If checking is false, the identifier result will always be the first one found.
     * There will also be no checking for identifier result conflicts.
     * In the case of no result being found, null will be returned.
     *
     * If the checking is true, the identifier result will be agreed by all identifiers
     * (except those which return null and will be ignored)
     * In the case that all identifiers returned null, null will be returned
     * In the case that two identifiers gave differing results, a conflict will be thrown.
     *
     * If useIdentifiers and useSequence, the two must be the same (neither null).
     * If useIdentifiers, the identifier result is returned,
     * unless it is null in which case the sequence result is returned.
     * If useSequence, the sequence result is returned,
     * unless it is null in which case the identifier result is returned.
     * If neither, both identifier and sequence result are considered,
     * if they are the same or one is null, the result is returned
     *
     * @return
     * @throws ResultConflictException
     */
    private String getIdentifier() throws ResultConflictException {
        String identifier = null;
        //Ignore neither - get a fully matching identifier
        if(useIdentifiers && useSequence){

            if(checkingEnabled) identifier = noneConflictingUniprotFromIdentifiers();
            else identifier = getFirstMappableIdentifier();
            //log.info("uniprot from identifiers is "+identifier);

            if(identifier != null
                    && identifier.equalsIgnoreCase(getSequenceMapping())){
                remapReport.setIdentifierFromIdentifiers(true);
                remapReport.setIdentifierFromSequence(true);
                return identifier;
            } else {
                throw new ResultConflictException("The identifier result "+identifier+
                        " does not match the sequence result "+getSequenceMapping());
            }
        }
        else if(!useIdentifiers && useSequence){
            identifier = getSequenceMapping();
            remapReport.setIdentifierFromSequence(true);
            if(identifier == null){
                if(checkingEnabled) identifier = noneConflictingUniprotFromIdentifiers();
                else identifier = getFirstMappableIdentifier();
                remapReport.setIdentifierFromSequence(false);
                remapReport.setIdentifierFromIdentifiers(true);
            }
            return identifier;
        }
        else if(useIdentifiers && !useSequence){
            if(checkingEnabled) identifier = noneConflictingUniprotFromIdentifiers();
            else identifier = getFirstMappableIdentifier();

            remapReport.setIdentifierFromIdentifiers(true);

            if(identifier == null){
                remapReport.setIdentifierFromSequence(true);
                remapReport.setIdentifierFromIdentifiers(false);
                return getSequenceMapping();
            }
            return identifier;

        }
        else if(!useIdentifiers && !useSequence){

            if(checkingEnabled) identifier = noneConflictingUniprotFromIdentifiers();
            else identifier = getFirstMappableIdentifier();

            remapReport.setIdentifierFromIdentifiers(true);

            if(identifier == null){
                remapReport.setIdentifierFromSequence(true);
                remapReport.setIdentifierFromIdentifiers(false);
                return getSequenceMapping();
            }else if (getSequenceMapping() != null &&
                    identifier.equalsIgnoreCase(getSequenceMapping())){
                remapReport.setIdentifierFromSequence(true);
                remapReport.setIdentifierFromIdentifiers(true);
                return identifier;
            } else {
                throw new ResultConflictException("The identifier result "+identifier+
                        " does not match the sequence result "+getSequenceMapping());
            }
        }
        return null;
    }

    public void setProtein(Protein p){
        remapReport = new RemapReport(checkingEnabled);
        remapFetcher.clean();
        this.p = p;
        if(p == null) return; //todo
        //if(p.getUniprotkb() != null) return false; //todo
        //if(p.getXrefs() != null || p.getXrefs().size() <= 0) return false; //todo

        if(p.getSequence() != null) remapFetcher.setSequence(p.getSequence());
        if(p.getOrganism() != null) remapFetcher.setOrganism(p.getOrganism());
        if(p.getXrefs() != null || p.getXrefs().size() < 0){
            for(Xref x :p.getXrefs()){
                remapFetcher.setIdentifierList(x);
            }
        }
    }

    /**
     *
     * @return A uniprot identifier if only one was found, null if there are none or there is a conflict.
     */
    private String noneConflictingUniprotFromIdentifiers() throws ResultConflictException {
        String uniprotIdentifier = null;
        if(p.getXrefs() != null || p.getXrefs().size() < 0){
            for(Xref x :p.getXrefs()){
                remapReport.getIdentificationResults().add(remapFetcher.getIdentifierListEntry(x));
                if(remapFetcher.getIdentifierListEntry(x).hasUniqueUniprotId()){
                    if(uniprotIdentifier == null){
                        uniprotIdentifier = remapFetcher.getIdentifierListEntry(x).getFinalUniprotId();
                    }else if(!uniprotIdentifier.equalsIgnoreCase(
                            remapFetcher.getIdentifierListEntry(x).getFinalUniprotId())){
                        throw new ResultConflictException("The identifier results "+uniprotIdentifier+
                                " and "+remapFetcher.getIdentifierListEntry(x).getFinalUniprotId()+
                                " do not match");
                    }
                }
            }
        }
        return uniprotIdentifier;
    }

    private String getFirstMappableIdentifier(){
        if(p.getXrefs() != null || p.getXrefs().size() < 0){

            for(Xref x :p.getXrefs()){
                remapReport.getIdentificationResults().add(remapFetcher.getIdentifierListEntry(x));
                if(remapFetcher.getIdentifierListEntry(x).hasUniqueUniprotId()){
                    return remapFetcher.getIdentifierListEntry(x).getFinalUniprotId();
                }
            }

        }
        return null;
    }

    private String getSequenceMapping(){
        remapReport.getIdentificationResults().add(remapFetcher.getSequenceMapping());
        if(remapFetcher.getSequenceMapping().hasUniqueUniprotId()){
            return remapFetcher.getSequenceMapping().getFinalUniprotId();
        }
        return null;
    }




    public boolean isCheckingEnabled() {
        return checkingEnabled;
    }

    public void setCheckingEnabled(boolean checkingEnabled) {
        this.checkingEnabled = checkingEnabled;
        clean();
    }

    public boolean isUseIdentifiers() {
        return useIdentifiers;
    }

    public void setUseIdentifiers(boolean useIdentifiers) {
        this.useIdentifiers = useIdentifiers;
        clean();
    }

    public boolean isUseSequence() {
        return useSequence;
    }

    public void setUseSequence(boolean useSequence) {
        this.useSequence = useSequence;
        clean();
    }

    public void clean(){
        setProtein(p);
        //Following done by setting p
        //remapReport = new RemapReport(checkingEnabled);
        //remapFetcher.clean();
    }
}
