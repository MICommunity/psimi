package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Xref;

/**
 * Xml implementation of a Gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */

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

    public String getEnsembl() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEnsembl(String ac) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEnsembleGenome() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEnsemblGenome(String ac) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getEntrezGeneId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEntrezGeneId(String id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRefseq() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRefseq(String ac) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void createDefaultInteractorType() {
        setInteractorType(new XmlCvTerm(Gene.GENE, Gene.GENE_MI));
    }
}
