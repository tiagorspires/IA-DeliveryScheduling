package Packages;

import java.util.Arrays;

public class Mutations {

    public static int randomIndex1;
    public static int randomIndex2;

    public static int mutationType = 1;

    public static void mutation1(Package[] newPath) {
        randomIndex1 = (int) (Math.random() * (newPath.length - 1));
        //index1 should be less than index2
        randomIndex2 = (int) (Math.random() * (newPath.length - randomIndex1 - 1) + randomIndex1 + 1);

        //move the elements between index1 and index2 one position to the right
        Package temp = newPath[randomIndex2];
        System.arraycopy(newPath, randomIndex1, newPath, randomIndex1 + 1, randomIndex2 - randomIndex1);
        //put the element in index1 in index2
        newPath[randomIndex1] = temp;
    }

    public static void mutation2(Package[] newPath) {
        randomIndex1 = (int) (Math.random() * (newPath.length - 2));
        randomIndex2 = (int) (Math.random() * ((newPath.length - randomIndex1) / 2));

        Package[] temp = Arrays.copyOfRange(newPath, randomIndex1, randomIndex1 + randomIndex2);

        System.arraycopy(newPath, randomIndex1 + randomIndex2, newPath, randomIndex1, randomIndex2);
        System.arraycopy(temp, 0, newPath, randomIndex1 + randomIndex2, randomIndex2);
    }

    public static void mutation3(Package[] newPath) {
        randomIndex1 = (int) (Math.random() * (newPath.length));
        randomIndex2 = (int) (Math.random() * (newPath.length - randomIndex1 - 1) + randomIndex1 + 1);

        //reverse the elements between index1 and index2
        for (int i = 0; i < (randomIndex2 - randomIndex1) / 2; i++) {
            Package temp = newPath[randomIndex1 + i];
            newPath[randomIndex1 + i] = newPath[randomIndex2 - i];
            newPath[randomIndex2 - i] = temp;
        }
    }

    public static void mutate(Package[] newPath) {
        if (mutationType == 1) {
            randomIndex1 = (int) (Math.random() * (newPath.length - 1));
            randomIndex2 = (int) (Math.random() * (newPath.length - 1));

            Package temp = newPath[randomIndex1];
            newPath[randomIndex1] = newPath[randomIndex2];
            newPath[randomIndex2] = temp;
        } else {
            //probabilities:
            // mutation1 89
            // mutation2 10
            // mutation3 1

            int random = (int) (Math.random() * 100);
            if (random < 89) {
                Mutations.mutation1(newPath);
            } else if (random < 99) {
                Mutations.mutation2(newPath);
            } else {
                Mutations.mutation3(newPath);
            }
        }
    }

}
