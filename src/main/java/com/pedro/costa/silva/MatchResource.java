package com.pedro.costa.silva;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Pedro Costa (pedro.costa@feedzai.com).
 * @since 1.1
 */
@Path("/match")
public class MatchResource {

    public static final int CELL_NUMBER_TO_MATCH_TO = 1;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws IOException {

        List<String> fileNames = CommonUtils.getFileListFromFolder();

        List<String> toMatchList = Arrays.asList("7548730741733980000",
                                                 "a0e0e6b09cc811e980255e225b9cba0d",
                                                 "b39ba29e9cb511e9801d5673f58e580c",
                                                 "d56ce3f29caf11e980245e225f2c770d"
        );

        String[] foundMatch = new String[toMatchList.size()];
        String[] foundKey = new String[toMatchList.size()];
        int iFoundMatch = 0;

        for (String currentFile : fileNames) {

            Iterator<Row> rowIter = CommonUtils.rowIter(currentFile, 0);

            while (true) {
                assert rowIter != null;
                if (!rowIter.hasNext()) {
                    break;
                }
                Cell cell = rowIter.next().getCell(CELL_NUMBER_TO_MATCH_TO);

                for (String match : toMatchList) {
                    if (cell.getCellType().name().equalsIgnoreCase("STRING")) {

                        if (match.equalsIgnoreCase(String.valueOf(cell.getStringCellValue()))) {

                            foundMatch[iFoundMatch] = currentFile;
                            foundKey[iFoundMatch] = match;
                            iFoundMatch++;
                        }
                    } else {
                        if (match.equalsIgnoreCase(String.valueOf(cell.getNumericCellValue()))) {

                            foundMatch[iFoundMatch] = currentFile;
                            foundKey[iFoundMatch] = match;
                            iFoundMatch++;
                        }
                    }
                }
            }
        }
        return "RESULTS:\n" + buildResponse(foundMatch, foundKey);
    }

    private String buildResponse(String[] foundMatch, String[] foundKey) {

        String returnString = "";
        for (int i = 0; i < foundMatch.length; i++) {
            String filePath = foundMatch[i];
            if (filePath!=null) {
                returnString = returnString + "\nFound key: " + foundKey[i] + "\nin file: " + filePath.substring(filePath.lastIndexOf("/")+1)+"\n";
            }
        }
        return returnString.isEmpty() ? "\nNo matches found!!" : returnString;
    }
}