package com.energy.util;

import com.energy.model.pricing.PricingOption;
import com.energy.model.state.StateOption;
import com.energy.service.EnergyFacade;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner sc = new Scanner(System.in);
    private final EnergyFacade facade;
    private boolean isManager = false; // نقش ساده

    public ConsoleUI(EnergyFacade facade) {
        this.facade = facade;
    }

    public void run() {
        System.out.println("\n— سیستم مدیریت انرژی —\n");
        login();
        boolean loop = true;
        while (loop) {
            printMenu();
            int choice = readInt("گزینه: ");
            switch (choice) {
                case 1 -> { if (isManager) changePricing(); else deny(); }
                case 2 -> { if (isManager) changeState(); else deny(); }
                case 3 -> calcCost();
                case 4 -> System.out.println(facade.status());
                case 5 -> login();   // تعویض کاربر
                case 6 -> loop = false; // خروج
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
            System.out.println("\n[Occupant] 3) محاسبه هزینه  4) مشاهده وضعیت  5) تعویض کاربر  6) خروج");
        }
    }

    private void changePricing() {
        System.out.println("تعرفه‌ها:");
        for (var o : PricingOption.values())
            System.out.printf("%d) %s%n", o.code(), o.title());
        int c = readInt("انتخاب: ");
        try {
            var opt = PricingOption.fromCode(c);
            facade.selectPricing(opt); // ← به‌جای system.setPricing(...)
            System.out.println("تعرفه جدید → " + opt.title());
        } catch (IllegalArgumentException e) {
            System.out.println("گزینه نامعتبر");
        }
    }

    private void changeState() {
        System.out.println("وضعیت‌ها:");
        for (var o : StateOption.values())
            System.out.printf("%d) %s%n", o.code(), o.title());
        int c = readInt("انتخاب: ");
        try {
            var opt = StateOption.fromCode(c);
            facade.selectState(opt); // ← به‌جای system.setState(...)
            System.out.println("وضعیت جدید → " + opt.title());
        } catch (IllegalArgumentException e) {
            System.out.println("گزینه نامعتبر");
        }
    }

    private void calcCost() {
        double units = readDouble("تعداد واحد مصرف: ");
        double cost = facade.previewCost(units); // ← به‌جای system.calculateCost(...)
        System.out.printf("هزینه مصرف: %,.0f تومان%n", cost);
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
