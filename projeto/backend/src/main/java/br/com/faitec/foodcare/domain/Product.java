package br.com.faitec.foodcare.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String nome;
    private double quantidade;
    private String dataDeValidade;
}
