package datafileshandler;

import org.apache.pdfbox.pdfparser.FDFParser;

import java.io.IOException;

public class FDFDataHandler {

    private FDFParser f = new FDFParser("D:\\fichaDoAluno1.fdf");

    public FDFDataHandler() throws IOException {
    }
}
