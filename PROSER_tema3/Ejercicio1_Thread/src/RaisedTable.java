public class RaisedTable extends Thread{

    private final int number;

    RaisedTable(int number){
        this.number=number;
    }

    @Override
    public void run(){
        for(int i =0; i<=10;i++){
            long result=1;
            System.out.printf("%d ^ %d = ", number , i);
            for(int j=0;j<i;j++){
                result *=number;
            }
            System.out.println(result);
        }
    }

}
