package register.util;

import bill.Bill;
import register.Register;

import java.util.*;

public class RegisterUtil {

    public void show(Register register, boolean showTotal) {
        if(showTotal) {
            System.out.print("$" + register.getTotal() + " ");
        }
        register.getBills().forEach(bill -> System.out.print(bill.getCount() + " "));
        System.out.println();
    }

    public void put(Register register, String[] commandArgs) {
        try {
            List<Integer> newBills = parseArgs(commandArgs);
            int index = 0;

            for (Bill bill : register.getBills()) {
                bill.add(newBills.get(index++));
            }

            show(register, true);
        } catch (Exception e) {
            System.out.println("Invalid input for 'put'. Must be of the form: 'put 1 2 3 4 5' ");
        }
    }

    public void take(Register register, String[] commandArgs) {
        try {
            List<Integer> newBills = parseArgs(commandArgs);
            int index = 0;

            for (Bill bill : register.getBills()) {
                bill.subtract(newBills.get(index++));
            }

            show(register, true);
        } catch (Exception e) {
            System.out.println("Invalid input for 'take'. Must be of the form: 'take 1 2 3 4 5' ");
        }
    }

    public void change(Register register, String[] commandArgs) {
        try {
            int change = Integer.parseInt(commandArgs[1]);

            Map<Integer, Register> changeMap = computeChangeMap(register);

            if(changeMap.containsKey(change)) {
                Register changeRegister = changeMap.get(change);
                show(changeRegister, false);

                int index = 0;

                for (Bill bill : register.getBills()) {
                    bill.subtract(changeRegister.getBills().get(index++).getCount());
                }
            } else {
                System.out.println("Sorry");
            }

        } catch (Exception e) {
            System.out.println("Invalid input for 'change'. Must be of the form: 'change 10' ");
        }
    }

    private Map<Integer, Register> computeChangeMap(Register register) {
        Map<Integer, Register> changeMap = new HashMap<>();

        // Order our bills by the count of each denomination
        register.getBills().sort(Comparator.comparing(Bill::getCount));
        Bill first = register.getBills().get(4);
        Bill second = register.getBills().get(3);
        Bill third = register.getBills().get(2);
        Bill fourth = register.getBills().get(1);
        Bill fifth = register.getBills().get(0);

        for (int a = 1; a <= register.getBills().get(4).getCount(); a++) {
            // the individual bills of this denomination
            int firstSingles = a * first.getDenomination();
            Bill firstBill = new Bill(first.getDenomination(), a);
            List<Bill> firstBillList = new ArrayList<>(Arrays.asList(firstBill));
            addToMap(changeMap, firstSingles, firstBillList);

            for (int b = 1; b <= register.getBills().get(3).getCount(); b++) {
                int secondSingles = b * second.getDenomination();
                Bill secondBill = new Bill(second.getDenomination(), b);
                List<Bill> secondsBillList = new ArrayList<>(Arrays.asList(secondBill));
                addToMap(changeMap, secondSingles, secondsBillList);

                int firstAndSecond = firstSingles + secondSingles;
                secondsBillList.add(firstBill);
                addToMap(changeMap, firstAndSecond, secondsBillList);

                for (int c = 1; c <= register.getBills().get(2).getCount(); c++) {
                    int thirdSingles = c * third.getDenomination();
                    Bill thirdBill = new Bill(third.getDenomination(), c);
                    List<Bill> thirdBillList = new ArrayList<>(Arrays.asList(thirdBill));
                    addToMap(changeMap, thirdSingles, thirdBillList);

                    int secondAndThirds = thirdSingles + secondSingles;
                    thirdBillList.add(secondBill);
                    addToMap(changeMap, secondAndThirds, thirdBillList);

                    int firstSecondThirds = secondAndThirds + firstSingles;
                    thirdBillList.add(firstBill);
                    addToMap(changeMap, firstSecondThirds, thirdBillList);

                    for (int d = 1; d <= register.getBills().get(1).getCount(); d++) {
                        int fourthSingles = d * fourth.getDenomination();
                        Bill fourthBill = new Bill(fourth.getDenomination(), d);
                        List<Bill> fourthBillList = new ArrayList<>(Arrays.asList(fourthBill));
                        addToMap(changeMap, fourthSingles, fourthBillList);

                        int fourthThird = fourthSingles + thirdSingles;
                        fourthBillList.add(thirdBill);
                        addToMap(changeMap, fourthThird, fourthBillList);

                        int fourthThirdSecond = fourthThird + secondSingles;
                        fourthBillList.add(secondBill);
                        addToMap(changeMap, fourthThirdSecond, fourthBillList);

                        int fourthThirdSecondFirst = fourthThirdSecond + firstSingles;
                        fourthBillList.add(firstBill);
                        addToMap(changeMap, fourthThirdSecondFirst, fourthBillList);

                        for (int e = 1; e <= register.getBills().get(0).getCount(); e++) {
                            int fifthSingles = e * fifth.getDenomination();
                            Bill fifthBill = new Bill(fifth.getDenomination(), e);
                            List<Bill> fifthBillList = new ArrayList<>(Arrays.asList(fifthBill));
                            addToMap(changeMap, fifthSingles, fifthBillList);

                            int fifthFourth = fifthSingles + fourthSingles;
                            fifthBillList.add(fourthBill);
                            addToMap(changeMap, fifthFourth, fifthBillList);

                            int fifthFourthThird = fifthFourth + thirdSingles;
                            fifthBillList.add(thirdBill);
                            addToMap(changeMap, fifthFourthThird, fifthBillList);

                            int fifthFourthThirdSecond = fifthFourthThird + secondSingles;
                            fifthBillList.add(secondBill);
                            addToMap(changeMap, fifthFourthThirdSecond, fifthBillList);

                            int fifthFourthThirdSecondFirst = fifthFourthThirdSecond + firstSingles;
                            fifthBillList.add(firstBill);
                            addToMap(changeMap, fifthFourthThirdSecondFirst, fifthBillList);
                        }
                    }
                }
            }
        }

        register.getBills().sort(Comparator.comparing(Bill::getDenomination).reversed());
        return changeMap;
    }

    private void addToMap(Map<Integer, Register> map, int sum, List<Bill> billList) {
        Register register = new Register(0,0,0,0,0);
        billList.forEach(bill -> register.addByDenomination(bill));

        if(!map.containsKey(sum)) {
            map.put(sum, register);
        }
    }

    private List<Integer> parseArgs(String[] commandArgs) {
        try {
            return Arrays.asList(
                    Integer.parseInt(commandArgs[1]),
                    Integer.parseInt(commandArgs[2]),
                    Integer.parseInt(commandArgs[3]),
                    Integer.parseInt(commandArgs[4]),
                    Integer.parseInt(commandArgs[5]));
        } catch (Exception e) {
            throw e;
        }
    }
}
