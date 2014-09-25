package psidev.psi.mi.jami.xml.model.extension.datasource;

import org.xml.sax.ErrorHandler;
import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

/**
 * Interface for PSI-MI XML datasources that can stream PSI-MI XML files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public interface PsiXmlStreamSource<I extends Interaction> extends MIFileDataSource, InteractionStream<I>, PsiXmlParserListener ,ErrorHandler {
}
