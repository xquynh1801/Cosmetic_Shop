package com.example.lthdt.repository;

import com.example.lthdt.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

}
