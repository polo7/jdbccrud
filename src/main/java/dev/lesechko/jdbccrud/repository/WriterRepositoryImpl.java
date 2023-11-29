package dev.lesechko.jdbccrud.repository;

import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Status;
import dev.lesechko.jdbccrud.model.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {
    private void addWriterIdForPosts(Long writerId, List<Post> posts) throws SQLException {
        String sql = "UPDATE posts SET writerId = ? WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            for (var post : posts) {
                stmnt.setLong(1, writerId);
                stmnt.setLong(2, post.getId());
                stmnt.addBatch();
            }
            stmnt.executeBatch();
        }
    }

    @Override
    public Writer save(Writer writer) {
        String sql = "INSERT INTO writers (firstName, lastName, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setString(1, writer.getFirstName());
            stmnt.setString(2, writer.getLastName());
            stmnt.setString(3, writer.getStatus().name());
            if (stmnt.executeUpdate() == 0) return null;
            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            generatedKeys.next();
            Long insertedId = generatedKeys.getLong(1);
            addWriterIdForPosts(insertedId, writer.getPosts());
            writer.setId(insertedId);
            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        String sql = "SELECT id, firstName, lastName, status FROM writers";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Writer writer = new Writer();
                writer.setId(rs.getLong("id"));
                writer.setFirstName(rs.getString("firstName"));
                writer.setLastName(rs.getString("lastName"));
                writer.setStatus(Status.valueOf(rs.getString("status")));
                writers.add(writer);
            }
            return writers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Writer getById(Long id) {
        Writer writer = null;
        String sql = """
                SELECT * FROM writers 
                LEFT JOIN posts ON writers.id = posts.writerId 
                WHERE writers.id = ?""";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                writer = new Writer();
                writer.setId(rs.getLong("id"));
                writer.setFirstName(rs.getString("firstName"));
                writer.setLastName(rs.getString("lastName"));
                writer.setStatus(Status.valueOf(rs.getString("status")));
                List<Post> posts = new ArrayList<>();
                do {
                    if (rs.getString("title") != null) {
                        Post post = new Post();
                        post.setId(rs.getLong(5));
                        post.setTitle(rs.getString("title"));
                        posts.add(post);
                    }
                } while (rs.next());
                writer.setPosts(posts);
            }
            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Writer update(Writer writer) {
        String sqlClearWriterPosts = "UPDATE posts SET writerId = NULL WHERE writerId = ?";
        try (PreparedStatement stmntClearWriterPosts = DbConnection.getPreparedStatement(sqlClearWriterPosts)) {
            // 1. очищаем автора по всех постах, где он есть
            stmntClearWriterPosts.setLong(1, writer.getId());
            stmntClearWriterPosts.executeUpdate();
            // 2. открываем список его новых постов и прописываем автора туда
            addWriterIdForPosts(writer.getId(), writer.getPosts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 3. обновляем самого автора
        String sql = "UPDATE writers SET firstName = ?, lastName = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setString(1, writer.getFirstName());
            stmnt.setString(2, writer.getLastName());
            stmnt.setString(3, writer.getStatus().name());
            stmnt.setLong(4, writer.getId());
            if (stmnt.executeUpdate() == 0) return null;
            return writer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "UPDATE writers SET status = 'DELETED' WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            return stmnt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
