package details.dtoAction;

public class DtoCalculation {

    private final String arg1;
    private final String arg2;
    private final String arithmetic;

    public DtoCalculation(String arg1, String arg2, String arithmetic) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arithmetic = arithmetic;
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public String getArithmetic() {
        return arithmetic;
    }
}
