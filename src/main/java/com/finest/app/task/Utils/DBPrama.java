package com.finest.app.task.Utils;

public class DBPrama {
    private String caseContent;
    private String crimeAddress;
    private String type;

    public DBPrama(String crimeAddress,String caseContent,String type)
    {
        this.caseContent = caseContent;
        this.crimeAddress = crimeAddress;
        this.type = type;
    }

    public String getCaseContent() {
        return caseContent;
    }

    public void setCaseContent(String caseContent) {
        this.caseContent = caseContent;
    }

    public String getCrimeAddress() {
        return crimeAddress;
    }

    public void setCrimeAddress(String crimeAddress) {
        this.crimeAddress = crimeAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}