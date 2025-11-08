# 🧠 Smart Contact Manager  
A secure cloud-based contact management system

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.3-green)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue)
![Redis](https://img.shields.io/badge/Cache-Redis-red)
![Render](https://img.shields.io/badge/Deployed%20on-Render-purple)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## 💡 Overview
Smart Contact Manager is your personal hub for managing, organizing, and securing your contacts — powered by modern web technologies and cloud integration.  
It offers seamless OAuth login, email notifications, and secure storage for your data using PostgreSQL on Supabase.

---

## ✨ Features
- 🔐 **Secure Authentication** with Spring Security (Email, Google & GitHub OAuth2)
- ☁️ **Cloud Image Uploads** via Cloudinary
- 💾 **Caching with Redis** for fast data access
- 📧 **Email Notifications** using SMTP
- 🧭 **Responsive UI** built with Thymeleaf, Tailwind CSS & JavaScript
- 🗄️ **PostgreSQL on Supabase** for persistent storage
- 🐳 **Docker and Render** deployment support
- 🧰 Easy to configure via `.env` variables

---

## 🧰 Tech Stack

| Layer | Technologies |
|-------|---------------|
| **Frontend** | Thymeleaf, HTML, Tailwind CSS, JavaScript |
| **Backend** | Java 21, Spring Boot 3.5.3 |
| **Database** | PostgreSQL (Supabase) |
| **Cache** | Redis |
| **Auth** | Spring Security, Google & GitHub OAuth |
| **Cloud Storage** | Cloudinary |
| **Email Service** | Gmail SMTP |
| **Deployment** | Render, Docker |

---

## 🔑 Environment Variables

| Variable | Description | 
|-----------|--------------|
| **DB_URL** | PostgreSQL database URL | 
| **DB_USERNAME** | Database username | 
| **DB_PASSWORD** | Database password | 
| **DEFAULT_PIC** | Default profile picture URL |
| **GOOGLE_ID** | Google OAuth client ID |
| **GOOGLE_SECRET** | Google OAuth client secret | 
| **GITHUB_KEY** | GitHub OAuth client ID | 
| **GITHUB_SECRET** | GitHub OAuth client secret | 
| **CLOUDINARY_NAME** | Cloudinary cloud name | 
| **CLOUDINARY_KEY** | Cloudinary API key | 
| **CLOUDINARY_SECRET** | Cloudinary API secret |
| **EMAIL** | Sender email address | 
| **EMAIL_PASSWORD** | Gmail app password |


---

## 🧩 Installation

### Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL (Supabase or Local)
- Redis Server
- Cloudinary Account
- Gmail App Password

### Steps
```bash
# Clone repository
git clone https://github.com/pulkitarora12/scm.git
cd scm

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run

```

Access the app ➜ http://localhost:8080
