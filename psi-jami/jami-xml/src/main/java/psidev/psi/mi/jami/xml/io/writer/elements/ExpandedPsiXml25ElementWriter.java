package psidev.psi.mi.jami.xml.io.writer.elements;

/**
 * Interface for an expanded XML 2.5 element writer.
 * If an element writer is expanded, it will write full object descriptions rather than object references.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public interface ExpandedPsiXml25ElementWriter<T extends Object> extends PsiXml25ElementWriter<T> {
}
