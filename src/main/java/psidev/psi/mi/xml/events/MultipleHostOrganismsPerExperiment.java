package psidev.psi.mi.xml.events;

import psidev.psi.mi.jami.datasource.DefaultFileSourceContext;
import psidev.psi.mi.xml.model.ExperimentDescription;
import psidev.psi.mi.xml.model.Organism;

import java.io.Serializable;
import java.util.Set;

/**
 * This event is fired when having multiple host organisms in one experiment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/03/13</pre>
 */

public class MultipleHostOrganismsPerExperiment extends DefaultFileSourceContext implements Serializable {

    private ExperimentDescription experiment;
    private Set<Organism> hostOrganisms;
    private String message;

    public MultipleHostOrganismsPerExperiment(ExperimentDescription exp, Set<Organism> values, String message){
        this.hostOrganisms = values;
        this.message = message;
        this.experiment = exp;
    }

    public Set<Organism> getMultipleHostOrganisms() {
        return hostOrganisms;
    }

    public String getMessage() {
        return message;
    }

    public ExperimentDescription getExperiment() {
        return experiment;
    }
}
