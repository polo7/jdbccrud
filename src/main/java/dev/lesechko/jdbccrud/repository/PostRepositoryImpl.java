package dev.lesechko.jdbccrud.repository;

import dev.lesechko.jdbccrud.model.Post;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    @Override
    public boolean save(Post post) {
        return false;
    }

    @Override
    public List<Post> getAll() {
//        SELECT * FROM posts LEFT JOIN post_labels ON post_labels.postId = posts.id LEFT JOIN labels ON post_labels.labelId = labels.id;
        return null;
    }

    @Override
    public Post getById(Long id) {
        return null;
    }

    @Override
    public boolean update(Post post) {
        String sqlClearPostLabels = "DELETE FROM post_labels WHERE id = ?";
        try (PreparedStatement stmntClearPostLabels = DbConnection.getPreparedStatement(sqlClearPostLabels)) {
            // 1. проходим по post_labels и удаляем все упоминания по этому postId
            stmntClearPostLabels.setLong(1, post.getId());
            stmntClearPostLabels.executeUpdate();

            // 2. открываем список post.getLabels() и добавляем новые labels, если такие есть
            String sqlAddPostLabels = "INSERT INTO post_labels (postId, labelId) VALUES (?, ?)";
            try (PreparedStatement stmntAddPostLabels = DbConnection.getPreparedStatement(sqlAddPostLabels)) {
                for (var label : post.getLabels()) {
                    stmntAddPostLabels.setLong(1, post.getId());
                    stmntAddPostLabels.setLong(2, label.getId());
                    stmntAddPostLabels.addBatch();
                }
                stmntAddPostLabels.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "UPDATE posts SET title = ?, content = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setString(1, post.getTitle());
            stmnt.setString(2, post.getContent());
            stmnt.setString(3, post.getStatus().name());
            stmnt.setLong(4, post.getId());
            return stmnt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
//        String sql = "DELETE FROM labels WHERE id = ?";
        String sql = "UPDATE posts SET status = 'DELETED' WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            return stmnt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
