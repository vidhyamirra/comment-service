package com.vidhya.integration.tests;

import com.vidhya.comment.dao.CommentRepository;
import com.vidhya.comment.services.CommentService;
import com.vidhya.model.CommentModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration
public class CommentStoreServiceImplTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private CommentService service;

    private CommentModel model;

    @Before
    public void setup() {
        model = new CommentModel();
        model.setUsername("vidhya");
        model.setId("43290876");
        model.setPageId("raw food");
        model.setPageId("user@maitri.com");
        model.setComment("This is comment");
        repository.deleteAll();
    }

    @Test
    public void testPutAndGet() throws IOException {
        service.put(model);

        CommentModel dbModel = service.get(model.getId());
        assertNotNull(dbModel);
        assertEquals(model.getComment(), dbModel.getComment());
        assertEquals(model.getId(), dbModel.getId());
        assertEquals(model.getPageId(), dbModel.getPageId());
        assertEquals(model.getUsername(), dbModel.getUsername());
        assertEquals(model.getEmailAddress(), dbModel.getEmailAddress());

        assertNotNull(dbModel.getLastModificationDate());
        assertNotNull(dbModel.getCreationDate());
        assertFalse(model.isSpam());
    }

    @Test
    public void testListNotFound() throws IOException {
        service.put(model);
        List<CommentModel> comment = service.list("sdfgskdtgpghoefe");
        assertTrue(comment.isEmpty());
    }

    @Test
    public void testList() throws IOException {
        service.put(model);
        List<CommentModel> comment = service.list(model.getPageId());
        assertNotNull(comment);
        assertEquals(1, comment.size());
        assertEquals(model.getId(), comment.get(0));
    }

    @Test
    public void testListSpam() throws IOException {
        model.setComment("Blocked comment");
        service.put(model);
        List<CommentModel> comment = service.listSpamComments(model.getPageId());
        assertNotNull(comment);
        assertEquals(1, comment.size());
        assertEquals(model.getId(), comment.get(0).getId());
    }
}