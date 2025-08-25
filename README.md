# 基隆景點瀏覽器 (前後端整合)

本專案包含：
1. **Keelung Tourist Sight API (後端, Spring Boot)**
2. **基隆景點瀏覽器 (前端, React)**

---

## ⚙️ 系統需求
- Java 17
- Node.js 16+
- npm 或 yarn

---

## 🚀 執行步驟

### 1. 啟動後端 API
1. 使用 IntelliJ IDEA 開啟 `backend/` 專案
2. 執行 `DemoApplication.java`
3. 確認 API 可用：  
   [http://localhost:8080/SightAPI?zone=七堵](http://localhost:8080/SightAPI?zone=七堵)

API 範例回傳：
```json
[
  {
    "sightName": "基隆火車站",
    "zone": "七堵區",
    "category": "交通",
    "description": "...",
    "address": "...",
    "tel": "...",
    "photoURL": "..."
  }
]
```

---

### 2. 啟動前端 React
1. 進入 `frontend/` 資料夾
   ```bash
   cd frontend
   ```
2. 安裝套件
   ```bash
   npm install
   ```
3. 啟動開發伺服器
   ```bash
   npm start
   ```
4. 瀏覽器開啟 [http://localhost:3000](http://localhost:3000) 🎉

---

## 📐 RWD 設計
- 桌機：一列三張卡片
- 平板：一列二張卡片
- 手機：一列一張卡片

---

## 📦 專案結構
```
project-root/
 ├── backend/      # Spring Boot API
 │    └── DemoApplication.java
 ├── frontend/     # React App
 │    └── src/App.js
 └── README.md     # 本文件
```

---

## 🛠️ 技術棧
- **後端**：Spring Boot 2.7.17, Java 17, Jsoup
- **前端**：React 18, Bootstrap 5

---

## 📱 手機測試 (選用)
若要在同一個 Wi-Fi 下用手機開啟：
1. 查詢電腦的內網 IP (例如 192.168.1.10)
2. 啟動後端 + 前端
3. 手機瀏覽器輸入：
   ```
   http://192.168.1.10:3000
   ```
   即可從手機端開啟 React 前端  
