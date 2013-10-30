package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Xref;

import java.util.List;

/**
 * PSI-XML 2.5 extension of an xref
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/10/13</pre>
 */

public interface ExtendedPsi25Xref extends Xref {

    public String getSecondary();
    public void setSecondary(String secondary);
    public List<Annotation> getAnnotations();
}
