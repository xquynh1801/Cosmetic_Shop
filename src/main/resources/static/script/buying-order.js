$(function () {
  changeUi()
})

function changeUi() {
  $('.nav-sidebar .buying-order').addClass('active');
  $(".nav-link#account-setting").addClass('active-nav');
}

$('.order-status').click(function() {
    let status = $(this).data('status');
    $.ajax({
        url: '/api/get-order-list?status='+status,
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            $('.nav-link.active').toggleClass("active");
            $('.order-status[data-status='+status+']').toggleClass("active");
            showListOrder(data);
            formatMoney();
        },
        error: function(data) {
            toastr.warning(data.responseJSON.message);
        }
    });
})

function showListOrder(data) {
    let html = '';
    for (item of data) {
        console.log(item);
        html += `
                             <div class="item">
                                <div class="header-item">
                                    <div class="order-number-wrapper">
                                        Mã đơn <a class="order-number" href="/tai-khoan/lich-su-giao-dich/${item.id}">#${item.id}</a>
                                    </div>
                                    <a class="full-details" href="/tai-khoan/lich-su-giao-dich/${item.id}">Xem chi tiết</a>
                                </div>
                                <div class="item-info-wrapper">
                                    <div class="product-info-wrapper">
                                        <div class="product-info"><span class="name">${item.tenNguoiNhan}</span></div>
                                    </div>
                                    <div>
                                        <p class="price">
                                            <span class="text-price">${item.tongtra}</span> &#x20AB;
                                        </p>
                                    </div>
                                    <div>
                                        <div><span>${item.createdAt}</span></div>
                                    </div>
                                </div>
                            </div>
                            <div class="item-small">
                                <div class="order-number-wrapper">
                                    Mã đơn <a class="order-number" href="/tai-khoan/lich-su-giao-dich/${item.id}">#${item.id}</a>
                                </div>
                                <div class="item-info-wrapper">
                                    <div class="product-info-wrapper">
                                        <div class="product-info">
                                            <a class="name" href="/tai-khoan/lich-su-giao-dich/${item.id}">${item.tenNguoiNhan}</a>
                                        </div>
                                    </div>
                                    <div class="product-info-wrapper">
                                        <div class="product-info"><span>${item.createdAt}</span></div>
                                    </div>
                                </div>
                            </div>
        `
    }
    $('#list-order').html(html);
}

// $('.pay-btn').click(function() {
//     let orderId = $(this).data("order");
//     $.ajax({
//         url: '/api/pay/'+orderId,
//         type: 'GET',
//         contentType: "application/json; charset=utf-8",
//         success: function(data) {
//             console.log(data);
//             location.href = data;
//         },
//         error: function(data) {
//             toastr.warning(data.responseJSON.message);
//         }
//     });
// })

$('.cancel-btn').click(function() {
    let orderId = $(this).data("order");
    $.ajax({
            url: '/api/cancel-order/'+orderId,
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            success: function(data) {
                toastr.success("Hủy đơn hàng thành công");
                $('.status').html("Đã hủy");
                $('.cancel-btn').remove();
                window.location.href = "/tai-khoan/lich-su-giao-dich";
            },
            error: function(data) {
                toastr.warning(data.responseJSON.message);
            }
    });
})