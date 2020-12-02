package com.paulok777.printable;

public class PrintableString extends Printable {
    private String printable;

    public PrintableString(String printable) {
        this.printable = printable;
    }

    public String getPrintable() {
        return printable;
    }

    public void setPrintable(String printable) {
        this.printable = printable;
    }

    @Override
    public String getPrintableString() {
        return printable;
    }

    @Override
    public String toString() {
        return "PrintableString {" +
                "printable=" + printable +
                '}';
    }
}
