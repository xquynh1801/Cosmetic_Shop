package com.example.lthdt.service;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.HoaDon;
import com.example.lthdt.repository.DonHangRepository;
import com.example.lthdt.repository.HoaDonRepository;
import com.example.lthdt.util.vnpay.VnpayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentVNPayService {
    private final String srcReq = "";
    private final String vnp_orderType = "other";
    private final String vnp_Version = "2.1.0";
    private final String vnp_currencyCode = "VND";
    private final String vnp_command = "querydr";
    private final String locate = "vn";
    private final String timeZoneVN = "Etc/GMT+7";
    private final String IpAddr = "192.168.16.104";

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private DonHangRepository donHangRepository;

    public HoaDon addHoaDon(HoaDon hoaDon){
        return hoaDonRepository.save(hoaDon);
    }

    public DonHang updateOrderPaymentStatus(Long orderId){
        DonHang order = donHangRepository.getOne(orderId);
        order.setIsPaid(1);
        return donHangRepository.save(order);
    }

    public String genUrl(Long amount, String description){

        Map<String, String> vnp_Params = new HashMap<>();

        // Call API
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_command);
        vnp_Params.put("vnp_TmnCode", VnpayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", vnp_currencyCode);
//        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", VnpayConfig.getRandomNumber(8));
        vnp_Params.put("vnp_OrderInfo", description);
        vnp_Params.put("vnp_OrderType", vnp_orderType);
        vnp_Params.put("vnp_Locale", locate);
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_Returnurl);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone(timeZoneVN));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        vnp_Params.put("vnp_IpAddr", IpAddr);

        // Add Params of 2.1.0 Version
        try {
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
            return paymentUrl;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
