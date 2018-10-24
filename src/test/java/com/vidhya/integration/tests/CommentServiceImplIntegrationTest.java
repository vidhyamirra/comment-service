package com.vidhya.integration.tests;

import com.vidhya.comment.dao.CommentRepository;
import com.vidhya.comment.services.CommentService;
import com.vidhya.comment.services.CommentServiceImpl;
import com.vidhya.model.CommentModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration
public class CommentServiceImplIntegrationTest {

	 @TestConfiguration
	    static class CommentServiceImplTestContextConfiguration {
	  
	        @Bean
	        public CommentService employeeService() {
	            return new CommentServiceImpl();
	        }
	    }
	 
    @MockBean
    private CommentRepository repository;

    @Autowired
    private CommentService service;

    private CommentModel model;

    @Before
    public void setup() {
      	CommentModel model = new CommentModel("10","1","vidhya","vidhya@maitri.com","Great Product",false,"",Instant.now(),"",Instant.now());
        Mockito.when(repository.findByName("vidhya"))
          .thenReturn(model);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "vidhya";
        CommentModel found = commentService/(name);
      
         assertThat(found.getName())
          .isEqualTo(name);
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