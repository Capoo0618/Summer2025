import React, { useState } from "react";

function App() {
  const zones = ["中山區", "信義區", "仁愛區", "中正區", "安樂區", "七堵區", "暖暖區"];
  const [sights, setSights] = useState([]);
  const [loading, setLoading] = useState(false);

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

      {/* 景點卡片 */}
      <div className="row">
        {sights.map((sight, i) => (
          <div className="col-12 col-md-4 mb-4" key={i}>
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">{sight.sightName}</h5>
                <p className="card-text">
                  <strong>區域：</strong>{sight.zone}<br />
                  <strong>分類：</strong>{sight.category}
                </p>
                <a
                  href={`https://www.google.com/maps/search/?api=1&query=${sight.address}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="btn btn-outline-success btn-sm me-2"
                >
                  地址
                </a>
                <button
                  className="btn btn-outline-info btn-sm"
                  data-bs-toggle="collapse"
                  data-bs-target={`#detail-${i}`}
                >
                  詳細資訊
                </button>
                <div className="collapse mt-2" id={`detail-${i}`}>
                  <img
                    src={sight.photoURL}
                    alt={sight.sightName}
                    className="img-fluid rounded mb-2"
                  />
                  <p>{sight.description}</p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App;
