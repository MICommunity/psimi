package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Xml implementation of a Gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "")
public class XmlGene extends XmlInteractor implements Gene{

    public XmlGene() {
    }

    public XmlGene(String name) {
        super(name);
    }

    public XmlGene(String name, String fullName) {
        super(name, fullName);
    }

    public XmlGene(String name, Organism organism) {
        super(name, organism);
    }

    public XmlGene(String name, String fullName, Organism organism) {
        super(name, fullName, organism);
    }

    public XmlGene(String name, Xref uniqueId) {
        super(name, uniqueId);
    }

    public XmlGene(String name, String fullName, Xref uniqueId) {
        super(name, fullName, uniqueId);
    }

    public XmlGene(String name, Organism organism, Xref uniqueId) {
        super(name, organism, uniqueId);
    }

    public XmlGene(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, organism, uniqueId);
    }

    public XmlGene(String name, String fullName, String ensembl) {
        super(name, fullName);

        if (ensembl != null){
            setEnsembl(ensembl);
        }
    }

    public XmlGene(String name, Organism organism, String ensembl) {
        super(name, organism);
        if (ensembl != null){
            setEnsembl(ensembl);
        }
    }

    public XmlGene(String name, String fullName, Organism organism, String ensembl) {
        super(name, fullName, organism);
        if (ensembl != null){
            setEnsembl(ensembl);
        }
    }

    @Override
    public void setXref(InteractorXrefContainer value) {
        if (value == null){
            this.xrefContainer = null;
        }
        else if (this.xrefContainer == null){
            this.xrefContainer = new GeneXrefContainer();
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
        else {
            this.xrefContainer.setPrimaryRef(value.getPrimaryRef());
            this.xrefContainer.getSecondaryRefs().clear();
            this.xrefContainer.getSecondaryRefs().addAll(value.getSecondaryRefs());
        }
    }

    @Override
    public void initialiseXrefContainer() {
        this.xrefContainer = new GeneXrefContainer();
    }

    @XmlTransient
    public String getEnsembl() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((GeneXrefContainer)xrefContainer).getEnsembl();
    }

    public void setEnsembl(String ac) {
        ((GeneXrefContainer)xrefContainer).setEnsembl(ac);
    }

    @XmlTransient
    public String getEnsembleGenome() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((GeneXrefContainer)xrefContainer).getEnsembleGenome();
    }

    public void setEnsemblGenome(String ac) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((GeneXrefContainer)xrefContainer).setEnsemblGenome(ac);
    }

    @XmlTransient
    public String getEntrezGeneId() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((GeneXrefContainer)xrefContainer).getEntrezGeneId();
    }

    public void setEntrezGeneId(String id) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((GeneXrefContainer)xrefContainer).setEntrezGeneId(id);
    }

    @XmlTransient
    public String getRefseq() {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        return ((GeneXrefContainer)xrefContainer).getRefseq();
    }

    public void setRefseq(String ac) {
        if (xrefContainer == null){
            initialiseXrefContainer();
        }
        ((GeneXrefContainer)xrefContainer).setRefseq(ac);
    }

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(Gene.GENE, Gene.GENE_MI));
    }
}
