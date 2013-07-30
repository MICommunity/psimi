package psidev.psi.mi.jami.bridges.fetcher;

import psidev.psi.mi.jami.model.Publication;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 29/07/13
 */
public interface PublicationFetcher {

    public Collection<Publication> getPublicationByPubmedID(String pubmedID);

}
