package com.lspuspcc.localloan;

import java.util.ArrayList;

public class AddLoanClass{

        public static ArrayList<AddLoanClass> loanArrayList = new ArrayList<>();
        public static String loanAmount;
        public static String monthlyPayment;
        public static String numberOfPayments;
        public static int id;
        public static String interest;

        public AddLoanClass (int id, String loanAmount, String interest, String term, String monthlyPayment) {
                // Default constructor required for SQLite
        }

        public AddLoanClass(String loanAmount, String monthlyPayment,String numberOfPayments, int id, String interest) {
                this.loanAmount = loanAmount;
                this.monthlyPayment = monthlyPayment ;
                this.numberOfPayments = numberOfPayments;
                this.id = id;
                this.interest = interest;
        }

        public static String getLoanAmount() {
                return loanAmount;
        }

        public void setId(String loanAmount) {
                this.loanAmount = loanAmount;
        }

        public static String getMonthlyPayment() {
                return monthlyPayment;
        }

        public void setMonthlyPayment(String monthlyPayment) {
                this.monthlyPayment = monthlyPayment;
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
