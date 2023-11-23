package dev.lesechko.jdbccrud.repository;

import dev.lesechko.jdbccrud.model.Post;
import dev.lesechko.jdbccrud.model.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean save(Writer writer) {
        String sql = "INSERT INTO writers (firstName, lastName, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setString(1, writer.getFirstName());
            stmnt.setString(2, writer.getLastName());
            stmnt.setString(3, writer.getStatus().name());
            if (stmnt.executeUpdate() != 0) {
                ResultSet generatedKeys = stmnt.getGeneratedKeys();
                generatedKeys.next();
                Long insertedId = generatedKeys.getLong(1);
                addWriterIdForPosts(insertedId, writer.getPosts());
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Writer> getAll() {
        return null;
    }

    @Override
    public Writer getById(Long aLong) {
        return null;
    }

    @Override
    public boolean update(Writer writer) {
        return false;
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
