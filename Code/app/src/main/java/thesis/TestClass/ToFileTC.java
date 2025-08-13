package thesis.TestClass;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import thesis.TestClass.OperationsTC.OperationTC;

public class ToFileTC {
    public static void saveToFile(TestClass<?> list, String filePath){
        List<? extends OperationTC<?>> history = list.getHistory();

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("<html><body><table border='1'>\n");
            writer.write("<tr><th>Thread ID</th><th>Operation Type</th><th>Value</th><th>Previous Size</th><th>Actual Size</th></tr>\n");

            for (OperationTC<?> op : history) {
                writer.write("<tr>");
                writer.write("<td>" + op.gettId() + "</td>");
                writer.write("<td>" + op.getOpType() + "</td>");
                writer.write("<td>" + op.getValue() + "</td>");
                writer.write("<td>" + op.getSizeBefore() + "</td>");
                writer.write("<td>" + op.getSizeAfter() + "</td>");
                writer.write("</tr>\n");
            }

            writer.write("</table></body></html>\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
