package org.hupo.psi.calimocho.model;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DefaultColumnDefinition extends AbstractDefined implements ColumnDefinition {


    private int position;
    private String key;
    private boolean allowsEmpty;
    private String emptyValue;
    private String fieldClassName;
    private String fieldParserClassName;
    private String fieldFormatterClassName;
    private String fieldSeparator;
    private String fieldDelimiter;

    public DefaultColumnDefinition( String key, int position ) {
        this.key = key;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition( int position ) {
        this.position = position;
    }

    public String getKey() {
        return key;
    }

    public void setKey( String key ) {
        this.key = key;
    }

    public boolean isAllowsEmpty() {
        return allowsEmpty;
    }

    public void setAllowsEmpty( boolean allowsEmpty ) {
        this.allowsEmpty = allowsEmpty;
    }

    public String getEmptyValue() {
        return emptyValue;
    }

    public void setEmptyValue( String emptyValue ) {
        this.emptyValue = emptyValue;
    }

    public String getFieldClassName() {
        return fieldClassName;
    }

    public void setFieldClassName( String fieldClassName ) {
        this.fieldClassName = fieldClassName;
    }

    public String getFieldParserClassName() {
        return fieldParserClassName;
    }

    public void setFieldParserClassName( String fieldParserClassName ) {
        this.fieldParserClassName = fieldParserClassName;
    }

    public String getFieldFormatterClassName() {
        return fieldFormatterClassName;
    }

    public void setFieldFormatterClassName( String fieldFormatterClassName ) {
        this.fieldFormatterClassName = fieldFormatterClassName;
    }

    public String getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator( String fieldSeparator ) {
        this.fieldSeparator = fieldSeparator;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public void setFieldDelimiter( String fieldDelimiter ) {
        this.fieldDelimiter = fieldDelimiter;
    }
}
