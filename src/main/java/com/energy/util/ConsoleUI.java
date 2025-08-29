package com.energy.util;


import com.energy.model.BuildingEnergySystem;
import com.energy.model.pricing.*;
import com.energy.model.state.*;


import java.util.Scanner;


public class ConsoleUI {
    private final Scanner sc = new Scanner(System.in);
    private final BuildingEnergySystem system;


    public ConsoleUI(BuildingEnergySystem system) { this.system = system; }


    public void run() {
        System.out.println("\n— سیستم مدیریت انرژی —\n");
        boolean loop = true;
        while (loop) {
            printMenu();
            int choice = readInt("گزینه: ");
            switch (choice) {
                case 1 -> changePricing();
                case 2 -> changeState();
                case 3 -> calcCost();
                case 4 -> System.out.println(system.status());
                case 5 -> loop = false;
                default -> System.out.println("گزینه نامعتبر");
            }
        }
        System.out.println("خروج از برنامه.");
    }


    private void printMenu() {
        System.out.println("\n1) تغییر تعرفه 2) تغییر وضعیت سیستم 3) محاسبه هزینه 4) مشاهده وضعیت 5) خروج");
    }


    private void changePricing() {
        System.out.println("تعرفه‌ها: 1) Standard 2) Peak Hours 3) Green Mode");
        int c = readInt("انتخاب: ");
        switch (c) {
            case 1 -> system.setPricing(new StandardPricing());
            case 2 -> system.setPricing(new PeakHoursPricing());
            case 3 -> system.setPricing(new GreenModePricing());
            default -> System.out.println("گزینه نامعتبر");
        }
        System.out.println("تعرفه جدید → " + system.getPricing().name());
    }


    private void changeState() {
        System.out.println("وضعیت‌ها: 1) Active 2) Eco Mode 3) Shutdown");
        int c = readInt("انتخاب: ");
        switch (c) {
            case 1 -> system.setState(new ActiveState());
            case 2 -> system.setState(new EcoModeState());
            case 3 -> system.setState(new ShutdownState());
            default -> System.out.println("گزینه نامعتبر");
        }
        System.out.println("وضعیت جدید → " + system.getState().name());
    }


    private void calcCost() {
        double units = readDouble();
        double cost = system.calculateCost(units);
        System.out.printf("هزینه مصرف (با توجه به وضعیت فعلی): %,.0f تومان\n", cost);
    }


    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ورودی نامعتبر؛ عدد صحیح وارد کنید.");
            }
        }
    }


    private double readDouble() {
        while (true) {
            try {
                System.out.print("تعداد واحد مصرف: ");
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ورودی نامعتبر؛ عدد اعشاری وارد کنید.");
            }
        }
    }
}