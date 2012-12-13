package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.model.*;

import java.util.Collection;

/**
 * An Entry contains a set of interactions, experiments, publications coming from a single source.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/12/12</pre>
 */

public interface Entry {

    public Source getSource();
    public void setSource(Source source);

    public Collection<Publication> getPublications();

    public Collection<Experiment> getExperiments();

    public Collection<ExperimentalInteraction> getExperimentalInteractions();
    public Collection<ModelledInteraction> getModelledInteractions();
    public Collection<CooperativeInteraction> getCooperativeInteractions();
    public Collection<AllostericInteraction> getAllostericInteractions();
    public Collection<? extends Interaction> getAllInteractions();

    public Collection<Interactor> getAllInteractors();
    public Collection<Protein> getProteins();
    public Collection<NucleicAcid> getNucleicAcids();
    public Collection<Gene> getGenes();
    public Collection<BioactiveEntity> getBioactiveEntities();
    public Collection<Complex> getComplexes();

    public Collection<Organism> getOrganisms();
    public Collection<CvTerm> getCvTerms();
}
