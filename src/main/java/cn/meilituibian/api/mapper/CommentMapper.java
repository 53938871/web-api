package cn.meilituibian.api.mapper;

import cn.meilituibian.api.domain.Comment;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    Long insertComment(Comment comment);

    Page<Comment> getCommentsByProjectId(Long projectId);

    List<Comment> getCommentsByOpenId(String openId);
}
