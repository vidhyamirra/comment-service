/**
 * 
 */
package com.vidhya.comment.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vidhya.model.CommentModel;

/**
 * @author VidhyaSrinivasan
 * @param <S>
 *
 */
public interface CommentRepository<S> extends MongoRepository<CommentModel, String> {

    void save (Optional<CommentModel> commentModel);
    
    List<CommentModel> findByPageId(String pageId);

    List<CommentModel> findByPageIdAndSpamIsTrue(String pageId);
    
    CommentModel findByName(String name);
}
