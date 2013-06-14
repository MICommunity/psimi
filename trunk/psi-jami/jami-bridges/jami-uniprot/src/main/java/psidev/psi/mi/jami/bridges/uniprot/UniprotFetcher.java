package psidev.psi.mi.jami.bridges.uniprot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BadResultException;
import psidev.psi.mi.jami.bridges.exception.BadSearchTermException;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.ProteinFetcher;
import psidev.psi.mi.jami.model.Protein;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Gabriel Aldam (galdam@ebi.ac.uk)
 * Date: 14/05/13
 * Time: 13:09
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
     * @throws BadResultException
     * @throws BadSearchTermException
     */
    public Collection<Protein> getProteinsByIdentifier(String identifier)
            throws BridgeFailedException, BadResultException, BadSearchTermException {

        if(identifier == null) throw new BadSearchTermException("Could not perform search on null identifier.");

        Collection<Protein> proteins = null;

        if(UNIPROT_PRO_REGEX.matcher(identifier).find()){
            //log.debug("Searching for the pro identifier ["+proIdentifier+"] (from identifier ["+identifier+"])");
            proteins = bridge.fetchFeatureBySearch(identifier.substring(4,-1));
        } else if (UNIPROT_MASTER_REGEX.matcher(identifier).find()){
            if(UNIPROT_ISOFORM_REGEX.matcher(identifier).find()){
                //log.debug("Searching for the isoform identifier ["+identifier+"]");
                proteins = bridge.fetchIsoformsByIdentifier(identifier);
            } else {
                //log.debug("Searching for the master identifier ["+identifier+"]");
                proteins = bridge.fetchMastersByIdentifier(identifier);
            }
        }

        if(proteins == null || proteins.size() == 0){
            return null;
        }

        return proteins;
    }

    public String getService() {
        return "UniprotKB";
    }
}
