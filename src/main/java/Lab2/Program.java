package Lab2;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        Program program1 = new Program();
        program1.run();
    }

    private void run() {
        FileHandler.fileHandler();
        Console.menu();
    }
}