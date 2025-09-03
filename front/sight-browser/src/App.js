import React, { useState } from "react";

function App() {
  const zones = ["中山區", "信義區", "仁愛區", "中正區", "安樂區", "七堵區", "暖暖區"];
  const [sights, setSights] = useState([]);
  const [loading, setLoading] = useState(false);
  const [selectedSight, setSelectedSight] = useState(null); // 用 Modal 顯示詳細

  const fetchSights = async (zone) => {
    setLoading(true);
    try {
      const res = await fetch(`/SightAPI?zone=${zone}`);
      if (!res.ok) throw new Error("API 錯誤");
      const data = await res.json();
      setSights(data);
    } catch (err) {
      console.error(err);
      alert("取得資料失敗");
    } finally {
      setLoading(false);
    }
  };

  const openDetail = (sight) => setSelectedSight(sight);
  const closeDetail = () => setSelectedSight(null);

  // Modal 內聯樣式（不需額外 CSS 檔）
  const overlayStyle = {
    position: "fixed",
    inset: 0,
    background: "rgba(0,0,0,0.5)",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    padding: "16px",
    zIndex: 1050,
  };
  const modalStyle = {
    background: "#fff",
    borderRadius: "12px",
    maxWidth: "720px",
    width: "100%",
    maxHeight: "85vh",
    overflow: "auto",
    boxShadow: "0 10px 30px rgba(0,0,0,0.2)",
  };

  return (
    <div className="container my-4">
      <h1 className="text-center mb-4">基隆景點瀏覽器</h1>

      {/* 區域按鈕 */}
      <div className="d-flex flex-wrap justify-content-center mb-4">
        {zones.map((z) => (
          <button
            key={z}
            className="btn btn-primary m-2"
            onClick={() => fetchSights(z)}
          >
            {z}
          </button>
        ))}
      </div>

      {loading && <p className="text-center">資料載入中...</p>}

      {/* 景點卡片 (CSS Grid RWD) */}
      <div
        className="d-grid gap-4"
        style={{
          gridTemplateColumns: "repeat(auto-fit, minmax(300px, 1fr))", // 手機 1 張、桌機 3 張
          alignItems: "start", // 每張卡片高度獨立
        }}
      >
        {sights.map((sight, index) => {
          const key = sight.id || sight.sightName || `sight-${index}`;
          return (
            <div className="card shadow-sm" key={key}>
              <div className="card-body">
                <h5 className="card-title fs-5 fw-bold">{sight.sightName}</h5>
                <hr />
                <p className="card-text mb-3">
                  <strong>區域：</strong>{sight.zone}<br />
                  <strong>分類：</strong>{sight.category}
                </p>
                <div className="d-flex flex-wrap gap-2">
                  <a
                    href={`https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(sight.address || sight.sightName || "")}`}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="btn btn-outline-success btn-sm"
                  >
                    地址
                  </a>
                  <button
                    className="btn btn-outline-info btn-sm"
                    onClick={() => openDetail(sight)}
                  >
                    詳細資訊
                  </button>
                </div>
              </div>
            </div>
          );
        })}
      </div>

      {/* 詳細資訊：自製 Modal（不影響版面高度） */}
      {selectedSight && (
        <div style={overlayStyle} role="dialog" aria-modal="true" onClick={closeDetail}>
          <div style={modalStyle} className="card" onClick={(e) => e.stopPropagation()}>
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-start">
                <h5 className="card-title fs-5 fw-bold mb-3">{selectedSight.sightName}</h5>
                <button className="btn btn-sm btn-outline-secondary" onClick={closeDetail}>
                  關閉
                </button>
              </div>

              {selectedSight.photoURL && (
                <img
                  src={selectedSight.photoURL}
                  alt={selectedSight.sightName}
                  className="img-fluid rounded mb-3"
                  onError={(e) => { e.currentTarget.style.display = "none"; }}
                />
              )}

              <p className="mb-2">
                <strong>區域：</strong>{selectedSight.zone}　
                <strong>分類：</strong>{selectedSight.category}
              </p>
              {selectedSight.address && (
                <p className="mb-3">
                  <strong>地址：</strong>
                  <a
                    href={`https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(selectedSight.address)}`}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    {selectedSight.address}
                  </a>
                </p>
              )}
              {selectedSight.description && <p className="mb-0">{selectedSight.description}</p>}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
