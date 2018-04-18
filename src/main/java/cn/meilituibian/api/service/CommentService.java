package cn.meilituibian.api.service;

import cn.meilituibian.api.domain.Comment;
import cn.meilituibian.api.mapper.CommentMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public Comment insertComment(Comment comment) {
        Long id = commentMapper.insertComment(comment);
        comment.setId(id);
        return comment;
    }

    public Page<Comment> getCommentsByProjectId(Long projectId, int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        return commentMapper.getCommentsByProjectId(projectId);
    }

    public List<Comment> getCommentsByOpenId(String openId){
        return commentMapper.getCommentsByOpenId(openId);
    }
}
