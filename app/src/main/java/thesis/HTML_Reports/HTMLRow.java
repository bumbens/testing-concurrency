package thesis.HTML_Reports;

public interface HTMLRow<T> {
    String gettId();
    String getOpType();
    T getValue();
    int getSizeBefore();
    int getSizeAfter();
    int getOpCounter();
} 