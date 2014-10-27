/**
 * Copyright 2009 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.intact.jami.ontology.iterator;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.impl.DefaultOntologyTerm;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Example URL: http://www.uniprot.org/taxonomy/?query=*&limit=10&format=list
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: UniprotTaxonomyOntologyIterator.java 19999 2014-02-28 09:47:55Z mdumousseau@yahoo.com $
 */
public class UniprotTaxonomyOntologyIterator extends LineOntologyIterator {

    private final static String UNIPROT_TAXONOMY = "uniprot taxonomy";
    private final static String UNIPROT_TAXONOMY_MI = "MI:0942";

    private static String BASE_URL = "http://www.uniprot.org/taxonomy/?format=tab&query=";

    public UniprotTaxonomyOntologyIterator(URL url) throws IOException {
        super(url);
    }

    public UniprotTaxonomyOntologyIterator(InputStream is) {
        super(is);
    }

    public UniprotTaxonomyOntologyIterator(Reader reader) {
        super(reader);
    }

    public UniprotTaxonomyOntologyIterator() throws IOException {
       this("*", 0, -1);
    }

    public UniprotTaxonomyOntologyIterator(int offset, int limit) throws IOException {
       this("*", offset, limit);
    }

    public UniprotTaxonomyOntologyIterator(String query, int offset, int limit) throws IOException {
       this(query, offset, limit, false);
    }

    public UniprotTaxonomyOntologyIterator(String query, int offset, int limit, boolean onlyReviewed) throws IOException {
       this(new URL(BASE_URL + URLEncoder.encode(query+(onlyReviewed? " AND reviewed:yes" : ""), "UTF-8") +
               "&offset=" + offset+
               "&limit=" + limit));
    }

    @Override
    public boolean skipLine(String line) {
        String[] cols = line.split("\t");

        if (line.startsWith("Taxon") || cols.length < 4) {
            return true;
        }

        return super.skipLine(line);
    }

    /**
     * Expected tab-delimited columns:
     *
     * <pre>
     * 0 Taxon
     * 1 Mnemonic
     * 2 Scientific Name
     * 3 Common Name
     * 4 Synonym
     * 5 Other Names
     * 6 Reviewed
     * 7 Rank
     * 8 Lineage
     * 9 Parent
     * </pre>

     * @param line The line to process
     * @return the ontology document
     */
    protected OntologyTerm processLine(String line) {
        String[] cols = line.split("\t");

        String childId = safeGet(cols, 0);

        String scientificName = safeGet(cols, 2);
        String commonName = safeGet(cols, 3);
        String synonym = safeGet(cols, 4);
        String[] otherNames = split(safeGet(cols, 5));

        String childName = scientificName;

        if (commonName != null && commonName.length() > 0) {
            childName = commonName;
        } else {
            childName = scientificName;
        }

        String parentId = safeGet(cols, 9);
        String parentName = "";
        String lineage = safeGet(cols, 8);

        // the parent name is the last element in the lineage
        if (lineage.lastIndexOf(";") > -1) {
            parentName = lineage.substring(lineage.lastIndexOf(";", lineage.length())+1).trim();
        }

        OntologyTerm term = createOntologyTerm(childId, scientificName, synonym, otherNames, childName);

        if (parentId != null){
            OntologyTerm parent = createOntologyTerm(parentId, parentName, null, null, parentName);
            if (parent != null){
                term.getParents().add(parent);
                parent.getChildren().add(term);
            }
        }

        return term;
    }

    private OntologyTerm createOntologyTerm(String childId, String scientificName, String synonym, String[] otherNames, String childName) {
        OntologyTerm term = new DefaultOntologyTerm(childName);
        term.getIdentifiers().add(XrefUtils.createIdentityXref(UNIPROT_TAXONOMY, UNIPROT_TAXONOMY_MI, childId));
        term.setFullName(scientificName);
        if (synonym != null){
            term.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, synonym));
        }
        if (otherNames != null){
            for (String name : otherNames){
                term.getSynonyms().add(AliasUtils.createAlias(Alias.SYNONYM, Alias.SYNONYM_MI, name));
            }
        }
        return term;
    }

    private String[] split(String s) {
        if (s == null || s.isEmpty()) return new String[0];
        
        return s.split("; ");
    }

    private String safeGet(String[] cols, int index) {
        if (cols.length > index) {
            return cols[index];
        } else {
            return "";
        }
    }

    @Override
    protected void processNextLine() {
        super.processNextLine();
    }
}
