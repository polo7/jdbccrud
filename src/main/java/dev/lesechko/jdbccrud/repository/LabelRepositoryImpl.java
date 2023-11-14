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
        return null;
    }

    @Override
    public boolean save(Label label) {
        String sql = "SELECT * FROM labels";
        try (Statement stmnt = DbConnection.getConnection()
                .createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
        ) {
            ResultSet rs = stmnt.executeQuery(sql);
            rs.moveToInsertRow();
            rs.updateString(DB_COL_NAME, label.getName());
            rs.updateString(DB_COL_STATUS, label.getStatus().name());
            rs.insertRow();
            rs.moveToCurrentRow();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Label label) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }
}
