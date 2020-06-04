package com.pedro.costa.silva;

import java.util.Iterator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Pedro Costa (pedro.costa@feedzai.com).
 * @since 1.0
 */
@Path("/count")
public class CountResource {

    public static final boolean XLS_HAS_HEADER_IN_EACH_SHEET = true;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String count() {

        int totalLinesInXlsxFile = 0;

        List<String> fileNames = CommonUtils.getFileListFromFolder();

        for (String currentFile : fileNames) {
            Iterator<Row> rowIter = CommonUtils.rowIter(currentFile, 0);
            if (rowIter != null) {
                while (rowIter.hasNext()) {
                    totalLinesInXlsxFile++;
                    rowIter.next();
                }
            }
        }
        return "Counting files... \n" + buildResponseMessage(totalLinesInXlsxFile, fileNames.size());
    }

    private String buildResponseMessage(int totalLines, int numberOfFiles) {
        return CountResource.XLS_HAS_HEADER_IN_EACH_SHEET ? "Total lines:  " + (totalLines - numberOfFiles) + " in " + numberOfFiles + " files." :
                "Total lines:  "
                        + totalLines
                        + " in "
                        + numberOfFiles
                        + " files.";
    }
}