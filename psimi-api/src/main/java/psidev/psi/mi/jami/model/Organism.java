package psidev.psi.mi.jami.model;

import java.util.Set;

/**
 * The organism is defined by a taxonomy identifier, short name, full name. It can have synonyms, xrefs and annotations.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/12</pre>
 */

public interface Organism {

    public String getShortName();
    public void setShortName(String name);

    public String getFullName();
    public void setFullName(String name);

    public int getTaxId();
    public void setTaxId(int taxId);

    public Set<Alias> getAliases();
    public void setAliases(Set<Alias> aliases);

    public CvTerm getCellType();
    public void setCellType(CvTerm cellType);

    public CvTerm getCompartment();
    public void setCompartment(CvTerm compartment);

    public CvTerm getTissue();
    public void setTissue(CvTerm tissue);
}
