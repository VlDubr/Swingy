package ru.srafe.swingy.model.inventory;

import java.io.Serializable;

public class Artifact implements Serializable {

    private static final long serialVersionUID = 3L;

    private ArtifactType type;
    private String name;
    private boolean isCursed;
    private int boost;

    public static final String[] artifactType = {"Оружие","Броня","Шлем"};

    protected Artifact(ArtifactType type, String name, boolean isCursed, int boost) {
        this.type = type;
        this.name = name;
        this.isCursed = isCursed;
        this.boost = boost;
    }

    public int getBoost(boolean getCurse) {
        if (getCurse && isCursed)
            return -boost;
        return boost;
    }

    public String getInfo(boolean getCurse) {
        if (getCurse && isCursed)
            return artifactType[type.ordinal()] + ": Усиление: " + -boost;
        return artifactType[type.ordinal()] + ": Усиление: " + boost;
    }

    public ArtifactType getType() {
        return type;
    }

    public void setType(ArtifactType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCursed() {
        return isCursed;
    }

    public void setCursed(boolean cursed) {
        isCursed = cursed;
    }

    public void setBoost(int boost) {
        this.boost = boost;
    }
}
