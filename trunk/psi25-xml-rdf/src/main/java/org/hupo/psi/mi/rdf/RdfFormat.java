package org.hupo.psi.mi.rdf;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public enum RdfFormat {

    BIOPAX_L2("BioPAX.L2"),
    BIOPAX_L3("BioPAX.L3"),
    RDF_XML("RDF/XML"),
    RDF_XML_ABBREV("RDF/XML-ABBREV"),
    N_TRIPLE("N-TRIPLE"),
    TURTLE("TURTLE"),
    N3("N3"),
    N3_PP("N3-PP"),
    N3_PLAN("N3-PLAIN"),
    N3_TRIPLE("N3-TRIPLE");

    private String name;

    private RdfFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

