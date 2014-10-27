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
package uk.ac.ebi.intact.jami.ontology;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a document in the lucene index for ontologies. It is just a convenience class that wraps the
 * access to documents by using the appropriate document fields. 
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: OntologyDocument.java 17592 2011-12-12 16:02:12Z brunoaranda $
 */
public final class OntologyDocument {

    private String ontology;
    private String parentId;
    private String parentName;
    private String childId;
    private String childName;
    private String relationshipType;
    private boolean cyclicRelationship;
    private Collection<String> childSynonyms;
    private Collection<String> parentSynonyms;

    public OntologyDocument(String ontology,
                            String parentId,
                            String parentName,
                            String childId,
                            String childName,
                            String relationshipType,
                            boolean cyclicRelationship) {
        this.ontology = ontology;
        this.parentId = parentId;
        this.parentName = parentName;
        this.childId = childId;
        this.childName = childName;
        this.relationshipType = relationshipType;
        this.cyclicRelationship = cyclicRelationship;
        
        this.childSynonyms = new HashSet<String>();
        this.parentSynonyms = new HashSet<String>();
    }

    public String getOntology() {
        return ontology;
    }

    public String getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public String getChildId() {
        return childId;
    }

    public String getChildName() {
        return childName;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public boolean isCyclicRelationship() {
        return cyclicRelationship;
    }

    public boolean isRoot() {
        return (parentId == null);
    }

    public boolean isLeaf() {
        return (childId == null);
    }
    
    public boolean addChildSynonym(String synonym) {
        return childSynonyms.add(synonym);
    }

    public boolean addParentSynonym(String synonym) {
        return parentSynonyms.add(synonym);
    }
    
    public void addAllChildSynonyms(Collection<String> synonyms) {
        for (String synonym : synonyms) {
            addChildSynonym(synonym);
        }
    }

    public void addAllParentSynonyms(Collection<String> synonyms) {
        for (String synonym : synonyms) {
            addParentSynonym(synonym);
        }
    }

    public void addAllChildSynonyms(String[] synonyms) {
        for (String synonym : synonyms) {
            addChildSynonym(synonym);
        }
    }

    public void addAllParentSynonyms(String[] synonyms) {
        for (String synonym : synonyms) {
            addParentSynonym(synonym);
        }
    }

    public Collection<String> getChildSynonyms() {
        return new HashSet<String> (childSynonyms);
    }

    public Collection<String> getParentSynonyms() {
        return new HashSet<String> (parentSynonyms);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OntologyDocument that = (OntologyDocument) o;

        if (childId != null ? !childId.equals(that.childId) : that.childId != null) return false;
        if (ontology != null ? !ontology.equals(that.ontology) : that.ontology != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ontology != null ? ontology.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + (childId != null ? childId.hashCode() : 0);
        result = 31 * result + (childName != null ? childName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OntologyDocument");
        sb.append("{ontology='").append(ontology).append('\'');
        sb.append(", parentId='").append(parentId).append('\'');
        sb.append(", parentName='").append(parentName).append('\'');
        sb.append(", childId='").append(childId).append('\'');
        sb.append(", childName='").append(childName).append('\'');
        sb.append(", relationshipType='").append(relationshipType).append('\'');
        sb.append(", cyclicRelationship=").append(cyclicRelationship);
        sb.append('}');
        return sb.toString();
    }



}
