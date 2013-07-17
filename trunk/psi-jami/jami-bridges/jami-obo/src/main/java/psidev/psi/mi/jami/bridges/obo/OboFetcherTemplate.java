package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Template fetcher for OBO files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class OboFetcherTemplate<T extends CvTerm> implements CvTermFetcher<T> {

    private Map<String, T> id2Term;
    private Map<String, T> name2Term;
    private CvTerm ontologyDatabase;

    public OboFetcherTemplate(CvTerm database, AbstractOboLoader<T> oboLoader, String filePath){
        if (oboLoader == null){
            throw new IllegalArgumentException("The OBO loader cannot be null and is needed to parse the OBO file");
        }
        if (filePath == null){
            throw new IllegalArgumentException("The OBO file cannot be null and is needed to load an ontology");
        }
        this.ontologyDatabase = database != null ? database : new DefaultCvTerm("unknown");
        initialiseLocalMaps();
        oboLoader.parseOboFile(new File(filePath), id2Term, name2Term);
    }

    public OboFetcherTemplate(String databaseName, AbstractOboLoader<T> oboLoader, String filePath){
        if (oboLoader == null){
            throw new IllegalArgumentException("The OBO loader cannot be null and is needed to parse the OBO file");
        }
        if (filePath == null){
            throw new IllegalArgumentException("The OBO file cannot be null and is needed to load an ontology");
        }
        this.ontologyDatabase = databaseName != null ? new DefaultCvTerm(databaseName) : new DefaultCvTerm("unknown");
        initialiseLocalMaps();
        oboLoader.parseOboFile(new File(filePath), id2Term, name2Term);
    }

    public T getCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName != null && !ontologyDatabaseName.equalsIgnoreCase(ontologyDatabaseName)){
            return null;
        }
        return id2Term.get(termIdentifier);
    }

    public T getCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase != null && !DefaultCvTermComparator.areEquals(ontologyDatabase, this.ontologyDatabase)){
            return null;
        }
        return id2Term.get(termIdentifier);
    }

    public T getCvTermByExactName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName != null && !ontologyDatabaseName.equalsIgnoreCase(ontologyDatabaseName)){
            return null;
        }
        return name2Term.get(searchName);
    }

    public T getCvTermByExactName(String searchName) throws BridgeFailedException {
        return name2Term.get(searchName);
    }

    public Collection<T> getCvTermByInexactName(String searchName, String databaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermByInexactName(String searchName, CvTerm database) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByExactNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<T> getCvTermsByExactNames(Collection<String> searchNames) throws BridgeFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void initialiseLocalMaps(){
        this.id2Term = new HashMap<String, T>();
        this.name2Term = new HashMap<String, T>();
    }
}
