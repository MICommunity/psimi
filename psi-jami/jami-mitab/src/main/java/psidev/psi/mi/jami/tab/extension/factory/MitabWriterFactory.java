package psidev.psi.mi.jami.tab.extension.factory;

import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.io.writer.*;

/**
 * Factory for creating a mitab writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public class MitabWriterFactory {

    private static final MitabWriterFactory instance = new MitabWriterFactory();

    private MitabWriterFactory(){
    }

    public static MitabWriterFactory getInstance() {
        return instance;
    }

    public InteractionWriter createMitabWriter(InteractionCategory interactionCategory, ComplexType complexType,
                                                  MitabVersion version, boolean extended){
        switch (complexType){
            case binary:
                return createMitabBinaryWriter(interactionCategory, version, extended);
            default:
                return createMitabWriter(interactionCategory, version, extended);
        }
    }

    public InteractionWriter createMitabBinaryWriter(InteractionCategory interactionCategory, MitabVersion version, boolean extended){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.mixed;
        }

        if (extended){
            switch (version){
                case v2_5:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab25BinaryEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledBinaryWriter();
                        case basic:
                            return new LightMitab25BinaryWriter();
                        default:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab25BinaryWriter();
                    }
                case v2_6:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab26BinaryEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab26ModelledBinaryWriter();
                        case basic:
                            return new LightMitab25BinaryWriter();
                        default:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab26BinaryWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab27BinaryEvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab27ModelledBinaryWriter();
                        case basic:
                            return new LightMitab27BinaryWriter();
                        default:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab27BinaryWriter();
                    }
            }
        }
        else{
            switch (version){
                case v2_5:
                    switch (interactionCategory){
                        case evidence:
                            return new Mitab25BinaryEvidenceWriter();
                        case modelled:
                            return new Mitab25ModelledBinaryWriter();
                        case basic:
                            return new LightMitab25BinaryWriter();
                        default:
                            return new Mitab25BinaryWriter();
                    }
                case v2_6:
                    switch (interactionCategory){
                        case evidence:
                            return new Mitab26BinaryEvidenceWriter();
                        case modelled:
                            return new Mitab26ModelledBinaryWriter();
                        case basic:
                            return new LightMitab25BinaryWriter();
                        default:
                            return new Mitab26BinaryWriter();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new Mitab27BinaryEvidenceWriter();
                        case modelled:
                            return new Mitab27ModelledBinaryWriter();
                        case basic:
                            return new LightMitab27BinaryWriter();
                        default:
                            return new Mitab27BinaryWriter();
                    }
            }
        }
    }

    public InteractionWriter createMitabWriter(InteractionCategory interactionCategory, MitabVersion version, boolean extended){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.mixed;
        }

        if (extended){
            switch (version){
                case v2_5:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab25EvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab25ModelledWriter();
                        case basic:
                            return new LightMitab25Writer();
                        default:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab25Writer();
                    }
                case v2_6:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab26EvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab26ModelledWriter();
                        case basic:
                            return new LightMitab25Writer();
                        default:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab26Writer();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab27EvidenceWriter();
                        case modelled:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab27ModelledWriter();
                        case basic:
                            return new LightMitab27Writer();
                        default:
                            return new psidev.psi.mi.jami.tab.io.writer.extended.Mitab27Writer();
                    }
            }
        }
        else{
            switch (version){
                case v2_5:
                    switch (interactionCategory){
                        case evidence:
                            return new Mitab25EvidenceWriter();
                        case modelled:
                            return new Mitab25ModelledWriter();
                        case basic:
                            return new LightMitab25Writer();
                        default:
                            return new Mitab25Writer();
                    }
                case v2_6:
                    switch (interactionCategory){
                        case evidence:
                            return new Mitab26EvidenceWriter();
                        case modelled:
                            return new Mitab26ModelledWriter();
                        case basic:
                            return new LightMitab25Writer();
                        default:
                            return new Mitab26Writer();
                    }
                default:
                    switch (interactionCategory){
                        case evidence:
                            return new Mitab27EvidenceWriter();
                        case modelled:
                            return new Mitab27ModelledWriter();
                        case basic:
                            return new LightMitab27Writer();
                        default:
                            return new Mitab27Writer();
                    }
            }
        }
    }
}
