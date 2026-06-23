<div align="center">

# 🎓 
<img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=900&size=70&pause=1000&color=007EC6&center=true&vCenter=true&width=800&height=150&lines=LMS+PROJECT;Learning+Management+System" alt="LMS PROJECT" />

<p align="center">
  <strong>JAVA와 JDBC를 활용한 안정적인 학사 관리 시스템</strong><br>
  데이터베이스 설계부터 효율적인 CRUD 구현까지 집중한 프로젝트입니다.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/JDBC-007396?style=for-the-badge&logo=java&logoColor=white">
</p>

<br>
</div>

---

## 🚀 Overview
> **학사 정보 관리의 효율성을 극대화하기 위한 LMS 솔루션입니다.** > 단순히 데이터를 저장하는 것을 넘어, 객체지향적 설계와 정규화된 DB 구조를 통해 실제 운영 가능한 수준의 백엔드 로직을 지향합니다.

---

## 🛠 Tech Stack

### **Language & Library**
* <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white"> `Java 17`
* <img src="https://img.shields.io/badge/JDBC-007396?style=flat-square"> `Java Database Connectivity`

### **Database**
* <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white"> `MySQL 8.0`

### **Development Tool**
* <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat-square&logo=intellij-idea&logoColor=white">
* <img src="https://img.shields.io/badge/DataGrip-000000?style=flat-square&logo=datagrip&logoColor=white">

---

## 🌟 Key Features

| 기능 | 설명 |
| :--- | :--- |
| **🔐 User System** | 학생, 교수, 관리자 권한별 차등 로그인 및 개인정보 관리 |
| **📚 Class Management** | 강의 등록, 수정, 삭제 및 JDBC Transaction을 이용한 수강 신청 |
| **📊 Score System** | 성적 입력 및 성적표 자동 생성 (GPA 계산 로직 포함) |
| **📅 Attendance** | 주차별 출석 상태 체크 및 실시간 DB 동기화 |

---

## 🏗 System Architecture

```mermaid
graph LR
    A[Java Application] -- JDBC Driver --> B[(MySQL Database)]
    subgraph "Logic Layer"
    A --> C[Service]
    C --> D[DAO]
    end
    subgraph "Data Layer"
    D --> B
    end
