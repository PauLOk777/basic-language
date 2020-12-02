package com.paulok777.program.statements.printable;

public abstract class Printable {
    public abstract String getPrintableString(boolean numberLine);

    public String getPrintableString() {
        return getPrintableString(false);
    }
}
