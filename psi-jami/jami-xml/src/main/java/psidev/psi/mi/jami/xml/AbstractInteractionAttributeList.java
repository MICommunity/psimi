package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.model.Checksum;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.xml.extension.AbstractXmlInteraction;
import psidev.psi.mi.jami.xml.extension.XmlChecksum;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

/**
 * Abstract list of attributes for an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/10/13</pre>
 */

public abstract class AbstractInteractionAttributeList<T extends AbstractXmlInteraction> extends AbstractJAXBList<T, Annotation> {
    @Override
    protected boolean addToSpecificIndex(int index, Annotation element) {
        return processAnnotation(index, element);
    }

    @Override
    protected boolean addElement(Annotation annotation) {
        return processAnnotation(null, annotation);
    }

    private boolean processAnnotation(Integer index, Annotation annotation) {
        if (AnnotationUtils.doesAnnotationHaveTopic(annotation, Checksum.CHECKSUM_MI, Checksum.CHECKUM)
                || AnnotationUtils.doesAnnotationHaveTopic(annotation, null, Checksum.RIGID)){
            XmlChecksum checksum = new XmlChecksum(annotation.getTopic(), annotation.getValue() != null ? annotation.getValue() : PsiXmlUtils.UNSPECIFIED);
            checksum.setSourceLocator(((FileSourceContext)annotation).getSourceLocator());
            getParent().getChecksums().add(checksum);
            return false;
        }
        else {
            return addAnnotation(index, annotation);
        }
    }

    private boolean addAnnotation(Integer index, Annotation annotation) {
        if (index == null){
            return super.add(annotation);
        }
        super.add(index, annotation);
        return true;
    }
}
