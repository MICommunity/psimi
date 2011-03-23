package org.hupo.psi.calimocho.model;

import org.hupo.psi.calimocho.io.FieldFormatter;
import org.hupo.psi.calimocho.io.FieldParser;

/**
 * Created by IntelliJ IDEA.
 * User: Training
 * Date: 23/03/11
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class ColumnDefinitionBuilder {

    private AbstractColumnDefinition columnDefinition;

    public ColumnDefinitionBuilder() {
        this.columnDefinition = new AbstractColumnDefinition(){};
    }

    public ColumnDefinitionBuilder setPosition(int position){
        this.columnDefinition.setPosition(position);

        return this;
    }

    public ColumnDefinitionBuilder setKey(String key){
        this.columnDefinition.setKey(key);

        return this;
    }

    public ColumnDefinitionBuilder setFieldSeparator(String separator){
        this.columnDefinition.setFieldSeparator(separator);

        return this;
    }

    public ColumnDefinitionBuilder setFieldDelimiter(String delimiter){
        this.columnDefinition.setFieldDelimiter(delimiter);

        return this;
    }

    public ColumnDefinitionBuilder setEmptyValue(String value){
        this.columnDefinition.setEmptyValue(value);

        return this;
    }

    public ColumnDefinitionBuilder setIsAllowsEmpty(boolean isAllowsEmpty){
       this.columnDefinition.setAllowsEmpty(isAllowsEmpty);

        return this;
    }

    public ColumnDefinitionBuilder setFieldParser(FieldParser parser){
       this.columnDefinition.setFieldParser(parser);

        return this;
    }

    public ColumnDefinitionBuilder setFieldFormatter(FieldFormatter formatter){
       this.columnDefinition.setFieldFormatter(formatter);

        return this;
    }

    public void validate() throws DefinitionException {
        /*if ( columnDefinition.getKey() == null)) {
            throw new DefinitionException( "No columns defined, at least one is expected" );
        }

        if ( docDefinition.getColumnSeparator() == null) {
            throw new DefinitionException( "Mandatory column separator is not defined" );
        } */
    }

    public ColumnDefinition build() {
        validate();

        return columnDefinition;
    }
}
