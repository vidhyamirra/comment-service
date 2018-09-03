package com.vidhya.comment.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vidhya.model.CommentModel;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentModel, String>{

    List<CommentModel> findByPageId(String pageId);

    List<CommentModel> findByPageIdAndSpamIsTrue(String pageId);
}
