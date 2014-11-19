package psidev.psi.mi.jami.xml.model.extension.factory;

import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.io.writer.compact.*;
import psidev.psi.mi.jami.xml.io.writer.compact.extended.LightCompactXmlBinaryWriter;
import psidev.psi.mi.jami.xml.io.writer.expanded.*;

/**
 * Factory for creating a PSI-XML writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public class PsiXmlWriterFactory {

    private static final PsiXmlWriterFactory instance = new PsiXmlWriterFactory();

    private PsiXmlWriterFactory(){
    }

    public static PsiXmlWriterFactory getInstance() {
        return instance;
    }

    public InteractionWriter createPsiXmlWriter(InteractionCategory interactionCategory, ComplexType complexType,
                                                PsiXmlType type, boolean extended, boolean named){
        switch (complexType){
            case binary:
                return createPsiXmlBinaryWriter(interactionCategory, type, extended, named);
            default:
                return createPsiXmlWriter(interactionCategory, type, extended, named);
        }
    }

    public InteractionWriter createPsiXmlBinaryWriter(InteractionCategory interactionCategory,
                                                      PsiXmlType type, boolean extended, boolean named){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.mixed;
        }

        if (extended){
            switch (type){
                case compact:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlBinaryEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlModelledBinaryWriter();
                        case basic:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.LightCompactXmlBinaryWriter();
                        case mixed:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlBinaryWriter();
                        default:
                            throw new IllegalArgumentException("Cannot find a XML binary writer for interaction category: "+interactionCategory);
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlBinaryEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlModelledBinaryWriter();
                        case basic:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.LightExpandedXmlBinaryWriter();
                        case mixed:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlBinaryWriter();
                        default:
                            throw new IllegalArgumentException("Cannot find a XML binary writer for interaction category: "+interactionCategory);
                    }
            }
        }
        else if (named){
            switch (type){
                case compact:
                    switch (interactionCategory){
                        case evidence:
                            return new CompactXmlNamedBinaryEvidenceWriter();
                        case modelled:
                            return new CompactXmlNamedModelledBinaryWriter();
                        case basic:
                            return new LightCompactXmlNamedBinaryWriter();
                        case mixed:
                            return new CompactXmlNamedBinaryWriter();
                        default:
                            throw new IllegalArgumentException("Cannot find a XML binary writer for interaction category: "+interactionCategory);
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new ExpandedXmlNamedBinaryEvidenceWriter();
                        case modelled:
                            return new ExpandedXmlNamedModelledBinaryWriter();
                        case basic:
                            return new LightExpandedXmlNamedBinaryWriter();
                        case mixed:
                            return new ExpandedXmlNamedBinaryWriter();
                        default:
                            throw new IllegalArgumentException("Cannot find a XML binary writer for interaction category: "+interactionCategory);
                    }
            }
        }
        else{
            switch (type){
                case compact:
                    switch (interactionCategory){
                        case evidence:
                            return new CompactXmlBinaryEvidenceWriter();
                        case modelled:
                            return new CompactXmlModelledBinaryWriter();
                        case basic:
                            return new LightCompactXmlBinaryWriter();
                        case mixed:
                            return new CompactXmlBinaryWriter();
                        default:
                            throw new IllegalArgumentException("Cannot find a XML binary writer for interaction category: "+interactionCategory);
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new ExpandedXmlBinaryEvidenceWriter();
                        case modelled:
                            return new ExpandedXmlModelledBinaryWriter();
                        case basic:
                            return new LightExpandedXmlBinaryWriter();
                        case mixed:
                            return new ExpandedXmlBinaryWriter();
                        default:
                            throw new IllegalArgumentException("Cannot find a XML binary writer for interaction category: "+interactionCategory);
                    }
            }
        }
    }

    public InteractionWriter createPsiXmlWriter(InteractionCategory interactionCategory, PsiXmlType type,
                                                boolean extended, boolean named){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.mixed;
        }

        if (extended){
            switch (type){
                case compact:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlModelledWriter();
                        case basic:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.LightCompactXmlWriter();
                        case complex:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlComplexWriter();
                        default:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlModelledWriter();
                        case basic:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.LightExpandedXmlWriter();
                        case complex:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlComplexWriter();
                        default:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlWriter();
                    }
            }
        }
        else if (named){
            switch (type){
                case compact:
                    switch (interactionCategory){
                        case evidence:
                            return new CompactXmlNamedEvidenceWriter();
                        case modelled:
                            return new CompactXmlNamedModelledWriter();
                        case basic:
                            return new LightCompactXmlNamedWriter();
                        case complex:
                            return new CompactXmlComplexWriter();
                        default:
                            return new CompactXmlNamedWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new ExpandedXmlNamedEvidenceWriter();
                        case modelled:
                            return new ExpandedXmlNamedModelledWriter();
                        case basic:
                            return new LightExpandedXmlNamedWriter();
                        case complex:
                            return new ExpandedXmlComplexWriter();
                        default:
                            return new ExpandedXmlNamedWriter();
                    }
            }
        }
        else{
            switch (type){
                case compact:
                    switch (interactionCategory){
                        case evidence:
                            return new CompactXmlEvidenceWriter();
                        case modelled:
                            return new CompactXmlModelledWriter();
                        case basic:
                            return new LightCompactXmlWriter();
                        case complex:
                            return new CompactXmlComplexWriter();
                        default:
                            return new CompactXmlWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new ExpandedXmlEvidenceWriter();
                        case modelled:
                            return new ExpandedXmlModelledWriter();
                        case basic:
                            return new LightExpandedXmlWriter();
                        case complex:
                            return new ExpandedXmlComplexWriter();
                        default:
                            return new ExpandedXmlWriter();
                    }
            }
        }
    }
}
