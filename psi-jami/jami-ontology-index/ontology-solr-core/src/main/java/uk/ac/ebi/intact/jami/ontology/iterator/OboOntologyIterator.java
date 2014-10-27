/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
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

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.obo.OboOntologyTermFetcher;
import psidev.psi.mi.jami.commons.MIFileUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Reads an OBO File and creates the <code>OntologyDocument<code> iterator.
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: OboOntologyIterator.java 19210 2013-05-16 15:52:25Z noedelta $
 */
public class OboOntologyIterator implements OntologyIterator {

    private CvTerm ontology;

    private OboOntologyTermFetcher oboFetcher;

    private Set<String> processedIds;
    private OntologyTerm currentTerm;
    private Iterator<OntologyTerm> termIterator;

    private Iterator<OntologyTerm> childIterator;

    public OboOntologyIterator(CvTerm ontology, URL path) throws IOException {
        if (ontology == null){
            throw new IllegalArgumentException("The ontology database cannot be null");
        }
        this.ontology = ontology;

        InputStream stream = path.openStream();
        try{
            File tempFile = MIFileUtils.storeAsTemporaryFile(stream, "ontology_"+System.currentTimeMillis(), ".obo");

            this.oboFetcher = new OboOntologyTermFetcher(ontology, tempFile.getAbsolutePath());
            tempFile.delete();
        }
        finally {
            stream.close();
        }

        this.processedIds = new HashSet<String>();
        try {
            this.termIterator = this.oboFetcher.fetchRootTerms(ontology).iterator();

            if (this.termIterator.hasNext()){
                this.currentTerm = this.termIterator.next();
                this.childIterator = getAllChildren(this.currentTerm).iterator();
                registerProcessedIds();
            }
        } catch (BridgeFailedException e) {
            throw new IOException("Cannot read the OBO file", e);
        }
    }

    private void registerProcessedIds() {
        for (Xref id : this.currentTerm.getIdentifiers()){
            this.processedIds.add(id.getId());
        }
    }

    protected void processNextLine(){
        if (this.childIterator != null && this.childIterator.hasNext()){
            this.currentTerm = this.childIterator.next();
        }
        else if (this.termIterator != null && this.termIterator.hasNext()){
            this.currentTerm = this.termIterator.next();
            this.childIterator = getAllChildren(this.currentTerm).iterator();
        }
        else{
            this.currentTerm = null;
        }
    }

    public boolean hasNext() {
        return currentTerm != null;
    }

    public OntologyTerm next() {
        OntologyTerm current = this.currentTerm;

        processNextLine();

        return current;
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot be removed");
    }


    protected Set<OntologyTerm> getAllChildren(OntologyTerm term) {
        Collection<OntologyTerm> allChildren = term.getChildren();
        Set<OntologyTerm> allChildrenClone = new HashSet<OntologyTerm>(allChildren);
        for (OntologyTerm termChild : allChildrenClone){
            allChildren.addAll(getAllChildren(termChild));
        }
        return allChildrenClone;
    }

}
