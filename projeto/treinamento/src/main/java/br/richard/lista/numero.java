package br.richard.lista;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public abstract class numero {
    public static void main(String[] args) {

        List<Integer> numeros = new ArrayList<>();



        for (int i = 0; i < 6; i++){
            numeros.add((1 + 1) * i);
            System.out.println(numeros);
        }


    }
}
