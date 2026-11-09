import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args) {

        System.out.println("Pulsa intro para comenzar el cronómetro ");

            try {
                while (true){
                    Process showTime = new ProcessBuilder(new String[]{"cmd.exe", "/c", "time"}).start();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(showTime.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line.split(" ")[4]);
                    }
                }

                try {
                    TimeUnit.MINUTES.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }

}
