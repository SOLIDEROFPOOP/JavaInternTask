package com.example.javataskinterview.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "data_tree")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    // Многие к одному: каждый элемент дерева имеет одного родителя
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_parent")
    private Tree parent;
    // Один ко многим: каждый элемент дерева может иметь много дочерних элементов
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    @JoinColumnOrFormula(column = @JoinColumn(name = "id_parent"))
    private List<Tree> children = new ArrayList<>();

}
