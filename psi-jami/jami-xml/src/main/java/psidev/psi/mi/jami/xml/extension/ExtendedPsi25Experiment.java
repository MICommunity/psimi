package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Organism;

import java.util.List;

/**
 * Extended experiment for PSI-XML 2,5 standards
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Experiment extends Experiment {
    public int getId();
    public void setId(int id);
    public String getShortLabel();
    public void setShortLabel(String name);
    public String getFullName();
    public void setFullName(String name);
    public List<Alias> getAliases();
    public List<Organism> getHostOrganisms();
    public CvTerm getParticipantIdentificationMethod();
    public void setParticipantIdentificationMethod(CvTerm method);
    public CvTerm getFeatureDetectionMethod();
    public void setFeatureDetectionMethod(CvTerm method);
}
