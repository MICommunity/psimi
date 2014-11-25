package psidev.psi.mi.jami.xml.model.extension.binary;

import psidev.psi.mi.jami.binary.impl.AbstractBinaryInteraction;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.model.Entry;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlLocator;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlInteraction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class for xml binary interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>29/10/13</pre>
 */

public abstract class AbstractXmlBinaryInteraction<P extends Participant> extends AbstractBinaryInteraction<P>
        implements PsiXmlInteraction<P>, FileSourceContext {

    private PsiXmlLocator sourceLocator;
    private String fullName;
    private List<Alias> aliases;
    private Entry entry;
    private int id;
    private Boolean intraMolecular;

    public AbstractXmlBinaryInteraction(){
        super();
    }

    public AbstractXmlBinaryInteraction(String shortName){
        super(shortName);
    }

    public AbstractXmlBinaryInteraction(String shortName, CvTerm type){
        super(shortName, type);
    }

    public AbstractXmlBinaryInteraction(P participantA, P participantB){
        super(participantA, participantB);

    }

    public AbstractXmlBinaryInteraction(String shortName, P participantA, P participantB){
        super(shortName, participantA, participantB);
    }

    public AbstractXmlBinaryInteraction(String shortName, CvTerm type, P participantA, P participantB){
        super(shortName, type, participantA, participantB);
    }

    public AbstractXmlBinaryInteraction(CvTerm complexExpansion){
        super(complexExpansion);
    }

    public AbstractXmlBinaryInteraction(String shortName, CvTerm type, CvTerm complexExpansion){
        super(shortName, type, complexExpansion);

    }

    public AbstractXmlBinaryInteraction(P participantA, P participantB, CvTerm complexExpansion){
        super(participantA, participantB, complexExpansion);

    }

    public AbstractXmlBinaryInteraction(String shortName, P participantA, P participantB, CvTerm complexExpansion){
        super(shortName, participantA, participantB, complexExpansion);
    }

    public AbstractXmlBinaryInteraction(String shortName, CvTerm type, P participantA, P participantB, CvTerm complexExpansion){
        super(shortName, type, participantA, participantB, complexExpansion);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = (PsiXmlLocator)sourceLocator;
    }

    public boolean isIntraMolecular(){
        return intraMolecular != null ? intraMolecular : false;
    }

    /**
     * Sets the value of the intraMolecular property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIntraMolecular(boolean value) {
        this.intraMolecular = value;
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public void setFullName(String name) {
        this.fullName = name;
    }

    @Override
    public Collection<Alias> getAliases() {
        if (this.aliases == null){
            this.aliases = new ArrayList<Alias>();
        }
        return this.aliases;
    }

    @Override
    public Entry getEntry() {
        return this.entry;
    }

    @Override
    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Binary interaction: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
