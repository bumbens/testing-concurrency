package thesis.HTML_Reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;


public class ToFileReport {
    public static void saveToFile(String filePath, Collection<?> finalSet, List <? extends HTMLRow<?>> rows) {
        // Implementation to save the outcome to a file

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            writer.write("<h2>Final Set:</h2>");
            writer.write("<p>" + finalSet + "</p>");
            writer.write("<html><body><table border='1'>\n");
            writer.write("<tr><th>Thread ID</th><th>Operation Type</th><th>Value</th><th>Size Before</th><th>Size After</th><th>Operation Counter</th></tr>\n");

            for (HTMLRow<?> op : rows) {
                writer.write("<tr>");
                writer.write("<td>" + op.gettId() + "</td>");
                writer.write("<td>" + op.getOpType() + "</td>");
                writer.write("<td>" + op.getValue() + "</td>");
                writer.write("<td>" + op.getSizeBefore() + "</td>");
                writer.write("<td>" + op.getSizeAfter() + "</td>");
                writer.write("<td>" + op.getOpCounter() + "</td>");
                writer.write("</tr>\n");
            }
            writer.write("</table></body></html>\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
