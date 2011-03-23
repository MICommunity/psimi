package org.hupo.psi.calimocho.model;

/**
 * TODO document this !
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 * @since TODO add POM version
 */
public class DocumentDefinitionBuilder {

    private AbstractDocumentDefinition docDefinition;

    public DocumentDefinitionBuilder() {
        docDefinition = new AbstractDocumentDefinition() {};
    }

    public DocumentDefinitionBuilder addColumnDefinition(ColumnDefinition columnDefinition) {
        docDefinition.getColumns().add(columnDefinition);
        return this;
    }

    public DocumentDefinitionBuilder setName(String name) {
        docDefinition.setName( name );
        return this;
    }

    public DocumentDefinitionBuilder setDefinition(String definition) {
        docDefinition.setDefinition( definition );
        return this;
    }

    public DocumentDefinitionBuilder setColumnSeparator(String columnSeparator) {
        docDefinition.setColumnSeparator( columnSeparator );
        return this;
    }

    public DocumentDefinitionBuilder setColumnDelimiter(String columnDelimiter) {
        docDefinition.setColumnDelimiter( columnDelimiter );
        return this;
    }

    public DocumentDefinitionBuilder setCommentPrefix(String prefix) {
        docDefinition.setCommentPrefix( prefix );
        return this;
    }

    public DocumentDefinitionBuilder extendDocumentDefinition (DocumentDefinition docDef){

        if (docDef != null){
            docDefinition.setColumnDelimiter(docDef.getColumnDelimiter());
            docDefinition.setDefinition(docDef.getDefinition());
            docDefinition.setName(docDef.getName());
            docDefinition.setColumnSeparator(docDef.getColumnSeparator());
            docDefinition.setCommentPrefix(docDef.getCommentPrefix());
            docDefinition.setPartial(docDef.isPartial());

            docDefinition.getColumns().addAll(docDef.getColumns());
        }
        return this;
    }
    public DocumentDefinition build() {
        validate();

        return docDefinition;
    }

    public void validate() throws DefinitionException {
        if ( docDefinition.getColumns().isEmpty()) {
            throw new DefinitionException( "No columns defined, at least one is expected" );
        }

        if ( docDefinition.getColumnSeparator() == null) {
            throw new DefinitionException( "Mandatory column separator is not defined" );
        }
    }
}
