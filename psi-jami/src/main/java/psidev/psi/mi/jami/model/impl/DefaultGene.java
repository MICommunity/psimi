package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.ExternalIdentifier;
import psidev.psi.mi.jami.model.Gene;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

/**
 * Default implementation for gene
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/01/13</pre>
 */

public class DefaultGene extends DefaultInteractor implements Gene {



    public DefaultGene(String name) {
        super(name, CvTermFactory.createGeneInteractorType());
    }

    public DefaultGene(String name, String fullName) {
        super(name, fullName, CvTermFactory.createGeneInteractorType());
    }

    public DefaultGene(String name, Organism organism) {
        super(name, CvTermFactory.createGeneInteractorType(), organism);
    }

    public DefaultGene(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), organism);
    }

    public DefaultGene(String name, ExternalIdentifier uniqueId) {
        super(name, CvTermFactory.createGeneInteractorType(), uniqueId);
    }

    public DefaultGene(String name, String fullName, ExternalIdentifier uniqueId) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), uniqueId);
    }

    public DefaultGene(String name, Organism organism, ExternalIdentifier uniqueId) {
        super(name, CvTermFactory.createGeneInteractorType(), organism, uniqueId);
    }

    public DefaultGene(String name, String fullName, Organism organism, ExternalIdentifier uniqueId) {
        super(name, fullName, CvTermFactory.createGeneInteractorType(), organism, uniqueId);
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
}
