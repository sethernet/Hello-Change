package register;

import bill.Bill;
import register.util.RegisterUtil;

import java.util.*;


public class Register {

    private List<Bill> bills;
    private RegisterUtil registerUtil;

    public Register(int twenties, int tens, int fives, int twos, int ones) {
        this.registerUtil = new RegisterUtil();

        this.bills = Arrays.asList(
                new Bill(20, twenties),
                new Bill(10, tens),
                new Bill(5, fives),
                new Bill(2, twos),
                new Bill(1, ones));
    }

    public void handleInput(String command) {
        String[] commandArgs = command.split(" ");

        switch(commandArgs[0])
        {
            case "show" :
                registerUtil.show(this, true);
                break;

            case "put" :
                registerUtil.put(this, commandArgs);
                break;

            case "take" :
                registerUtil.take(this, commandArgs);
                break;

            case "change" :
                registerUtil.change(this, commandArgs);
                break;

            case "quit" :
                System.out.println("Bye");
                System.exit(0);
                break;

            default :
                System.out.println("Invalid entry. Input must begin with 'show', 'put', 'take', 'change', or 'quit'.");
        }
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void addByDenomination(Bill newBill) {
        for (Bill bill : bills) {
            if(bill.getDenomination() == newBill.getDenomination()) {
                bill.setCount(bill.getCount() + newBill.getCount());
            }
        }
    }

    public int getTotal() {
        int total = 0;
        for (Bill bill : bills) {
            total += bill.getDenomination() * bill.getCount();
        }

        return total;
    }
}
