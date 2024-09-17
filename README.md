
# Website E-Commerce



## Tổng quan

Dự án này là một nền tảng thương mại điện tử cho phép người dùng duyệt sản phẩm, thêm vào giỏ hàng và hoàn tất mua sắm trực tuyến. Hệ thống hỗ trợ tính năng đăng nhập/đăng ký tài khoản người dùng, đánh giá sản phẩm, tích hợp thanh toán và bảng điều khiển cho quản trị viên để quản lý sản phẩm, danh mục, đơn hàng và mở rộng hơn cùng với các tính năng độc đáo như vòng quay may mắn và hệ thống VIP.

## Demo

Link: [`DEMO`](https://phuanshop.id.vn/)

## Tính năng

### Chức năng chung
- Tạo tài khoản: Gửi mã OTP qua email để xác minh tài khoản.
- Đăng nhập: Hỗ trợ đăng nhập qua Google/Facebook.
- Khôi phục mật khẩu: Qua email.
- Xem chi tiết sản phẩm: Hiển thị thông tin chi tiết sản phẩm.
- Tìm kiếm sản phẩm: Hỗ trợ tìm kiếm và lọc kết quả.
- Giỏ hàng: Thêm, chỉnh sửa, và xóa sản phẩm.
- Bình luận & Đánh giá: Người dùng có thể để lại bình luận và đánh giá sản phẩm.
- Báo cáo bình luận vi phạm: Báo cáo các bình luận không phù hợp.
- Xem thông tin người bình luận: Xem thông tin cơ bản của người dùng để lại bình luận.
- Lịch sử mua hàng: Hiển thị lịch sử các đơn hàng đã mua.
- Thông báo: Thông báo tình trạng đơn hàng.
- Chỉnh sửa thông tin cá nhân: Cập nhật thông tin người dùng.
- Chỉnh sửa avatar: Đổi ảnh đại diện.
- Đổi khung viền: Tùy chỉnh khung viền avatar.
- Vòng quay may mắn: Nhận thưởng thông qua vòng quay may mắn.
- Mã voucher: Áp dụng voucher khi thanh toán.
- Thanh toán trực tuyến: Hỗ trợ thanh toán qua VNPay và Momo.
- Chat hỗ trợ trực tuyến: Cung cấp kênh hỗ trợ trực tuyến.
### Chức năng trang Admin
- Thống kê - Báo cáo: Xuất báo cáo dưới dạng file PDF.
- Quản lý đơn hàng: Theo dõi và xử lý đơn hàng.
- Quản lý tài khoản: Quản lý thông tin và phân quyền cho người dùng.
- Quản lý sản phẩm: Thêm, sửa, và xóa sản phẩm.
- Quản lý khuyến mãi: Thiết lập và quản lý khuyến mãi.
- Phân loại sản phẩm: Quản lý danh mục sản phẩm.
- Quản lý bình luận: Kiểm duyệt và xử lý bình luận.
- Quản lý yêu cầu: Quản lý các yêu cầu từ khách hàng.
### Quản lý tài khoản
- Admin: Cấp và xóa quyền cho các tài khoản Manager và User.
- Manager: Có thể quản lý hệ thống nhưng không thể cấp quyền cho tài khoản khác.

## Sự khác biệt

Nền tảng này có những tính năng khác biệt so với các dự án thương mại điện tử thông thường:

- Vòng quay may mắn: Người dùng có cơ hội nhận các phần thưởng như khung avatar, danh hiệu VIP, và voucher.
- Hệ thống VIP: Người dùng sẽ tích lũy điểm kinh nghiệm qua việc đăng nhập hàng ngày và mua hàng để nâng cấp VIP. Cấp VIP càng cao, ưu đãi càng lớn.
- Xác thực OTP: Sử dụng reCaptcha và gửi mã OTP qua email hoặc số điện thoại để đảm bảo an toàn.
- Thông báo chi tiết đơn hàng: Người dùng sẽ nhận được thông báo chi tiết đơn hàng qua email sau khi xác nhận mua hàng.
- Xử lý đa luồng: Hệ thống tự động chạy các tác vụ định kỳ, xóa thông báo và mã OTP đã hết hạn sau 30 ngày.

## Công nghệ sử dụng

- Ngôn ngữ: Java (Spring Boot)
- Cơ sở dữ liệu: MySQL (AWS RDS)
- Nền tảng triển khai: AWS Elastic Beanstalk
- Lưu trữ media: Cloudinary
- Bảo mật: SSL/TLS qua Cloudflare, OTP, reCaptcha
- Thanh toán trực tuyến: VNPay, Momo
- Xác thực đăng nhập: Google, Facebook

## Hướng dẫn cài đặt

### 1. Chuẩn bị môi trường
Sử dụng Java 18 (JDK-18.x.x), công cụ IDE (IntelliJ, NetBean, VSCode,...), Apache Tomcat (version 9.x.x), MySQL
### 2. Clone repository
To deploy this project run

```bash
  git clone https://github.com/ngphuan2017/WebApp.git
```
### 3. Cài đặt các phụ thuộc
- Cài đặt các thư viện phụ thuộc từ `pom.xml`
- Các cài đặt kết nối Database, Gmail, Google, Facebook, reCaptcha, VnPay, Momo, AIOv2 từ file `application.properties` đã được ẩn đi vì lý do dữ liệu cá nhân.

## Tài khoản thử nghiệm

| Quyền truy cập | Username     | Password                       |
| :-------- | :------- | :-------------------------------- |
| **Quyền quản trị (Admin)**      | `********(đã che)` | `********` |
| **Quyền quản lý (Manager)**      | `admin` | `11012001` |
| **Khách hàng**      | `ngphuan2023` | `11012001` |

Liên hệ qua [Zalo](https://zalo.me/0941622670) hoặc [Facebook](https://fb.com/11012001an) để được cấp quyền tài khoản Admin (không áp dụng cho tài khoản liên kết).

## Đóng góp

Chúng tôi hoan nghênh mọi đóng góp từ cộng đồng! Hãy fork repository và gửi pull request để được xem xét.

## Feedback

Nếu bạn muốn trao đổi thêm, hãy liên hệ qua phuan2017@gmail.com hoặc phuanshop2023@gmail.com

## Giấy phép

Đối với các dự án nguồn mở, hãy nói nó được cấp phép như thế nào.
