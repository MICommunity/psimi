package psidev.psi.mi.jami.xml.model.extension.factory;

import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.xml.PsiXmlType;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
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
                                                PsiXmlType type, PsiXmlVersion version, boolean extended, boolean named){
        switch (complexType){
            case binary:
                return createPsiXmlBinaryWriter(interactionCategory, type, version, extended, named);
            default:
                return createPsiXmlWriter(interactionCategory, type, version, extended, named);
        }
    }

    public InteractionWriter createPsiXmlBinaryWriter(InteractionCategory interactionCategory,
                                                      PsiXmlType type, PsiXmlVersion version, boolean extended, boolean named){
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
                        default:
                            return new psidev.psi.mi.jami.xml.io.writer.compact.extended.CompactXmlBinaryWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlBinaryEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlModelledBinaryWriter();
                        case basic:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.LightExpandedXmlBinaryWriter();
                        default:
                            return new psidev.psi.mi.jami.xml.io.writer.expanded.extended.ExpandedXmlBinaryWriter();
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
                            return new CompactXmlNamedBinaryEvidenceWriter();
                        case basic:
                            return new LightCompactXmlNamedBinaryWriter();
                        default:
                            return new CompactXmlNamedBinaryWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new ExpandedXmlNamedBinaryEvidenceWriter();
                        case modelled:
                            return new ExpandedXmlNamedModelledBinaryWriter();
                        case basic:
                            return new LightExpandedXmlNamedBinaryWriter();
                        default:
                            return new ExpandedXmlNamedBinaryWriter();
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
                        default:
                            return new CompactXmlBinaryWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new ExpandedXmlBinaryEvidenceWriter();
                        case modelled:
                            return new ExpandedXmlModelledBinaryWriter();
                        case basic:
                            return new LightExpandedXmlBinaryWriter();
                        default:
                            return new ExpandedXmlBinaryWriter();
                    }
            }
        }
    }

    public InteractionWriter createPsiXmlWriter(InteractionCategory interactionCategory, PsiXmlType type,
                                                PsiXmlVersion version, boolean extended, boolean named){
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
                            return new CompactXmlNamedEvidenceWriter();
                        case basic:
                            return new LightCompactXmlNamedWriter();
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
                        default:
                            return new ExpandedXmlWriter();
                    }
            }
        }
    }
}
