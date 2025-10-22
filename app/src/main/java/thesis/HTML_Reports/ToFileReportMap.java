package thesis.HTML_Reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ToFileReportMap {
    public static void saveToFile(String filePath, List <? extends HTMLRowMap<?,?>> rows) {
        // Implementation to save the outcome to a file

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("<html><body><table border='1'>\n");
            writer.write("<tr><th>Thread ID</th><th>Operation Type</th><th>Key</th><th>Previous value</th><th>Resulting value</th><th>Size Before</th><th>Size After</th><th>Operation Counter</th></tr>\n");

            for (HTMLRowMap<?,?> op : rows) {
                writer.write("<tr>");
                writer.write("<td>" + op.gettId() + "</td>");
                writer.write("<td>" + op.getOpType() + "</td>");
                writer.write("<td>" + op.getKey() + "</td>");
                writer.write("<td>" + op.getOldValueMap() + "</td>");
                writer.write("<td>" + op.getNewValueMap() + "</td>");
                writer.write("<td>" + op.getSizeBefore() + "</td>");
                writer.write("<td>" + op.getSizeAfter() + "</td>");
                writer.write("<td>" + op.getOpCounter() + "</td>");
                writer.write("</tr>\n");
            }

            writer.write("</table></body></html>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
