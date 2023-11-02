package com.example.lthdt.controller;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.NhanHieuRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.service.NhanHieuService;
import com.example.lthdt.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private NhanHieuRepository nhanHieuRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private NhanHieuService nhanHieuService;

    @GetMapping("/")
    public String getIndexPage(Model model) {
        // Get new products
        List<SanPhamDTO> newProducts = sanPhamService.getListNewProduct();
        for(SanPhamDTO sp: newProducts){
            List<LoaiSPDTO> loaiSp = loaiSPRepository.findLoaiSPtheoSanPhamID(sp.getId());
            loaiSp.sort(new Comparator<LoaiSPDTO>() {
                @Override
                public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                    return Long.compare(o1.getGia(), o2.getGia());
                }
            });
            sp.setLoaiSps(loaiSp);
        }
        model.addAttribute("products", newProducts);

        // Get best seller products
//        List<SanPhamDTO> bestSellerProducts = sanPhamService.getListBestSellerProduct();
//        model.addAttribute("bestSellerProducts", bestSellerProducts);

        return "shop/index";
    }

    @GetMapping("/san-pham")
    public String getShopPage(Model model) {
        // Get list brand
        List<NhanHieuDTO> brands = nhanHieuService.getListBrand();
        model.addAttribute("brands", brands);


        ArrayList<Integer> brandIds = new ArrayList<Integer>();
        for (NhanHieuDTO brand : brands) {
            brandIds.add(brand.getId());
        }
        model.addAttribute("brandIds", brandIds);

        System.out.println("============>listBrand " + brands.size());

        // Get list product
        FilterSPReq req = new FilterSPReq(brandIds, (long) 0, Long.MAX_VALUE, 1);
        TrangDTO result = sanPhamService.filterProduct(req);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("listProduct", result.getItems());

        return "shop/product";
    }

    @GetMapping("/api/tim-kiem")
    public String searchProduct(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) Integer page) {
        TrangDTO result = sanPhamService.searchProductByKeyword(keyword, page);

        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("listProduct", result.getItems());
        model.addAttribute("keyword", keyword);
        if (((List<?>)result.getItems()).size() > 0) {
            model.addAttribute("hasResult", true);
        } else {
            model.addAttribute("hasResult", false);
        }

        return "shop/search";
    }

    @GetMapping("/san-pham/{id}")
    public String getDetailProductPage(Model model, @PathVariable String id) {
        // Get detail info
        SanPhamDTO product;
        try {
            product = sanPhamService.getDetailProductById(id);
            System.out.println("============>spName " + product.getTen());
            System.out.println("============>spImg " + product.getImage().get(0));
            System.out.println("============>spNhanhieu " + product.getNhanHieu().getName());

            List<LoaiSPDTO> loaiSp = loaiSPRepository.findLoaiSPtheoSanPhamID(product.getId());

            loaiSp.sort(new Comparator<LoaiSPDTO>() {
                @Override
                public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                    return Long.compare(o1.getGia(), o2.getGia());
                }
            });
            product.setLoaiSps(loaiSp);
        } catch (NotFoundException ex) {
            return "error/404";
        } catch (Exception ex) {
            return "error/500";
        }
        model.addAttribute("product", product);

        return "shop/detail";
    }

    @PostMapping("/api/san-pham/loc")
    public ResponseEntity<?> filterProduct(@RequestBody FilterSPReq req) {
        // Validate
        if (req.getMinPrice() == null) {
            req.setMinPrice((long) 0);
        } else {
            if (req.getMinPrice() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mức giá phải lớn hơn 0");
            }
        }
        if (req.getMaxPrice() == null) {
            req.setMaxPrice(Long.MAX_VALUE);
        } else {
            if (req.getMaxPrice() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mức giá phải lớn hơn 0");
            }
        }

        TrangDTO result = sanPhamService.filterProduct(req);

        return ResponseEntity.ok(result);
    }

}
