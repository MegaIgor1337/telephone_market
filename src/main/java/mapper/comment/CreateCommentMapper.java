package mapper.comment;

import dao.UserRepository;
import dto.CreateCommentDto;
import entity.Comment;
import lombok.Getter;
import mapper.Mapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

public class CreateCommentMapper implements Mapper<CreateCommentDto, Comment> {
    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Getter
    public static final CreateCommentMapper INSTANCE = new CreateCommentMapper();
    private final UserRepository userRepository = new UserRepository(HibernateUtil
            .getSessionFromFactory(sessionFactory));

    @Override
    public Comment mapFrom(CreateCommentDto object) {
        return Comment.builder()
                .id(Long.valueOf(object.getId()))
                .comment(object.getComment())
                .user(userRepository.findById(Long.valueOf(object.getUserId())).get())
                .build();
    }

}
