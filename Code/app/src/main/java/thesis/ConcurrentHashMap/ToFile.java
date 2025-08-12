package thesis.ConcurrentHashMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import thesis.ConcurrentHashMap.OperationsHM.OperationHM;

public class ToFile {
    public static void saveToFile(ObservableConcurrentMap<?, ?> map, String filePath, Object actualValue) {
        // Implementation to save the outcome to a file

        List<? extends OperationHM<?, ?>> history = map.getHistory();
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("<html><body><h1>Final value: "+ actualValue + "<h1></br><table border='1'>\n");
            writer.write("<tr><th>Thread ID</th><th>Operation Type</th><th>Key</th><th>Previous value</th><th>Resulting value</th><th>Operation Counter</th></tr>\n");

            for (OperationHM<?, ?> op : history) {
                writer.write("<tr>");
                writer.write("<td>" + op.gettId() + "</td>");
                writer.write("<td>" + op.getOpType() + "</td>");
                writer.write("<td>" + op.getKey() + "</td>");
                writer.write("<td>" + op.getInput() + "</td>");
                writer.write("<td>" + op.getOutput() + "</td>");
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
