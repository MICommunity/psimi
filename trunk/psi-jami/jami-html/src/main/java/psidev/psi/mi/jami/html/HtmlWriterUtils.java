package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.*;

/**
 * Utility class for writing molecular interactions in html
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/04/13</pre>
 */

public class HtmlWriterUtils {

    public static String getHtmlAnchorFor(InteractionEvidence interaction){

        if (interaction != null){
            // first extract file context
            if (interaction instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) interaction;

                if (context.getSourceLocator() != null){
                    FileSourceLocator locator = context.getSourceLocator();

                    return locator.toString();
                }
            }

            // get IMEx id
            if (interaction.getImexId() != null){
                return interaction.getImexId();
            }

            if (!interaction.getIdentifiers().isEmpty()){
                return interaction.getIdentifiers().iterator().next().getId();
            }

            return Integer.toString(interaction.hashCode());
        }
        return null;
    }

    public static String getHtmlAnchorFor(Experiment experiment){

        if (experiment != null){
            // first extract file context
            if (experiment instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) experiment;

                if (context.getSourceLocator() != null){
                    FileSourceLocator locator = context.getSourceLocator();

                    return locator.toString();
                }
            }

            return Integer.toString(experiment.hashCode());
        }
        return null;
    }

    public static String getHtmlAnchorFor(ParticipantEvidence participant){

        if (participant != null){
            // first extract file context
            if (participant instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) participant;

                if (context.getSourceLocator() != null){
                    FileSourceLocator locator = context.getSourceLocator();

                    return locator.toString();
                }
            }
            return Integer.toString(participant.hashCode());
        }
        return null;
    }

    public static String getHtmlAnchorFor(Interactor participant){

        if (participant != null){
            // first extract file context
            if (participant instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) participant;

                if (context.getSourceLocator() != null){
                    FileSourceLocator locator = context.getSourceLocator();

                    return locator.toString();
                }
            }

            if (!participant.getIdentifiers().isEmpty()){
                return participant.getIdentifiers().iterator().next().getId();
            }

            return Integer.toString(participant.hashCode());
        }
        return null;
    }

    public static String getHtmlAnchorFor(FeatureEvidence feature){

        if (feature != null){
            // first extract file context
            if (feature instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) feature;

                if (context.getSourceLocator() != null){
                    FileSourceLocator locator = context.getSourceLocator();

                    return locator.toString();
                }
            }

            if (!feature.getIdentifiers().isEmpty()){
                return feature.getIdentifiers().iterator().next().getId();
            }

            return Integer.toString(feature.hashCode());
        }
        return null;
    }
}
