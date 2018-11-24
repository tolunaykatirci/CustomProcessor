import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Assembler {
    public static void main(String[] args) {

        String[] ISAs = {"ADD R5,R0,R2", "ADDI R3,R1,0", "LD R5,12", "JUMP 5", "JUMP -4"};
        List<String> ops = readInstructions("input.txt");
        List<String> hexCodes = new ArrayList<>();

        for (String a: ops) {
            System.out.println(assemblyParser(a));
            String hex = binaryToHex(assemblyParser(a));
            System.out.println(hex);
            hexCodes.add(hex);
        }

        writeToFile(hexCodes, "output.hex");
    }



    public static String assemblyParser(String line) {
        String[] parts = line.split(" ", 2);
        Instruction instruction = Instruction.valueOf(parts[0].trim());
        parts[1] = parts[1].trim();

        if( instruction == Instruction.ADD || instruction == Instruction.AND ){
            String[] registers = parts[1].split(",");
            Register dst = Register.valueOf(registers[0].trim());
            Register src1 = Register.valueOf(registers[1].trim());
            Register src2 = Register.valueOf(registers[2].trim());
            return instruction.getAction() +
                    dst.getAction() +
                    src1.getAction() +
                    src2.getAction();
        }
        else if (instruction == Instruction.ADDI || instruction == Instruction.ANDI ){
            String[] registers = parts[1].split(",");
            Register dst = Register.valueOf(registers[0].trim());
            Register src1 = Register.valueOf(registers[1].trim());
            Integer imm = Integer.parseInt(registers[2].trim());
            return instruction.getAction() +
                    dst.getAction() +
                    src1.getAction() +
                    intToBinary(imm.toString(),4);
        }
        else if (instruction == Instruction.JUMP ||
                instruction == Instruction.JE ||
                instruction == Instruction.JA ||
                instruction == Instruction.JB ||
                instruction == Instruction.JAE ||
                instruction == Instruction.JBE
                ){
            return instruction.getAction() + intToBinary(parts[1],12);
        }
        else if (instruction == Instruction.CMP) {
            String[] registers = parts[1].split(",");
            Register op1 = Register.valueOf(registers[0].trim());
            Register op2 = Register.valueOf(registers[1].trim());
            return instruction.getAction() +
                    op1.getAction() +
                    op2.getAction() +
                    " 0000";
        }
        else if (instruction == Instruction.LD || instruction == Instruction.ST){
            String[] registers = parts[1].split(",");
            Register reg = Register.valueOf(registers[0].trim());
            String addr = intToBinary(registers[1].trim(), 8);

            return instruction.getAction() +
                    reg.getAction() +
                    addr;
        }
        else {
            return instruction.getAction() + parts[1];
        }
    }


    public static String intToBinary(String value, int limit) {
        int value2 = Integer.parseInt(value);
        String a = Integer.toBinaryString(value2);
        if (value2 >= 0){ //positive
            while (a.length() < limit){
                a = "0" + a;
            }
        }
        else { //negative
            a = a.substring(a.length() - limit);
        }
        return a;
    }

    public static List<String> readInstructions(String path){
        List<String> values = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = br.readLine()) != null){
                values.add(currentLine);
            }
        }
        catch (Exception e){
           e.printStackTrace();
        }
        finally {
            try {
                br.close();
                fr.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return values;
        }
    }

    public static String binaryToHex(String binary){
        return Integer.toHexString(Integer.parseInt(binary, 2));
    }

    public static void writeToFile(List<String> hexCodes, String path){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(path);
            fileWriter.write("v2.0 raw\n");
            for (String code : hexCodes) {
                fileWriter.write(code + " ");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                fileWriter.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
