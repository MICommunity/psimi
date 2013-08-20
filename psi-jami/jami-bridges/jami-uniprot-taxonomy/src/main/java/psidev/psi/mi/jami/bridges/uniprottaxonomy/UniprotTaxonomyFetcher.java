package psidev.psi.mi.jami.bridges.uniprottaxonomy;

import com.hp.hpl.jena.rdf.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.OrganismFetcher;
import psidev.psi.mi.jami.bridges.util.OrganismUtil;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.impl.DefaultAlias;
import psidev.psi.mi.jami.model.impl.DefaultOrganism;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 28/06/13
 */
public class UniprotTaxonomyFetcher implements OrganismFetcher {

    private final Logger log = LoggerFactory.getLogger(UniprotTaxonomyFetcher.class.getName());

    private static final String UNIPROT_NS = "http://purl.uniprot.org/core/";
    private static final String UNIPROT_TAXONOMY_NS = "http://purl.uniprot.org/taxonomy/";

    private InputStream getInputStream( int taxid ) throws IOException {
        String urlStr = UNIPROT_TAXONOMY_NS + taxid + ".rdf";
        URL url = new URL( urlStr );
        return url.openStream();
    }


    public Organism getOrganismByTaxID(int taxID) throws BridgeFailedException {
        Organism organism = OrganismUtil.createSpecialistOrganism(taxID);
        if ( organism == null ) {
            organism = getOrganismFromStream( taxID );
        }
        return organism;
    }

    private Organism getOrganismFromStream(int taxID) throws BridgeFailedException {
        final InputStream stream;
        try {
            stream = getInputStream( taxID );
        } catch (IOException e) {
            throw new BridgeFailedException("Input stream failed to open",e);
        }

        Organism organism = null;
        try{
            Model model = ModelFactory.createDefaultModel();
            model.read(stream, null);
            Resource taxonomyResource = model.getResource(UNIPROT_TAXONOMY_NS + taxID);

            if(log.isWarnEnabled()){
                // check first if it has been replaced by another record (would contain the replacedBy property)
                Property replacedByProperty = model.getProperty(UNIPROT_NS, "replacedBy");
                boolean isReplaced = model.contains(taxonomyResource, replacedByProperty);

                if (isReplaced) {
                    Statement replacedStatement = model.getProperty(taxonomyResource, replacedByProperty);
                    String replacedUri = replacedStatement.getObject().asResource().getURI();
                    String replacedByTaxidStr = replacedUri.replaceAll(UNIPROT_TAXONOMY_NS, "");
                    int replacedByTaxid = Integer.parseInt(replacedByTaxidStr);

                    log.info( "WARNING - the taxid replacement for " + taxID + " is " + replacedByTaxid );
                }
            }

            organism = new DefaultOrganism( taxID );

            // standard properties
            /*String mnemonic = getLiteral(model, taxonomyResource, "mnemonic");
            if (mnemonic != null) term.setMnemonic(mnemonic);  */

            String commonName = getLiteral(model, taxonomyResource, "commonName");
            if(commonName != null && commonName.length() > 0)
                organism.setCommonName(commonName);

            String scientificName = getLiteral(model, taxonomyResource, "scientificName");
            if(scientificName != null && scientificName.length() > 0)
                organism.setScientificName(scientificName);

            String synonym = getLiteral(model, taxonomyResource, "synonym");
            if(synonym != null && synonym.length() > 0)
                organism.getAliases().add(new DefaultAlias(synonym));
        }
        finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return organism;
    }

    public Collection<Organism> getOrganismsByTaxIDs(Collection<Integer> taxIDs) throws BridgeFailedException {
        Collection<Organism> results = new ArrayList<Organism>();
        for(Integer taxID : taxIDs){
            results.add(getOrganismByTaxID(taxID));
        }
        return results;
    }


    private String getLiteral(Model model, Resource taxonomyResource, String propertyName) {
        Property property = model.getProperty(UNIPROT_NS, propertyName);
        if (model.contains(taxonomyResource, property)) {
            return model.getProperty(taxonomyResource, property).getLiteral().getString();
        }
        return null;
    }
}
