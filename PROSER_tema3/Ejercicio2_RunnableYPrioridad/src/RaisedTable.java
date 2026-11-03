public class RaisedTable implements Runnable {

    private final int number;

    public RaisedTable(int number) {
        this.number = number;
    }

    @Override
    public void run() {

        for (int i = 0; i <= 10; i++) {
            long result = 1;

            for (int j = 0; j < i; j++) {
                result *= number;
            }

            System.out.printf("%s: %d ^ %d = %d%n",
                    Thread.currentThread().getName(),
                    number, i, result);
        }
    }
}