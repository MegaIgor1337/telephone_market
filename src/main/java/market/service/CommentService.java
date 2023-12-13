package market.service;

import market.service.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAccessedComments();
    CommentDto save(String commentStr, Long userId);
    List<CommentDto> findCommentsByUserId(Long id);
    List<CommentDto> getModerateComments();
    void updateCommentFromModeratingToAccess(Long id);
    boolean deleteComment(Long id);
}
