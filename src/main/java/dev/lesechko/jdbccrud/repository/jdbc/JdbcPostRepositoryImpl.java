package dev.lesechko.jdbccrud.repository.jdbc;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.utils.DbConnectionUtils;
import dev.lesechko.jdbccrud.repository.PostRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {
    private void addLabelsForPostId(List<Label> labels, Long postId) throws SQLException {
        String sql = "INSERT INTO post_labels (postId, labelId) VALUES (?, ?)";
        try (PreparedStatement stmnt = DbConnectionUtils.getPreparedStatement(sql)) {
            for (var label : labels) {
                stmnt.setLong(1, postId);
                stmnt.setLong(2, label.getId());
                stmnt.addBatch();
            }
            stmnt.executeBatch();
        }
    }

    @Override
    public Post save(Post post) {
        String sql = "INSERT INTO posts (title, content, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmnt = DbConnectionUtils.getPreparedStatement(sql)) {
            stmnt.setString(1, post.getTitle());
            stmnt.setString(2, post.getContent());
            stmnt.setString(3, post.getStatus().name());
            if (stmnt.executeUpdate() == 0) return null;
            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            generatedKeys.next();
            Long insertedId = generatedKeys.getLong(1);
            addLabelsForPostId(post.getLabels(), insertedId);
            post.setId(insertedId);
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT id, title, status FROM posts";
        try (PreparedStatement stmnt = DbConnectionUtils.getPreparedStatement(sql)) {
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setStatus(Status.valueOf(rs.getString("status")));
                posts.add(post);
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Post getById(Long id) {
        Post post = null;
        String sql = """
                SELECT * FROM posts 
                LEFT JOIN post_labels ON post_labels.postId = posts.id 
                LEFT JOIN labels ON post_labels.labelId = labels.id
                WHERE posts.id = ?""";
        try (PreparedStatement stmnt = DbConnectionUtils.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setStatus(Status.valueOf(rs.getString("status")));
                List<Label> labels = new ArrayList<>();
                do {
                    if (rs.getString("name") != null) {
                        Label label = new Label();
                        label.setId(rs.getLong("labelId"));
                        label.setName(rs.getString("name"));
                        label.setStatus(Status.valueOf(rs.getString(10)));
                        labels.add(label);
                    }
                } while (rs.next());
                post.setLabels(labels);
            }
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Post update(Post post) {
        String sqlClearPostLabels = "DELETE FROM post_labels WHERE postId = ?";
        try (PreparedStatement stmntClearPostLabels = DbConnectionUtils.getPreparedStatement(sqlClearPostLabels)) {
            // 1. проходим по post_labels и удаляем все упоминания по этому postId
            stmntClearPostLabels.setLong(1, post.getId());
            stmntClearPostLabels.executeUpdate();
            // 2. открываем список post.getLabels() и добавляем новые labels, если такие есть
            addLabelsForPostId(post.getLabels(), post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 3. обновляем сам пост
        String sql = "UPDATE posts SET title = ?, content = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmnt = DbConnectionUtils.getPreparedStatement(sql)) {
            stmnt.setString(1, post.getTitle());
            stmnt.setString(2, post.getContent());
            stmnt.setString(3, post.getStatus().name());
            stmnt.setLong(4, post.getId());
            if (stmnt.executeUpdate() == 0) return null;
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
//        String sql = "DELETE FROM labels WHERE id = ?";
        String sql = "UPDATE posts SET status = 'DELETED' WHERE id = ?";
        try (PreparedStatement stmnt = DbConnectionUtils.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            return stmnt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
