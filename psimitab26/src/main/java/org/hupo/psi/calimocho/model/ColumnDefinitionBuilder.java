package org.hupo.psi.calimocho.model;

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

    public ColumnDefinitionBuilder setIsAllowsEmpty(boolean isAllowsEmpty){
       this.columnDefinition.setAllowsEmpty(isAllowsEmpty);

        return this;
    }
}
