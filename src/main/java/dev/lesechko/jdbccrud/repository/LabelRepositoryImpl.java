package dev.lesechko.jdbccrud.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Status;


public class LabelRepositoryImpl implements LabelRepository {
    @Override
    public Label save(Label label) {
        String sql = "INSERT INTO labels (name, status) VALUES (?, ?)";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setString(1, label.getName());
            stmnt.setString(2, label.getStatus().name());
            if (stmnt.executeUpdate() == 0) return null;
            ResultSet generatedKeys = stmnt.getGeneratedKeys();
            generatedKeys.next();
            label.setId(generatedKeys.getLong(1));
            return label;
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = new LinkedList<>();
        String sql = "SELECT * FROM labels";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                Label label = new Label();
                label.setId(rs.getLong("id"));
                label.setName(rs.getString("name"));
                label.setStatus(Status.valueOf(rs.getString("status")));
                labels.add(label);
            }
            return labels;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Label getById(Long id) {
        Label label = null;
        String sql = "SELECT * FROM labels WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                label = new Label();
                label.setId(rs.getLong("id"));
                label.setName(rs.getString("name"));
                label.setStatus(Status.valueOf(rs.getString("status")));
            }
            return label;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Label update(Label label) {
        String sql = "UPDATE labels SET name = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setString(1, label.getName());
            stmnt.setString(2, label.getStatus().name());
            stmnt.setLong(3, label.getId());
            if (stmnt.executeUpdate() == 0) return null;
            return label;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
//        String sql = "DELETE FROM labels WHERE id = ?";
        String sql = "UPDATE labels SET status = 'DELETED' WHERE id = ?";
        try (PreparedStatement stmnt = DbConnection.getPreparedStatement(sql)) {
            stmnt.setLong(1, id);
            return stmnt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
