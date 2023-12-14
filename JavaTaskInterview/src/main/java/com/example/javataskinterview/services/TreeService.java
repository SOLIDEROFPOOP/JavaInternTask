package com.example.javataskinterview.services;

import com.example.javataskinterview.entities.Tree;
import com.example.javataskinterview.repositories.TreeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TreeService {
    private final TreeRepository treeRepository;
    @Autowired
    public TreeService(TreeRepository treeRepository){
        this.treeRepository = treeRepository;
    }
    // Показывает Все древо если оно пустое то возвращает текст то что он пустой
    public String viewTree(){
        List<Tree> rootNodes = treeRepository.findByParentIsNull();
        if (rootNodes.isEmpty()) return "empty add categories instead";
        StringBuilder tree = new StringBuilder();
        for (Tree t : rootNodes){
            tree.append(viewNode(t , 0));
        }
        return tree.toString();
    }
    // Рекурсивно показывает все категории внутри родительского класса показывает абсолютно все
    private String viewNode(Tree node, int depth){
        StringBuilder nodeView = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            nodeView.append("  ");
        }

        // Add node's name
        nodeView.append(node.getName()).append("\n");

        // Recursively process children
        for (Tree child : node.getChildren()) {
            nodeView.append(viewNode(child, depth + 1));
        }

        return nodeView.toString();
    }
    // Добавляет елемент если введен один то это главный
    public void addElement(String name){
        Tree newTree = new Tree();
        newTree.setName(name);
        newTree.setParent(null);
        newTree.setChildren(null);
        treeRepository.save(newTree);
    }
    // Если введены два то это вводит младшую категорию в старшего
    public void addElement(String parentName, String name){
        Tree currentTree = treeRepository.findByName(parentName);
        if (currentTree != null){
            Tree newTree = new Tree();
            newTree.setName(name);
            newTree.setParent(currentTree);
            List<Tree> children = currentTree.getChildren();
            children.add(newTree);
            currentTree.setChildren(children);
            treeRepository.save(currentTree);
        }else {

        }
    }
    // ну это обычное удаление
    public void removeElement(String name){
        treeRepository.deleteTreeByName(name);
    }

}
