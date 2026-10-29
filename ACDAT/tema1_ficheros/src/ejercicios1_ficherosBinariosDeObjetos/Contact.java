package ejercicios1_ficherosBinariosDeObjetos;

public class Contact {


    private String name;
    private String phoneNumber;
    private String adress;
    private int pCode;
    private String birthDate;
    private boolean hasDebt;
    private double amount;

    public Contact(String name, String phoneNumber, String adress, int pCode, String birthDate, boolean debt, double amount) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.pCode = pCode;
        this.birthDate = birthDate;
        this.hasDebt = debt;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public int getpCode() {
        return pCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean hasDebt() {
        return hasDebt;
    }

    public double getAmount() {
        return amount;
    }
}
