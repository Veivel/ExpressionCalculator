import java.util.List;

public class CalculationException extends RuntimeException{
    private long result;
    private List<String> postfix;

    public CalculationException(String msg, long result) {
        super(msg);
        this.result = result;
    }

    public CalculationException(String msg, List<String> postfix) {
        super(msg);
        this.postfix = postfix;
    }

    public long getResult() {
        return this.result;
    }

    public List<String> getPostfix() {
        return this.postfix;
    }
}
