package ravioli.gravioli.rpg.player;

public class BankAccount {
    private RPGPlayer owner;

    private int pocket;
    private int bank;

    public BankAccount(RPGPlayer owner) {
        this.owner = owner;
    }

    public int getPocket() {
        return this.pocket;
    }

    public int getBank() {
        return this.bank;
    }

    public void setPocket(int pocket) {
        this.pocket = pocket;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public void addPocket(int amount) {
        this.pocket += amount;
    }

    public void subtractPocket(int amount) {
        this.pocket -= amount;
    }

    public void addBank(int amount) {
        this.bank += amount;
    }

    public void subtractBank(int amount) {
        this.bank -= amount;
    }

    public void transferToBank(int amount) {
        this.bank += amount;
        this.pocket -= amount;
    }

    public void transferToPocket(int amount) {
        this.bank -= amount;
        this.pocket += amount;
    }
}
