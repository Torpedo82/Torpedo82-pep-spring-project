package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer>{
    List<Message> findBypostedBy(int postedBy);
}
