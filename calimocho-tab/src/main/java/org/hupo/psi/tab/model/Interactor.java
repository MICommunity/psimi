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
package org.hupo.psi.tab.model;


import org.hupo.psi.calimocho.model.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class Interactor {

    private List<Field> identifiers;
    private List<Field> alternativeIdentifiers;
    private List<Field> aliases;
    private Field taxid;
    private List<Field> biologicalRoles;
    private List<Field> experimentalRoles;
    private Field interactorType;
    private List<Field> xrefs;
    private List<Field> annotations;
    private List<Field> parameters;
    private Field checksum;

    public Interactor() {
        identifiers = new ArrayList<Field>();
        alternativeIdentifiers = new ArrayList<Field>();
        aliases = new ArrayList<Field>();
        biologicalRoles = new ArrayList<Field>();
        experimentalRoles = new ArrayList<Field>();
        xrefs = new ArrayList<Field>();
        annotations = new ArrayList<Field>();
        parameters = new ArrayList<Field>();
    }

    public List<Field> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<Field> identifiers) {
        this.identifiers = identifiers;
    }

    public List<Field> getAlternativeIdentifiers() {
        return alternativeIdentifiers;
    }

    public void setAlternativeIdentifiers(List<Field> alternativeIdentifiers) {
        this.alternativeIdentifiers = alternativeIdentifiers;
    }

    public List<Field> getAliases() {
        return aliases;
    }

    public void setAliases(List<Field> aliases) {
        this.aliases = aliases;
    }

    public Field getTaxid() {
        return taxid;
    }

    public void setTaxid(Field taxid) {
        this.taxid = taxid;
    }

    public List<Field> getBiologicalRoles() {
        return biologicalRoles;
    }

    public void setBiologicalRoles(List<Field> biologicalRoles) {
        this.biologicalRoles = biologicalRoles;
    }

    public List<Field> getExperimentalRoles() {
        return experimentalRoles;
    }

    public void setExperimentalRoles(List<Field> experimentalRoles) {
        this.experimentalRoles = experimentalRoles;
    }

    public Field getInteractorType() {
        return interactorType;
    }

    public void setInteractorType(Field interactorType) {
        this.interactorType = interactorType;
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

    public List<Field> getParameters() {
        return parameters;
    }

    public void setParameters(List<Field> parameters) {
        this.parameters = parameters;
    }

    public Field getChecksum() {
        return checksum;
    }

    public void setChecksum(Field checksum) {
        this.checksum = checksum;
    }
}
