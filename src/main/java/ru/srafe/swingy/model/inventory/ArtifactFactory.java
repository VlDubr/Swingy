package ru.srafe.swingy.model.inventory;

import java.util.Random;

import static ru.srafe.swingy.model.inventory.ArtifactType.*;

public class ArtifactFactory {

    public static Artifact getRandomArtifact(int villainLevel) {
        Random random = new Random(System.currentTimeMillis());
        ArtifactType type = WEAPON;
        int temp = random.nextInt(3);
        switch (temp) {
            case (0):
                type = WEAPON;
                break;
            case (1):
                type = ARMOR;
                break;
            case (2):
                type = HELM;
        }

        int boost = random.nextInt(10 * villainLevel);
        return new Artifact(type, type.toString(), random.nextInt(100) < 5, boost);
    }

    public static Artifact cloneArtifact(Artifact artifact) {
        return new Artifact(artifact.getType(), artifact.getName(), artifact.isCursed(), artifact.getBoost(false));
    }
}
