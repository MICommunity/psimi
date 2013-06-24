package psidev.psi.mi.jami.bridges.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 */
public class UniprotFetcher
        implements ProteinFetcher {

    private final Logger log = LoggerFactory.getLogger(UniprotFetcher.class.getName());

    private UniprotBridge bridge;

    public UniprotFetcher() {
        log.debug("Starting uniprot bridge");
        bridge = new UniprotBridge();
    }



    public static final Pattern UNIPROT_PRO_REGEX = Pattern.compile("PRO[_|-][0-9]{10}");
    public static final Pattern UNIPROT_ISOFORM_REGEX = Pattern.compile("-[0-9]");
    public static final Pattern UNIPROT_MASTER_REGEX = Pattern.compile(
            "[A-NR-Z][0-9][A-Z][A-Z0-9][A-Z0-9][0-9]|"+
            "[OPQ][0-9][A-Z0-9][A-Z0-9][A-Z0-9][0-9]");

    /**
     *
     * @param identifier
     * @return
     * @throws BridgeFailedException
     */
    public Collection<Protein> getProteinsByIdentifier(String identifier)
            throws BridgeFailedException {

        if(identifier == null) throw new IllegalArgumentException("Could not perform search on null identifier.");

        Collection<Protein> proteins = null;

        if(UNIPROT_PRO_REGEX.matcher(identifier).find()){
           // log.warn("doing identifier "+identifier);
            //log.debug("Searching for the pro identifier ["+proIdentifier+"] (from identifier ["+identifier+"])");
            proteins = bridge.fetchFeatureBySearch(identifier.substring(4,identifier.length()-1));
        } else if (UNIPROT_MASTER_REGEX.matcher(identifier).find()){
            if(UNIPROT_ISOFORM_REGEX.matcher(identifier).find()){
                //log.debug("Searching for the isoform identifier ["+identifier+"]");
                proteins = bridge.fetchIsoformsByIdentifier(identifier);
            } else {
                //log.debug("Searching for the master identifier ["+identifier+"]");
                proteins = bridge.fetchMastersByIdentifier(identifier);
            }
        }

        if(proteins == null || proteins.isEmpty()){
            return Collections.EMPTY_LIST;
        }

        return proteins;
    }

    public Collection<Protein> getProteinsByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        return Collections.EMPTY_LIST;
    }

}
