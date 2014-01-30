package psidev.psi.mi.jami.bridges.fetcher.mock;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.BioactiveEntityFetcher;
import psidev.psi.mi.jami.model.BioactiveEntity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/08/13
 */
public class MockBioactiveEntityFetcher
        extends AbstractMockFetcher<Collection<BioactiveEntity>>
        implements BioactiveEntityFetcher{

    public Collection<BioactiveEntity> fetchByIdentifier(String identifier) throws BridgeFailedException {
        return super.getEntry(identifier);
    }

    public Collection<BioactiveEntity> fetchByIdentifiers(Collection<String> identifiers) throws BridgeFailedException {
        Collection<BioactiveEntity> resultsList= new ArrayList<BioactiveEntity>();
        for(String identifier : identifiers){
            resultsList.addAll(getEntry(identifier));
        }
        return resultsList;
    }
}
