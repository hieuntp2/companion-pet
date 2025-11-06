# Companion Pet Android (Phase 1)

Dự án này khởi tạo bộ khung cho ứng dụng Android-first theo tài liệu thiết kế "Nghe – Nhìn – Học". Mục tiêu của nhánh là thiết lập cấu trúc Jetpack Compose, các `ViewModel` giả lập, cùng với luồng UI chính để chứng minh kiến trúc nghe/nhìn/học chạy song song ngay trên thiết bị.

## Thành phần chính

- **UI Compose**: màn hình tổng quan với các khu vực kết nối Bluetooth, preview camera, push-to-talk và khu vực ghi nhớ.
- **Audio / Vision / Behavior / Memory Modules**: mỗi module có `ViewModel` và trạng thái UI tương ứng để dễ dàng thay thế bằng triển khai thực tế (ASR, CameraX, Planner,...).
- **Bluetooth Bridge Placeholder**: mô phỏng hành vi kết nối HC-05 phục vụ kiểm thử giao diện.

## Xây dựng

Dự án sử dụng Android Gradle Plugin 8.5.2 và Kotlin 2.0.20. Sau khi cài đặt Android Studio (Giraffe+), mở thư mục `companion-pet` và đồng bộ Gradle để bắt đầu phát triển.

```bash
./gradlew tasks
```

Các dependency quan trọng đã được khai báo cho CameraX, ML Kit (face/object), Room và DataStore để phục vụ các bước triển khai tiếp theo.
