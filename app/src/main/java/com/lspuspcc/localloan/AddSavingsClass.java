package com.lspuspcc.localloan;

import java.util.ArrayList;

public class AddSavingsClass {

    public static ArrayList<AddSavingsClass> savingsArrayList = new ArrayList<>();
    public static String principal;
    public static String monthlyContribution;
    public static String numberOfPayments;
    public static int id;
    public static String interest;

    public AddSavingsClass (int id, String principal, String interest, String term, String monthlyPayment) {
        // Default constructor required for SQLite
    }

    public AddSavingsClass(String principal, String monthlyContribution,String numberOfPayments, int id, String interest) {
        this.principal = principal;
        this.monthlyContribution = monthlyContribution ;
        this.numberOfPayments = numberOfPayments;
        this.id = id;
        this.interest = interest;
    }

    public static String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public static String getMonthlyContribution() {
        return monthlyContribution ;
    }

    public void setMonthlyContribution(String monthlyPayment) {
        this.monthlyContribution = monthlyContribution ;
    }

    public static String getnumberOfPayments() {
        return numberOfPayments;
    }

    public void setnumberOfPayments(String numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
