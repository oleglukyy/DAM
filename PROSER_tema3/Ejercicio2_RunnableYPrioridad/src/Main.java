public class Main {

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {

            RaisedTable raisedTable = new RaisedTable(i);

            Thread thread = new Thread(raisedTable, "Thread-" + i);

            if (i % 2 == 0) {
                thread.setPriority(Thread.MAX_PRIORITY);
            } else {
                thread.setPriority(Thread.MIN_PRIORITY);
            }

            thread.start();
        }
    }
}