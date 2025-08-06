
# Keelung Tourist Sight API (HW2)

這是一個使用 Spring Boot 製作的簡易 API，能夠根據區域（zone）查詢基隆的旅遊景點。

## ✅ 執行方式

1. 使用 IntelliJ 開啟專案。
2. 執行 `DemoApplication.java`。
3. 在瀏覽器開啟以下網址，即可取得指定區域的景點資訊：

```
http://localhost:8080/SightAPI?zone=七堵
```

## 🧪 範例輸出

```json
[
  {
    "name": "基隆火車站",
    "zone": "七堵",
    "category": "交通",
    "description": "...",
    "address": "...",
    "tel": "..."
  },
  ...
]
```

## 🧩 技術細節

- Spring Boot 2.7.17
- Java 17
- Jsoup 1.21.1
- API 格式：`GET /SightAPI?zone={區域}`

## 🛠️ 建立者

Eric Chang
