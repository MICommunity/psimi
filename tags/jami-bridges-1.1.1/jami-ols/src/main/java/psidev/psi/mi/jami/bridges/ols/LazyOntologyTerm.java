package psidev.psi.mi.jami.bridges.ols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultXref;
import uk.ac.ebi.ols.soap.Query;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * A lazy ontology term, which only checks for parents of children when required.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 21/08/13
 */
public class LazyOntologyTerm
        extends LazyCvTerm implements OntologyTerm{

    protected final Logger log = LoggerFactory.getLogger(LazyOntologyTerm.class.getName());

    private Collection<OntologyTerm> parents;
    private Collection<OntologyTerm> children;
    private String definition;

    public LazyOntologyTerm(Query queryService, String fullName, Xref identityRef, String ontologyName) {
        super(queryService, fullName, identityRef, ontologyName);
    }

    public String getDefinition(){
        if (!hasLoadedMetadata()){
            initialiseMetaData( getOriginalXref() );
        }
        return this.definition;
    }

    public void setDefinition(String def) {
        this.definition = def;
    }

    public Collection<OntologyTerm> getParents() {
        if(parents == null){
            initialiseOlsParents( getOriginalXref() );
        }
        return this.parents;
    }

    public Collection<OntologyTerm> getChildren() {
        if (children == null){
            initialiseOlsChildren( getOriginalXref() );
        }
        return this.children;
    }


    @Override
    public String toString() {
        return (getMIIdentifier() != null ? getMIIdentifier() : (getMODIdentifier() != null ? getMODIdentifier() : (getPARIdentifier() != null ? getPARIdentifier() : "-"))) + " ("+getFullName()+")";
    }

    @Override
    protected void initialiseDefinition(String description) {
        if (this.definition == null){
            this.definition = description;
        }
    }

    // == QUERY METHODS =======================================================================

    private void initialiseOlsChildren(Xref identifier){
        this.children = new ArrayList<OntologyTerm>();
        Map<String,String> childrenIDs;
        try{
            childrenIDs = getQueryService().getTermChildren(identifier.getId(), getOntologyName(), 1, null);
        } catch (RemoteException e) {
            throw new LazyTermLoadingException("Cannot load children", e);
        }
        String id = !getIdentifiers().isEmpty() ? getIdentifiers().iterator().next().getId():null;
        Iterator<Map.Entry<String,String>> childrenIterator = childrenIDs.entrySet().iterator();

        try {
            Map<String, String> relations = getQueryService().getTermRelations(id, getOntologyName());

            while (childrenIterator.hasNext()){
                Map.Entry<String,String> child = childrenIterator.next();

                if (relations.containsKey(child.getKey())){
                    String relation = relations.get(child.getKey());
                    // remove other parents to avoid cyclic dependencies
                    if (relation == null || (!relation.equals("part_of") && !relation.equals("is_a"))){
                        childrenIterator.remove();
                    }
                }
            }
        } catch (RemoteException e) {
            throw new IllegalStateException( "RemoteException while trying to connect to OLS." );
        }

        for(Map.Entry<String,String> entry: childrenIDs.entrySet()){
            this.children.add( new LazyOntologyTerm(getQueryService(),
                    entry.getValue(),
                    new DefaultXref(getOriginalXref().getDatabase() , entry.getKey(), getOriginalXref().getQualifier()),
                    getOntologyName()));
        }
    }

    private void initialiseOlsParents(Xref identifier){
        this.parents = new ArrayList<OntologyTerm>();
        Map<String,String> parentIDs;
        try{
            parentIDs = getQueryService().getTermParents(identifier.getId(), getOntologyName());
        } catch (RemoteException e) {
            log.warn("LazyOntologyTerm "+toString()+" failed whilst attempting to access metaData.",e);
            throw new IllegalStateException("The query service has failed.");
        }
        Iterator<Map.Entry<String,String>> parentIterator = parentIDs.entrySet().iterator();

        while (parentIterator.hasNext()){
            Map.Entry<String,String> parent = parentIterator.next();

            try {
                Map<String, String> relations = getQueryService().getTermRelations(parent.getKey(), getOntologyName());
                String id = !getIdentifiers().isEmpty() ? getIdentifiers().iterator().next().getId():null;
                if (relations.containsKey(id)){
                    String relation = relations.get(id);
                    // remove other parents to avoid cyclic dependencies
                    if (relation == null || (!relation.equals("part_of") && !relation.equals("is_a"))){
                        parentIterator.remove();
                    }
                }
            } catch (RemoteException e) {
                throw new IllegalStateException( "RemoteException while trying to connect to OLS." );
            }
        }

        for(Map.Entry<String,String> entry: parentIDs.entrySet()){
            this.parents.add( new LazyOntologyTerm(getQueryService(),
                    entry.getValue(),
                    new DefaultXref(getOriginalXref().getDatabase() , entry.getKey(), getOriginalXref().getQualifier()),
                    getOntologyName()));
        }
    }
}
