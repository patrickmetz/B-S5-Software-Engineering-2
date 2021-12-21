package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dtos.JobAdvertisementDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.JobAdvertisementDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobAdvertisementDAO {

    public void setJobAdvertisementToUser(int userId, int advertisementId) throws DatabaseLayerException {
        String query = "INSERT INTO carlook.user_to_job_advertisement VALUES (?, ?)";

        try {
            PreparedStatement ps = null;
            try {
                ps = JDBCConnection.getInstance().getPreparedStatement(query);
                ps.setInt(1, userId);
                ps.setInt(2, advertisementId);
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }
            assert ps != null;
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl! Bitte den Programmier benachrichtigen!");
        }
    }

    public List<JobAdvertisementDTO> getJobAdvertisementsOfUser(UserDTO userDTO) throws DatabaseLayerException {
        ResultSet set = null;

        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            if (statement != null) {
                set = statement.executeQuery(
                        "SELECT * "
                                + "FROM carlook.user_to_job_advertisment "
                                + "WHERE carlook.user_to_rolle.userid = '" + userDTO.getId() + "'");
            }

        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl! Bitte den Programmier benachrichtigen!");
        }

        if (set == null) return null;

        List<JobAdvertisementDTO> liste = new ArrayList<JobAdvertisementDTO>();
        JobAdvertisementDTOImpl dto = null;

        try {
            while (set.next()) {
                dto = new JobAdvertisementDTOImpl();

                // fetch id of job advertisement, i.e. the 2nd integer column
                // of table "user_to_job_advertisement"
                dto.setId(set.getInt(2));
                liste.add(dto);
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl! Bitte den Programmier benachrichtigen!");
        }
        return liste;
    }
}
