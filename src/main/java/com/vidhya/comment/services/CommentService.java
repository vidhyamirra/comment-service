package com.vidhya.comment.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.vidhya.model.CommentModel;

public interface CommentService {

    String put(CommentModel model) throws IOException;

    List<CommentModel> list(String pageId) throws IOException;

    Optional<CommentModel> get(String id);

    List<CommentModel> listSpamComments(String pageId) throws IOException;

    void delete(String id);
    
    CommentModel getCommentsByUserName(String name);
}
