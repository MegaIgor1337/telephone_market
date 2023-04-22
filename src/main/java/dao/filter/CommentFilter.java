package dao.filter;

import entity.user.User;

public record CommentFilter(int limit,
                            int offset,
                            long id,
                            String comment,
                            User user) {
}
