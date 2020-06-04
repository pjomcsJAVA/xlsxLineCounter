package com.pedro.costa.silva;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Pedro Costa (pedro.costa@feedzai.com).
 * @since 1.1
 */
public class CommonUtils {

    public static final String EXTENSION_FOR_FILES = ".xlsx";
    public static final String FOLDER_TO_CHECK_FOR_FILES = "/home/pedro.costa/xlsFileCounter";

    protected static List<String> getFileListFromFolder(){
        try {
            return Files.walk(Paths.get(FOLDER_TO_CHECK_FOR_FILES))
                                          .map(java.nio.file.Path::toString)
                                          .filter(f -> f.endsWith(EXTENSION_FOR_FILES))
                                          .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    protected static Iterator<Row> rowIter (String currentFile, int sheetNumber) {

        try {
            InputStream is = new FileInputStream(currentFile);
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(sheetNumber);
            return sheet.rowIterator();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private CommonUtils() {
    }
}
