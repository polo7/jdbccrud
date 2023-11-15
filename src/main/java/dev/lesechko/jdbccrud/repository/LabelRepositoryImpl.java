package dev.lesechko.jdbccrud.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import dev.lesechko.jdbccrud.model.Label;
import dev.lesechko.jdbccrud.model.Status;


public class LabelRepositoryImpl implements LabelRepository {
    private static final String DB_COL_ID = "id";
    private static final String DB_COL_NAME = "name";
    private static final String DB_COL_STATUS = "status";

    @Override
    public boolean save(Label label) {
        try (Statement stmnt = DbConnection.getConnection().createStatement()) {
            String sql = """
                    INSERT INTO labels (%s, %s) 
                    VALUES ('%s', '%s')
                    """.formatted(DB_COL_NAME, DB_COL_STATUS, label.getName(), label.getStatus().name());
            return stmnt.executeUpdate(sql) != 0;
        } catch (SQLException | NullPointerException e) {
            return false;
        }
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = new LinkedList<>();
        String sql = "SELECT * FROM labels";
        try (Statement stmnt = DbConnection.getConnection().createStatement()) {
            ResultSet rs = stmnt.executeQuery(sql);
            while (rs.next()) {
                Label label = new Label();
                label.setId(rs.getInt(DB_COL_ID));
                label.setName(rs.getString(DB_COL_NAME));
                label.setStatus(Status.valueOf(rs.getString(DB_COL_STATUS)));
                labels.add(label);
            }
            return labels;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Label getById(Integer id) {
        Label label = null;
        String sql = "SELECT * FROM labels WHERE id=" + id;
        try (Statement stmnt = DbConnection.getConnection().createStatement()) {
            ResultSet rs = stmnt.executeQuery(sql);
            if (rs.next()) {
                label = new Label();
                label.setId(rs.getInt(DB_COL_ID));
                label.setName(rs.getString(DB_COL_NAME));
                label.setStatus(Status.valueOf(rs.getString(DB_COL_STATUS)));
            }
            return label;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Label label) {
        String sql = """
                UPDATE labels
                SET %s = '%s', %s = '%s'
                WHERE %s = %s
                """.formatted(
                        DB_COL_NAME, label.getName(), DB_COL_STATUS, label.getStatus(),
                        DB_COL_ID, label.getId());
        try (Statement stmnt = DbConnection.getConnection().createStatement()) {
            return stmnt.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM labels WHERE id=" + id; // or SQL UPDATE + update STATUS -> DELETED ?
        try (Statement stmnt = DbConnection.getConnection().createStatement()) {
            return stmnt.executeUpdate(sql) != 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
