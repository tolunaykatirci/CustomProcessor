public enum Register {
    R0("0000"),
    R1("0001"),
    R2("0010"),
    R3("0011"),
    R4("0100"),
    R5("0101"),
    R6("0110"),
    R7("0111"),
    R8("1000"),
    R9("1001"),
    R10("1010"),
    R11("1011"),
    R12("1100"),
    R13("1101"),
    R14("1110"),
    R15("1111");

    private String action;

    Register(String action) {
        this.action = action;
    }

    public String getAction()
    {
        return this.action;
    }

}
