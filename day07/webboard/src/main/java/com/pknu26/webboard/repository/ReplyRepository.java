package com.pknu26.webboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu26.webboard.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

}
