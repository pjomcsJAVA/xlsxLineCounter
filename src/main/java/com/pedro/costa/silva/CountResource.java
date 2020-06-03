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

@Path("/match")
public class CountResource {

    public static final String EXTENSION_FOR_FILES = ".xlsx";
    public static final String FOLDER_TO_CHECK_FOR_FILES = "/home/pedro.costa/xlsFileCounter";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws IOException {

        List<String> fileNames = Files.walk(Paths.get(FOLDER_TO_CHECK_FOR_FILES))
                                      .map(java.nio.file.Path::toString)
                                      .filter(f -> f.endsWith(EXTENSION_FOR_FILES))
                                      .collect(Collectors.toList());

        List<String> toMatchList = Arrays.asList("7548730741733980000", "a0e0e6b09cc811e980255e225b9cba0d" ,"b39ba29e9cb511e9801d5673f58e580c","d56ce3f29caf11e980245e225f2c770d");

        String[] foundMatch = new String[100000];
        int iFoundMatch=0;

        String[] foundKey = new String[100000];


        for (String currentFile : fileNames) {
            InputStream is = new FileInputStream(currentFile);
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> rowIter = sheet.rowIterator();

            while (rowIter.hasNext()) {
                Cell cell = rowIter.next().getCell(1);

                for (String match : toMatchList) {
                    if (cell.getCellType().name().equalsIgnoreCase("STRING")) {

                        if (match.equalsIgnoreCase(String.valueOf(cell.getStringCellValue()))) {
                            iFoundMatch++;
                            foundMatch[iFoundMatch] = currentFile;
                            foundKey[iFoundMatch] = match;

                        }
                    } else {
                        if (match.equalsIgnoreCase(String.valueOf(cell.getNumericCellValue()))) {
                            iFoundMatch++;
                            foundMatch[iFoundMatch] = currentFile;
                            foundKey[iFoundMatch] = match;
                        }
                    }
                }
            }
        }
       return "RESULTS: " + buildResponse(foundMatch,foundKey) ;
    }

    private String buildResponse(String[] foundMatch, String[] foundKey){

        if(foundMatch.length==0){
            return "No matches found!!!!";
        }else{
            String returnString="";
            for (int i = 0; i < foundMatch.length ; i++) {
                returnString = returnString+ "File: "+ foundMatch[i] + " for key: "+foundKey[i];
            }
            return returnString;
        }
    }
}