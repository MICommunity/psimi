package psidev.psi.mi.validator.extension.rules.dependencies;

import psidev.psi.mi.xml.model.CvType;
import psidev.psi.mi.xml.model.DbReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a CV term with an id and a full name. The boolean value includeChildren allows to know if when we find this term
 * in a dependency, the children of this CV Term are also included in the dependency.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id: Term.java 56 2010-01-22 15:37:09Z marine.dumousseau@wanadoo.fr $
 * @since 2.0
 */
public class Term {

    private String id;
    private String name;
    private boolean includeChildren;
    private Term parent = null;

    public Term( String id, String name ) {

        if (id != null){
            // NONE is the value for empty columns.
            if (id.equals("NONE")){
                this.id = null;
            }
            else {
                this.id = id;
            }
        }
        else {
            this.id = null;
        }

        if (name != null){
            if (name.equals("NONE")){
                this.name = null;
            }
            else {
                this.name = name;
            }
        }
        else {
            this.name = null;
        }

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Term getParent() {
        return parent;
    }

    public boolean isIncludeChildren() {
        return includeChildren;
    }

    public boolean isDeducedFromItsParent() {
        return parent != null;
    }

    public void setIncludeChildren( boolean includeChildren ) {
        this.includeChildren = includeChildren;
    }

    public void setParent( Term parent ) {
        this.parent = parent;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Term term = ( Term ) o;

        if (id != null){
            if ( !id.equals( term.id ) ) return false;
        }
        else {
            if (name != null){
                if ( !name.equals( term.name )){
                    return false;
                }
            }
        }

        return true;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null && name != null) ? name.hashCode() : 0);
        if (name == null){
            result = prime * result + ((id == null) ? 0 : id.hashCode());
        }
        return result;

    }

    @Override
    public String toString() {
        return "Term{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", includeChildren=" + includeChildren +
                ", dependency deduced from its parent =" + Term.printTerm(parent) +
                '}';
    }

    public static Term buildTerm( CvType cv ) {
        if (cv == null){
            return null;
        }
        final String id = getMiIdentifier( cv );
        if( id == null ) {
            return null;
        }
        return new Term( id, cv.getNames().getShortLabel() );
    }

    public static String printTerm( Term term ) {
        StringBuilder sb = new StringBuilder( 32 );

        if (term != null){
            if (term.getId() != null){
                sb.append(term.getId());

                if (term.getName() != null){
                    sb.append("(name : " + term.getName() + ")");
                }
            }
            else {
                sb.append(term.getName()).append(" (").append(term.getId()).append(")");
            }
        }
        else {
            sb.append(term);
        }

        return sb.toString();
    }

    public static String printTerms( Collection<Term> terms ) {
        StringBuilder sb = new StringBuilder( terms.size() * 32 );
        for ( Iterator<Term> termIterator = terms.iterator(); termIterator.hasNext(); ) {
            Term term = termIterator.next();
            sb.append( printTerm(term) );
            if( termIterator.hasNext() ) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private static String getMiIdentifier( CvType cv ) {

        Collection<DbReference> refs = new ArrayList<DbReference>();
        if (cv != null){
            if( cv.getXref() != null ) {
                refs.add( cv.getXref().getPrimaryRef() );
                refs.addAll( cv.getXref().getSecondaryRef() );

                for ( Iterator<DbReference> iterator = refs.iterator(); iterator.hasNext(); ) {
                    DbReference ref = iterator.next();

                    if ( "MI:0488".equals( ref.getDbAc() ) || "psi-mi".equals( ref.getDb() ) ) {
                        if ( "MI:0356".equals( ref.getRefTypeAc() ) || "identity".equals( ref.getRefType() ) ) {
                            return ref.getId();
                        }
                    }
                }
            }
        }

        return null;
    }
}
