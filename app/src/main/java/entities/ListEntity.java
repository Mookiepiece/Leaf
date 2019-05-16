package entities;

import util.PriceTransUtil;

public class ListEntity{
    private String month;
    private int balance100;
    private String note;

    public ListEntity() {
    }

    public ListEntity(String month, int balance100, String note) {
        this.month = month;
        this.balance100 = balance100;
        this.note = note;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getBalance100() {
        return balance100;
    }
    public String  getBalanceDecimal() {
        return PriceTransUtil.Int2Decimal(balance100);
    }

    public void setBalance100(int balance100) {
        this.balance100 = balance100;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
