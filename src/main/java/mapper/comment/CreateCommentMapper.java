package mapper.comment;

import dao.UserDao;
import dto.CreateCommentDto;
import entity.Comment;
import mapper.Mapper;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

public class CreateCommentMapper implements Mapper<CreateCommentDto, Comment> {
    public static final CreateCommentMapper INSTANCE = new CreateCommentMapper();
    private final UserDao userDao = UserDao.getINSTANCE();
    private final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    @Override
    public Comment mapFrom(CreateCommentDto object) {
        return Comment.builder()
                .id(Long.valueOf(object.getId()))
                .comment(object.getComment())
                .user(userDao.find(sessionFactory, Long.valueOf(object.getUserId())).get())
                .build();
    }

    public static CreateCommentMapper getInstance() {
        return INSTANCE;
    }
}
