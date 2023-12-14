package com.example.javataskinterview.repositories;

import com.example.javataskinterview.entities.Tree;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree,Long> {
    // находит старшие категории с которых все начинается
    public List<Tree> findByParentIsNull();
    // ищет по имени
    Tree findByName(String name);
    // просто удаляет
    @Transactional
    void deleteTreeByName(String name);
}
