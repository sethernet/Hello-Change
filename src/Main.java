import register.Register;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("ready");

        Scanner in = new Scanner(System.in);
        Register register = new Register(0, 0, 0, 0, 0);

        while(in.hasNextLine()){
            String command = in.nextLine();
            register.handleInput(command);
        }
    }
}
