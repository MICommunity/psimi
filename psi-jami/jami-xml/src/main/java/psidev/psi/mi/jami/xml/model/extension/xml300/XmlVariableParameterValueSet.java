package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.utils.comparator.experiment.VariableParameterValueSetComparator;
import psidev.psi.mi.jami.xml.cache.PsiXmlIdCache;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.model.reference.AbstractXmlIdReference;

import javax.xml.bind.annotation.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * XML 3.0 implementation of variable parameter value set
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://psi.hupo.org/mi/mif300")
public class XmlVariableParameterValueSet implements VariableParameterValueSet,FileSourceContext,Locatable {

    private PsiXmLocator sourceLocator;

    @XmlLocation
    @XmlTransient
    private Locator locator;
    private JAXBVariableValueRefList jaxbVariableValueRefList;

    public XmlVariableParameterValueSet(){
        initialiseVatiableParameterValuesSet();
    }

    protected void initialiseVatiableParameterValuesSet(){
        this.jaxbVariableValueRefList = new JAXBVariableValueRefList();
    }

    public int size() {
        return jaxbVariableValueRefList.variableParameterValues.size();
    }

    public boolean isEmpty() {
        return jaxbVariableValueRefList.variableParameterValues.isEmpty();
    }

    public boolean contains(Object o) {
        return jaxbVariableValueRefList.variableParameterValues.contains(o);
    }

    public Iterator<VariableParameterValue> iterator() {
        return jaxbVariableValueRefList.variableParameterValues.iterator();
    }

    public Object[] toArray() {
        return jaxbVariableValueRefList.variableParameterValues.toArray();
    }

    public <T> T[] toArray(T[] ts) {
        return jaxbVariableValueRefList.variableParameterValues.toArray(ts);
    }

    public boolean add(VariableParameterValue variableParameterValue) {
        return jaxbVariableValueRefList.variableParameterValues.add(variableParameterValue);
    }

    public boolean remove(Object o) {
        return jaxbVariableValueRefList.variableParameterValues.remove(o);
    }

    public boolean containsAll(Collection<?> objects) {
        return jaxbVariableValueRefList.variableParameterValues.containsAll(objects);
    }

    public boolean addAll(Collection<? extends VariableParameterValue> variableParameterValues) {
        return this.jaxbVariableValueRefList.variableParameterValues.addAll(variableParameterValues);
    }

    public boolean retainAll(Collection<?> objects) {
        return jaxbVariableValueRefList.variableParameterValues.retainAll(objects);
    }

    public boolean removeAll(Collection<?> objects) {
        return jaxbVariableValueRefList.variableParameterValues.removeAll(objects);
    }

    public void clear() {
        jaxbVariableValueRefList.variableParameterValues.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof VariableParameterValueSet)){
            return false;
        }

        return VariableParameterValueSetComparator.areEquals(this, (VariableParameterValueSet) o);
    }

    @Override
    public int hashCode() {
        return VariableParameterValueSetComparator.hashCode(this);
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocation(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @XmlElement(name = "variableValueRef", type = Integer.class, required = true)
    public Set<Integer> getJAXBVariableValueRefs() {
        return this.jaxbVariableValueRefList;
    }

    //////////////////////////////////////////////////////////////
    /**
     * The variable value ref list used by JAXB to populate variable value refs
     */
    private class JAXBVariableValueRefList extends HashSet<Integer>{
        private Set<VariableParameterValue> variableParameterValues;

        public JAXBVariableValueRefList(){
            super();
            variableParameterValues = new HashSet<VariableParameterValue>();
        }

        @Override
        public boolean add(Integer val) {
            if (val == null){
                return false;
            }
            return variableParameterValues.add(new VariableParameterValueRef(val));
        }

        @Override
        public boolean addAll(Collection<? extends Integer> c) {
            if (c == null){
                return false;
            }
            boolean added = false;

            for (Integer a : c){
                if (add(a)){
                    added = true;
                }
            }
            return added;
        }

        @Override
        public String toString() {
            return "Variable parameter values: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }

        ////////////////////////////////////////////////////// classes
        /**
         * Variable value ref for variable parameter value
         */
        private class VariableParameterValueRef extends AbstractXmlIdReference implements VariableParameterValue {
            private final Logger logger = Logger.getLogger("XmlVariableParameterValueSet");
            private VariableParameterValue delegate;

            public VariableParameterValueRef(int ref) {
                super(ref);
            }

            public boolean resolve(PsiXmlIdCache parsedObjects) {
                if (parsedObjects.contains(this.ref)){
                    VariableParameterValue obj = parsedObjects.getVariableParameterValue(this.ref);
                    if (obj != null){
                        variableParameterValues.remove(this);
                        variableParameterValues.add(obj);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public String toString() {
                return "Variable parameter value Reference: "+ref+(getSourceLocator() != null ? ", "+getSourceLocator().toString():super.toString());
            }

            public FileSourceLocator getSourceLocator() {
                return sourceLocator;
            }

            public void setSourceLocator(FileSourceLocator locator) {
                throw new UnsupportedOperationException("Cannot set the source locator of a variable parameter value ref");
            }

            @Override
            public String getValue() {
                logger.log(Level.WARNING, "The variable parameter value reference "+ref+" is not resolved. Some default properties will be initialised by default");
                if (this.delegate == null){
                    initialiseParameterValueDelegate();
                }
                return this.delegate.getValue();
            }

            @Override
            public Integer getOrder() {
                logger.log(Level.WARNING, "The variable parameter value reference "+ref+" is not resolved. Some default properties will be initialised by default");
                if (this.delegate == null){
                    initialiseParameterValueDelegate();
                }
                return this.delegate.getOrder();
            }

            @Override
            public VariableParameter getVariableParameter() {
                logger.log(Level.WARNING, "The variable parameter value reference "+ref+" is not resolved. Some default properties will be initialised by default");
                if (this.delegate == null){
                    initialiseParameterValueDelegate();
                }
                return this.delegate.getVariableParameter();
            }

            private void initialiseParameterValueDelegate() {
                this.delegate = new XmlVariableParameterValue();
            }
        }
    }
}
