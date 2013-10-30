package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Experiment;

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
}
