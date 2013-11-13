package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.*;

import java.util.List;

/**
 * Extended experiment for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Experiment extends NamedExperiment {
    public int getId();
    public void setId(int id);
    public List<Organism> getHostOrganisms();
    public CvTerm getParticipantIdentificationMethod();
    public void setParticipantIdentificationMethod(CvTerm method);
    public CvTerm getFeatureDetectionMethod();
    public void setFeatureDetectionMethod(CvTerm method);
}
