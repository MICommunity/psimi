package psidev.psi.mi.jami.tab.extension.factory;

import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.extension.datasource.*;

/**
 * Factory for creating a mitab datasource
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public class MitabDataSourceFactory {

    private static final MitabDataSourceFactory instance = new MitabDataSourceFactory();

    private MitabDataSourceFactory(){
    }

    public static MitabDataSourceFactory getInstance() {
        return instance;
    }

    public MitabStreamSource createMitabDataSource(InteractionCategory interactionCategory, ComplexType complexType,
                                                  boolean streaming){
        switch (complexType){
            case binary:
                return createMitabBinaryDataSource(interactionCategory, streaming);
            default:
                return createMitabDataSource(interactionCategory, streaming);
        }
    }

    public MitabStreamSource createMitabBinaryDataSource(InteractionCategory interactionCategory, boolean streaming){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.evidence;
        }

        if (streaming){
            switch (interactionCategory){
                case evidence:
                    return new MitabBinaryEvidenceStreamSource();
                case modelled:
                    return new MitabModelledBinaryStreamSource();
                case basic:
                    return new LightMitabBinaryStreamSource();
                case mixed:
                    return new MitabBinaryEvidenceStreamSource();
                default:
                    throw new IllegalArgumentException("Cannot create a MITAB binary source for Interaction category: "+interactionCategory);
            }
        }
        else{
            switch (interactionCategory){
                case evidence:
                    return new MitabBinaryEvidenceSource();
                case modelled:
                    return new MitabModelledBinarySource();
                case basic:
                    return new LightMitabBinarySource();
                case mixed:
                    return new MitabBinaryEvidenceSource();
                default:
                    throw new IllegalArgumentException("Cannot create a MITAB binary source for Interaction category: "+interactionCategory);
            }
        }
    }

    public MitabStreamSource createMitabDataSource(InteractionCategory interactionCategory, boolean streaming){
        if (interactionCategory == null){
            interactionCategory = InteractionCategory.evidence;
        }

        if (streaming){
            switch (interactionCategory){
                case evidence:
                    return new MitabEvidenceStreamSource();
                case modelled:
                    return new MitabModelledStreamSource();
                case basic:
                    return new LightMitabStreamSource();
                case mixed:
                    return new MitabEvidenceStreamSource();
                default:
                    throw new IllegalArgumentException("Cannot create a MITAB source for Interaction category: "+interactionCategory);
            }
        }
        else{
            switch (interactionCategory){
                case evidence:
                    return new MitabEvidenceSource();
                case modelled:
                    return new MitabModelledSource();
                case basic:
                    return new LightMitabSource();
                case mixed:
                    return new MitabEvidenceSource();
                default:
                    throw new IllegalArgumentException("Cannot create a MITAB source for Interaction category: "+interactionCategory);
            }
        }
    }
}
