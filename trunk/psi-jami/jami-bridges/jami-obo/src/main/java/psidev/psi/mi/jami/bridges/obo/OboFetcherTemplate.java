package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.fetcher.CvTermFetcher;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.comparator.cv.DefaultCvTermComparator;

import java.io.File;
import java.util.*;

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

    public T fetchCvTermByIdentifier(String termIdentifier, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName != null && !this.ontologyDatabase.getShortName().equalsIgnoreCase(ontologyDatabaseName)){
            return null;
        }
        return id2Term.get(termIdentifier);
    }

    public T fetchCvTermByIdentifier(String termIdentifier, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase != null && !DefaultCvTermComparator.areEquals(ontologyDatabase, this.ontologyDatabase)){
            return null;
        }
        return id2Term.get(termIdentifier);
    }

    public T fetchCvTermByName(String searchName, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName != null && !this.ontologyDatabase.getShortName().equalsIgnoreCase(ontologyDatabaseName)){
            return null;
        }
        return name2Term.get(searchName);
    }

    public Collection<T> fetchCvTermByName(String searchName) throws BridgeFailedException {
        return Collections.singletonList(name2Term.get(searchName));
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName != null && !this.ontologyDatabase.getShortName().equalsIgnoreCase(ontologyDatabaseName)){
            return Collections.EMPTY_LIST;
        }
        Collection<T> terms = new ArrayList<T>(termIdentifiers.size());

        for (String id : termIdentifiers){
            if (id2Term.containsKey(id)){
                terms.add(id2Term.get(id));
            }
        }

        return terms;
    }

    public Collection<T> fetchCvTermsByIdentifiers(Collection<String> termIdentifiers, CvTerm ontologyDatabase) throws BridgeFailedException {
        if (ontologyDatabase != null && !DefaultCvTermComparator.areEquals(ontologyDatabase, this.ontologyDatabase)){
            return Collections.EMPTY_LIST;
        }
        Collection<T> terms = new ArrayList<T>(termIdentifiers.size());

        for (String id : termIdentifiers){
            if (id2Term.containsKey(id)){
                terms.add(id2Term.get(id));
            }
        }

        return terms;
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames, String ontologyDatabaseName) throws BridgeFailedException {
        if (ontologyDatabaseName != null && !this.ontologyDatabase.getShortName().equalsIgnoreCase(ontologyDatabaseName)){
            return Collections.EMPTY_LIST;
        }
        Collection<T> terms = new ArrayList<T>(searchNames.size());

        for (String name : searchNames){
            if (name2Term.containsKey(name)){
                terms.add(name2Term.get(name));
            }
        }

        return terms;
    }

    public Collection<T> fetchCvTermsByNames(Collection<String> searchNames) throws BridgeFailedException {
        Collection<T> terms = new ArrayList<T>(searchNames.size());

        for (String name : searchNames){
            if (name2Term.containsKey(name)){
                terms.add(name2Term.get(name));
            }
        }

        return terms;
    }

    protected void initialiseLocalMaps(){
        this.id2Term = new HashMap<String, T>();
        this.name2Term = new HashMap<String, T>();
    }
}
