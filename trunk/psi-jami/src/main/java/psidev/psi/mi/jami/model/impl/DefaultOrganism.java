package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.comparator.organism.UnambiguousOrganismComparator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for organism
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/01/13</pre>
 */

public class DefaultOrganism implements Organism, Serializable {

    private String commonName;
    private String scientificName;
    private int taxId;
    private Collection<Alias> aliases;
    private CvTerm cellType;
    private CvTerm compartment;
    private CvTerm tissue;

    public DefaultOrganism(int taxId){
        if (taxId == -1 || taxId == -2 || taxId == -3 || taxId == -4 || taxId > 0){
            this.taxId = taxId;
        }
        else {
            throw new IllegalArgumentException("The taxId "+taxId+" is not a valid taxid. Only NCBI taxid or -1, -2, -3, -4 are valid taxids.");
        }
    }

    public DefaultOrganism(int taxId, String commonName){
        this(taxId);
        this.commonName = commonName;
    }

    public DefaultOrganism(int taxId, String commonName, String scientificName){
        this(taxId, commonName);
        this.scientificName = scientificName;
    }

    public DefaultOrganism(int taxId, CvTerm cellType, CvTerm tissue, CvTerm compartment){
        this(taxId);
        this.cellType = cellType;
        this.tissue = tissue;
        this.compartment = compartment;
    }

    public DefaultOrganism(int taxId, String commonName, CvTerm cellType, CvTerm tissue, CvTerm compartment){
        this(taxId, commonName);
        this.cellType = cellType;
        this.tissue = tissue;
        this.compartment = compartment;
    }

    public DefaultOrganism(int taxId, String commonName, String scientificName, CvTerm cellType, CvTerm tissue, CvTerm compartment){
        this(taxId, commonName, scientificName);
        this.cellType = cellType;
        this.tissue = tissue;
        this.compartment = compartment;
    }

    protected void initialiseAliases(){
        this.aliases = new ArrayList<Alias>();
    }

    protected void initialiseAliasesWith(Collection<Alias> aliases){
        if (aliases == null){
            this.aliases = Collections.EMPTY_LIST;
        }
        else {
            this.aliases = aliases;
        }
    }

    public String getCommonName() {
        return this.commonName;
    }

    public void setCommonName(String name) {
        this.commonName = name;
    }

    public String getScientificName() {
        return this.scientificName;
    }

    public void setScientificName(String name) {
        this.scientificName = name;
    }

    public int getTaxId() {
        return this.taxId;
    }

    public Collection<Alias> getAliases() {
        if (aliases == null){
            initialiseAliases();
        }
        return this.aliases;
    }

    public CvTerm getCellType() {
        return this.cellType;
    }

    public void setCellType(CvTerm cellType) {
        this.cellType = cellType;
    }

    public CvTerm getCompartment() {
        return this.compartment;
    }

    public void setCompartment(CvTerm compartment) {
        this.compartment = compartment;
    }

    public CvTerm getTissue() {
        return this.tissue;
    }

    public void setTissue(CvTerm tissue) {
        this.tissue = tissue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Organism)){
            return false;
        }

        return UnambiguousOrganismComparator.areEquals(this, (Organism) o);
    }

    @Override
    public int hashCode() {
        return UnambiguousOrganismComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return taxId + "(" + (commonName != null ? commonName : "-" )+")";
    }
}
