package com.vidhya.comment.services;

import com.vidhya.comment.api.LoggingController;
import com.vidhya.comment.dao.CommentRepository;
import com.vidhya.model.CommentModel;
import com.vidhya.spamdetection.services.SpamDetectorImpl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    
	private static final org.apache.logging.log4j.Logger loggerNative = LogManager.getLogger(LoggingController.class);
    
	@Autowired 
    private CommentRepository commentRepository;

    @Autowired
    private SpamDetectorImpl spamDetectorImpl;

    @Override
    public String put(CommentModel model) throws IOException {
    	loggerNative.info("START :: Inside the save comment ......");
        if (StringUtils.isEmpty(model.getId())) {
            model.setId(UUID.randomUUID().toString());
        }
    	loggerNative.info("Validating the comments for spam words.....");

        if(spamDetectorImpl.isSpam(model.getUsername()) ||
            spamDetectorImpl.isSpam(model.getEmailAddress()) ||
                spamDetectorImpl.isSpam(model.getComment())) {
            model.setSpam(true);
        	loggerNative.info("Comments has spam words.....");

        }
    	loggerNative.info("No Spam words in review comments....");

        final Optional<CommentModel> dbModel = get(model.getId());
        if (dbModel != null) {
        	dbModel.get().setUsername(model.getUsername());
            dbModel.get().setComment(model.getComment());
            commentRepository.save(dbModel);
        }
        else {
            commentRepository.save(model);
        }
        loggerNative.info("END :: User Review Comment saved successfully");
        return model.getId();
    }

    @Override
    public List<CommentModel> list(String pageId) throws IOException {
    	loggerNative.info("START :: Inside get all comments for pageId");
        List<CommentModel> commentModelLst = this.commentRepository.findAll();
    	loggerNative.info("END :: Inside get all comments for pageId");

		return commentModelLst;
    }

    @Override
    public Optional<CommentModel> get(String id) {
    	loggerNative.info("START :: Inside get user comment by Id"+id);
    	 Optional<CommentModel> comment = commentRepository.findById(id);
    	loggerNative.info("END :: Inside get user comment by Id;"+id);
        return comment;
     }

    @Override
    public List<CommentModel> listSpamComments(String pageId) throws IOException {
    	loggerNative.info("START :: Inside get all spam comments by pageId"+pageId);
    	List<CommentModel> listSpamCommments = this.commentRepository.findByPageIdAndSpamIsTrue(pageId);
    	loggerNative.info("END :: Inside get all spam comments by pageId"+pageId);
        return listSpamCommments;
    }

    @Override
    public void delete(String id) {

    }

	@Override
	public CommentModel getCommentsByUserName(String name) {
		// TODO Auto-generated method stub
		return this.commentRepository.findByName(name);
	}
}
