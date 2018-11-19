import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Creator creator = new Creator();

        try {
            creator.create();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
