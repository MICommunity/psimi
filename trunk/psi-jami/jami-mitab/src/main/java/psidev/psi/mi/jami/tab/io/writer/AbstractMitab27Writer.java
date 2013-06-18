package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

/**
 * Abstract writer for Mitab 2.7.
 *
 * The general options when calling method initialiseContext(Map<String, Object> options) are :
 *  - output_file_key : File. Specifies the file where to write
 *  - output_stream_key : OutputStream. Specifies the stream where to write
 *  - output_writer_key : Writer. Specifies the writer.
 *  If these three options are given, output_file_key will take priority, then output_stream_key an finally output_writer_key. At leats
 *  one of these options should be provided when initialising the context of the writer
 *  - complex_expansion_key : Class<? extends ComplexExpansionMethod>. Specifies the ComplexExpansion class to use. By default, it is SpokeExpansion if nothing is specified
 *  - mitab_header_key : Boolean. Specifies if the writer should write the MITAB header when starting to write or not
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public abstract class AbstractMitab27Writer extends AbstractMitab26Writer {

    public AbstractMitab27Writer() {
        super();
        setVersion(MitabVersion.v2_7);
    }

    public AbstractMitab27Writer(File file) throws IOException {
        super(file);
        setVersion(MitabVersion.v2_7);
    }

    public AbstractMitab27Writer(OutputStream output) throws IOException {
        super(output);
        setVersion(MitabVersion.v2_7);
    }

    public AbstractMitab27Writer(Writer writer) throws IOException {
        super(writer);
        setVersion(MitabVersion.v2_7);
    }

    public AbstractMitab27Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
        setVersion(MitabVersion.v2_7);
    }

    public AbstractMitab27Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
        setVersion(MitabVersion.v2_7);
    }

    public AbstractMitab27Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
        super(writer, expansionMethod);
        setVersion(MitabVersion.v2_7);
    }

    @Override
    /**
     * Writes the binary interaction and its participants in MITAB 2.7
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinary(BinaryInteraction interaction, Participant a, Participant b) throws IOException {
        // write 2.6 columns
        super.writeBinary(interaction, a, b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write features
        // write features A
        writeParticipantFeatures(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write features B
        writeParticipantFeatures(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);

        // write stc A
        writeParticipantStoichiometry(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write stc B
        writeParticipantStoichiometry(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip identification A
        getWriter().write(MitabUtils.EMPTY_COLUMN);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip identification B
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    @Override
    /**
     * Writes the modelled binary interaction and its participants in MITAB 2.7
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeModelledBinary(ModelledBinaryInteraction interaction, ModelledParticipant a, ModelledParticipant b) throws IOException {
        // write 2.6 columns
        super.writeModelledBinary(interaction, a, b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write features
        // write features A
        writeParticipantFeatures(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write features B
        writeParticipantFeatures(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write stc A
        writeParticipantStoichiometry(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write stc B
        writeParticipantStoichiometry(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip identification A
        getWriter().write(MitabUtils.EMPTY_COLUMN);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip identification B
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    @Override
    /**
     * Writes the binary interaction evidence and its participants in MITAB 2.7
     * @param interaction
     * @param a
     * @param b
     * @throws IOException
     */
    protected void writeBinaryEvidence(BinaryInteractionEvidence interaction, ParticipantEvidence a, ParticipantEvidence b) throws IOException {
        // write 2.6 columns
        super.writeBinaryEvidence(interaction, a, b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write features
        // write features A
        writeParticipantFeatures(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write features B
        writeParticipantFeatures(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write stc A
        writeParticipantStoichiometry(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // write stc B
        writeParticipantStoichiometry(b);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip identification A
        writeParticipantIdentificationMethod(a);
        getWriter().write(MitabUtils.COLUMN_SEPARATOR);
        // skip identification B
        writeParticipantIdentificationMethod(b);
    }

    /**
     * Writes participant features
     * @param participant
     */
    protected void writeParticipantFeatures(Participant participant) throws IOException {

        if (participant != null){

            if (!participant.getFeatures().isEmpty()){

                Iterator<? extends Feature> featureIterator = participant.getFeatures().iterator();
                while(featureIterator.hasNext()){
                    writeFeature(featureIterator.next());

                    if (featureIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes participant stoichiometry
     * @param participant
     */
    protected void writeParticipantStoichiometry(Participant participant) throws IOException {

        if (participant != null){

            if (participant.getStoichiometry() != null){
                Stoichiometry stc = participant.getStoichiometry();
                // same stoichiometry max/end
                if (stc.getMaxValue() == stc.getMinValue()){
                    getWriter().write(Long.toString(stc.getMinValue()));
                }
                else{
                    getWriter().write(Long.toString(stc.getMinValue()));
                    getWriter().write("-");
                    getWriter().write(Long.toString(stc.getMaxValue()));
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes participant identification method(s)
     * @param participant
     */
    protected void writeParticipantIdentificationMethod(ParticipantEvidence participant) throws IOException {

        if (participant != null){

            if (!participant.getIdentificationMethods().isEmpty()){
                Iterator<CvTerm> methodIterator = participant.getIdentificationMethods().iterator();
                while(methodIterator.hasNext()){
                    writeCvTerm(methodIterator.next());
                    if (methodIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    /**
     * Writes a feature
     * @param feature
     */
    protected abstract void writeFeature(Feature feature) throws IOException;
}
