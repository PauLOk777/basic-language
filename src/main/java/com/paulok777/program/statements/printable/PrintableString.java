package com.paulok777.program.statements.printable;

public class PrintableString extends Printable {
    private String printable;

    public PrintableString(String printable) {
        this.printable = "\"" + printable.substring(1, printable.length() - 1) + "\"";
    }

    public String getPrintable() {
        return printable;
    }

    public void setPrintable(String printable) {
        this.printable = printable;
    }

    @Override
    public String getPrintableString(boolean numberLine) {
        return printable;
    }

    @Override
    public String toString() {
        return "PrintableString {" +
                "printable=" + printable +
                '}';
    }
}
