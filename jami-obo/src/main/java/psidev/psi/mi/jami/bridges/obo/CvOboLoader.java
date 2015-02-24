package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.utils.AnnotationUtils;

/**
 * Basic implementation of Obo loader
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class CvOboLoader extends AbstractOboLoader<CvTerm> {
    public CvOboLoader(CvTerm database) {
        super(database);
    }

    public CvOboLoader(String databaseName) {
        super(databaseName);
    }

    @Override
    protected CvTerm instantiateNewTerm(String name, Xref identity) {
        return new DefaultCvTerm("", name, identity);
    }

    @Override
    protected void createDefinitionFor(String def, CvTerm term) {
        term.getAnnotations().add(AnnotationUtils.createAnnotation("definition", def));
    }
}
