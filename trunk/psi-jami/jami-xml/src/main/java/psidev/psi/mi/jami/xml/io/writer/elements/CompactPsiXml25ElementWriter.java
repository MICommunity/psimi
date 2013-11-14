package psidev.psi.mi.jami.xml.io.writer.elements;

/**
 * Interface for a compact XML 2.5 element writer.
 * If an element writer is compact, it will write references rather than object descriptions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public interface CompactPsiXml25ElementWriter<T extends Object> extends PsiXml25ElementWriter<T> {
}
