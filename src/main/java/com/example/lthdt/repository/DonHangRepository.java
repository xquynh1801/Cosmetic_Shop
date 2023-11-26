package com.example.lthdt.repository;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {
    @Query(nativeQuery = true, value ="SELECT * FROM donhang where nguoidat=?1")
    public List<DonHang> findByUserId(Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM donhang WHERE donhang.trangthai = ?1 AND donhang.nguoidat = ?2")
    public List<DonHang> getListOrderOfPersonByStatus(int status, long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM donhang WHERE donhang.id = ?1 AND donhang.nguoidat = ?2")
    public DonHang getOrderOfPersonById(int id, long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE donhang SET trangthai = 4 WHERE donhang.id = ?1 AND nguoidat = ?2", nativeQuery = true)
    public int cancel(long iddonhang, Long iduser);

    @Modifying
    @Transactional
    @Query(value = "UPDATE donhang SET trangthai = ?1, modified_at=?2, modified_by=?3 WHERE id = ?4", nativeQuery = true)
    public int update(int trangthai, Timestamp modifiedAt, User modifiedBy, Long id);

    @Query(nativeQuery = true, value = "SELECT * FROM donhang dh WHERE dh.modified_at BETWEEN ? AND ? AND dh.trangthai = 3")
    List<DonHang> findOrdersByModifiedAtBetween(Date batdau, Date ketthuc);

    @Query(nativeQuery = true, value = "SELECT * FROM donhang dh WHERE dh.modified_at BETWEEN ? AND ? AND dh.trangthai = 3 AND dh.nguoidat=?")
    List<DonHang> findOrdersOfUser(Date batdau, Date ketthuc, User user);

    @Query(nativeQuery = true, value = "SELECT DISTINCT donhang.*\n" +
            "FROM donhang\n" +
            "WHERE donhang.trangthai LIKE CONCAT('%',?1,'%') \n")
    public List<DonHang> adminGetListDH(String trangthai);

}
