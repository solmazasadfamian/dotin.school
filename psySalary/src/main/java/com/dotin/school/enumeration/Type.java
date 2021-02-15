package main.java.com.dotin.school.enumeration;

public enum Type {
    DEBTOR("debtor"),CREDITOR("creditor");

    public final String label;

    private Type(String label) {
        this.label = label;
    }
}
