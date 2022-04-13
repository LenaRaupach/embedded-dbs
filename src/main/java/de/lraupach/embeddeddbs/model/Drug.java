package de.lraupach.embeddeddbs.model;

public class Drug {
    private String name;
    private String cas;

    public Drug(String name, String cas) {
        this.name = name;
        this.cas = cas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }
}
