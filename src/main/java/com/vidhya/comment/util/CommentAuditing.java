/**
 * 
 */
package com.vidhya.comment.util;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * @author VidhyaSrinivasan
 *
 */
@Component
public class CommentAuditing implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Vidhya");
    }

}