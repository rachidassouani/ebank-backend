package io.rachidassouani.ebankbackend.dto;

public class TransferDTO {
    private long idAccountSource;
    private long idAccountDestination;
    private double amount;
    private String description;

    public long getIdAccountSource() {
        return idAccountSource;
    }

    public void setIdAccountSource(long idAccountSource) {
        this.idAccountSource = idAccountSource;
    }

    public long getIdAccountDestination() {
        return idAccountDestination;
    }

    public void setIdAccountDestination(long idAccountDestination) {
        this.idAccountDestination = idAccountDestination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
