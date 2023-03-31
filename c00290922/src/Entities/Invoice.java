/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This is the data needed to save the Invoice into the database
 *      it will be called primarily when working with invoice service
 */
package Entities;

import java.time.LocalDate;

public class Invoice {
    private int invoiceId;
    private String dateCreated;
    private double amountDue;
    private Invoice.Status status;
    private LocalDate dueDate;
    private double totalCost;
    private double amountPaid;
    private int customerId;
    public enum Status {
        // only 6 statuses can be defined at this point so we will use enum for it
        draft,
        approved,
        sent,
        paid,
        overdue
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
