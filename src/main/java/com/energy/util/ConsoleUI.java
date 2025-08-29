package com.energy.util;

import com.energy.model.BuildingEnergySystem;
import com.energy.model.pricing.*;
import com.energy.model.state.*;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner sc = new Scanner(System.in);
    private final BuildingEnergySystem system;
    private boolean isManager = false;

    public ConsoleUI(BuildingEnergySystem system) { this.system = system; }

    public void run() {
        System.out.println("\n— سیستم مدیریت انرژی —\n");
        login();
        boolean loop = true;
        while (loop) {
            printMenu();
            int choice = readInt("گزینه: ");
            switch (choice) {
                case 1 -> { // تغییر تعرفه (فقط مدیر)
                    if (isManager) changePricing(); else deny();
                }
                case 2 -> { // تغییر وضعیت (فقط مدیر)
                    if (isManager) changeState(); else deny();
                }
                case 3 -> calcCost();            // همه
                case 4 -> System.out.println(system.status()); // همه
                case 5 -> login();               // تعویض کاربر
                case 6 -> loop = false;          // خروج
                default -> System.out.println("گزینه نامعتبر");
            }
        }
        System.out.println("خروج از برنامه.");
    }

    private void login() {
        System.out.println("ورود: 1) Manager  2) Occupant");
        int c = readInt("انتخاب: ");
        isManager = (c == 1);
        System.out.println(isManager ? "به‌عنوان Manager وارد شدید." : "به‌عنوان Occupant وارد شدید.");
    }

    private void printMenu() {
        if (isManager) {
            System.out.println("\n[Manager] 1) تغییر تعرفه  2) تغییر وضعیت  3) محاسبه هزینه  4) مشاهده وضعیت  5) تعویض کاربر  6) خروج");
        } else {
            // شماره‌ها را ثابت نگه می‌داریم تا سوییچ بالا تغییر نکند
            System.out.println("\n[Occupant] 3) محاسبه هزینه  4) مشاهده وضعیت  5) تعویض کاربر  6) خروج");
        }
    }

    private void changePricing() {
        System.out.println("تعرفه‌ها: 1) Standard  2) Peak Hours  3) Green Mode");
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
        System.out.println("وضعیت‌ها: 1) Active  2) Eco Mode  3) Shutdown");
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
        double units = readDouble("تعداد واحد مصرف: ");
        double cost = system.calculateCost(units);
        System.out.printf("هزینه مصرف (با توجه به وضعیت فعلی): %,.0f تومان%n", cost);
    }

    private void deny() { System.out.println("اجازه این عمل را ندارید."); }

    private int readInt(String prompt) {
        while (true) {
            try { System.out.print(prompt); return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("ورودی نامعتبر؛ عدد صحیح وارد کنید."); }
        }
    }
    private double readDouble(String prompt) {
        while (true) {
            try { System.out.print(prompt); return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("ورودی نامعتبر؛ عدد اعشاری وارد کنید."); }
        }
    }
}
