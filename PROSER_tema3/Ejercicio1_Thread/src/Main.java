public class Main {

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            RaisedTable table = new RaisedTable(i);
            table.start();
        }

    }

}
