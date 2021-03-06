package dao;

import util.JdbcUtil;
import vo.RegistLevel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 挂号级别管理
 * @version 0.1
 * @author Vector_Wu
 */

public class RLMDao implements IRLMDao {
    Connection con = null;

    public void setConnection(Connection con){
        this.con = con;
    }

    /**
     * 根据条件查询有效挂号级别列表
     * @param code
     * @return RegistLevelList
     * @throws SQLException
     */
    @Override
    public List<RegistLevel> SelectRegistLevel(String code) throws SQLException {
        String sql ="select ID,RegistCode,RegistName,SequenceNo,RegistFee,RegistQuota,DelMark\n" +
                "FROM registlevel \n" +
                "where (RegistCode like \"%\"?\"%\" Or RegistName like \"%\"?\"%\")\n" +
                "and DelMark = 1\n" +
                "order by SequenceNo";

        con = JdbcUtil.getConnection();
        PreparedStatement pstm= con.prepareStatement(sql);
        pstm.setString(1,code);
        pstm.setString(2,code);
        ResultSet rs = pstm.executeQuery();
        List<RegistLevel> RegistLevelList = new ArrayList<>();
        while (rs.next()){
            RegistLevel rl = new RegistLevel();
            rl.setId(rs.getInt(1));
            rl.setRegistCode(rs.getString(2));
            rl.setRegistName(rs.getString(3));
            rl.setSequenceNo(rs.getInt(4));
            rl.setRegistFree(rs.getDouble(5));
            rl.setRegistquota(rs.getInt(6));
            rl.setDelMark(rs.getInt(7));
            RegistLevelList.add(rl);
        }
        JdbcUtil.release(null,pstm,null);
        return RegistLevelList;
    }

    /**
     * 查询并新增挂号级别
     * @param Rcode
     * @param Rname
     * @param Rno
     * @param Rfee
     * @param Rquota
     * @throws SQLException
     */

    @Override
    public void AddRegistLevel(String Rcode,String Rname,int Rno,double Rfee,int Rquota) throws SQLException {
            String sql ="SELECT count(id) \n" +
                    "FROM RegistLevel\n" +
                    "where RegistCode = ?\n" +
                    "and DelMark = 1";
            String sql2="INSERT INTO RegistLevel(RegistCode,RegistName,SequenceNo,RegistFee,RegistQuota,DelMark)\n" +
                    "VALUES(?,?,?,?,?,?)";

        con = JdbcUtil.getConnection();
        PreparedStatement pstm= con.prepareStatement(sql);
        pstm.setString(1,Rcode);
        ResultSet rs = pstm.executeQuery();
        int num=0;
        while(rs.next()){
            num=rs.getInt(1);
        }
        if(num==0){
            pstm= con.prepareStatement(sql2);
            pstm.setString(1,Rcode);
            pstm.setString(2,Rname);
            pstm.setInt(3,Rno);
            pstm.setDouble(4,Rfee);
            pstm.setInt(5,Rquota);
            pstm.executeUpdate();
        }else{
            JdbcUtil.release(null,pstm,null);
        }
        JdbcUtil.release(null,pstm,null);
    }

    /**
     * 查询挂号级别
     * @param id
     * @return 查询挂号级别list
     * @throws SQLException
     */
    @Override
    public List<RegistLevel> SelectupdateRegistLevel(int id) throws SQLException {
        String sql="select ID,RegistCode,RegistName,SequenceNo,RegistFee,RegistQuota,DelMark\n" +
                "FROM RegistLevel \n" +
                "where ID=?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        List<RegistLevel> registLevelList = new ArrayList<>();
        while (rs.next()){
            RegistLevel uprl = new RegistLevel();
            uprl.setId(rs.getInt(1));
            uprl.setRegistCode(rs.getString(2));
            uprl.setRegistName(rs.getString(3));
            uprl.setSequenceNo(rs.getInt(4));
            uprl.setRegistFree(rs.getDouble(5));
            uprl.setRegistquota(rs.getInt(6));
            uprl.setDelMark(rs.getInt(7));
            registLevelList.add(uprl);
        }
        JdbcUtil.release(null,pstm,null);
        return registLevelList;
    }

    /**
     * 更新挂号级别
     * @param Rcode
     * @param Rname
     * @param Rno
     * @param Rfee
     * @param Rquota
     * @throws SQLException
     */
    @Override
    public void UpdatesaveRegistLevel(String Rcode,String Rname,int Rno,Double Rfee,int Rquota) throws SQLException {
        String sql="SELECT count(id) \n" +
                "FROM RegistLevel\n" +
                "where RegistCode = ?\n" +
                "and DelMark = 1";

        String sql2="update  RegistLevel Set RegistCode=?,RegistName=?,SequenceNo=?,RegistFee=?,RegistQuota=?\n" +
                "WHERE RegistCode = ?";

        PreparedStatement pstm= con.prepareStatement(sql);
        pstm.setString(1,Rcode);
        ResultSet rs = pstm.executeQuery();
        int num = 0;

        while(rs.next()){
            num = rs.getInt(1);

        }if (num==0){
            pstm= con.prepareStatement(sql2);
            pstm.setString(1,Rcode);
            pstm.setString(2,Rname);
            pstm.setInt(3,Rno);
            pstm.setDouble(4,Rfee);
            pstm.setInt(5,Rquota);
            pstm.executeUpdate();
        }else {
            JdbcUtil.release(null,pstm,null);
        }
        JdbcUtil.release(null,pstm,null);
    }

    /**
     * 删除挂号级别
     * @param id
     * @throws SQLException
     */
    @Override
    public void DeleteRegistLevel(int id) throws SQLException {
        String sql="update  RegistLevel\n" +
                "set DelMark = 0 \n" +
                "WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1,id);
        JdbcUtil.release(null,pstm,null);
    }
}