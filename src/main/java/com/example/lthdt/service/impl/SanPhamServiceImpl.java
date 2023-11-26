package com.example.lthdt.service.impl;

import com.example.lthdt.entity.*;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.exception.InternalServerException;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.*;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.dto.UserDTO;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.CreateProductReq;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.service.GioHangSanPhamService;
import com.example.lthdt.service.GioHangService;
import com.example.lthdt.service.LoaiSPService;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import com.example.lthdt.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private GioHangSanPhamRepository gioHangSanPhamRepository;

    @Autowired
    private SanPhamMuaRepository sanPhamMuaRepository;

    @Override
    public List<SanPhamDTO> getListNewProduct() {
        List<SanPham> sanphams = sanPhamRepository.getAllAvailable(5);
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:sanphams){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        return sp;
    }

    @Override
    public List<SanPhamDTO> getAllProduct() {
        List<SanPham> sanphams = sanPhamRepository.getAllPr();
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:sanphams){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        return sp;
    }

    @Override
    public TrangDTO filterProduct(FilterSPReq req) {
        int limit = 16;
        PageUtil page  = new PageUtil(limit, req.getPage());
        // Get list product and totalItems
        List<SanPham> products;
        products = sanPhamRepository.locSanPham(req.getBrands());

        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:products){
            SanPhamDTO tmp_sp = SanPhamMapper.toSanPhamDTO(s);

            List<LoaiSPDTO> loaiSp = loaiSPRepository.findLoaiSPtheoSanPhamIDvaKhoangGia(tmp_sp.getId(), req.getMinPrice(), req.getMaxPrice());
            if(loaiSp.size() > 0) {
                loaiSp.sort(new Comparator<LoaiSPDTO>() {
                    @Override
                    public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                        return Long.compare(o1.getGia(), o2.getGia());
                    }
                });
                tmp_sp.setLoaiSps(loaiSp);
                sp.add(tmp_sp);
            }
        }
        // Calculate total pages
        int totalPages = page.calculateTotalPage(sp.size());

        TrangDTO result = new TrangDTO(sp, totalPages, req.getPage());

        return result;
    }

    @Override
    public TrangDTO searchProductByKeyword(String keyword, Integer page) {
        // Validate
        if (keyword == null) {
            keyword = "";
        }
        if (page == null) {
            page = 1;
        }

        int limit = 15;
        PageUtil pageInfo = new PageUtil(limit, page);

        // Get list product and totalItems
        List<SanPham> products = sanPhamRepository.searchProductByKeyword(keyword, limit, pageInfo.calculateOffset());
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:products){
            SanPhamDTO tmp_sp = SanPhamMapper.toSanPhamDTO(s);
            List<LoaiSPDTO> loaiSp = loaiSPRepository.findLoaiSPtheoSanPhamID(tmp_sp.getId());
            loaiSp.sort(new Comparator<LoaiSPDTO>() {
                @Override
                public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                    return Long.compare(o1.getGia(), o2.getGia());
                }
            });
            tmp_sp.setLoaiSps(loaiSp);
            sp.add(tmp_sp);
        }

        int totalItems = sanPhamRepository.countProductByKeyword(keyword);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        TrangDTO result = new TrangDTO(sp, totalPages, page);

        return result;
    }

    @Override
    public SanPhamDTO getDetailProductById(String id) {
        // Get product info
        Optional<SanPham> result = sanPhamRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        SanPham product = result.get();

        if (!product.isAvailable()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        SanPhamDTO dto = SanPhamMapper.toSanPhamDTO(product);

        return dto;
    }

    @Override
    public String createProduct(CreateProductReq req) {
        // Validate info
//        if (req.getProductImages().size() == 0) {
//            throw new BadRequestException("Danh sách ảnh trống");
//        }

        SanPham product = SanPhamMapper.toProduct(req);
        product.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        product.setTong_ban(0);
        // Gen id
        String productId = RandomStringUtils.randomAlphanumeric(6);
        product.setId(productId);

        try {
            sanPhamRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi thêm sản phẩm");
        }

        return productId;
    }

    @Override
    public void updateProduct(String id, CreateProductReq req) {
        // Check product exist
        Optional<SanPham> rs = sanPhamRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        // Validate info
        if (req.getProductImages().size() == 0) {
            throw new BadRequestException("Danh sách ảnh trống");
        }

        SanPham product = SanPhamMapper.toProduct(req);
        product.setId(id);

        try {
            sanPhamRepository.save(product);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật thông tin sản phẩm");
        }
    }

    @Override
    public void deleteProduct(String id) {
        // Check product exist
        Optional<SanPham> rs = sanPhamRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        List<LoaiSPDTO> lsps = loaiSPRepository.findLoaiSPtheoSanPhamID(id);

        // If have order, can't delete
        int countOrder=0;
        for(LoaiSPDTO l:lsps){
            countOrder = sanPhamMuaRepository.countByLoaiSanPhamMua_Id(l.getId());
        }
        if (countOrder > 0) {
            throw new BadRequestException("Sản phẩm đã được đặt hàng không thể xóa");
        }

        try {
            for(LoaiSPDTO l:lsps){
                gioHangSanPhamRepository.deleteGioHangSanPhamsByLoaiSanPham_Id(l.getId());
            }

            loaiSPRepository.deleteByProductId(id);

            sanPhamRepository.deleteById(id);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new InternalServerException("Lỗi khi xóa sản phẩm");
        }
    }

    @Override
    public List<SanPhamDTO> getAllAvailable() {
        List<SanPham> sanPhamList = sanPhamRepository.getAllAvailable();
        List<SanPhamDTO> sanPhamDTOList = new ArrayList<>();
        for(SanPham sp:sanPhamList){
            sanPhamDTOList.add(SanPhamMapper.toSanPhamDTO(sp));
        }

        return sanPhamDTOList;
    }

    @Override
    public TrangDTO adminGetListProduct(String name, String brand) {
        int limit = 15;
        PageUtil pageInfo  = new PageUtil(limit, 1);

        List<SanPham> products = sanPhamRepository.adminGetListProduct(name, brand, limit , pageInfo.calculateOffset());
        int totalItems = sanPhamRepository.countAdminGetListProduct(name, brand);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        return new TrangDTO(products, totalPages, pageInfo.getPage());
    }

}