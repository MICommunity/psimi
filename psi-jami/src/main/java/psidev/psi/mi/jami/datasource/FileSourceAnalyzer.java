package psidev.psi.mi.jami.datasource;

import psidev.psi.mi.jami.utils.MolecularInteractionFileDataSourceUtils;

import java.io.*;

/**
 * The file source analyzer will recognise what kind of molecular interaction source a given file is from.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class FileSourceAnalyzer {

    public static String XML_EXTENSION=".xml";
    public static String TXT_EXTENSION=".txt";
    public static String CSV_EXTENSION=".csv";
    public static String TSV_EXTENSION=".tsv";

    public MolecularInteractionSource getMolecularInteractionSourceFor(File file) throws IOException {

        if (file == null){
            throw new FileNotFoundException("The file cannot be null.");
        }
        else if (isFileSignature(file, XML_EXTENSION)){
            // check xml files
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MolecularInteractionSource.other;
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")) || line.trim().startsWith("<?xml"))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MolecularInteractionSource.other;
                }
                else if (line.startsWith("<entrySet")){
                    return MolecularInteractionSource.psi25_xml;
                }
                else{
                    return MolecularInteractionSource.other;
                }
            }
            finally {
                reader.close();
            }
        }
        else if (isFileSignature(file, TXT_EXTENSION) || isFileSignature(file, CSV_EXTENSION) || isFileSignature(file, TSV_EXTENSION)){
            // check text formats
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MolecularInteractionSource.other;
                }

                // skip empty lines or break lines
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MolecularInteractionSource.other;
                }
                else if (line.toLowerCase().trim().startsWith("#ID(s) interactor A\tID(s) interactor B\tAlt. ID(s) interactor A\tAlt. ID(s) interactor B\tAlias(es) interactor A\tAlias(es) interactor B\tInteraction detection method(s)\tPublication 1st author(s)\tPublication Identifier(s)\tTaxid interactor A\tTaxid interactor B\tInteraction type(s)\tSource database(s)\tInteraction identifier(s)\tConfidence value(s)".toLowerCase())){
                    return MolecularInteractionSource.mitab;
                }
                else if (line.contains("\t") && line.split("\t").length >= 15){
                    return MolecularInteractionSource.mitab;
                }
                else{
                    return MolecularInteractionSource.other;
                }
            }
            finally {
                reader.close();
            }
        }
        else {
            // check xml files
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try{
                String line = reader.readLine();

                if (line == null){
                    return MolecularInteractionSource.other;
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return MolecularInteractionSource.other;
                }
                // we have xml
                else if (line.trim().startsWith("<?xml")){
                    line = reader.readLine();
                    if (line.startsWith("<entrySet")){
                        return MolecularInteractionSource.psi25_xml;
                    }
                    else {
                        return MolecularInteractionSource.other;
                    }
                }
                else if (line.startsWith("<entrySet")){
                    return MolecularInteractionSource.psi25_xml;
                }
                else if (line.toLowerCase().trim().startsWith("#ID(s) interactor A\tID(s) interactor B\tAlt. ID(s) interactor A\tAlt. ID(s) interactor B\tAlias(es) interactor A\tAlias(es) interactor B\tInteraction detection method(s)\tPublication 1st author(s)\tPublication Identifier(s)\tTaxid interactor A\tTaxid interactor B\tInteraction type(s)\tSource database(s)\tInteraction identifier(s)\tConfidence value(s)".toLowerCase())){
                    return MolecularInteractionSource.mitab;
                }
                else if (line.contains("\t") && line.split("\t").length >= 15){
                    return MolecularInteractionSource.mitab;
                }
                else{
                    return MolecularInteractionSource.other;
                }
            }
            finally {
                reader.close();
            }
        }
    }

    public OpenedInputStream getMolecularInteractionSourceFor(InputStream stream) throws IOException {

        if (stream == null){
            throw new IOException("The input stream cannot be null.");
        }
        else {

            File temp = MolecularInteractionFileDataSourceUtils.storeAsTemporaryFile(stream, "molecular_interaction_stream"+System.currentTimeMillis(), ".txt");

            // check first line
            BufferedReader reader = new BufferedReader(new FileReader(temp));

            try {
                String line = reader.readLine();

                if (line == null){
                    return new OpenedInputStream(temp, MolecularInteractionSource.other);
                }

                // skip empty lines or break lines or xml encode line
                while (line != null && (line.trim().length() == 0 || line.trim().equals(System.getProperty("line.separator")))){
                    line = reader.readLine();
                }

                if (line == null){
                    return new OpenedInputStream(temp, MolecularInteractionSource.other);
                }
                // we have xml
                else if (line.trim().startsWith("<?xml")){
                    line = reader.readLine();
                    if (line.startsWith("<entrySet")){
                        return new OpenedInputStream(temp, MolecularInteractionSource.psi25_xml);
                    }
                    else {
                        return new OpenedInputStream(temp, MolecularInteractionSource.other);
                    }
                }
                else if (line.startsWith("<entrySet")){
                    return new OpenedInputStream(temp,  MolecularInteractionSource.psi25_xml);
                }
                else if (line.toLowerCase().trim().startsWith("#ID(s) interactor A\tID(s) interactor B\tAlt. ID(s) interactor A\tAlt. ID(s) interactor B\tAlias(es) interactor A\tAlias(es) interactor B\tInteraction detection method(s)\tPublication 1st author(s)\tPublication Identifier(s)\tTaxid interactor A\tTaxid interactor B\tInteraction type(s)\tSource database(s)\tInteraction identifier(s)\tConfidence value(s)".toLowerCase())){
                    return new OpenedInputStream(temp,  MolecularInteractionSource.mitab);
                }
                else if (line.contains("\t") && line.split("\t").length >= 15){
                    return new OpenedInputStream(temp,  MolecularInteractionSource.mitab);
                }
                else{
                    return new OpenedInputStream(temp, MolecularInteractionSource.other);
                }
            }
            finally {
                reader.close();
            }
        }
    }

    public boolean isFileSignature(File file, String signature){
        if (file == null || signature == null){
            return false;
        }
        else if (file.getName().endsWith(signature)){
            return true;
        }
        else {
            return false;
        }
    }
}
