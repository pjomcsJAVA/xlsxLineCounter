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

@Path("/hello")
public class MatchResource {

    public static final String EXTENSION_FOR_FILES = ".xlsx";
    public static final String FOLDER_TO_CHECK_FOR_FILES = "/home/pedro.costa/xlsFileCounter";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws IOException {

        int totalLinesInXlsxFile = 0;

        List<String> fileNames = Files.walk(Paths.get(FOLDER_TO_CHECK_FOR_FILES))
                                      .map(java.nio.file.Path::toString)
                                      .filter(f -> f.endsWith(EXTENSION_FOR_FILES))
                                      .collect(Collectors.toList());

        for (String currentFile : fileNames) {
            InputStream is = new FileInputStream(currentFile);
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIter = sheet.rowIterator();

            while (rowIter.hasNext()) {
                totalLinesInXlsxFile++;
                rowIter.next();
            }
        }

        return "Counting files... Total lines:  " + (totalLinesInXlsxFile - fileNames.size()) + " in " + fileNames.size() +" files.";
    }
}