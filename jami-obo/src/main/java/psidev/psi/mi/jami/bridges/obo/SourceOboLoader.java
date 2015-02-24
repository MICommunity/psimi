package psidev.psi.mi.jami.bridges.obo;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Source;
import psidev.psi.mi.jami.model.Xref;
import psidev.psi.mi.jami.model.impl.DefaultSource;
import psidev.psi.mi.jami.utils.AnnotationUtils;

/**
 * Implementation of Obo loader for sources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/07/13</pre>
 */

public class SourceOboLoader extends AbstractOboLoader<Source> {
    public SourceOboLoader(CvTerm database) {
        super(database);
    }

    public SourceOboLoader(String databaseName) {
        super(databaseName);
    }

    @Override
    protected Source instantiateNewTerm(String name, Xref identity) {
        return new DefaultSource("", name, identity);
    }

    @Override
    protected void createDefinitionFor(String def, Source term) {
        term.getAnnotations().add(AnnotationUtils.createAnnotation("definition", def));
    }
}
