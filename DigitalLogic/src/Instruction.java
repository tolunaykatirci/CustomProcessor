public enum Instruction {
    AND("0001"),
    ADD("0010"),
    LD("0011"),
    ST("0100"),
    ANDI("0101"),
    ADDI("0110"),
    CMP("0111"),
    JUMP("1000"),
    JE("1001"),
    JA("1010"),
    JB("1011"),
    JBE("1100"),
    JAE("1101");

    private String action;

    Instruction(String action) {
        this.action = action;
    }

    public String getAction()
    {
        return this.action;
    }
}
