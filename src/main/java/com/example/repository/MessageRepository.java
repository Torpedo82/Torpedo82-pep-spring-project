package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{
}
