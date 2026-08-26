# LibraryOOP# Personal Library Tracker

![Java](https://img.shields.io/badge/Java-17+-orange) ![MIT License](https://img.shields.io/badge/License-MIT-green)

---

## 📌 Sürüm Bilgisi | Version Info  
 ● Current Version: 2.3.0
 ● Geçerli Sürüm: 2.3.0

---
 
| Language / Dil | Status / Durum |
|---------------|----------------|
| English | Coming Soon |
| Türkçe | ✅ |

---

## 🇬🇧 English

This project is a **console-based personal library tracking system** developed using Java and Object-Oriented Programming (OOP) principles.  
Multiple users can register and log into the system, add books they have read, and view their personal reading lists.

Each user manages their own books independently, and all data is stored persistently using a JSON file.  
Whenever a book is added, the system automatically saves the updated data.

The project focuses on practicing:

- Object-Oriented Programming (OOP)
- Class responsibility separation
- JSON-based data persistence
- Console-based application flow

---

# ✨ Features

- 👤 User registration system  
- 🔐 Secure login mechanism 
- 🔐 Password requirements
- 📚 Personal book list for each user  
- ➕ Add books with title and author  
- 📖 View saved books anytime after login  
- 💾 Persistent data storage using JSON  
- 🗂 Automatic JSON file creation if it does not exist  
- 🧱 Object-Oriented design (Library, User, Book classes)

---

# 🚀 Usage

## 🟢 macOS / Linux

### Compile
```bash
javac -cp ".:lib/json.jar" Codes/*.java
```
### Run
```bash
java -cp ".:lib/json.jar:Codes" MainLibrary
```

## 🟢 Windows

### Compile
```bash
javbash
javac -cp ".;lib/json.jar" Codes\*.java
```
### Run
```bash
java -cp ".;lib/json.jar;Codes" MainLibrary
```

---

## 🇹🇷 Türkçe

Bu proje, Java ve Nesne Yönelimli Programlama (OOP) prensipleri kullanılarak geliştirilmiş **konsol tabanlı kişisel kütüphane takip sistemidir**.  
Birden fazla kullanıcı sisteme kayıt olabilir, giriş yapabilir ve okudukları kitapları kendi hesaplarına ekleyebilir.

Her kullanıcı kendi kitap listesini bağımsız olarak yönetir ve tüm veriler JSON dosyası kullanılarak kalıcı olarak saklanır.  
Her kitap eklendiğinde sistem otomatik olarak verileri JSON dosyasına kaydeder.

Projenin amacı:

- OOP tasarım pratiği yapmak
- Sınıf sorumluluklarını doğru ayırmak
- JSON ile veri kalıcılığı sağlamak
- Konsol tabanlı uygulama mantığını öğrenmek

---

## ✨ Özellikler

- 👤 Kullanıcı kayıt sistemi  
- 🔐 Güvenli giriş mekanizması  
- 🔐 Şifre gereklilikleri
- 📚 Her kullanıcı için kişisel kitap listesi  
- ➕ Kitap adı ve yazar bilgisi ekleme  
- 📖 Giriş yaptıktan sonra kayıtlı kitapları görüntüleme  
- 💾 JSON ile kalıcı veri saklama  
- 🗂 JSON dosyası yoksa otomatik oluşturma  
- 🧱 Nesne Yönelimli Programlama tasarımı (Library, User, Book sınıfları)

---

## 🟢 macOS / Linux

### Derleme
```bash
javac -cp ".:lib/json.jar" Codes/*.java
```
### Çalıştırma
```bash
java -cp ".:lib/json.jar:Codes" MainLibrary
```

## 🟢 Windows

### Derleme
```bash
javbash
javac -cp ".;lib/json.jar" Codes\*.java
```
### Çalıştırma
```bash
java -cp ".;lib/json.jar;Codes" MainLibrary
```

---

## 📌 Version History / Sürüm Geçmişi

| Version | Date       | Description |
|--------:|------------|-------------|
| v2.3.0  | 2026-08-26 | Input manager and comment lines have been created | Girdi yönetimi ve yorum satırları oluşturuldu |
| v2.2.0  | 2026-08-25 | Library system has been created. / Kütüphane sistemi oluşturuldu |
| v2.1.0  | 2026-08-22 | Loan class has been created. Borrow and return dates have been logged / Loan class'ı oluşturuldu. Ödünç alma-verme tarihleri loglanmaya başlandı |
| v2.0.0  | 2026-08-21 | Redesigned the library and bookshelf system. Added ReadingRecord, LibraryBook, reading status, book source tracking and Borrowable interface / Kütüphane ve kitaplık sistemi yeniden tasarlandı. ReadingRecord, LibraryBook, okuma durumu, kitap kaynağı takibi ve Borrowable interface’i eklendi. |
| v1.3.0  | 2026-08-04 | Added category, reading status, and rating information for books / Kitaplar için kategori, okuma durumu ve puanlama eklendi |
| v1.2.2  | 2026-08-03 | Bug has been fixed in capitalize words function / Capitalize word fonksiyonundaki bug düzeltildi |
| v1.2.1  | 2026-07-22 | Reading list menu has been improved / Okuma listesi menüsü iyileştirildi |
| v1.2.0  | 2026-07-20 | Refactored the project structure by separating menu handling and username/password validation into dedicated classes / Proje yapısı yeniden düzenlendi, menü yönetimi ile kullanıcı adı ve şifre doğrulama işlemleri ayrı sınıflara taşındı |
| v1.1.0  | 2026-02-22 | Password requirements have been created / Şifre gereksinimleri eklendi |
| v1.0.3  | 2026-02-21 | Min password length requirement has been added / Minimum şifre uzunluğu gerekliliği eklendi |
| v1.0.2  | 2026-02-18 | Automatically capitalize the first letter of book and author names / Kitap ve yazar adlarının ilk harflerini otomatik büyütme eklendi |
| v1.0.1  | 2026-02-17 | Checking password method has been created / Parola kontrolü metodu oluşturuldu |
| v1.0.0  | 2026-02-14 | Initial release / İlk sürüm |

---