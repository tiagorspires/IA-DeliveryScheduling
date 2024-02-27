import Packages.Package;

import java.util.Arrays;

public class Mutations {
    public static void mutation1(Package[] newPath) {
        int randomIndex1 = (int) (Math.random() * (newPath.length - 1));
        //index1 should be less than index2
        int randomIndex2 = (int) (Math.random() * (newPath.length - randomIndex1 - 1) + randomIndex1 + 1);

        //move the elements between index1 and index2 one position to the right
        Package temp = newPath[randomIndex2];
        System.arraycopy(newPath, randomIndex1, newPath, randomIndex1 + 1, randomIndex2 - randomIndex1);
        //put the element in index1 in index2
        newPath[randomIndex1] = temp;
    }

    public static void mutation2(Package[] newPath) {
        int randomIndex1 = (int) (Math.random() * (newPath.length - 2));
        int range = (int) (Math.random() * ((newPath.length - randomIndex1) / 2));

        Package[] temp = Arrays.copyOfRange(newPath, randomIndex1, randomIndex1 + range);

        System.arraycopy(newPath, randomIndex1 + range, newPath, randomIndex1, range);
        System.arraycopy(temp, 0, newPath, randomIndex1 + range, range);
    }

    public static void mutation3(Package[] newPath) {
        int randomIndex1 = (int) (Math.random() * (newPath.length));
        int randomIndex2 = (int) (Math.random() * (newPath.length - randomIndex1 - 1) + randomIndex1 + 1);

        //reverse the elements between index1 and index2
        for (int i = 0; i < (randomIndex2 - randomIndex1) / 2; i++) {
            Package temp = newPath[randomIndex1 + i];
            newPath[randomIndex1 + i] = newPath[randomIndex2 - i];
            newPath[randomIndex2 - i] = temp;
        }
    }
}
