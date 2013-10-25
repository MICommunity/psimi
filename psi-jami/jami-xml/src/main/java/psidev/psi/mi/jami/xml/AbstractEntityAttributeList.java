package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Annotation;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.xml.extension.AbstractXmlEntity;
import psidev.psi.mi.jami.xml.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.extension.XmlStoichiometry;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

/**
 * Abstract class for jaxb attribute list in entity
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/10/13</pre>
 */

public class AbstractEntityAttributeList<T extends AbstractXmlEntity> extends AbstractJAXBList<T, Annotation>{

    public AbstractEntityAttributeList(){
        super();
    }

    @Override
    protected boolean addToSpecificIndex(int index, Annotation element) {
        return processAnnotation(null, element);
    }

    @Override
    protected boolean addElement(Annotation annotation) {
        return processAnnotation(null, annotation);
    }

    protected boolean addAnnotation(Integer index, Annotation annotation) {
        if (index == null){
           return super.add(annotation);
        }
        super.add(index, annotation);
        return true;
    }

    private boolean processAnnotation(Integer index, Annotation annotation) {
        // we have stoichiometry
        if(AnnotationUtils.doesAnnotationHaveTopic(annotation, Annotation.COMMENT_MI, Annotation.COMMENT)
                && annotation.getValue() != null && annotation.getValue().trim().toLowerCase().startsWith(PsiXmlUtils.STOICHIOMETRY_PREFIX)){
            String stc = annotation.getValue().substring(annotation.getValue().indexOf(PsiXmlUtils.STOICHIOMETRY_PREFIX) + PsiXmlUtils.STOICHIOMETRY_PREFIX.length()).trim();

            // we have stoichiometry range
            if (stc.contains("-") && !stc.startsWith("-")){
                String [] stcs = stc.split("-");
                // we recognize the stoichiometry range
                if (stcs.length == 2){
                    try{
                        XmlStoichiometry s = new XmlStoichiometry(Long.parseLong(stcs[0]), Long.parseLong(stcs[1]));
                        s.setSourceLocator((PsiXmLocator)getParent().getSourceLocator());
                        getParent().setStoichiometry(s);
                        return false;
                    }
                    catch (NumberFormatException e){
                        e.printStackTrace();
                        return addAnnotation(index, annotation);
                    }
                }
                // we cannot recognize the stoichiometry range, we add that as a simple annotation
                else {
                    return addAnnotation(index, annotation);
                }
            }
            // simple stoichiometry
            else {
                try{
                    XmlStoichiometry s = new XmlStoichiometry(Long.parseLong(stc));
                    s.setSourceLocator((PsiXmLocator)getParent().getSourceLocator());
                    getParent().setStoichiometry(s);
                    return false;
                }
                // not a number, keep the annotation as annotation
                catch (NumberFormatException e){
                    e.printStackTrace();
                    return addAnnotation(index, annotation);
                }
            }
        }
        else{
            return addAnnotation(null, annotation);
        }
    }
}
