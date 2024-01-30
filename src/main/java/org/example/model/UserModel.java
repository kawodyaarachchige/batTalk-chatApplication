package org.example.model;

import org.example.db.DbConnection;
import org.example.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean saveUser(UserDTO userDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        //String sql = "INSERT INTO user VALUES (?,?, AES_ENCRYPT(?, '43ad-8c7a-603b'),?)";
        String sql = "INSERT INTO user VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, userDto.getEmail());
        pstm.setString(2, userDto.getName());
        pstm.setString(3, userDto.getPassword());
        pstm.setBytes(4, userDto.getProfilePic());
        return pstm.executeUpdate() > 0;
    }
    public static UserDTO getUser(String email, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        //String sql = "SELECT (email,name,CONVERT(AES_DECRYPT(password,'43ad-8c7a-603b') USING utf8)AS decrypted_password,profile_pic) FROM user WHERE email = ? AND password = ?";
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, email);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            return new UserDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getBytes(4)
            );
        }return null;
    }
    public static UserDTO getUser(String email) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT * FROM User WHERE email=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,email);
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            return new UserDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getBytes(4)
            );
        }return null;
    }
    public static boolean updateUserPassword(String email,String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="UPDATE User SET password=? WHERE email=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,password);
        pstm.setString(2,email);
        return pstm.executeUpdate()>0;
    }
}
