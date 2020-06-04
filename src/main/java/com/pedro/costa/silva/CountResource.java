package com.pedro.costa.silva;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Pedro Costa (pedro.costa@feedzai.com).
 * @since 1.0
 */
@Path("/count")
public class CountResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String count() throws IOException {

        int totalLinesInXlsxFile = 0;

        List<String> fileNames = CommonUtils.getFileListFromFolder();

        for (String currentFile : fileNames) {
            Iterator<Row> rowIter = CommonUtils.rowIter(currentFile, 0);
            if (rowIter!=null) {
                while (rowIter.hasNext()) {
                    totalLinesInXlsxFile++;
                    rowIter.next();
                }
            }
        }
        return "Counting files... Total lines:  " + (totalLinesInXlsxFile - fileNames.size()) + " in " + fileNames.size() +" files.";
    }

    private String buildResponseMessage(int totallines, boolean hasHeader, int numerOfFiles){
return "";

    }
}