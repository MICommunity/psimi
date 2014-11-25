package psidev.psi.mi.jami.xml.model.extension.factory;

import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.model.extension.datasource.XmlEvidenceSource;
import psidev.psi.mi.jami.xml.model.extension.datasource.*;

/**
 * Factory for creating a Psi-XML datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public class PsiXmlDataSourceFactory {

    private static final PsiXmlDataSourceFactory instance = new PsiXmlDataSourceFactory();

    private PsiXmlDataSourceFactory(){
    }

    public static PsiXmlDataSourceFactory getInstance() {
        return instance;
    }

    public PsiXmlStreamSource createPsiXmlDataSource(InteractionCategory interactionCategory, ComplexType complexType,
                                                     boolean streaming){
        switch (complexType){
            case binary:
                return createPsiXmlBinaryDataSource(interactionCategory, streaming);
            default:
                return createPsiXmlDataSource(interactionCategory, streaming);
        }
    }

    public PsiXmlStreamSource createPsiXmlBinaryDataSource(InteractionCategory interactionCategory, boolean streaming){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.mixed;
        }

        if (streaming){
            switch (interactionCategory){
                case evidence:
                    return new XmlBinaryEvidenceStreamSource();
                case modelled:
                    return new XmlModelledBinaryStreamSource();
                case basic:
                    return new LightXmlBinaryStreamSource();
                case mixed:
                    return new XmlBinaryStreamSource();
                default:
                    throw new IllegalArgumentException("Cannot find a XML binary source for interaction category: "+interactionCategory);
            }
        }
        else{
            switch (interactionCategory){
                case evidence:
                    return new XmlBinaryEvidenceSource();
                case modelled:
                    return new XmlModelledBinarySource();
                case basic:
                    return new LightXmlBinarySource();
                case mixed:
                    return new XmlBinarySource();
                default:
                    throw new IllegalArgumentException("Cannot find a XML binary source for interaction category: "+interactionCategory);
            }
        }
    }

    public PsiXmlStreamSource createPsiXmlDataSource(InteractionCategory interactionCategory, boolean streaming){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.mixed;
        }

        if (streaming){
            switch (interactionCategory){
                case evidence:
                    return new XmlEvidenceStreamSource();
                case modelled:
                    return new XmlModelledStreamSource();
                case basic:
                    return new LightXmlStreamSource();
                case mixed:
                    return new XmlStreamSource();
                case complex:
                    return new XmlComplexStreamSource();
                default:
                    throw new IllegalArgumentException("Cannot find a XML source for interaction category: "+interactionCategory);            }
        }
        else{
            switch (interactionCategory){
                case evidence:
                    return new XmlEvidenceSource();
                case modelled:
                    return new XmlModelledSource();
                case basic:
                    return new LightXmlSource();
                case mixed:
                    return new XmlSource();
                case complex:
                    return new XmlComplexSource();
                default:
                    throw new IllegalArgumentException("Cannot find a XML source for interaction category: "+interactionCategory);            }
        }
    }
}
