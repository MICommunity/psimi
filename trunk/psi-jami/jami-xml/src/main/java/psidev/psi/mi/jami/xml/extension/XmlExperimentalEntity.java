package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Xml implementation of experimental entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/10/13</pre>
 */
@XmlTransient
public class XmlExperimentalEntity extends AbstractXmlEntity<FeatureEvidence> implements ExperimentalEntity{
    private Collection<CvTerm> identificationMethods;
    private Collection<CvTerm> experimentalPreparations;
    private Collection<Confidence> confidences;
    private Collection<Parameter> parameters;

    private ArrayList<CvTerm> experimentalRoles;
    private ArrayList<Organism> hostOrganisms;

    public XmlExperimentalEntity() {
    }

    public XmlExperimentalEntity(Interactor interactor) {
        super(interactor);
    }

    public XmlExperimentalEntity(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public XmlExperimentalEntity(Interactor interactor, Stoichiometry stoichiometry) {
        super(interactor, stoichiometry);
    }

    public XmlExperimentalEntity(Interactor interactor, CvTerm bioRole, Stoichiometry stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    public CvTerm getExperimentalRole() {
        if (this.experimentalRoles == null){
            this.experimentalRoles = new ArrayList<CvTerm>();
        }
        if (this.experimentalRoles.isEmpty()){
           this.experimentalRoles.add(new XmlCvTerm(Participant.UNSPECIFIED_ROLE, Participant.UNSPECIFIED_ROLE_MI));
        }
        return this.experimentalRoles.get(0);
    }

    public void setExperimentalRole(CvTerm expRole) {
        if (this.experimentalRoles == null && expRole != null){
            this.experimentalRoles = new ArrayList<CvTerm>();
            this.experimentalRoles.add(expRole);
        }
        else if (this.experimentalRoles != null){
            if (!this.experimentalRoles.isEmpty() && expRole == null){
                this.experimentalRoles.remove(0);
            }
            else if (expRole != null){
                this.experimentalRoles.remove(0);
                this.experimentalRoles.add(0, expRole);
            }
        }
    }

    public Collection<CvTerm> getIdentificationMethods() {
        if (identificationMethods == null){
            initialiseIdentificationMethods();
        }
        return this.identificationMethods;
    }

    public Collection<CvTerm> getExperimentalPreparations() {
        if (experimentalPreparations == null){
            initialiseExperimentalPreparations();
        }
        return this.experimentalPreparations;
    }

    public Organism getExpressedInOrganism() {
        return (this.hostOrganisms != null && !this.hostOrganisms.isEmpty())? this.hostOrganisms.iterator().next() : null;
    }

    public void setExpressedInOrganism(Organism organism) {
        if (this.hostOrganisms == null && organism != null){
            this.hostOrganisms = new ArrayList<Organism>();
            this.hostOrganisms.add(organism);
        }
        else if (this.hostOrganisms != null){
            if (!this.hostOrganisms.isEmpty() && organism == null){
                this.hostOrganisms.remove(0);
            }
            else if (organism != null){
                this.hostOrganisms.remove(0);
                this.hostOrganisms.add(0, organism);
            }
        }    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null){
            initialiseConfidences();
        }
        return this.confidences;
    }

    public Collection<Parameter> getParameters() {
        if (parameters == null){
            initialiseParameters();
        }
        return this.parameters;
    }

    @Override
    public String toString() {
        return super.toString() + (getExperimentalRole() != null ? ", " + getExperimentalRole().toString() : "") + (getExpressedInOrganism() != null ? ", " + getExpressedInOrganism().toString() : "");
    }

    protected void initialiseExperimentalPreparations() {
        this.experimentalPreparations = new ArrayList<CvTerm>();
    }

    protected void initialiseConfidences() {
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseParameters() {
        this.parameters = new ArrayList<Parameter>();
    }

    protected void initialiseIdentificationMethods(){
        this.identificationMethods = new ArrayList<CvTerm>();
    }
}
