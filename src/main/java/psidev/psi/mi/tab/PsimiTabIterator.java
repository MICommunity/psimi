package psidev.psi.mi.tab;

import psidev.psi.mi.tab.model.BinaryInteraction;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 26/07/2012
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public interface PsimiTabIterator extends Iterator<BinaryInteraction> {

    boolean hasNext();

    BinaryInteraction next();

    void remove();
}
