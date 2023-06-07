package market.mapper.comment;

import market.repository.UserRepository;
import market.dto.CreateCommentDto;
import market.entity.Comment;
import lombok.RequiredArgsConstructor;
import market.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCommentMapper implements Mapper<CreateCommentDto, Comment> {

    private final UserRepository userRepository;

    @Override
    public Comment mapFrom(CreateCommentDto object) {
        return Comment.builder()
                .id(Long.valueOf(object.getId()))
                .comment(object.getComment())
                .user(userRepository.findById(Long.valueOf(object.getUserId())).get())
                .build();
    }

}
