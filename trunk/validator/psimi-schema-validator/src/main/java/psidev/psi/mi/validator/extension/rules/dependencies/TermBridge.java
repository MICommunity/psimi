package psidev.psi.mi.validator.extension.rules.dependencies;

import psidev.psi.tools.ontology_manager.interfaces.OntologyTermI;

import java.util.Collection;

/**
 * Bridge between the class Term and the interface OntologyTermI
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>26-Apr-2010</pre>
 */

public class TermBridge implements OntologyTermI {

    private Term term;

    public TermBridge(Term term) {
        this.term = term;
    }

    public String getTermAccession() {
        return term != null ? term.getId() : null;
    }

    public String getPreferredName() {
        return term != null ? term.getName() : null;
    }

    public void setTermAccession(String accession) {
        if (term != null){
            term.setId(accession);
        }
        else {
            term = new Term(accession, null);
        }
    }

    public void setPreferredName(String preferredName) {
        if (term != null){
            term.setName(preferredName);
        }
        else {
            term = new Term(null, preferredName);
        }
    }

    public Collection<String> getNameSynonyms() {
        return null;
    }

    public void setNameSynonyms(Collection<String> nameSynonyms) {
    }
}
