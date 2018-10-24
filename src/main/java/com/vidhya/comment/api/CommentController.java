/**
 * 
 */
package com.vidhya.comment.api;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidhya.comment.services.CommentService;
import com.vidhya.model.CommentModel;

/**
 * @author VidhyaSrinivasan
 * 
 * This class is to post comments in to the database
 *
 */
@RestController
@RequestMapping("/comment/v1")
public class CommentController {
	private final org.apache.logging.log4j.Logger loggerNative = LogManager.getLogger(this.getClass());

	@Autowired
	CommentService commentService;
	
	@PostMapping
	public void saveComment(@RequestBody CommentModel model) {
		try {
			loggerNative.info("Invoking saveComment...");
			this.commentService.put(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping
	public void getAllComments(@RequestBody String pageId) {
		 try {
			loggerNative.info("Invoking get all user comments...");
			this.commentService.list(pageId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@GetMapping
	public void getSpamCommentsByPage(@RequestBody String pageId) {
		try {
			loggerNative.info("Invoking get all spam comments...");
			this.commentService.listSpamComments(pageId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
