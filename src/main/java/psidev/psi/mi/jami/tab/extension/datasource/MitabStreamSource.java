package psidev.psi.mi.jami.tab.extension.datasource;

import psidev.psi.mi.jami.datasource.InteractionStream;
import psidev.psi.mi.jami.datasource.MIFileDataSource;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.tab.listener.MitabParserListener;

/**
 * Interface for MITAB datasources that can stream MITAB files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/05/14</pre>
 */

public interface MitabStreamSource<I extends Interaction> extends MIFileDataSource, InteractionStream<I>, MitabParserListener {
}
