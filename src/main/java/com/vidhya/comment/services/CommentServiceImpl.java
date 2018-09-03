package com.vidhya.comment.services;


import com.vidhya.comment.dao.CommentRepository;
import com.vidhya.model.CommentModel;
import com.vidhya.spamdetection.services.SpamDetectorImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired 
    private CommentRepository commentRepository;

    @Autowired
    private SpamDetectorImpl spamDetectorImpl;

    @Override
    @Transactional
    public String put(CommentModel model) throws IOException {
        if (StringUtils.isEmpty(model.getId())) {
            model.setId(UUID.randomUUID().toString());
        }
        if(spamDetectorImpl.isSpam(model.getUsername()) ||
            spamDetectorImpl.isSpam(model.getEmailAddress()) ||
                spamDetectorImpl.isSpam(model.getComment())) {
            model.setSpam(true);
        }

        final CommentModel dbModel = get(model.getId());
        if (dbModel != null) {
            dbModel.setUsername(model.getUsername());
            dbModel.setComment(model.getComment());
            dbModel.setLastModificationDate(Calendar.getInstance());
            commentRepository.save(dbModel);
        }
        else {
            model.setCreationDate(Calendar.getInstance());
            model.setLastModificationDate(Calendar.getInstance());
            commentRepository.save(model);
        }
        return model.getId();
    }

    @Override
    public List<CommentModel> list(String pageId) throws IOException {
        return null;
    }

    @Override
    public CommentModel get(String id) {
        return commentRepository.findOne(id);
    }

    @Override
    public List<CommentModel> listSpamComments(String pageId) throws IOException {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
