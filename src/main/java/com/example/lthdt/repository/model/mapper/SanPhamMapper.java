package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.model.dto.SanPhamDTO;

public class SanPhamMapper {
//    public static DetailSPDTO toDetailSPDTO(SanPham product) {
//        DetailSPDTO rs = new DetailSPDTO();
//        rs.setId(product.getId());
//        rs.setNhanHieu(product.getNhanHieu());
//        rs.setMota(product.getMota());
//        rs.setTen(product.getTen());
//        rs.setSlug(product.getSlug());
//        rs.setProductImages(product.getProductImages());
//        rs.setTong_ban(product.getTong_ban());
//
//        return rs;
//    }

    public static SanPhamDTO toSanPhamDTO(SanPham sanPham){
        return new SanPhamDTO(
                sanPham.getId(),
                sanPham.getTen(),
                sanPham.getSlug(),
                sanPham.getTong_ban(),
                sanPham.getProductImages(),
                sanPham.getMota(),
                sanPham.getNhanHieu());
    }

//    public static Product toProduct(CreateProductReq req) {
//        Product product = new Product();
//        product.setName(req.getName());
//        product.setDescription(req.getDescription());
//        product.setPrice(req.getPrice());
//        product.setAvailable(req.isAvailable());
//        product.setProductImages(req.getProductImages());
//        // Gen slug
//        Slugify slg = new Slugify();
//        product.setSlug(slg.slugify(req.getName()));
//        // Set brand
//        Brand brand = new Brand();
//        brand.setId(req.getBrandId());
//        product.setBrand(brand);
//        // Set category
//        ArrayList<Category> categories = new ArrayList<Category>();
//        for (Integer id : req.getCategoryIds()) {
//            Category category = new Category();
//            category.setId(id);
//            categories.add(category);
//        }
//        product.setCategories(categories);
//
//        return product;
//    }
}
