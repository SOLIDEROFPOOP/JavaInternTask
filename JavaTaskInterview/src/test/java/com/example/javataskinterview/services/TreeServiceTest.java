package com.example.javataskinterview.services;

import com.example.javataskinterview.repositories.TreeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TreeServiceTest {
    @Autowired
    private TreeService treeService;
    @Test
    void addElement(){
        treeService.addElement("ok");
    }
    @Test
    void testAddElement() {
        treeService.addElement("test1", "test1child1");
    }
    @Test
    void removeElement(){
        treeService.removeElement("ok");
    }
    @Test
    void viewTree(){
        String ok = treeService.viewTree();
        System.out.println(ok);
    }
}