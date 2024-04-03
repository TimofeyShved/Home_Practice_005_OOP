package com.company;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int myZnach[] = {1, 2, 3};
        int myVes[] = {1, 2, 10};

        Ves v = new Ves(myZnach, myVes);
        System.out.println("finish: " + v.randomZnach());
    }
}

class Ves{

    int znach[], ves[];

    Ves(int znach[], int ves[]){
        if (znach.length == ves.length) {
            this.znach = znach;
            this.ves = ves;
        }else {
            System.out.println("error matrix");
        }
    }

    int randomZnach(){

        boolean r = false;
        int rzch = 0;

        int newVes[] = ves;
        Arrays.sort(newVes);

        while (r == false){
            for (int i = newVes.length-1; i >= 0; i--) {
                for (int k = 0; k < ves.length; k++) {

                    if (newVes[i]==ves[k] && r == false) {

                        for (int j = 0; j < newVes[i]; j++) {

                            int randomNumber = new Random().nextInt(znach.length);
                            if (randomNumber == znach[k]){
                                rzch=randomNumber;
                                r = true;
                                return rzch;
                            }
                        }

                    }

                }
            }
        }
        return rzch;
    }
}