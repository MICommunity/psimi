/**
 * Copyright 2010 The European Bioinformatics Institute, and others.
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
package org.hupo.psi.calimocho.tab.model;

import org.hupo.psi.calimocho.model.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class BinaryInteraction {

    private Interactor interactorA;
    private Interactor interactorB;
    private List<Field> detectionMethods;
    private List<Field> authors;
    private List<Field> publications;
    private Field interactionType;
    private List<Field> sources;
    private List<Field> identifiers;
    private List<Field> confidences;
    private Field expansionMethod;
    private List<Field> xrefs;
    private List<Field> annotations;
    private List<Field> hostOrganisms;
    private List<Field> parameters;
    private Date creationDate;
    private Date updateDate;
    private Field checksum;
    private boolean negative;

    public BinaryInteraction() {
        detectionMethods = new ArrayList<Field>();
        authors = new ArrayList<Field>();
        publications = new ArrayList<Field>();
        sources = new ArrayList<Field>();
        identifiers = new ArrayList<Field>();
        confidences = new ArrayList<Field>();
        xrefs = new ArrayList<Field>();
        annotations = new ArrayList<Field>();
        hostOrganisms = new ArrayList<Field>();
        parameters = new ArrayList<Field>();
    }

    public BinaryInteraction(Interactor interactorA, Interactor interactorB) {
        this();
        this.interactorA = interactorA;
        this.interactorB = interactorB;
    }

    public Interactor getInteractorA() {
        return interactorA;
    }

    public void setInteractorA(Interactor interactorA) {
        this.interactorA = interactorA;
    }

    public Interactor getInteractorB() {
        return interactorB;
    }

    public void setInteractorB(Interactor interactorB) {
        this.interactorB = interactorB;
    }

    public List<Field> getDetectionMethods() {
        return detectionMethods;
    }

    public void setDetectionMethods(List<Field> detectionMethods) {
        this.detectionMethods = detectionMethods;
    }

    public List<Field> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Field> authors) {
        this.authors = authors;
    }

    public List<Field> getPublications() {
        return publications;
    }

    public void setPublications(List<Field> publications) {
        this.publications = publications;
    }

    public Field getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(Field interactionType) {
        this.interactionType = interactionType;
    }

    public List<Field> getSources() {
        return sources;
    }

    public void setSources(List<Field> sources) {
        this.sources = sources;
    }

    public List<Field> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Field> identifiers) {
        this.identifiers = identifiers;
    }

    public List<Field> getConfidences() {
        return confidences;
    }

    public void setConfidences(List<Field> confidences) {
        this.confidences = confidences;
    }

    public Field getExpansionMethod() {
        return expansionMethod;
    }

    public void setExpansionMethod(Field expansionMethod) {
        this.expansionMethod = expansionMethod;
    }

    public List<Field> getXrefs() {
        return xrefs;
    }

    public void setXrefs(List<Field> xrefs) {
        this.xrefs = xrefs;
    }

    public List<Field> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Field> annotations) {
        this.annotations = annotations;
    }

    public List<Field> getHostOrganisms() {
        return hostOrganisms;
    }

    public void setHostOrganisms(List<Field> hostOrganisms) {
        this.hostOrganisms = hostOrganisms;
    }

    public List<Field> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field> parameters) {
        this.parameters = parameters;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Field getChecksum() {
        return checksum;
    }

    public void setChecksum(Field checksum) {
        this.checksum = checksum;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }
}
