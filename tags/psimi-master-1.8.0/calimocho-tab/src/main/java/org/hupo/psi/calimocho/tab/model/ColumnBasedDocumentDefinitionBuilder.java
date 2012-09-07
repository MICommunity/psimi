package org.hupo.psi.calimocho.tab.model;

import org.hupo.psi.calimocho.model.DefinitionException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since 1.0
 */
public class ColumnBasedDocumentDefinitionBuilder {

    private AbstractColumnBasedDocumentDefinition docDefinition;

    public ColumnBasedDocumentDefinitionBuilder() {
        docDefinition = new AbstractColumnBasedDocumentDefinition() {};
    }

    public ColumnBasedDocumentDefinitionBuilder addColumnDefinition(ColumnDefinition columnDefinition) {
        docDefinition.getColumns().add( columnDefinition );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder setName(String name) {
        docDefinition.setName( name );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder setDefinition(String definition) {
        docDefinition.setDefinition( definition );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder setColumnSeparator(String columnSeparator) {
        docDefinition.setColumnSeparator( columnSeparator );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder setEmptyValue(String emptyColumn) {
        docDefinition.setEmptyValue( emptyColumn );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder setColumnDelimiter(String columnDelimiter) {
        docDefinition.setColumnDelimiter( columnDelimiter );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder setCommentPrefix(String prefix) {
        docDefinition.setCommentPrefix( prefix );
        return this;
    }

    public ColumnBasedDocumentDefinitionBuilder extendDocumentDefinition (ColumnBasedDocumentDefinition docDef){

        if (docDef != null){
            docDefinition.setColumnDelimiter(docDef.getColumnDelimiter());
            docDefinition.setDefinition(docDef.getDefinition());
            docDefinition.setName(docDef.getName());
            docDefinition.setColumnSeparator(docDef.getColumnSeparator());
            docDefinition.setCommentPrefix(docDef.getCommentPrefix());
            docDefinition.setPartial(docDef.isPartial());
            docDefinition.setEmptyValue(docDef.getEmptyValue());

            docDefinition.getColumns().addAll(docDef.getColumns());
        }
        return this;
    }
    public ColumnBasedDocumentDefinition build() {
        validate();

        // sort the columns by position
        Collections.sort( docDefinition.getColumns() );

        return docDefinition;
    }

    public void validate() throws DefinitionException {
        if ( docDefinition.getColumns().isEmpty()) {
            throw new DefinitionException( "No columns defined, at least one is expected" );
        }

        if ( docDefinition.getColumnSeparator() == null) {
            throw new DefinitionException( "Mandatory column separator is not defined" );
        }

        Map<Integer, String> processedColumnPositions = new HashMap<Integer, String>();

        for (ColumnDefinition col : docDefinition.getColumns()){

            if (processedColumnPositions.containsKey(col.getPosition())){
                throw new DefinitionException( "Column " + col.getKey() + " and column " + processedColumnPositions.get(col.getPosition()) + " have the same position " + col.getPosition() );
            }
            else {
                if (processedColumnPositions.containsValue(col.getKey())){
                    throw new DefinitionException( "Column " + col.getPosition() + " has a key (" + col.getKey() + ") which already exists in previous column definitions." );
                }

                processedColumnPositions.put(col.getPosition(), col.getKey());
            }
        }
    }
}
